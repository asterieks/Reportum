import {Component, OnInit} from "@angular/core";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Project} from "../common/project/project.model";
import {ToastyService, ToastyConfig, ToastOptions, ToastData} from "ng2-toasty";
import {AdminService} from "../service/admin.service";
import {RoleValidator} from "../validators/RoleValidator";
import {EmailValidator} from "../validators/EmailValidator";

export class PropertyHolder {
    auth:string;
    debug:string;
    host:string;
    port:string;
    protocol:string;
    starttls:string;
    managerEmail:string;
    adminEmail:string;
    jobNotificationTitle:string;
    updateJobMessage:string;
    checkJobMessage:string;
    checkJobTime:string;
    resetStateTime:string;
}

export class User {
     id:string;
     fullName:string;
     profile:string;
     password:string;
}

@Component({
    selector: 'admin',
    templateUrl: '../static/app/admin/admin.component.html',
    styleUrls: ['../static/app/admin/admin.component.css'],
})

export class AdminComponent implements OnInit {

    configTabs: string[] = ["Users", "Projects", "Properties", "Logs"];
    showUsersFlag: boolean = true;
    showProjectsFlag: boolean = false;
    showPropsFlag: boolean = false;
    showLogsFlag: boolean = false;
    users: User[] = [];
    projects: Project[] = [];
    properties: PropertyHolder;
    addUserForm:FormGroup;
    addProjectForm:FormGroup;
    selectedTab: string;

    constructor(private fb: FormBuilder,
                private adminService: AdminService,
                private toastyService:ToastyService,
                private toastyConfig: ToastyConfig){
        this.toastyConfig.theme = 'bootstrap';
        this.addUserForm = fb.group({
            useremeil: ['', Validators.required,EmailValidator.invalidEmail],
            username: ['', Validators.required],
            userrole: ['', Validators.required, RoleValidator.invalidRole],
            userpassword: ['', Validators.required]
        });
    }

    ngOnInit() {
        this.selectedTab = this.configTabs[0];
        this.getUsers();
    }

    onTabSelect(tabName: string) {
        if("Users"==tabName){
            this.getUsers();
        } else if("Projects"==tabName) {
            this.addProjectForm = this.fb.group({
                projectname: ['', Validators.required],
                manager: ['', Validators.required,EmailValidator.invalidEmail],
                teamlead: ['', Validators.required,EmailValidator.invalidEmail],
                reporter: ['', Validators.required,EmailValidator.invalidEmail]
            });
            this.getProjects();
        } else if("Properties"==tabName) {
            this.getProperties();
        } else if("Logs"==tabName) {
            this.showLogsFlag = true;
            this.showPropsFlag = false;
            this.showUsersFlag = false;
            this.showProjectsFlag = false;
        } else {
            console.log("Error on admin page!");
        }
        this.selectedTab = tabName;
    }

    onRemoveProject(projectId){
        console.log(projectId);
        this.adminService.deleteProject(projectId).subscribe(data => {
            console.log("Status " + data);
            if(data===200){
                this.getProjects();
                this.showSuccessToast("The project is removed.");
            } else {
                this.showErrorToast("The error is ocured while project removing!");
            }
        });
    }

    onRemoveUser(userId){
        console.log(userId);
        this.adminService.deleteUser(userId).subscribe(data => {
            console.log("Status " + data);
            if(data===200){
                this.getUsers();
                this.showSuccessToast("The user is removed.");
            } else {
                this.showErrorToast("The error is ocured while user removing!");
            }
        });
    }

    addNewUser(form){
        let user: User = {
            id: form.value.useremeil,
            fullName: form.value.username,
            profile:  form.value.userrole,
            password: form.value.userpassword
        };
        this.adminService.addUser(user).subscribe(data => {
            console.log("Status " + data);
            if(data===200){
                this.getUsers();
                this.showSuccessToast("The user is added.");
                this.addUserForm.reset();
            } else {
                this.showErrorToast("The error is ocurred while user adding!");
            }

        });
    }

    addNewProject(form){
        let reporter: User = {
            id: form.value.reporter,
            fullName:null,
            profile:null,
            password:null
        };
        let manager: User = {
            id: form.value.manager,
            fullName:null,
            profile:null,
            password:null
        };
        let teamLead: User = {
            id: form.value.teamlead,
            fullName:null,
            profile:null,
            password:null
        };
        let project: Project = {
            projectId : null,
            projectName: form.value.projectname,
            reporter: reporter,
            teamLeader:  teamLead,
            manager: manager,
            state: null,
            stateDate: null
        };
        this.adminService.addProject(project).subscribe(data => {
            console.log("Status " + data);
            if(data===200){
                this.getProjects();
                this.showSuccessToast("The project is added.");
                this.addProjectForm.reset();
            } else {
                this.showErrorToast("The error is ocurred while project adding!");
            }

        });
    }

    getUsers(){
        this.adminService.getUsers().subscribe(data => {
                if (data) {
                    this.users = data;
                    this.showUsersFlag = true;
                    this.showProjectsFlag = false;
                    this.showPropsFlag = false;
                    this.showLogsFlag = false;
                }
            }
        )
    }

    getProjects(){
        this.adminService.getProjects().subscribe(data => {
                if (data) {
                    this.projects = data;
                    this.showProjectsFlag = true;
                    this.showUsersFlag = false;
                    this.showPropsFlag = false;
                    this.showLogsFlag = false;
                }
            }
        )
    }

    getProperties(){
        this.adminService.getProperties().subscribe(data => {
                if (data) {
                    this.properties = data;
                    this.showPropsFlag = true;
                    this.showUsersFlag = false;
                    this.showProjectsFlag = false;
                    this.showLogsFlag = false;
                }
            }
        )
    }

    private showSuccessToast(msg: string) {
        var toastOptions:ToastOptions = {
            title: "Success",
            msg: msg,
            showClose: true,
            timeout: 5000,
            theme: 'bootstrap',
            onAdd: (toast:ToastData) => {},
            onRemove: function(toast:ToastData) {}
        };
        this.toastyService.success(toastOptions);
    }

    private showErrorToast(msg: string) {
        var toastOptions:ToastOptions = {
            title: "Error",
            msg: msg,
            showClose: true,
            timeout: 5000,
            theme: 'bootstrap',
            onAdd: (toast:ToastData) => {},
            onRemove: function(toast:ToastData) {}
        };
        this.toastyService.error(toastOptions);
    }
}

function isEmailValid(control) {
    return control => {
        var regex = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
        return regex.test(control.value) ? null : { invalidEmail: true };
    }
}

function isRoleValid(control) {
    let role = control.value;
    return role === 'ADMIN' || role === 'MANAGER' || role === 'REPORTER' || role === 'LEAD' ? null : { userrole: true };
}

