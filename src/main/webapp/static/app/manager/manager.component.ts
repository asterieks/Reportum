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

    constructor(private _managerService: ManagerService,
                private _fb: FormBuilder,
                private _reportService: ReportService,
                private sharedService:SharedService){}

    ngOnInit() {
        this.grid_label = this._managerService.getGridLabel();
        this.button_models = this._managerService.getButton();
        this.reportForm = this._fb.group({
             review: ['', [<any>Validators.required, <any>Validators.minLength(5)]],
             issues: ['', [<any>Validators.required, <any>Validators.minLength(5)]],
              plans: ['', [<any>Validators.required, <any>Validators.minLength(5)]]
        });
        this.sharedService.reportLoadedEvent.subscribe(data => this.loadForm(data));
    }

    private onSubmit(model: any) {
        let report= {
            review: model.value.review,
            issues: model.value.issues,
            plans:  model.value.plans,
            project:this.selectedProjectId
        };
        this.addReport(report);
    }

    addReport(report: Report){
        this._reportService.addLeadReport(report).subscribe();
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
