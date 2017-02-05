import {Component, OnInit, ElementRef} from "@angular/core";
import {FormGroup, FormBuilder, Validators} from "@angular/forms";
import {ReportService} from "../common/report/report.service";
import {Project} from "../common/project/project.model";
import {ProjectService} from "../common/project/project.service";
import {DataService} from "../common/data/data.service";

@Component({
    selector: 'manager',
    template: require('./manager.component.html'),
    styles: [require('./manager.component.css')]
})
//refactored!!!
export class ManagerComponent implements OnInit {
    public reportForm: FormGroup;
    selectedProject: any;
    projectState: string;
    show = false;
    showAggregated=true;
    reports:any;
    htmlVariable: string
    isSaveButtonValid:boolean=false;
    templateForProjectSorting: number[]=[];

    constructor(private fb: FormBuilder,
                private reportService: ReportService,
                private projectService: ProjectService,
                private elementRef:ElementRef,
                private dataService: DataService){}

    ngOnInit() {
        this.initForm();
        this.getReportsAndShow();
    }

    onSubmit(form: any) {
        let reportToUpdate = {
                reviewPart: form.value.review,
                issuePart: form.value.issues,
                planPart:  form.value.plans,
                project: this.selectedProject,
                reportedBy: this.dataService.loginData.email
        };
        this.checkProjectIfUpdatedAndSaveReport(reportToUpdate);
    }

    onProjectSelect(project: Project){
        this.selectedProject=project;
        this.reportService.getReports(this.dataService.loginData.email)
            .subscribe(data => {
                this.reports=data;
                this.isSaveButtonValid=true;
                this.findSpecificReportAndShow(data);
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
        this.reportService.getReports(this.dataService.loginData.email)
            .subscribe(data => {
                this.reports=data;
                this.aggregateAndShowReports(data);
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
        let name = '<h5><b><u>Project '+report.project.projectName+'</u></b></h5>';
        let review='<p><b><u>Review:</u></b></p>' +
            '<p>'+report.reviewPart+'</p>';
        let issues='<p><b><u>Issues:</u></b></p>' +
            '<p>'+report.issuePart+'</p>';
        let plans= '<p><b><u>Plans:</u></b></p>' +
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
    private findSpecificReportAndShow(reports:any[]) {
        let report=this.findSpecificReport(this.selectedProject);
        this.hideAggregatedReports();
        if(report){
            this.showSpecificReport(report);
        } else{
            this.showEmptyReport();
        }
    }

    private findSpecificReport(project:Project): any {
        for (let report of this.reports) {
            if(report.project.projectId===project.projectId){
                return report;
            }
        }
        return null;
    }

    private hideAggregatedReports() {
        this.show=true;
        this.showAggregated=!this.show;
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
        this.reportService.getReports(this.dataService.loginData.email).subscribe(reports => {
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

    //related to aggregate button click
    private unselectProjectButton() {
        let el=this.elementRef.nativeElement.getElementsByClassName('list-group-item selected');
        if(el[0]){
            el[0].setAttribute('class','list-group-item');
        }
    }
}

