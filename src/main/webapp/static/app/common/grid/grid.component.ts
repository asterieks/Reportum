import {Component, Input} from "@angular/core";
import {ManagerService} from "../../manager/manager.service";
import {GridOptions} from "ag-grid/main";
import {GridLabel} from "./grid_label.model";
import {ReportService} from "../report/report.service";


@Component({
    selector: 'grid_component',
    template: require('./grid.component.html'),
    styles: [require('./grid_label.component.css')]
})
export class GridComponent {
    public gridOptions:GridOptions;

    @Input() grid_label: GridLabel;

    constructor ( private reportService: ReportService,
                  private managerService: ManagerService ) {}

    ngOnInit() {
        this.gridOptions = <GridOptions>{};
        this.gridOptions.columnDefs = this.managerService.createColumnDefs();
        this.getReports();
    }

    private getReports() {
        this.reportService.getReports("lead@gmail.com")
            .subscribe(data => this.gridOptions.api.setRowData(data));
    }
}
