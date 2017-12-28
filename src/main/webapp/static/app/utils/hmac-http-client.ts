import {Injectable} from "@angular/core";
import {Observable} from "rxjs/Observable";
import {SecurityToken} from "../security/securityToken";
import * as AppUtils from "../utils/app.utils";
import {AccountEventsService} from "../account/account.events.service";
import "rxjs/add/operator/map";
import "rxjs/add/operator/catch";
import "rxjs/add/operator/share";
import * as CryptoJS from "crypto-js";
import {HttpClient, HttpHandler, HttpHeaders} from "@angular/common/http";


@Injectable()
export class HmacHttpClient extends HttpClient {
    http:HttpClient;
    accountEventsService:AccountEventsService;
    constructor(handler: HttpHandler, accountEventsService:AccountEventsService) {
        super(handler);
        this.accountEventsService = accountEventsService;
    }
    addSecurityHeader(url:string, method:string, options: any, body: any):void {

        if(AppUtils.UrlMatcher.matches(url)) {

            let securityToken:SecurityToken = new SecurityToken(JSON.parse(localStorage.getItem(AppUtils.STORAGE_SECURITY_TOKEN)));
            let date:string = new Date().toISOString();
            let secret:string = securityToken.publicSecret;

            let message = '';
            if (method === 'PUT' || method === 'POST' || method === 'PATCH') {
               message = method + body + url + date;
            } else {
                message = method + url + date;
            }
            options.headers = options.headers.set(AppUtils.CSRF_CLAIM_HEADER, localStorage.getItem(AppUtils.CSRF_CLAIM_HEADER));

            if (securityToken.isEncoding('HmacSHA256')) {
                options.headers = options.headers.set(AppUtils.HEADER_X_DIGEST, CryptoJS.HmacSHA256(message, secret).toString());
            } else if (securityToken.isEncoding('HmacSHA1')) {
                options.headers = options.headers.set(AppUtils.HEADER_X_DIGEST, CryptoJS.HmacSHA1(message, secret).toString());
            } else if (securityToken.isEncoding('HmacMD5')) {
                options.headers = options.headers.set(AppUtils.HEADER_X_DIGEST, CryptoJS.HmacMD5(message, secret).toString());
            }
            options.headers = options.headers.set(AppUtils.HEADER_X_ONCE, date);

            // console.log('url',url);
            // console.log('message',message);
            // console.log('secret',secret);
            // console.log('hmac message',options.headers.get(AppUtils.HEADER_X_DIGEST));
        }

    }
    setOptions(options?: any):any {
        if(!options) {
            options = {};
        }
        if(!options.headers) {
            options.headers = new HttpHeaders();
        }
        return options;
    }
    mapResponse(res,observer):void {
        if(res.ok && res.headers) {
            let securityToken:SecurityToken = new SecurityToken(JSON.parse(localStorage.getItem(AppUtils.STORAGE_SECURITY_TOKEN)));
            if(securityToken) {
                localStorage.setItem(AppUtils.STORAGE_SECURITY_TOKEN,JSON.stringify(securityToken));
            }
        }
        observer.next(res);
        observer.complete();
    }
    handleErrorResponse(res, observer):void {
        if(res.status === 403) {
            console.log('Unauthorized request:',res.message);
            //TODO check if its necessaru to send error to logout
            this.accountEventsService.logout({error:res.message});
        }
        observer.complete();
    }
    get(url: string, options?: any): Observable<any> {
        options = this.setOptions(options);
        this.addSecurityHeader(url,'GET',options,null);

        return Observable.create(observer => {
            super.get(url, options)
                 .subscribe(
                     data=>{
                         this.mapResponse(data,observer);
                     },
                     error=>{
                         this.handleErrorResponse(error,observer);
                     }
            );
        });
    }
    post(url: string, body: any, options?: any): Observable<any> {
        options = this.setOptions(options);
        this.addSecurityHeader(url,'POST',options, body);

        return Observable.create(observer => {
            super.post(url,body,options)
                .subscribe(
                    data=>{
                        this.mapResponse(data,observer);
                    },
                    error=>{
                        this.handleErrorResponse(error,observer);
                    });
        });
    }
    put(url: string, body: any, options?: any): Observable<any> {
        options = this.setOptions(options);
        this.addSecurityHeader(url,'PUT',options, body);

        return Observable.create(observer => {
            super.put(url,body,options)
                .subscribe(
                    data=>{
                        this.mapResponse(data,observer);
                    },
                    error=>{
                        this.handleErrorResponse(error,observer);
                    });
        });
    }
}
