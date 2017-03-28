import {Component} from "@angular/core";
import {Router} from '@angular/router';
import {LoginService} from './login/login.service';
import * as AppUtils from "./utils/app.utils";
import {Account} from "./account/account";

@Component({
  selector: 'app',
  template: require('./app.component.html'),
  styles: [require('./app.component.css')],
  providers: [LoginService]
})

export class AppComponent {
    constructor(router:Router, private loginService:LoginService) {
        router.events.subscribe(e => {
            console.log("+AppComponent: router event: "+e.url);
            let url = e.url;
            if(url !== '/authenticate' && url !== '/') {
                if(!loginService.isAuthenticated() || !this.isUrlAllowedForAccount(url)) {
                    console.log("+AppComponent: navigate to Login");
                    router.navigate(['/authenticate']);
                } else {
                    console.log("+AppComponent: send login success ");
                    loginService.sendLoginSuccess();
                }
            }
        });
    }

    private isUrlAllowedForAccount(url: string): boolean {
        console.log('+AppComponent: in isUrlAllowedForAccount');
        let currentAccount = this.getCurrentAccount();
        if((currentAccount.profile=='REPORTER' && url == '/reporter')
            || (currentAccount.profile=='LEAD' && url == '/manager'))
        {
            return true;
        } else {
            this.loginService.logout();
        }
        return false;
    }

    private getCurrentAccount(){
        return new Account(JSON.parse(localStorage.getItem(AppUtils.STORAGE_ACCOUNT_TOKEN)));
    }
}
