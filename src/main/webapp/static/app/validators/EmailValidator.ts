import {AbstractControl} from '@angular/forms';
export class EmailValidator {

    static invalidEmail(control: AbstractControl): {[key: string]: any} {
        return new Promise( resolve => {
            let email = control.value;
            let pattern = /^[_a-z0-9]+(\.[_a-z0-9]+)*@[a-z0-9-]+(\.[a-z0-9-]+)*(\.[a-z]{2,4})$/;
            if (!pattern.test(email)) {
                resolve({invalidEmail: true});
            } else {
                resolve(null);
            }
        });
    }
}