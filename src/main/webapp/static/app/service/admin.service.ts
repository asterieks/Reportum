import {Injectable} from "@angular/core";
import {HttpClient, HttpHeaders, HttpResponse} from "@angular/common/http";
import {Observable} from "rxjs/Rx";
import * as AppUtils from "../utils/app.utils";
import {User} from "../admin/admin.component";
import {Project} from "../common/project/project.model";


export const headers = new HttpHeaders({'Content-Type' : 'application/json'});

@Injectable()
export class AdminService{

    constructor(private http : HttpClient){}

    getUsers(): Observable<User[]>{
        let url = AppUtils.BACKEND_API_ROOT_URL + AppUtils.BACKEND_ADMIN_PATH + `/users`;
        return this.http.get(url, {headers: headers, observe: 'response'})
                        .map((res:HttpResponse<any>) => res.body)
                        .catch((error:any) => Observable.throw(error.json().error || 'Server error'));
    }

    getProjects(): Observable<Project[]>{
        let url =  AppUtils.BACKEND_API_ROOT_URL + AppUtils.BACKEND_ADMIN_PATH + `/projects`;
        return this.http.get(url, {headers: headers, observe: 'response'})
            .map((res:HttpResponse<any>) => res.body)
            .catch((error:any) => Observable.throw(error.json().error || 'Server error'));
    }

    getProperties(): Observable<any>{
        let url =  AppUtils.BACKEND_API_ROOT_URL + AppUtils.BACKEND_ADMIN_PATH + `/properties`;
        return this.http.get(url, {headers: headers, observe: 'response'})
            .map((res:HttpResponse<any>) => res.body)
            .catch((error:any) => Observable.throw(error.json().error || 'Server error'));
    }

    deleteUser(id:string): Observable<any>{
        let url =  AppUtils.BACKEND_API_ROOT_URL + AppUtils.BACKEND_ADMIN_PATH + `/users/`+id;
        return this.http.delete(url, {headers: headers, observe: 'response'})
            .map((res:HttpResponse<any>) => res.status)
            .catch((error:any) => Observable.throw('Server error'));
    }

    deleteProject(id:number): Observable<any>{
        let url =  AppUtils.BACKEND_API_ROOT_URL + AppUtils.BACKEND_ADMIN_PATH + `/projects/`+id;
        return this.http.delete(url, {headers: headers, observe: 'response'})
            .map((res:HttpResponse<any>) => res.status)
            .catch((error:any) => Observable.throw('Server error'));
    }

    addUser (user: User): Observable<any> {
        let url =  AppUtils.BACKEND_API_ROOT_URL + AppUtils.BACKEND_ADMIN_PATH + `/users`;
        let body = JSON.stringify(user);
        return this.http.post(url, body, {headers: headers, observe: 'response'})
            .map((res:HttpResponse<any>) => res.status)
            .catch((error:any) => Observable.throw(error.json().error || 'Server error'));
    }

    addProject (project: Project): Observable<any> {
        let url =  AppUtils.BACKEND_API_ROOT_URL + AppUtils.BACKEND_ADMIN_PATH + `/projects`;
        let body = JSON.stringify(project);
        return this.http.post(url, body, {headers: headers, observe: 'response'})
            .map((res:HttpResponse<any>) => res.status)
            .catch((error:any) => Observable.throw(error.json().error || 'Server error'));
    }
}