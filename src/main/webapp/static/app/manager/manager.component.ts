import {Component, OnInit} from "@angular/core";
import {FormGroup, FormBuilder, Validators} from "@angular/forms";
import {ManagerService} from "./manager.service";
import {ReportService} from "../common/report/report.service";
import {SharedService} from "../common/shared.service";
import {Button} from "../common/button/button.model";
import {GridLabel} from "../common/grid/grid_label.model";

@Component({
    selector: 'manager',
    template: require('./manager.component.html'),
    styles: [require('./manager.component.css')]
})

export class ManagerComponent implements OnInit {
    public reportForm: FormGroup;
    grid_label: GridLabel;
    button_models: Button[];
    selectedProject: any;
    selectedReportId: number;
    show = true;
    showAggregated=false;
    agregated_report: string;

    constructor(private managerService: ManagerService,
                private fb: FormBuilder,
                private reportService: ReportService,
                private sharedService:SharedService){}

    ngOnInit() {
        this.grid_label = this.managerService.getGridLabel();
        this.button_models = this.managerService.getButton();
        this.reportForm = this.fb.group({
             review: ['', [<any>Validators.required, <any>Validators.minLength(5)]],
             issues: ['', [<any>Validators.required, <any>Validators.minLength(5)]],
              plans: ['', [<any>Validators.required, <any>Validators.minLength(5)]]
        });
        this.sharedService.reportLoadedEvent.subscribe(data => this.loadForm(data));
        this.sharedService.aggregateEvent.subscribe(data => this.aggregateReports(data));
    }

    onSubmit(form: any) {
        let report = {
                reviewPart: form.value.review,
                issuePart: form.value.issues,
                planPart:  form.value.plans,
                project: this.selectedProject
        };
        this.updateReport(this.selectedReportId,report);
    }

    private updateReport(reportId:number, report: any){
        this.reportService.updateReports(reportId, report).subscribe();
    }

    private loadForm(report: any){
        this.reportForm.patchValue({
            review : report.review,
            issues : report.issues,
            plans  : report.plans
        });
        this.selectedProject=report.project;
        this.selectedReportId =report.reportId;
    }

    private aggregateReports(show:boolean){
        this.reportService.getReports("lead@gmail.com").subscribe(data => this.showAggregatedReport(data));
        this.show=show;
        this.showAggregated=!show;
    }

    private showAggregatedReport(reports:any[]){
        let temp='';
        for (let report of reports) {
            let name='--'+report.project.projectName+'--'+'\r\n';
            let review='Review:\r\n'+report.reviewPart+'\r\n';
            let issues='Issues:\r\n'+report.issuePart+'\r\n';
            let plans='Plans:\r\n'+report.planPart+'\r\n';
            let end='\r\n';
            temp=temp+name+review+issues+plans+end;
        }
        this.agregated_report=temp;
    }
}

