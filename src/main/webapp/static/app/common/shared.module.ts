import { NgModule }        from '@angular/core';
import { CommonModule }    from '@angular/common';
import { Elastic }         from 'angular2-elastic';

import { SelectComponent }  from './select/select_label.component';
import { ButtonComponent }  from './button/button.component';

@NgModule({
  imports:      [ CommonModule, Elastic ],      // module dependencies
  declarations: [ SelectComponent, ButtonComponent ],                           // components and directives
  exports:      [ SelectComponent, ButtonComponent, CommonModule, Elastic ]
})
export class SharedModule { }