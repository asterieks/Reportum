import {Injectable}               from "@angular/core";
import { Http, Response, Headers} from '@angular/http';
import { Observable }             from 'rxjs/Rx';

@Injectable()
export class LoginService{
     constructor(private http : Http){}

getUserCredential(username:string): Observable<any[]>{
     let headers = new Headers({ 'Content-Type': 'application/json' });
     return this.http.get(`/login/`+username, {headers: headers})
                .map((res:Response) => res.json())
                .do(val => console.log(val));
    }
}
