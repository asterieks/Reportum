import {Component} from '@angular/core';
import {Router} from '@angular/router';
import {FormBuilder, Validators, FormGroup} from '@angular/forms';
import {LoginService} from './login.service';
import {Account} from '../account/account';
import {AccountEventsService} from '../account/account.events.service';

@Component({
    selector: 'login',
    template: require('./login.html'),
    styles: [require('./login.css')],
    providers: [LoginService]
})
export class Login {
    username:string;
    password:string;
    router:Router;
    wrongCredentials:boolean;
    loginForm:FormGroup;
    loginService:LoginService;
    account:Account;
    error:string;
    constructor(router: Router,form: FormBuilder,loginService:LoginService,accountEventService:AccountEventsService) {
        this.router = router;
        this.wrongCredentials = false;
        this.loginService = loginService;
        this.loginForm = form.group({
            username: ['', Validators.required],
            password: ['', Validators.required]
        });
        accountEventService.subscribe((account) => {
            if(!account.authenticated) {
                if(account.error) {
                    if(account.error.indexOf('BadCredentialsException') !== -1) {
                        this.error = 'Username and/or password are invalid !';
                    } else {
                        this.error = account.error;
                    }
                }
            }
        });
    }
    authenticate(event) {
        event.preventDefault();
        this.loginService.authenticate(this.loginForm.value.username,this.loginForm.value.password).subscribe(account => {
            if(account){
                this.account = account;
                console.log('Successfully logged',account);
                if(account.profile==="REPORTER"){
                    this.router.navigate(['/reporter']);
                } else if(account.profile==="LEAD"){
                    this.router.navigate(['/manager']);
                }
            }
        });
    }
}
