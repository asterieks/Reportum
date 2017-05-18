import {Component} from '@angular/core';
import {AccountEventsService} from '../account/account.events.service';
import {LoginService} from '../login/login.service';

@Component({
    selector: 'app_header',
    template: require('./header.html'),
    styles: [require('./header.css')],
    providers: [LoginService],
})
export class Header {
    authenticated:boolean;
    loginService:LoginService;
    full_name:string;
    show = false;
    constructor(accountEventService:AccountEventsService,loginService:LoginService) {
        this.loginService = loginService;
        accountEventService.subscribe((account) => {
            if(!account.authenticated) {
                this.authenticated = false;
                this.loginService.logout(false);
                this.show = false;
                this.full_name="";
            } else {
                this.authenticated = true;
                this.full_name = account.fullName;
                this.show = true;
            }
        });
    }
    logout(event:Event):void {
        event.preventDefault();
        this.loginService.logout();
    }
}
