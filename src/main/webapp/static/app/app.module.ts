import { NgModule }      from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppComponent }  from './app.component';
import { routing }       from './app.routes';
import { HomeModule }    from './home/home.module';

@NgModule({
  imports:      [ BrowserModule, routing, HomeModule ],      // module dependencies
  declarations: [ AppComponent ],                            // components and directives
  bootstrap:    [ AppComponent ],                            // root component
  providers:    [ ]                                          // services
})
export class AppModule { }
