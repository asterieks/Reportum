
import {AbstractControl} from '@angular/forms';
export class RoleValidator {

    static invalidRole(control: AbstractControl): {[key: string]: any} {
        return new Promise( resolve => {
            let role = control.value;
            if (role != 'ADMIN' && role != 'MANAGER'  && role != 'LEAD'  && role != 'REPORTER' ) {
                resolve({invalidRole: true});
            } else {
                resolve(null);
            }
        });
    }
}