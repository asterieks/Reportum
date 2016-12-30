import { Injectable }  from '@angular/core';
import { Http, Response, Headers, RequestOptions } from '@angular/http';
import { Observable }  from 'rxjs/Rx';

import { Report }      from './report.model';

@Injectable()
export class ReportService{

    constructor(private http : Http){}

    addReport (body: Report): Observable<Report[]> {
        let bodyString = JSON.stringify(body);
        let headers      = new Headers({ 'Content-Type': 'application/json' });
        let options       = new RequestOptions({ headers: headers });
        return this.http.post(`/report`, bodyString, options)
                        .map((res:Response) => res.json())
                        .catch((error:any) => Observable.throw(error.json().error || 'Server error'));
    }
}