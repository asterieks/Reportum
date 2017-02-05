import {NgModule} from "@angular/core";
import {BrowserModule} from "@angular/platform-browser";
import {ReactiveFormsModule, FormsModule} from "@angular/forms";
import {HttpModule} from "@angular/http";
import {LoginComponent} from "./login.component";
import {LoginService} from "./login.service";
import {DataService} from "../common/data/data.service";


@NgModule({
  imports:      [ BrowserModule, HttpModule, ReactiveFormsModule, FormsModule ],
  declarations: [ LoginComponent ],
  bootstrap:    [ LoginComponent ],
  providers:    [ LoginService, DataService ]                         // services
})

export class LoginModule { }
