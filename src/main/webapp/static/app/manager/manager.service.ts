import {Injectable} from "@angular/core";
import {GridLabel} from "../common/grid/grid_label.model";
import {Button} from "../common/button/button.model";
import {ClickableComponent} from "../common/grid/clickable/clickable.component";
import {ClickableParentComponent} from "../common/grid/clickable/clickable.parent.component";


@Injectable()
export class ManagerService {
    private grid_label: GridLabel = { name: 'Actual projects' };
    private button_models: Button[] = [
                                        {name: 'Save', id:'manager_save_button'},
                                        {name: 'Aggregate', id:'manager_aggregate_button'}
                                       ];
    private projectsTableColumnDef = [
        {headerName: "Project name", valueGetter: 'data.project.projectName',  width: 210},
        {headerName: "Actual state",         valueGetter: 'data.project.state',   width: 210},
        {   headerName: "State",
            cellRendererFramework: {component: ClickableParentComponent,dependencies: [ClickableComponent]},
            width: 90,
            cellStyle:{
                border:0
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
