import { Injectable }             from '@angular/core';
import { Http, Response, Headers} from '@angular/http';
import { Observable }             from 'rxjs/Rx';

import { Project }                from './project.model';

@Injectable()
export class ProjectService{

    constructor(private http : Http){}

    getUserProjects(): Observable<Project[]>{
        let headers = new Headers({ 'Content-Type': 'application/json' });
        return this.http.get(`/projects/reporter/asterieks@gmail.com`, {headers: headers})
                                 .map((res:Response) => res.json())
                                 .catch((error:any) => Observable.throw(error.json().error || 'Server error'));
    }

    getManagerProjects(): Observable<Project[]>{
        let headers = new Headers({ 'Content-Type': 'application/json' });
        return this.http.get(`/projects/lead/lead@gmail.com`, {headers: headers})
                                 .map((res:Response) => res.json())
                                 .catch((error:any) => Observable.throw(error.json().error || 'Server error'));
    }
    //    headers.append('Accept', 'application/json');
}

