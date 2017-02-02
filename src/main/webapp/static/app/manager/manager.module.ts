import {NgModule} from "@angular/core";
import {BrowserModule} from "@angular/platform-browser";
import {ReactiveFormsModule, FormsModule} from "@angular/forms";
import {HttpModule} from "@angular/http";
import {DndModule} from "ng2-dnd";
import {SharedModule} from "../common/shared.module";
import {ProjectService} from "../common/project/project.service";
import {ReportService} from "../common/report/report.service";
import {ManagerComponent} from "./manager.component";
import {SortableComponent} from "../common/sortable/sorttable.component";

@NgModule({
  imports:      [ BrowserModule, HttpModule, ReactiveFormsModule, FormsModule, SharedModule,  DndModule.forRoot()],
  declarations: [ ManagerComponent, SortableComponent ],
  bootstrap:    [ ManagerComponent ],
  providers:    [ ProjectService, ReportService ]                         // services
})

export class ManagerModule { }
