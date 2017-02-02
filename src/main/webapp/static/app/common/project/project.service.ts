import {Injectable} from "@angular/core";
import {Http, Response, Headers} from "@angular/http";
import {Observable} from "rxjs/Rx";
import {Project} from "./project.model";

@Injectable()
export class ProjectService{

    constructor(private http : Http){}
    //to get all user projects
    getProjects(userId:string): Observable<Project[]>{
        let headers = new Headers({ 'Content-Type': 'application/json' });
        return this.http.get(`/users/`+userId+`/projects`, {headers: headers}) //asterieks@gmail.com
                                 .map((res:Response) => res.json())
                                 .catch((error:any) => Observable.throw(error.json().error || 'Server error'));
    }
    //to get specific project
    getProject(id:number): Observable<Project>{
        let headers = new Headers({ 'Content-Type': 'application/json' });
        return this.http.get(`/projects/`+id, {headers: headers})
            .map((res:Response) => res.json())
            .catch((error:any) => Observable.throw(error.json().error || 'Server error'));
    }
}

