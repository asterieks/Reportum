import { Component, OnInit, EventEmitter } from '@angular/core';
import { NgFor, NgIf }       from '@angular/common';
import { HttpModule }        from '@angular/http'
import { FormGroup, FormArray, FormBuilder, Validators } from '@angular/forms';

import { ManagerService }    from './manager.service';
import { ReportService }     from '../common/report/report.service';
import { SharedService }     from "../common/shared.service";

import { Button }            from '../common/button/button.model';
import { GridLabel }         from '../common/grid/grid_label.model';
import { Report }            from '../common/report/report.model';

@Component({
    selector: 'manager',
    template: require('./manager.component.html'),
    styles: [require('./manager.component.css')]
})

export class ManagerComponent implements OnInit {
    public reportForm: FormGroup;
    grid_label: GridLabel;
    button_models: Button[];
    selectedProjectId: number;
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
        let report= {
            review: form.value.review,
            issues: form.value.issues,
            plans:  form.value.plans,
            project:this.selectedProjectId
        };
        this.updateReport(report);
    }

    updateReport(report: Report){
        this.reportService.update(report).subscribe();
    }

    loadForm(report: any){
        this.reportForm.patchValue({
            review : report.review,
            issues : report.issues,
            plans  : report.plans
        });
        this.selectedProjectId=report.project;
        this.selectedReportId =report.reportId;
    }

    aggregateReports(show:boolean){
        this.reportService.get('lead@gmail.com').subscribe(data => this.showAggregatedReport(data));
        this.show=show;
        this.showAggregated=!show;
    }

    showAggregatedReport(reports:any[]){
        let temp='';
        for (let report of reports) {
            let name='--'+report.projectId.projectName+'--'+'\r\n';
            let review='Review:\r\n'+report.reviewPart+'\r\n';
            let issues='Issues:\r\n'+report.issuePart+'\r\n';
            let plans='Plans:\r\n'+report.planPart+'\r\n';
            let end='\r\n';
            temp=temp+name+review+issues+plans+end;
        }
        this.agregated_report=temp;
    }
}

