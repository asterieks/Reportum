export class SecurityToken {
    publicSecret:string;
    securityLevel:string;
    constructor(token:{publicSecret:string,securityLevel:string}) {
        if(token){
            this.publicSecret=token.publicSecret;
            this.securityLevel=token.securityLevel;
        }
    }
    isEncoding(encoding:string):boolean {
        return this.securityLevel
            && this.securityLevel === encoding;
    }
}
