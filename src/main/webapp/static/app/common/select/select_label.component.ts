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
    @Output() binder: EventEmitter<number> = new EventEmitter<number>();

    constructor (private _projectService: ProjectService) {}

    ngOnInit() {
        this.getUserProjects();
    }

    getUserProjects() {
        this._projectService.getUserProjects().map((projects: Array<Project>) =>
            {
                if (projects) {
                    this.projects = projects;
                    this.binder.emit(projects[0].projectId);
                }
            }
        ).subscribe();
    }

    onChange(selectedProjectId:number) {
        this.binder.emit(selectedProjectId);
    }
}
