import {NgModule} from "@angular/core";
import {BrowserModule} from "@angular/platform-browser";
import {ReactiveFormsModule, FormsModule} from "@angular/forms";
import {HttpModule} from "@angular/http";
import {DndModule} from "ng2-dnd";
import {SharedModule} from "../common/shared.module";
import {ProjectService} from "../common/project/project.service";
import {ReportService} from "../common/report/report.service";
import {ManagerComponent} from "./manager.component";
import {SortableComponent} from "../common/sortable/sortable.component";
import {DataService} from "../common/data/data.service";

@NgModule({
  imports:      [ BrowserModule, HttpModule, ReactiveFormsModule, FormsModule, SharedModule,  DndModule.forRoot()],
  declarations: [ ManagerComponent, SortableComponent ],
  bootstrap:    [ ManagerComponent ],
  providers:    [ ProjectService, ReportService, DataService ]                         // services
})

export class ManagerModule { }
