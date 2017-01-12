import { Injectable }             from '@angular/core';

import { GridLabel }              from '../common/grid/grid_label.model';
import { Button }                 from '../common/button/button.model';

import {ButtonComponent}          from "../common/button/button.component";
import {ClickableComponent}       from "../common/grid/clickable/clickable.component";
import {ClickableParentComponent} from "../common/grid/clickable/clickable.parent.component";


@Injectable()
export class ManagerService {
    private grid_label: GridLabel = { name: 'Actual projects' };
    private button_models: Button[] = [{name: 'Send', id:'manager_send_button'}];
    private projectsTableColumnDef = [
        {headerName: "Project name", valueGetter: 'data.projectName',  width: 210},
        {headerName: "Date",         valueGetter: 'data.actualDate',   width: 210},
        {   headerName: "State",
            cellRendererFramework: {component: ClickableParentComponent,dependencies: [ClickableComponent]},
            width: 90,
            cellStyle:{
                border:0
                // 'border-right-width': '1px',
                // 'border-right-style':'dotted',
                // 'border-right-color':'#808080'
            }
        }
    ];

    createColumnDefs() {
        return this.projectsTableColumnDef;
    };

    getGridLabel(): GridLabel {
        return this.grid_label;
    };

    getButton(): Button[]{
        return this.button_models;
    };
}
