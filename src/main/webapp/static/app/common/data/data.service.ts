import {Injectable} from "@angular/core";
import {Http, Response, Headers} from "@angular/http";
import {Observable} from "rxjs/Rx";

@Injectable()
export class DataService{

    public loginData;

    constructor(private http : Http){}

}