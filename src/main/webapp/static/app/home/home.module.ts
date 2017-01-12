import { NgModule }            from '@angular/core';
import { BrowserModule }       from '@angular/platform-browser';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { HttpModule }          from '@angular/http';
import { SharedModule }       from '../common/shared.module';

import { HomeService }         from './home.service';
import { ProjectService }      from '../common/project/project.service';
import { ReportService }       from '../common/report/report.service';

import { HomeComponent }       from './home.component';

@NgModule({
  imports:      [ BrowserModule, HttpModule, ReactiveFormsModule, FormsModule, SharedModule ],
  declarations: [ HomeComponent ],
  bootstrap:    [ HomeComponent ],
  providers:    [ HomeService, ProjectService, ReportService ]                         // services
})

export class HomeModule { }
