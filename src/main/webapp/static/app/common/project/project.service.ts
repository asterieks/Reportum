import { Injectable }             from '@angular/core';
import { Http, Response, Headers} from '@angular/http';
import { Observable }             from 'rxjs/Rx';

import { Project }                from './project.model';

@Injectable()
export class ProjectService{

    constructor(private http : Http){}

    getUserProjects(): Observable<Project[]>{
        let projects$ = this.http.get(`/projects/reporter/asterieks@gmail.com`, {headers: this.getHeaders()})
                                 .map(mapProjects)
                                 .catch(handleError);
        return projects$;
    }

    private getHeaders(){
        let headers = new Headers();
        headers.append('Accept', 'application/json');
        return headers;
    }
}

function mapProjects(response:Response): Project[]{
    return response.json();
}

function handleError (error: any) {
    let errorMsg = error.message || `Yikes! There was was a problem with our hyperdrive device and we couldn't retrieve your data!`
    return Observable.throw(errorMsg);
}

