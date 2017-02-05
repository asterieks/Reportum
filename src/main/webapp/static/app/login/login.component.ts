import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Http, Response, Headers } from '@angular/http';
import {FormGroup, FormBuilder, Validators} from "@angular/forms";
import {LoginService} from "./login.service";
import {DataService} from "../common/data/data.service";


const styles   = require('./login.css');
const template = require('./login.html');

@Component({
  selector: 'login',
  template: template,
  styles: [ styles ]
})
export class LoginComponent {

private loginForm: FormGroup;

  constructor(public fb: FormBuilder,
              public router: Router,
              public http: Http,
              public loginService: LoginService,
              public dataService: DataService) {}

   ngOnInit() {
            this.loginForm = this.fb.group({
                  username: ["", Validators.required],
                  password: ["", Validators.required]
             });
        }

  login(event, username, password) {
    this.loginService.getUserCredential(username)
        .subscribe(data => {
            if (data.roleName=="REPORTER"){
                this.dataService.loginData=data;
                this.router.navigate(['/reporter']);
            }
            if (data.roleName=="LEAD" || data.roleName =="MANAGER"){
                this.dataService.loginData=data;
                this.router.navigate(['/manager']);
            }
        });
    }
}
