import {NgModule} from "@angular/core";
import {BrowserModule} from "@angular/platform-browser";
import {ReactiveFormsModule, FormsModule} from "@angular/forms";
import {SharedModule} from "../common/shared.module";
import {ProjectService} from "../common/project/project.service";
import {ReportService} from "../common/report/report.service";
import {ReporterComponent} from "./reporter.component";

@NgModule({
  imports:      [ BrowserModule, ReactiveFormsModule, FormsModule, SharedModule ],
  declarations: [ ReporterComponent ],
  bootstrap:    [ ReporterComponent ],
  providers:    [ ProjectService, ReportService ]                         // services
})

export class ReporterModule { }
