import {Component, OnInit, EventEmitter, Output} from "@angular/core";
import {ProjectService} from "../project/project.service";
import {Project} from "../project/project.model";
import {Account} from "../../account/account";
import * as AppUtils from "../../utils/app.utils";

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

    defineColorBy(projectState: string): any {
        if(projectState==='Reported'){
            return {'background-color': 'rgba(255,144,43,0.7)'};
        } else if (projectState==='Reviewed'){
            return {'background-color': 'rgba(39,194,76,0.7)'};
        }
        return {'background-color': 'rgba(240,80,80,0.7)'};
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
        let currentAccount = this.getCurrentAccount();
        console.log('+SortableComponent: try to load projects for user '+ currentAccount.id);
        this.projectService.getProjects(currentAccount.id)
            .subscribe(data => {
                if (data) {
                    this.projects = data;

                    this.createTemplateAndSend();
                }
        });
    }

    private getCurrentAccount(){
        return new Account(JSON.parse(localStorage.getItem(AppUtils.STORAGE_ACCOUNT_TOKEN)));
    }
}