import { NgModule }      from '@angular/core';
import { IsAuthorized } from './is-authorized.directive';

@NgModule({
    bootstrap: [ ],
    declarations: [ IsAuthorized ],
    exports: [ IsAuthorized ]
})
export class UtilsModule { }
