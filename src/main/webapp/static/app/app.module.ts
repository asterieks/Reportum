import { NgModule }      from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { HomeModule }    from './home/home.module';
import { ManagerModule } from './manager/manager.module';

import { AppComponent }  from './app.component';
import { routing }       from './app.routes';

@NgModule({
  imports:      [ BrowserModule, routing, HomeModule, ManagerModule ],      // module dependencies
  declarations: [ AppComponent ],                            // components and directives
  bootstrap:    [ AppComponent ],                            // root component
  providers:    [ ]                                          // services
})
export class AppModule { }
