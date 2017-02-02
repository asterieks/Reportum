import {Component, OnInit} from "@angular/core";
import {FormGroup, FormBuilder, Validators} from "@angular/forms";
import {ReporterService} from "./reporter.service";
import {ReportService} from "../common/report/report.service";
import {Button} from "../common/button/button.model";
import {SelectLabel} from "../common/select/select_label.model";
import {Project} from "../common/project/project.model";

@Component({
    selector: 'reporter',
    template: require('./reporter.component.html'),
    styles: [require('./reporter.component.css')]
})

export class ReporterComponent implements OnInit {
    public reportForm: FormGroup;
    select_label: SelectLabel;
    button_models: Button[];
    project: Project;
    projectState: string;
    private reports: any[];

    constructor(private reporterService: ReporterService,
                private fb: FormBuilder,
                private reportService: ReportService){}

    ngOnInit() {
        this.select_label = this.reporterService.getSelect();
        this.button_models = this.reporterService.getButton();
        this.reportForm = this.fb.group({
             review: ['', [<any>Validators.required, <any>Validators.minLength(5)]],
             issues: ['', [<any>Validators.required, <any>Validators.minLength(5)]],
              plans: ['', [<any>Validators.required, <any>Validators.minLength(5)]]
        });
    }

    onSubmit(form: any) {
        let report = {
                reviewPart: form.value.review,
                issuePart: form.value.issues,
                planPart:  form.value.plans,
                project: this.project,
                reportedBy: 'asterieks@gmail.com'
        };
        if(this.projectState==="Updated"){
            let reportId=this.findReport(this.project);
            this.updateReport(reportId,report);
        } else {
            this.addReport(report);
        }
    }

    private addReport(report: any){
        this.reportService.addReports(report).subscribe(data=>{
            if(data===201){
                this.projectState="Updated";
            }
        });
    }

    private updateReport(reportId:number, report: any){
        this.reportService.updateReports(reportId, report).subscribe();
    }

    onSelectChange(project:Project):void {
        this.project=project;
        this.projectState=project.state;
    }

    onReportLoad(reports:any):void {
        this.reports=reports;
    }

    private findReport(project:Project) {
        for (let report of this.reports) {
            if(report.project.projectId===project.projectId){
                return report.reportId;
            }
        }
    }
}
