import {Injectable} from "@angular/core";
import {HttpClient, HttpHeaders, HttpResponse} from "@angular/common/http";
import {Observable} from "rxjs/Rx";
import * as AppUtils from "../../utils/app.utils";

export const headers = new HttpHeaders({'Content-Type' : 'application/json'});

@Injectable()
export class ReportService{

    constructor(private http : HttpClient){}

    addReports (report: any): Observable<any> {
        let url = AppUtils.BACKEND_API_ROOT_URL + `/reports`;
        let body = JSON.stringify(report);
        return this.http.post(url, body, {headers: headers, observe: 'response'})
                        .map((res:HttpResponse<any>) => res.status)
                        .catch((error:any) => Observable.throw(error.json().error || 'Server error'));
    }

    updateReports (projectId: number, report: any): Observable<any> {
        let url = AppUtils.BACKEND_API_ROOT_URL + `/projects/`+projectId+`/reports`;
        let body = JSON.stringify(report);
        return this.http.put(url, body, {headers: headers, observe: 'response'})
                        .map((res:HttpResponse<any>) => res.status)
                        .catch((error:any) => Observable.throw(error.json().error || 'Server error'));
    }

    getReportByProjectId(projectId:number): Observable<any>{
        let url = AppUtils.BACKEND_API_ROOT_URL + `/projects/`+projectId+`/reports`;
        return this.http.get(url, {headers: headers, observe: 'response'})
                        .map((res:HttpResponse<any>) => res.body)
                        .catch((error:any) => Observable.throw(error.json().error || 'Server error'));
    }

    getPrevReportByProjectId(projectId:number): Observable<any>{
        let url = AppUtils.BACKEND_API_ROOT_URL + `/projects/`+projectId+`/prev/reports`;
        return this.http.get(url, {headers: headers, observe: 'response'})
                        .map((res:HttpResponse<any>) => res.body)
                        .catch((error:any) => Observable.throw(error.json().error || 'Server error'));
    }

    getReports(userId:string): Observable<any>{
        let url = AppUtils.BACKEND_API_ROOT_URL + `/users/`+userId+`/reports`;
        return this.http.get(url, {headers: headers, observe: 'response'})
                        .map((res:HttpResponse<any>) => res.body)
                        .catch((error:any) => Observable.throw(error.json().error || 'Server error'));
    }
}