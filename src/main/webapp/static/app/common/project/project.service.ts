import {Injectable} from "@angular/core";
import {HttpClient, HttpResponse} from "@angular/common/http";
import {Observable} from "rxjs/Rx";
import * as AppUtils from "../../utils/app.utils";
import {headers} from "../report/report.service";

@Injectable()
export class ProjectService{

    constructor(private http : HttpClient){}
    //to get all user projects
    getProjects(userId:string): Observable<any>{
        let url = AppUtils.BACKEND_API_ROOT_URL + `/users/`+userId+`/projects`;
        return this.http.get(url, {headers: headers, observe: 'response'})
                        .map((res:HttpResponse<any>) => res.body)
                        .catch((error:any) => Observable.throw(error.json().error || 'Server error'));
    }
    //to get specific project
    getProject(id:number):Observable<any> {
        let url = AppUtils.BACKEND_API_ROOT_URL + `/projects/`+id;
        return this.http.get(url, {headers: headers, observe: 'response'})
                        .map((res:HttpResponse<any>) => res.body)
                        .catch((error:any) => Observable.throw(error.json().error || 'Server error'));
    }
}

