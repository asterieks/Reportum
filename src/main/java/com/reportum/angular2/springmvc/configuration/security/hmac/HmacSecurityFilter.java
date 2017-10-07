package com.reportum.angular2.springmvc.configuration.security.hmac;

import com.reportum.angular2.springmvc.configuration.security.SecurityUtils;
import com.reportum.angular2.springmvc.configuration.security.WrappedRequest;
import org.apache.commons.io.Charsets;
import org.springframework.util.Assert;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Hmac verification filter
 * Created by Michael DESIGAUD on 16/02/2016.
 */
public class HmacSecurityFilter extends GenericFilterBean {

    public static final Integer JWT_TTL = 20;

    private static final String PHRASE_PART_HEADER = "' header";

    private HmacRequester hmacRequester;

    public HmacSecurityFilter(HmacRequester hmacRequester) {
        this.hmacRequester = hmacRequester;
    }



    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        WrappedRequest wrappedRequest = new WrappedRequest(request);

        try {
            Assert.notNull(hmacRequester, "hmacRequester must not be null");

            if (!hmacRequester.canVerify(request)) {
                filterChain.doFilter(wrappedRequest, response);
            } else {
                //Get Authentication header
                Cookie jwtCookie = SecurityUtils.findJwtCookie(request);
                Assert.notNull(jwtCookie,"No jwt cookie found");

                String jwt = jwtCookie.getValue();
                validateJwt(jwt);

                String digestClient = request.getHeader(HmacUtils.X_DIGEST);
                validateDigestClient(digestClient);

                //Get X-Once header
                String xOnceHeader = request.getHeader(HmacUtils.X_ONCE);
                validateXonnceHeader(xOnceHeader);

                String url = request.getRequestURL().toString();
                url = addExtraInfoToUrl(url, request.getQueryString());

                String encoding = HmacSigner.getJwtClaim(jwt, HmacSigner.ENCODING_CLAIM_PROPERTY);
                String iss = HmacSigner.getJwtIss(jwt);

                //Get public secret key
                String secret = hmacRequester.getPublicSecret(iss);
                Assert.notNull(secret, "Secret key cannot be null");

                String message = buildMessage(request, wrappedRequest,url,xOnceHeader);

                //Digest are calculated using a public shared secret
                String digestServer = HmacSigner.encodeMac(secret, message, encoding);

                if (digestClient.equals(digestServer)) {
                    logger.debug("Request is valid, digest are matching");

                    Map<String,String> customClaims = new HashMap<>();
                    customClaims.put(HmacSigner.ENCODING_CLAIM_PROPERTY, HmacUtils.HMAC_SHA_256);
                    HmacToken hmacToken = HmacSigner.getSignedToken(secret,String.valueOf(iss),JWT_TTL,customClaims);
                    response.setHeader(HmacUtils.X_TOKEN_ACCESS, hmacToken.getJwt());

                    filterChain.doFilter(wrappedRequest, response);
                } else {
                    logger.debug("Server message: " + message);
                    throw new HmacException("Digest are not matching! Client: " + digestClient + " / Server: " + digestServer);
                }
            }

        }catch(Exception e){
            logger.error("Error while generating hmac token");
            logger.error(e.toString());
            response.setStatus(403);
            response.getWriter().write(e.getMessage());
        }
    }

    private void validateJwt(String jwt) throws HmacException {
        if (jwt == null || jwt.isEmpty()) {
            throw new HmacException("The JWT is missing from the '" + HmacUtils.AUTHENTICATION + PHRASE_PART_HEADER);
        }
    }

    private void validateDigestClient(String digestClient) throws HmacException {
        if (digestClient == null || digestClient.isEmpty()) {
            throw new HmacException("The digest is missing from the '" + HmacUtils.X_DIGEST + PHRASE_PART_HEADER);
        }
    }

    private void validateXonnceHeader(String xOnceHeader) throws HmacException {
        if (xOnceHeader == null || xOnceHeader.isEmpty()) {
            throw new HmacException("The date is missing from the '" + HmacUtils.X_ONCE + PHRASE_PART_HEADER);
        }
    }

    private String addExtraInfoToUrl(String url, String queryString) throws UnsupportedEncodingException {
        if (queryString != null) {
            return url + "?" + URLDecoder.decode(queryString, Charsets.UTF_8.displayName());
        }
        return url;
    }

    private String buildMessage(HttpServletRequest request, WrappedRequest wrappedRequest, String url, String xOnceHeader) throws IOException {
        if ("POST".equals(request.getMethod()) || "PUT".equals(request.getMethod()) || "PATCH".equals(request.getMethod())) {
            return request.getMethod().concat(wrappedRequest.getBody()).concat(url).concat(xOnceHeader);
        }
        return request.getMethod().concat(url).concat(xOnceHeader);
    }
}
