import {Component} from "@angular/core";
import {Router, NavigationEnd} from "@angular/router";
import {LoginService} from "./login/login.service";
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
        router.events.filter((event) => event instanceof NavigationEnd).subscribe((event: NavigationEnd)  => {
            let url = event.url;
            console.log("AppComponent:DEBAG " + event.toString());
            if(url !== '/authenticate' && url !== '/') {
                if(!loginService.isAuthenticated() || !this.isUrlAllowedForAccount(url)) {
                    loginService.logout();
                } else {
                    loginService.sendLoginSuccess();
                }
            } else if(url == '/' && loginService.isAuthenticated()){
                loginService.logout();
            }
        });
    }

    private isUrlAllowedForAccount(url: string): boolean {
        let currentAccount = this.getCurrentAccount();
        if((currentAccount.profile=='REPORTER' && url == '/reporter')
            || ( (currentAccount.profile=='LEAD' || currentAccount.profile=='MANAGER') && url == '/manager')
            || (currentAccount.profile=='ADMIN' && url == '/admin'))
        {
            return true;
        }
        return false;
    }

    private getCurrentAccount(){
        return new Account(JSON.parse(localStorage.getItem(AppUtils.STORAGE_ACCOUNT_TOKEN)));
    }
}
