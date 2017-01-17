import { Component, Input, OnInit, Output, EventEmitter } from '@angular/core';

import { ProjectService } from '../project/project.service';

import { SelectLabel }    from './select_label.model';
import { Project }        from '../project/project.model';

@Component ({
    selector: 'select_component',
    template: require('./select_label.component.html'),
    styles: [require('./select_label.component.css')]
})

export class SelectComponent implements OnInit {
    projects: Array<Project> = [];

    @Input() select_label: SelectLabel;
    @Output() binder: EventEmitter<Project> = new EventEmitter<Project>();

    constructor (private _projectService: ProjectService) {}

    ngOnInit() {
        this.getUserProjects();
    }

    getUserProjects() {
        this._projectService.getUserProjects().subscribe(data => {
            if (data) {
                this.projects = data;
                this.binder.emit(data[0]);
            }
        });
    }

    onChange(selectedProjectId:number) {
        for (let project of this.projects) {
            if(project.projectId==selectedProjectId){
                this.binder.emit(project);
            }
        }
    }
}
