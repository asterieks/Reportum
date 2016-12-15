import { Component, OnInit } from '@angular/core';
import { NgFor }             from '@angular/common';
import { HttpModule }        from '@angular/http'
import { FormGroup, FormArray, FormBuilder, Validators } from '@angular/forms';

import { SelectComponent }   from '../common/select/select.fragment.component';

import { HomeService }       from './home.service';
import { ReportService }     from '../common/report.service';

import { ButtonModel }       from '../common/button/button.model';
import { SelectModel }       from '../common/select/select.fragment.model';
import { ReportModel }       from '../common/models/report.model';


@Component({
    selector: 'home',
    template: require('./home.component.html'),
    styles: [require('./home.component.css')]
})

export class HomeComponent implements OnInit {
    select_model: SelectModel;
    button_models: ButtonModel[];
    public reportForm: FormGroup;

    constructor(private _homeService: HomeService, private _fb: FormBuilder, private _reportService: ReportService){}

    ngOnInit() {
        this.select_model = this._homeService.getSelect();
        this.button_models = this._homeService.getButton();
        this.reportForm = this._fb.group({
             review: ['', [<any>Validators.required, <any>Validators.minLength(5)]],
             issues: ['', [<any>Validators.required, <any>Validators.minLength(5)]],
              plans: ['', [<any>Validators.required, <any>Validators.minLength(5)]]
        });
    }

    save(model: any) {
        console.log("save");
        let report= {
            review: model.value.review,
            issues: model.value.issues,
            plans: model.value.plans,
            reporter: 'asterieks@gmail.com'
        };
        this.addReport(report);
    }

    addReport(report: ReportModel){
        this._reportService.addReport(report).subscribe((dataResponse) => {
            console.log("exec");
        });
    }
}
