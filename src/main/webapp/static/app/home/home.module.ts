import { NgModule }            from '@angular/core';
import { BrowserModule }       from '@angular/platform-browser';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { HttpModule }          from '@angular/http';
import { Elastic }             from 'angular2-elastic';

import { HomeService }         from './home.service';
import { ProjectService }      from '../common/project.service';
import { ReportService }       from '../common/report.service';

import { HomeComponent }       from './home.component';
import { SelectComponent }     from '../common/select/select.fragment.component';

import { ButtonComponent }     from '../common/button/button.component';



@NgModule({
  imports:      [ BrowserModule, Elastic, HttpModule, ReactiveFormsModule, FormsModule ],
  declarations: [ HomeComponent, SelectComponent, ButtonComponent ],
  bootstrap:    [ HomeComponent ],
  providers:    [ HomeService, ProjectService, ReportService ]                         // services
})

export class HomeModule { }
