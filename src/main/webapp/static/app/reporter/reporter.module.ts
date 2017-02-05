import {NgModule} from "@angular/core";
import {BrowserModule} from "@angular/platform-browser";
import {ReactiveFormsModule, FormsModule} from "@angular/forms";
import {HttpModule} from "@angular/http";
import {SharedModule} from "../common/shared.module";
import {ReporterService} from "./reporter.service";
import {ProjectService} from "../common/project/project.service";
import {ReportService} from "../common/report/report.service";
import {ReporterComponent} from "./reporter.component";
import {DataService} from "../common/data/data.service";

@NgModule({
  imports:      [ BrowserModule, HttpModule, ReactiveFormsModule, FormsModule, SharedModule ],
  declarations: [ ReporterComponent ],
  bootstrap:    [ ReporterComponent ],
  providers:    [ ReporterService, ProjectService, ReportService, DataService ]                         // services
})

export class ReporterModule { }
