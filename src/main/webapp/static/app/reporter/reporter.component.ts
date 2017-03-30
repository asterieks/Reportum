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
        //TODO check this
        //let currentAccount = this.getCurrentAccount();
        this.reportService.getReportByProjectId(this.selectedProject.projectId)
            .subscribe(data => {
                this.reports=data;
                this.isSaveButtonValid=true;

                this.displayReport(data);
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
    private displayReport(report:any) {
        if(report){
            this.showThisReport(report);
        } else{
            this.showEmptyReport();
        }
    }

    private showThisReport(report: any){
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
        if(this.selectedProject.state != 'Delayed'){
            this.saveReport(this.selectedProject,reportToUpdate);
        } else{
            this.refreshDataAndSaveReport(reportToUpdate);
        }
    }

    private refreshDataAndSaveReport(reportToUpdate:any) {
        this.projectService.getProject(this.selectedProject.projectId).subscribe(project => {
            if (project) {
                this.selectedProject = project;

                if(this.selectedProject.state != 'Delayed'){
                    this.saveReport( this.selectedProject.projectId,reportToUpdate);
                } else {
                    this.addReport(reportToUpdate);
                }
            }
        });
    }

    private saveReport(project:Project, reportToUpdate: any){
        this.reportService.updateReports(project.projectId, reportToUpdate).subscribe();
    }

    private addReport(report: any){
        this.reportService.addReports(report).subscribe(data=>{
            if(data===201){
                //TODO check if it's necessary
                this.projectState="Updated";
                console.log("Report posted!");
            }
        });
    }

    private getCurrentAccount(){
        return new Account(JSON.parse(localStorage.getItem(AppUtils.STORAGE_ACCOUNT_TOKEN)));
    }
}
