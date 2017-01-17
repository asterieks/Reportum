import { Component, OnInit } from '@angular/core';
import { NgFor }             from '@angular/common';
import { HttpModule }        from '@angular/http'
import { FormGroup, FormArray, FormBuilder, Validators } from '@angular/forms';

import { ReporterService }   from './reporter.service';
import { ReportService }     from '../common/report/report.service';

import { Button }            from '../common/button/button.model';
import { SelectLabel }       from '../common/select/select_label.model';
import { Report }            from '../common/report/report.model';
import { Project }           from '../common/project/project.model';

@Component({
    selector: 'reporter',
    template: require('./reporter.component.html'),
    styles: [require('./reporter.component.css')]
})

export class ReporterComponent implements OnInit {
    public reportForm: FormGroup;
    select_label: SelectLabel;
    button_models: Button[];
    project: Project;
    projectState: string;

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
        let report = {
                review: form.value.review,
                issues: form.value.issues,
                plans: form.value.plans,
                project: this.project.projectId
        };
        if(this.projectState==="Updated"){
            this.updateReport(report);
        } else {
            this.addReport(report);
        }
    }

    addReport(report: Report){
        this.reportService.add(report).subscribe(data=>this.projectState="Updated");
    }

    updateReport(report: Report){
        this.reportService.update(report).subscribe();
    }

    onChange(project:Project):void {
        this.project=project;
        this.projectState=project.state;
    }
}
