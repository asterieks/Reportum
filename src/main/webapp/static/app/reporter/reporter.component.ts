import { Component, OnInit } from '@angular/core';
import { NgFor }             from '@angular/common';
import { HttpModule }        from '@angular/http'
import { FormGroup, FormArray, FormBuilder, Validators } from '@angular/forms';

import { ReporterService }   from './reporter.service';
import { ReportService }     from '../common/report/report.service';

import { Button }            from '../common/button/button.model';
import { SelectLabel }       from '../common/select/select_label.model';
import { Report }            from '../common/report/report.model';

@Component({
    selector: 'reporter',
    template: require('./reporter.component.html'),
    styles: [require('./reporter.component.css')]
})

export class ReporterComponent implements OnInit {
    public reportForm: FormGroup;
    select_label: SelectLabel;
    button_models: Button[];
    selectedProjectId: number;

    constructor(private reporterService: ReporterService,
                private fb: FormBuilder,
                private reportService: ReportService){}

    ngOnInit() {
        this.select_label = this.reporterService.getSelect();
        this.button_models = this.reporterService.getButton();
        this.reportForm = this.fb.group({
             review: ['', [<any>Validators.required, <any>Validators.minLength(5)]],
             issues: ['', [<any>Validators.required, <any>Validators.minLength(5)]],
              plans: ['', [<any>Validators.required, <any>Validators.minLength(5)]]
        });
    }

    onSubmit(form: any) {
        let report= {
            review: form.value.review,
            issues: form.value.issues,
            plans: form.value.plans,
            project: this.selectedProjectId
        };
        this.addReport(report);
    }

    addReport(report: Report){
        this.reportService.addReport(report).subscribe();
    }

    onChange(selectedProjectId:number):void {
        this.selectedProjectId=selectedProjectId;
    }
}
