import {NgModule} from "@angular/core";
import {BrowserModule} from "@angular/platform-browser";
import {ReporterModule} from "./reporter/reporter.module";
import {RouterModule} from '@angular/router';
import {ManagerModule} from "./manager/manager.module";
import {AppComponent} from "./app.component";
import {routing} from "./app.routes";
import {LoginModule} from "./login/login.module";
import {AccountEventsService} from './account/account.events.service';
import {LocationStrategy, HashLocationStrategy} from "@angular/common";
import {HttpClientModule, HttpClient, HttpHandler} from "@angular/common/http";
import {HmacHttpClient} from "./utils/hmac-http-client";
import {AccountModule} from './account/account.module';
import {UtilsModule} from './utils/utils.module';
import {ToastyModule} from "ng2-toasty";
import {Header} from "./header/header";

@NgModule({
  imports:      [ BrowserModule, routing, ReporterModule, ManagerModule,
                  ReporterModule, RouterModule, LoginModule, AccountModule,
                  UtilsModule, ToastyModule.forRoot(), HttpClientModule ],      // module dependencies
  declarations: [ AppComponent, Header ],                            // components and directives
  bootstrap:    [ AppComponent ],                            // root component
  providers:      [
    {provide: LocationStrategy, useClass: HashLocationStrategy},
    {
      provide: HttpClient,
      useFactory: (handler: HttpHandler, accountEventService: AccountEventsService) => {
        return new HmacHttpClient(handler, accountEventService);
      },
      deps: [HttpHandler, AccountEventsService],
      multi: false
    }]                                         // services
})
export class AppModule { }
