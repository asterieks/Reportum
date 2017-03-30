import {Injectable} from "@angular/core";
import {Http, Response, Headers, RequestOptions} from "@angular/http";
import {Observable} from "rxjs/Rx";
import {Report} from "./report.model";
import * as AppUtils from '../../utils/app.utils';

@Injectable()
export class ReportService{

    constructor(private http : Http){}

    addReports (body: any): Observable<number> {
        let bodyString = JSON.stringify(body);
        let headers = new Headers({ 'Content-Type': 'application/json' });
        let options = new RequestOptions({ headers: headers });

        return this.http.post(AppUtils.BACKEND_API_ROOT_URL + `/reports`, bodyString, options)
                        .map((res:Response) => res.status)
                        .catch((error:any) => Observable.throw(error.json().error || 'Server error'));
    }

    updateReports (projectId: number, body: any): Observable<Report[]> {
        let bodyString = JSON.stringify(body);
        let headers = new Headers({ 'Content-Type': 'application/json' });
        let options = new RequestOptions({ headers: headers });

        return this.http.put(AppUtils.BACKEND_API_ROOT_URL + `/projects/`+projectId+`/reports`, bodyString, options)
                        .map((res:Response) => res.json())
                        .catch((error:any) => Observable.throw(error.json().error || 'Server error'));
    }

    getReportByProjectId(projectId:number): Observable<Report[]>{
        let headers = new Headers({ 'Content-Type': 'application/json' });
        return this.http.get(AppUtils.BACKEND_API_ROOT_URL + `/projects/`+projectId+`/reports`, {headers: headers})
                                 .map((res:Response) => res.json())
                                 .catch((error:any) => Observable.throw(error.json().error || 'Server error'));
    }

    getReports(userId:string): Observable<any[]>{
        let headers = new Headers({ 'Content-Type': 'application/json' });
        return this.http.get(AppUtils.BACKEND_API_ROOT_URL + `/users/`+userId+`/reports`, {headers: headers})
                        .map((res:Response) => res.json())
                        .catch((error:any) => Observable.throw(error.json().error || 'Server error'));
    }
}