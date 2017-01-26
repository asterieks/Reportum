import {Injectable} from "@angular/core";
import {Http, Response, Headers} from "@angular/http";
import {Observable} from "rxjs/Rx";
import {Project} from "./project.model";

@Injectable()
export class ProjectService{

    constructor(private http : Http){}

    getProjects(userId:string): Observable<Project[]>{
        let headers = new Headers({ 'Content-Type': 'application/json' });
        return this.http.get(`/users/`+userId+`/projects`, {headers: headers}) //asterieks@gmail.com
                                 .map((res:Response) => res.json())
                                 .catch((error:any) => Observable.throw(error.json().error || 'Server error'));
    }
}

