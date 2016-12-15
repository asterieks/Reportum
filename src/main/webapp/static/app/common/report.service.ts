import { Injectable } from '@angular/core';
import { Http, Response, Headers, RequestOptions } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { ReportModel } from './models/report.model';

@Injectable()
export class ReportService{

constructor(private http : Http){}

    addReport (body: Object): Observable<ReportModel[]> {
        //TODO some trouble here
        let bodyString = JSON.stringify(body);
        let headers      = new Headers({ 'Content-Type': 'application/json' });
        let options       = new RequestOptions({ headers: headers });
        console.log("addReport");
        return this.http.post(`/report`, bodyString, options)
                         .map((res:Response) => res.json())
                         .catch((error:any) => Observable.throw(error.json().error || 'Server error'));
    }
}