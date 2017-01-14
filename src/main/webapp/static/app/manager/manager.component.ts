import { Component, OnInit, EventEmitter } from '@angular/core';
import { NgFor }             from '@angular/common';
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
        this.reportService.updateReport(report).subscribe();
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
}
