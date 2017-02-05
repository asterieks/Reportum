import {Component, Input, OnInit, Output, EventEmitter} from "@angular/core";
import {ProjectService} from "../project/project.service";
import {ReportService} from "../report/report.service";
import {SelectLabel} from "./select_label.model";
import {Project} from "../project/project.model";
import {DataService} from "../data/data.service";

@Component ({
    selector: 'select_component',
    template: require('./select_label.component.html'),
    styles: [require('./select_label.component.css')]
})

export class SelectComponent implements OnInit {
    projects: Array<Project> = [];


    @Input() select_label: SelectLabel;
    @Output() project_binder: EventEmitter<Project> = new EventEmitter<Project>();
    @Output() report_binder: EventEmitter<any> = new EventEmitter<any>();

    constructor (
        private projectService: ProjectService,
        private reportService: ReportService,
        public dataService: DataService){}

    ngOnInit() {
        this.loadUserProjects();
        this.loadUserReports();
    }

    private loadUserProjects() {
        this.projectService.getProjects(this.dataService.loginData.email).subscribe(data => {
            if (data) {
                this.projects = data;
                this.project_binder.emit(data[0]);
            }
        });
    }

    private loadUserReports() {
        this.reportService.getReports(this.dataService.loginData.email).subscribe(data => {
            if (data) {
                this.report_binder.emit(data);
            }
        });
    }

    onChange(selectedProjectId:number) {
        for (let project of this.projects) {
            if(project.projectId==selectedProjectId){
                this.project_binder.emit(project);
            }
        }
    }

}
