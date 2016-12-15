import { Component, Input, OnInit } from '@angular/core';
import { SelectModel } from './select.fragment.model';
import { ProjectService } from '../project.service';
import { Project }  from '../models/project.model';
import { HttpModule } from '@angular/http';

@Component ({
    selector: 'select_model',
    template: require('./select.fragment.component.html'),
    styles: [require('./select.fragment.component.css')]
})

export class SelectComponent implements OnInit {
    errorMessage: string;
    multiple: boolean = false;
    options: Array<any> = [];
    options1: Array<any> = [];
    option;
    mode = 'Observable';

    constructor (private projectService: ProjectService) {}

    ngOnInit() {
        this.getUserProjects();
    }

    getUserProjects() {
        this.projectService.getUserProjects().
            map((tasks: Array<any>) =>
                {
                    if (tasks)
                        {
                            let numOptions = tasks.length;
                            let opts = new Array(numOptions);
                            for (let i = 0; i < numOptions; i++) {
                                opts[i] = {
                                        value: tasks[i].projectName.toString(),
                                        label: tasks[i].projectName.toString()
                                };
                            }
                            this.options = opts.slice(0);
                            this.option=this.options[0];

                        }
                    return tasks;
                }
            ).subscribe(result => this.options1 = result);

    }


    @Input() select_model: SelectModel;
}
