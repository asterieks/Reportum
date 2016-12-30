import { Component, Input, OnInit, Output, EventEmitter } from '@angular/core';
import { HttpModule }     from '@angular/http';

import { ProjectService } from '../project/project.service';

import { SelectLabel }    from './select.label.model';
import { Project }        from '../project/project.model';

@Component ({
    selector: 'select_model',
    template: require('./select.fragment.component.html'),
    styles: [require('./select.fragment.component.css')]
})

export class SelectComponent implements OnInit {
    projects: Array<Project> = [];

    @Input() select_model: SelectLabel;
    @Output() binder: EventEmitter<number> = new EventEmitter<number>();

    constructor (private projectService: ProjectService) {}

    ngOnInit() {
        this.getUserProjects();
    }

    getUserProjects() {
        this.projectService.getUserProjects().map((projects: Array<Project>) =>
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
