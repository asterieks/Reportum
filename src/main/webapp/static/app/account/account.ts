export class Account {
    id:string;
    fullName:string;
    profile:string;
    authorities:Array<string>;
    authenticated = true;
    constructor(account?:{id:string,fullName:string,profile:string,authorities:Array<string>}) {
        if(account) {
            this.id=account.id;
            this.profile=account.profile;
            this.fullName=account.fullName;
            this.authorities=account.authorities;
            this.authenticated = false;
        }
    }
}
