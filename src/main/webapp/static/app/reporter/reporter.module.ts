import {NgModule} from "@angular/core";
import {BrowserModule} from "@angular/platform-browser";
import {ReactiveFormsModule, FormsModule} from "@angular/forms";
import {HttpModule} from "@angular/http";
import {SharedModule} from "../common/shared.module";
import {ReporterService} from "./reporter.service";
import {ProjectService} from "../common/project/project.service";
import {ReportService} from "../common/report/report.service";
import {ReporterComponent} from "./reporter.component";

@NgModule({
  imports:      [ BrowserModule, HttpModule, ReactiveFormsModule, FormsModule, SharedModule ],
  declarations: [ ReporterComponent ],
  bootstrap:    [ ReporterComponent ],
  providers:    [ ReporterService, ProjectService, ReportService ]                         // services
})

export class ReporterModule { }
