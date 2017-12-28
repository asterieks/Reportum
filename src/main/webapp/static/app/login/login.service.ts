import {Injectable} from '@angular/core';
import {Response,Headers} from '@angular/http';
import 'rxjs/add/operator/map';
import {Account} from '../account/account';
import {AccountEventsService} from '../account/account.events.service';
import {SecurityToken} from '../security/securityToken';
import {Observable} from 'rxjs/Observable';
import * as AppUtils from '../utils/app.utils';
import {Router} from '@angular/router';
import {HttpClient, HttpResponse} from "@angular/common/http";
import {headers} from "../common/report/report.service";

@Injectable()
export class LoginService {
    constructor(private http:HttpClient,private accountEventService:AccountEventsService,private router: Router) {}
    authenticate(username:string,password:string):Observable<Account> {
        let url = AppUtils.BACKEND_API_ROOT_URL+AppUtils.BACKEND_API_AUTHENTICATE_PATH;
        let body = JSON.stringify({login:username,password:password});
        return this.http.post(url, body, {headers: headers, observe: 'response'})
            .map((res:any) => {
                let securityToken:SecurityToken = new SecurityToken(
                    {
                    publicSecret:res.headers.get(AppUtils.HEADER_X_SECRET),
                    securityLevel:res.headers.get(AppUtils.HEADER_WWW_AUTHENTICATE)
                    }
                );
                let body = res.body;
                localStorage.setItem(AppUtils.CSRF_CLAIM_HEADER, res.headers.get(AppUtils.CSRF_CLAIM_HEADER));
                localStorage.setItem(AppUtils.STORAGE_ACCOUNT_TOKEN, JSON.stringify(body));
                localStorage.setItem(AppUtils.STORAGE_SECURITY_TOKEN,JSON.stringify(securityToken));

                let account:Account = new Account(body);
                this.sendLoginSuccess(account);
                return account;
            })
            .catch((error:any) => Observable.throw(error.message || 'Server error'));
    }
    sendLoginSuccess(account?:Account):void {
        console.log("in sendLoginSuccess");
        if(!account) {
            account = new Account(JSON.parse(localStorage.getItem(AppUtils.STORAGE_ACCOUNT_TOKEN)));
        }
        this.accountEventService.loginSuccess(account);
    }
    isAuthenticated():boolean {
        return !!localStorage.getItem(AppUtils.STORAGE_ACCOUNT_TOKEN);
    }
    removeAccount():void {
        localStorage.removeItem(AppUtils.STORAGE_ACCOUNT_TOKEN);
        localStorage.removeItem(AppUtils.STORAGE_SECURITY_TOKEN);
        localStorage.removeItem(AppUtils.CSRF_CLAIM_HEADER);
    }
    logout(callServer:boolean = true):void {
        if(callServer) {
            console.log('Logging out --> call to server');
            this.http.get(AppUtils.BACKEND_API_ROOT_URL + '/logout',{observe: 'response'})
                .map((res) => res)
                .catch((error:any) => Observable.throw(error.json().error || 'Server error'))
                .subscribe(() => {
                this.accountEventService.logout(new Account(JSON.parse(localStorage.getItem(AppUtils.STORAGE_ACCOUNT_TOKEN))));
                this.removeAccount();
                this.router.navigate(['/authenticate']);
            });
        } else {
            console.log('Logging out --> remove account');
            this.removeAccount();
            this.router.navigate(['/authenticate']);
        }
    }
    isAuthorized(roles:Array<string>):boolean {
        let authorized:boolean = false;
        if(this.isAuthenticated() && roles) {
            let account:Account = new Account(JSON.parse(localStorage.getItem(AppUtils.STORAGE_ACCOUNT_TOKEN)));
            if(account && account.authorities) {

                roles.forEach((role:string) => {
                    if(account.authorities.indexOf(role) !== -1) {
                        authorized = true;
                    }
                });
            }
        }
        return authorized;
    }
}
