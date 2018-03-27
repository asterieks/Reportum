import {NgModule} from "@angular/core";
import {BrowserModule} from "@angular/platform-browser";
import {ReactiveFormsModule} from "@angular/forms";
import {SharedModule} from "../common/shared.module";
import {AdminService} from "../service/admin.service";
import {AdminComponent} from "./admin.component";

@NgModule({
  imports:      [ BrowserModule, ReactiveFormsModule, SharedModule ],
  declarations: [ AdminComponent ],
  bootstrap:    [ AdminComponent ],
  providers:    [ AdminService ]                         // services
})

export class AdminModule { }
