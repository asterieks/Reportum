import { NgModule }            from '@angular/core';
import { BrowserModule }       from '@angular/platform-browser';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { HttpModule }          from '@angular/http';
import { AgGridModule }        from "ag-grid-ng2/main";
import { SharedModule }        from '../common/shared.module';

import { ManagerService }      from './manager.service';
import { ProjectService }      from '../common/project/project.service';
import { ReportService }       from '../common/report/report.service';
import { SharedService }       from "../common/shared.service";

import { GridComponent }       from '../common/grid/grid.component';
import { ManagerComponent }    from './manager.component';

@NgModule({
  imports:      [ BrowserModule, HttpModule, ReactiveFormsModule, FormsModule, AgGridModule.forRoot(), SharedModule ],
  declarations: [ ManagerComponent, GridComponent ],
  bootstrap:    [ ManagerComponent ],
  providers:    [ ManagerService, ProjectService, ReportService, SharedService ]                         // services
})

export class ManagerModule { }
