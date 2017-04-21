import {Component, OnInit, EventEmitter, Output, Input, OnChanges} from "@angular/core";
import {ProjectService} from "../project/project.service";
import {Project} from "../project/project.model";
import {Account} from "../../account/account";
import * as AppUtils from "../../utils/app.utils";

@Component ({
    selector: 'sortable_component',
    template: require('./sortable.component.html'),
    styles: [require('./sortable.component.css')]
})

export class SortableComponent implements OnInit, OnChanges{
    selectedProject: any;
    projects: any;
    @Input() submitTriggerCount:boolean;
    @Output() project_binder: EventEmitter<Project> = new EventEmitter<Project>();
    @Output() project_order_binder: EventEmitter<Project[]> = new EventEmitter<Project[]>();
    @Input() requester:string;

    constructor (
        private projectService: ProjectService) {}

    ngOnInit() {
        if(this.requester === 'reporter'){
            this.loadManagerProjectsAndPreselectEmptyOrFirstOne();
        } else {
            this.loadManagerProjects();
        }
    }

    onSelect(project: any): void {
        this.selectedProject = project;
        this.project_binder.emit(project);
    }

    onDrop(event){
        this.createTemplateAndSend();
    }

    ngOnChanges(...args: any[]) {
        if(args[0].submitTriggerCount.currentValue!=0){
            this.loadManagerProjects();
        }
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

    private loadManagerProjectsAndPreselectEmptyOrFirstOne() {
        let currentAccount = this.getCurrentAccount();
        this.projectService.getProjects(currentAccount.id)
            .subscribe(data => {
                if (data) {
                    this.projects = data;
                    this.createTemplateAndSend();
                    this.preSelectProject(this.findDelayedProjectOrReturnFirst(data));
                }
            });
    }

    private loadManagerProjects() {
        let currentAccount = this.getCurrentAccount();
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

    private preSelectProject(project: Project) {
        this.selectedProject = project;
        this.project_binder.emit(project);
    }

    private findDelayedProjectOrReturnFirst(projects: Project[]) {
        for (let project of projects) {
            if(project.state === 'Delayed'){
                return project;
            }
        }
        return projects[0];
    }
}