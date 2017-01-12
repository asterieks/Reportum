import { Component, Input, Output, EventEmitter }  from '@angular/core';

import { ProjectService }    from '../project/project.service';
import { ManagerService }    from '../../manager/manager.service';

import { GridOptions }       from 'ag-grid/main';
import { GridLabel }         from './grid_label.model';


@Component({
    selector: 'grid_component',
    template: require('./grid.component.html'),
    styles: [require('./grid_label.component.css')]
})
export class GridComponent {
    public gridOptions:GridOptions;

    @Input() grid_label: GridLabel;

    constructor ( private _projectService: ProjectService, private _managerService: ManagerService ) {}

    ngOnInit() {
        this.gridOptions = <GridOptions>{};
        this.gridOptions.columnDefs = this._managerService.createColumnDefs();
        this.getManagerProjects();
    }

    getManagerProjects() {
        this._projectService.getManagerProjects()
            .subscribe(data => this.gridOptions.api.setRowData(data));
    }
}
