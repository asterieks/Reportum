import { Component, Output, EventEmitter } from '@angular/core';

import { ReportService }       from '../../report/report.service';
import { SharedService }       from '../../shared.service';

import { AgRendererComponent } from 'ag-grid-ng2/main';

@Component({
    selector: 'clickable-cell',
    template: `
    <ag-clickable (eventEmmiter)="onClick($event)" [cell]="cell"></ag-clickable>
    `
})

export class ClickableParentComponent implements AgRendererComponent {
    private params:any;
    public cell:any;

    constructor(private reportService: ReportService, private sharedService: SharedService){}

    agInit(params:any):void {
        this.params = params;
        this.cell = {reportId: params.data.reportId, date: params.data.actualDate};
    }

    onClick(reportId:number):void {
        this.reportService.getReporterReport(reportId).subscribe(data => this.load(data));
    }

    load(report:any){
        this.sharedService.loadReport(report);
    }
}
