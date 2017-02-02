import {Component, OnInit, EventEmitter, Output} from "@angular/core";
import {ProjectService} from "../project/project.service";
import {ReportService} from "../report/report.service";
import {Project} from "../project/project.model";

@Component ({
    selector: 'sortable_component',
    template: require('./sortable.component.html'),
    styles: [require('./sortable.component.css')]
})

export class SortableComponent implements OnInit{
    selectedProject: any;
    projects: any;
    @Output() project_binder: EventEmitter<Project> = new EventEmitter<Project>();
    @Output() project_order_binder: EventEmitter<Project[]> = new EventEmitter<Project[]>();

    constructor (
        private projectService: ProjectService) {}

    ngOnInit() {
        this.loadManagerProjects();
    }

    onSelect(project: any): void {
        this.selectedProject = project;
        this.project_binder.emit(project);
    }

    onDrop(event){
        this.createTemplateAndSend();
    }

    private createTemplateAndSend() {
        let template=this.createTemplateForSorting();
        this.project_order_binder.emit(template);
    }

    private createTemplateForSorting() {
        let template=[];
        for (let project of this.projects) {
            template.push(project.projectId);
        }
        return template;
    }

    private loadManagerProjects() {
        this.projectService.getProjects("lead@gmail.com")
            .subscribe(data => {
                if (data) {
                    this.projects = data;

                    this.createTemplateAndSend();
                }
        });
    }




}