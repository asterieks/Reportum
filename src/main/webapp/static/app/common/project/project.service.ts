import {Injectable} from "@angular/core";
import {Http, Response, Headers} from "@angular/http";
import {Observable} from "rxjs/Rx";
import {Project} from "./project.model";
import * as AppUtils from '../../utils/app.utils';

@Injectable()
export class ProjectService{

    constructor(private http : Http){}
    //to get all user projects
    getProjects(userId:string): Observable<Project[]>{
        let headers = new Headers({ 'Content-Type': 'application/json' });
        return this.http.get(AppUtils.BACKEND_API_ROOT_URL + `/users/`+userId+`/projects`, {headers: headers})
                                 .map((res:Response) => res.json())
                                 .catch((error:any) => Observable.throw(error.json().error || 'Server error'));
    }
    //to get specific project
    getProject(id:number): Observable<Project>{
        let headers = new Headers({ 'Content-Type': 'application/json' });
        return this.http.get(AppUtils.BACKEND_API_ROOT_URL + `/projects/`+id, {headers: headers})
            .map((res:Response) => res.json())
            .catch((error:any) => Observable.throw(error.json().error || 'Server error'));
    }
}

