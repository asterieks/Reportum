import { NgModule }      from '@angular/core';
import { FormsModule,ReactiveFormsModule} from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';

import { Login } from './login';
import { LoginService } from './login.service';
import { AlertComponent } from '../alert/alert.component';
import { AlertService} from '../alert/alert.service';

@NgModule({
    imports: [ FormsModule, ReactiveFormsModule, BrowserModule ],
    bootstrap: [ Login ],
    declarations: [ Login, AlertComponent ],
    providers: [ LoginService, AlertService ]
})
export class LoginModule { }
