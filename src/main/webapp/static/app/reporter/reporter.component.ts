import {Component, OnInit} from "@angular/core";
import {FormGroup, FormBuilder, Validators} from "@angular/forms";
import {ReportService} from "../common/report/report.service";
import {Project} from "../common/project/project.model";
import {ProjectService} from "../common/project/project.service";
import * as AppUtils from "../utils/app.utils";
import {Account} from "../account/account";
import {ToastyService, ToastyConfig, ToastOptions, ToastData} from "ng2-toasty";

@Component({
    selector: 'reporter',
    template: require('./reporter.component.html'),
    styles: [require('./reporter.component.css')]
})

export class ReporterComponent implements OnInit {
    public reportForm: FormGroup;
    selectedProject: any;
    requester: string = "reporter";
    //TODO check if we need that
    isSaveButtonValid:boolean=false;
    isReviewChanged: boolean = false;
    isIssuesChanged: boolean = false;
    isPlansChanged: boolean = false;
    isPrevButtonPressed:boolean = false;
    selectedProjectName:string;
    tempReportHolder: any = {
        reviewPart : '',
        issuePart : '',
        planPart : ''
    };
    downloadedReportHolder: any = {
        reviewPart : '',
        issuePart : '',
        planPart : ''
    };
    templateForProjectSorting: number[]=[];
    submitTrigger:number = 0;

    constructor(private fb: FormBuilder,
                private reportService: ReportService,
                private projectService: ProjectService,
                private toastyService:ToastyService,
                private toastyConfig: ToastyConfig){

        this.toastyConfig.theme = 'bootstrap';
    }

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
        this.switchOffSaveButton();
        this.isPrevButtonPressed = false;
    }

    onProjectSelect(project: Project){
        this.selectedProject=project;
        this.switchOffSaveButton();
        this.isPrevButtonPressed = false;
        this.reportService.getReportByProjectId(this.selectedProject.projectId)
            .subscribe(data => {
                if(data){
                    this.showThisReport(data);
                    this.tempReportHolder = data;
                    this.downloadedReportHolder = JSON.parse(JSON.stringify(data));
                } else {
                    this.showEmptyReport();
                }
                this.selectedProjectName=this.selectedProject.projectName;
            });
    }

    onLoadPrevButtonClick(event){
        event.preventDefault();
        this.isPrevButtonPressed = true;
        this.reportService.getPrevReportByProjectId( this.selectedProject.projectId)
            .subscribe(data => {
                if(data){
                    this.showThisReport(data);
                    this.tempReportHolder = data;
                    this.downloadedReportHolder = JSON.parse(JSON.stringify(data));
                } else {
                    this.showEmptyReport();
                }
                this.selectedProjectName=this.selectedProject.projectName;
            });
    }

    onProjectDrop(templateForSorting: number[]){
        this.templateForProjectSorting = templateForSorting;
    }

    onKeyReview(text: string) {
        let trimmedText = text.trim();
        if(trimmedText && this.downloadedReportHolder.reviewPart != trimmedText) {
            this.tempReportHolder.reviewPart = text;
            this.isReviewChanged=true;
        } else if(this.downloadedReportHolder.reviewPart && trimmedText=="") {
            this.tempReportHolder.reviewPart = text;
            this.isReviewChanged=true;
        } else {
            this.tempReportHolder.reviewPart = text;
            this.isReviewChanged=false;
        }
    }

    onKeyIssue(text: string) {
        let trimmedText = text.trim();
        if(trimmedText && this.downloadedReportHolder.issuePart != trimmedText){
            this.tempReportHolder.issuePart = text;
            this.isIssuesChanged=true;
        } else if(this.downloadedReportHolder.issuePart && trimmedText=="") {
            this.tempReportHolder.issuePart = text;
            this.isReviewChanged=true;
        } else {
            this.tempReportHolder.issuePart = text;
            this.isIssuesChanged=false;
        }
    }

    onKeyPlan(text: string) {
        let trimmedText = text.trim();
        if(trimmedText && this.downloadedReportHolder.planPart != trimmedText ){
            this.tempReportHolder.planPart = text;
            this.isPlansChanged=true;
        } else if(this.downloadedReportHolder.planPart && trimmedText=="") {
            this.tempReportHolder.planPart = text;
            this.isReviewChanged=true;
        } else {
            this.tempReportHolder.planPart = text;
            this.isPlansChanged=false;
        }
    }

    isDisabledSaveButton(): boolean {
        let anyChanges = this.isAnyPartChanged();
        let isAllEmpty = this.isAllFieldEmpty();
        if(isAllEmpty){
            this.switchOffSaveButton();
        }
        return !anyChanges || isAllEmpty;
    }

    isDisabledLoadPrevButton(): boolean {
        return this.isPrevButtonPressed || !this.selectedProjectName;
    }

    private isAllFieldEmpty():boolean {
        if (this.tempReportHolder.reviewPart && this.tempReportHolder.reviewPart.trim()){
            return false;
        }
        if (this.tempReportHolder.issuePart && this.tempReportHolder.issuePart.trim()){
            return false;
        }
        if (this.tempReportHolder.planPart && this.tempReportHolder.planPart.trim()){
            return false;
        }
        return true;
    }

    private isAnyPartChanged(): boolean {
        return this.isReviewChanged || this.isIssuesChanged || this.isPlansChanged;
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
        this.reportService.updateReports(project.projectId, reportToUpdate).subscribe(data=>{
                if(data===201){
                    this.updateState();
                    this.showSuccessToast();
                } else {
                    this.showErrorToast();
                }
        });
    }

    private addReport(report: any){
        this.reportService.addReports(report).subscribe(data=>{
            if(data===201){
                this.updateState();
                this.showSuccessToast();
            } else {
                this.showErrorToast();
            }
        });
    }

    private getCurrentAccount(){
        return new Account(JSON.parse(localStorage.getItem(AppUtils.STORAGE_ACCOUNT_TOKEN)));
    }

    private updateState() {
        this.submitTrigger = this.submitTrigger+1;
    }

    private switchOffSaveButton() {
        this.isReviewChanged=false;
        this.isIssuesChanged=false;
        this.isPlansChanged=false;
    }

    private showSuccessToast() {
        var toastOptions:ToastOptions = {
            title: "Success",
            msg: "Report is saved.",
            showClose: true,
            timeout: 5000,
            theme: 'bootstrap',
            onAdd: (toast:ToastData) => {},
            onRemove: function(toast:ToastData) {}
        };
        this.toastyService.success(toastOptions);
    }

    private showErrorToast() {
        var toastOptions:ToastOptions = {
            title: "Error",
            msg: "Report isn't saved.",
            showClose: true,
            timeout: 5000,
            theme: 'bootstrap',
            onAdd: (toast:ToastData) => {},
            onRemove: function(toast:ToastData) {}
        };
        this.toastyService.error(toastOptions);
    }
}
