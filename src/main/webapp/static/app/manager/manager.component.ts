import {Component, OnInit, ElementRef} from "@angular/core";
import {FormGroup, FormBuilder, Validators} from "@angular/forms";
import {ReportService} from "../common/report/report.service";
import {Project} from "../common/project/project.model";
import {ProjectService} from "../common/project/project.service";
import * as AppUtils from "../utils/app.utils";
import {Account} from "../account/account";
import {ToastyService, ToastyConfig, ToastOptions, ToastData} from "ng2-toasty";

@Component({
    selector: 'manager',
    template: require('./manager.component.html'),
    styles: [require('./manager.component.css')]
})

export class ManagerComponent implements OnInit {
    public reportForm: FormGroup;
    selectedProject: any;
    requester: string = "manager";
    show = false;
    showAggregated=true;
    reports:any;
    htmlVariable: string;
    isSaveButtonValid:boolean=false;
    isApproveButtonValid:boolean=false;
    isReviewChanged: boolean = false;
    isIssuesChanged: boolean = false;
    isPlansChanged: boolean = false;
    isPrevButtonPressed:boolean = false;
    templateForProjectSorting: number[]=[];
    submitTrigger:number = 0;
    selectedProjectName:string;
    switchOffApproveButton: boolean = false;
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
    ckeditorConfig = {
        height: 110,
        uiColor: '#E6E6FA',
        removePlugins: "elementspath,div",
        language: "en",
        extraPlugins: "autogrow",
        autoGrow_minHeight: 110,
        autoGrow_maxHeight: 300,
        autoGrow_bottomSpace: 10,
        resize_enabled: false,
        scayt_autoStartup: true,
        toolbarGroups: [
            { name: 'basicstyles', groups: [ 'basicstyles' ] },
            { name: 'paragraph', element: 'div', groups: [ 'list', 'indent'] },
            { name: 'editing',     groups: [ 'selection', 'spellchecker' ] },
            { name: 'styles' }
        ],
        removeButtons: 'Language,RemoveFormat,CopyFormatting,Subscript,Superscript,Strike',
        enterMode: 3
    };

    constructor(private fb: FormBuilder,
                private reportService: ReportService,
                private projectService: ProjectService,
                private elementRef:ElementRef,
                private toastyService:ToastyService,
                private toastyConfig: ToastyConfig){

        this.toastyConfig.theme = 'bootstrap';
    }

    ngOnInit() {
        this.initForm();
        this.getReportsAndShow();
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
        this.switchOffApproveButton = true;
        this.isPrevButtonPressed = false;
    }

    onProjectSelect(project: Project){
        this.selectedProject=project;
        this.switchOffSaveButton();
        this.switchOffApproveButton = false;
        this.isPrevButtonPressed = false;
        this.reportService.getReportByProjectId(this.selectedProject.projectId)
            .subscribe(data => {
                if(data){
                    this.showThisReport(data);
                    this.tempReportHolder = data;
                    this.downloadedReportHolder = JSON.parse(JSON.stringify(data));
                    this.isApproveButtonValid = false;
                } else {
                    this.showEmptyReport();
                    this.tempReportHolder = {reviewPart:"", issuePart:"", planPart:"", project:null};
                    this.downloadedReportHolder = {reviewPart:"", issuePart:"", planPart:"", project:null};
                    this.isApproveButtonValid = false;
                }
                this.selectedProjectName=this.selectedProject.projectName;
            });
    }

    onProjectDrop(templateForSorting: number[]){
        this.templateForProjectSorting = templateForSorting;
    }

    onAggregateButtonClick(event){
        event.preventDefault();
        this.selectedProjectName="";
        this.getReportsAndShow();
        this.isSaveButtonValid=false;
        this.tempReportHolder = {reviewPart:"", issuePart:"", planPart:"", project:null};
        this.downloadedReportHolder = {reviewPart:"", issuePart:"", planPart:"", project:null};
        this.unselectProjectButton();

        this.isPrevButtonPressed = false;
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
                    this.isApproveButtonValid = true;
                } else {
                    this.showEmptyReport();
                    this.tempReportHolder = {reviewPart:"", issuePart:"", planPart:"", project:null};
                    this.downloadedReportHolder = {reviewPart:"", issuePart:"", planPart:"", project:null};
                }
                this.selectedProjectName=this.selectedProject.projectName;
            });
    }

    onKeyReview(text: string) {
        let trimmedText = text;
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
        let trimmedText = text;
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
        let trimmedText = text;
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

    isDisabledApproveButton(): boolean {
        return this.isApproveButtonValid || this.switchOffApproveButton
            || this.isSelectedProjectDelayedOrReviewed()
            || this.isAllFieldEmpty()
            || this.isAnyPartChanged();
    }

    isDisabledLoadPrevButton(): boolean {
        return this.isPrevButtonPressed || this.showAggregated;
    }

    private isSelectedProjectDelayedOrReviewed(){
        return !this.downloadedReportHolder.project
            || this.downloadedReportHolder.project.state === 'Delayed'
            || this.downloadedReportHolder.project.state === 'Reviewed';
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

    private getReportsAndShow() {
        let currentAccount = this.getCurrentAccount();
        this.reportService.getReports(currentAccount.id)
            .subscribe(data => {
                if(data){
                    this.reports=data;
                    this.aggregateAndShowReports(this.reports);
                }
            });
    }

    private aggregateAndShowReports(reports:any[]){
        let sortedReports=reports;

        if(this.templateForProjectSorting.length>0){
            sortedReports=this.sortReports(reports, this.templateForProjectSorting);
        }

        let aggregatedReport = this.aggregateReports(sortedReports);
        this.showAggregatedReports(aggregatedReport);
    }

    private aggregateReports(reports:any[]): string{
        let aggregatedReport='<br><br>';
        for (let report of reports) {
            aggregatedReport=aggregatedReport+this.wrapEachReportInTags(report);
        }
        return aggregatedReport;
    }

    private showAggregatedReports(aggregatedReport:string) {
        this.htmlVariable=aggregatedReport;
        this.show=false;
        this.showAggregated=!this.show;
    }

    private wrapEachReportInTags(report:any): string {
        let name = '<h5><b>'+report.project.projectName+'</u></b></h5>';
        let review='<p>Review:</p>' +
            '<p>'+report.reviewPart+'</p>';
        let issues='<p>Issues:</p>' +
            '<p>'+report.issuePart+'</p>';
        let plans= '<p>Plans:</p>' +
            '<p>'+report.planPart+'</p>'+
            '<br>';
        return name+review+issues+plans;
    }

    private sortReports(reports:any[], templateForProjectSorting:number[]): any[] {
        let sortedReports=[];
        for (let id of templateForProjectSorting) {
            for (let report of reports) {
                if (id===report.project.projectId) {
                    sortedReports.push(report);
                    break;
                }
            }
        }
        return sortedReports;
    }

    private hideAggregatedReports() {
        this.show=true;
        this.showAggregated=!this.show;
    }

    private showThisReport(report: any){
        this.hideAggregatedReports();
        this.reportForm.patchValue({
            review : report.reviewPart,
            issues : report.issuePart,
            plans  : report.planPart
        });
    }

    private showEmptyReport() {
        this.hideAggregatedReports();
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

    //related to aggregate button click
    private unselectProjectButton() {
        let el=this.elementRef.nativeElement.getElementsByClassName('list-group-item selected');
        if(el[0]){
            el[0].setAttribute('class','list-group-item');
        }
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

