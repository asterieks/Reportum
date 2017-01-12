import { Component, OnInit } from '@angular/core';
import { NgFor }             from '@angular/common';
import { HttpModule }        from '@angular/http'
import { FormGroup, FormArray, FormBuilder, Validators } from '@angular/forms';

import { HomeService }       from './home.service';
import { ReportService }     from '../common/report/report.service';

import { Button }            from '../common/button/button.model';
import { SelectLabel }       from '../common/select/select_label.model';
import { Report }            from '../common/report/report.model';

@Component({
    selector: 'home',
    template: require('./home.component.html'),
    styles: [require('./home.component.css')]
})

export class HomeComponent implements OnInit {
    public reportForm: FormGroup;
    select_label: SelectLabel;
    button_models: Button[];
    selectedProjectId: number;

    constructor(private _homeService: HomeService, private _fb: FormBuilder, private _reportService: ReportService){}

    ngOnInit() {
        this.select_label = this._homeService.getSelect();
        this.button_models = this._homeService.getButton();
        this.reportForm = this._fb.group({
             review: ['', [<any>Validators.required, <any>Validators.minLength(5)]],
             issues: ['', [<any>Validators.required, <any>Validators.minLength(5)]],
              plans: ['', [<any>Validators.required, <any>Validators.minLength(5)]]
        });
    }

    save(model: any) {
        let report= {
            review: model.value.review,
            issues: model.value.issues,
            plans: model.value.plans,
            project: this.selectedProjectId
        };
        this.addReport(report);
    }

    addReport(report: Report){
        this._reportService.addReport(report).subscribe();
    }

    onChange(selectedProjectId:number):void {
        this.selectedProjectId=selectedProjectId;
    }
}
