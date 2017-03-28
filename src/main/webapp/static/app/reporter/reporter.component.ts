import {Component, OnInit, ElementRef} from "@angular/core";
import {FormGroup, FormBuilder, Validators} from "@angular/forms";
import {ReportService} from "../common/report/report.service";
import {Project} from "../common/project/project.model";
import {ProjectService} from "../common/project/project.service";
import * as AppUtils from "../utils/app.utils";
import {Account} from "../account/account";

@Component({
    selector: 'reporter',
    template: require('./reporter.component.html'),
    styles: [require('./reporter.component.css')]
})

export class ReporterComponent implements OnInit {
    public reportForm: FormGroup;
    selectedProject: any;
    projectState: string;
    reports:any;
    isSaveButtonValid:boolean=false;
    templateForProjectSorting: number[]=[];

    constructor(private fb: FormBuilder,
                private reportService: ReportService,
                private projectService: ProjectService,
                private elementRef:ElementRef){}

    ngOnInit() {
        this.initForm();
    }

    onSubmit(form: any) {
        let currentAccount = this.getCurrentAccount();
        let reportToUpdate = {
            reviewPart: form.value.review,
            issuePart: form.value.issues,
            planPart:  form.value.plans,
            project: this.selectedProject,
            reportedBy: currentAccount.id
        };
        this.checkProjectIfUpdatedAndSaveReport(reportToUpdate);
    }

    onProjectSelect(project: Project){
        this.selectedProject=project;
        let currentAccount = this.getCurrentAccount();
        this.reportService.getReports(currentAccount.id)
            .subscribe(data => {
                this.reports=data;
                this.isSaveButtonValid=true;

                this.findSpecificReportAndShow(data);
            });
    }

    onProjectDrop(templateForSorting: number[]){
        this.templateForProjectSorting = templateForSorting;
    }

    isValidSaveButton(): boolean{
        return !this.isSaveButtonValid;
    }

    //---------methods---------------------------------------------------
    //related to initialization
    private initForm() {
        this.reportForm = this.fb.group({
            review: ['', [<any>Validators.required, <any>Validators.minLength(5)]],
            issues: ['', [<any>Validators.required, <any>Validators.minLength(5)]],
            plans: ['', [<any>Validators.required, <any>Validators.minLength(5)]]
        });
    }

    //related to projectSelect event
    private findSpecificReportAndShow(reports:any[]) {
        let report=this.findSpecificReport(this.selectedProject);
        if(report){
            this.showSpecificReport(report);
        } else{
            this.showEmptyReport();
        }
    }

    private findSpecificReport(project:Project): any {
        if(this.reports){
            for (let report of this.reports) {
                if(report.project.projectId===project.projectId){
                    return report;
                }
            }
        }
        return null;
    }

    private showSpecificReport(report: any){
        this.reportForm.patchValue({
            review : report.reviewPart,
            issues : report.issuePart,
            plans  : report.planPart
        });
    }

    private showEmptyReport() {
        this.reportForm.patchValue({
            review : '',
            issues : '',
            plans  : ''
        });
    }

    // related to submit
    private checkProjectIfUpdatedAndSaveReport(reportToUpdate: any) {
        if(this.selectedProject.state){
            this.saveReport(this.selectedProject,reportToUpdate);
        } else{
            this.refreshDataAndSaveReport(reportToUpdate);
        }
    }

    private refreshDataAndSaveReport(reportToUpdate:any) {
        this.projectService.getProject(this.selectedProject.projectId).subscribe(project => {
            if (project) {
                this.selectedProject = project;

                if(project.state){
                    this.refreshReportsAndSaveReport(reportToUpdate);
                } else {
                    this.addReport(reportToUpdate);
                }
            }
        });
    }

    private refreshReportsAndSaveReport(reportToUpdate:any) {
        let currentAccount = this.getCurrentAccount();
        this.reportService.getReports(currentAccount.id).subscribe(reports => {
            if(reports){
                this.reports=reports;

                this.saveReport(this.selectedProject,reportToUpdate);
            }
        });
    }

    private saveReport(project:Project, reportToUpdate: any){
        let prevReportVersion=this.findSpecificReport(project);
        if(prevReportVersion) {
            this.reportService.updateReports(prevReportVersion.reportId, reportToUpdate).subscribe();
        } else {
            console.log("Error: transaction aborted");
            console.log("Error: can't find report after update");
            this.addReport(reportToUpdate);
        }
    }

    private addReport(report: any){
        this.reportService.addReports(report).subscribe(data=>{
            if(data===201){
                //TODO check if it's necessary
                this.projectState="Updated";
            }
        });
    }

    private getCurrentAccount(){
        return new Account(JSON.parse(localStorage.getItem(AppUtils.STORAGE_ACCOUNT_TOKEN)));
    }
}
