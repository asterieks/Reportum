import {Component, OnInit, ElementRef} from "@angular/core";
import {FormGroup, FormBuilder, Validators} from "@angular/forms";
import {ReportService} from "../common/report/report.service";
import {Project} from "../common/project/project.model";
import {ProjectService} from "../common/project/project.service";
import * as AppUtils from "../utils/app.utils";
import {Account} from "../account/account";

@Component({
    selector: 'manager',
    template: require('./manager.component.html'),
    styles: [require('./manager.component.css')]
})

export class ManagerComponent implements OnInit {
    public reportForm: FormGroup;
    selectedProject: any;
    projectState: string;
    show = false;
    showAggregated=true;
    reports:any;
    htmlVariable: string;
    isSaveButtonValid:boolean=false;
    templateForProjectSorting: number[]=[];

    constructor(private fb: FormBuilder,
                private reportService: ReportService,
                private projectService: ProjectService,
                private elementRef:ElementRef){}

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

    onAggregateButtonClick(event){
        event.preventDefault();
        this.getReportsAndShow()
        this.isSaveButtonValid=false;
        this.unselectProjectButton();
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

    private getReportsAndShow() {
        let currentAccount = this.getCurrentAccount();
        this.reportService.getReports(currentAccount.id)
            .subscribe(data => {
                if(data){
                    this.reports=data;
                    this.aggregateAndShowReports(data);
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

    //related to projectSelect event
    private displayReport(report:any[]) {
        this.hideAggregatedReports();
        if(report){
            this.showThisReport(report);
        } else{
            this.showEmptyReport();
        }
    }

    private hideAggregatedReports() {
        this.show=true;
        this.showAggregated=!this.show;
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
}

