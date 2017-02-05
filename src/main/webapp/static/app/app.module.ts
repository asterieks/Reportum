import {NgModule}       from "@angular/core";
import {BrowserModule}  from "@angular/platform-browser";
import {ReporterModule} from "./reporter/reporter.module";
import {ManagerModule}  from "./manager/manager.module";
import {LoginModule}    from "./login/login.module";
import {AppComponent}   from "./app.component";
import {routing}        from "./app.routes";

@NgModule({
  imports:      [ BrowserModule, routing, ReporterModule, ManagerModule, LoginModule ],      // module dependencies
  declarations: [ AppComponent ],                            // components and directives
  bootstrap:    [ AppComponent ],                            // root component
  providers:    [ ]                                          // services
})
export class AppModule { }
