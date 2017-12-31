import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import {ElasticModule} from "angular2-elastic";
import {ButtonComponent} from "./button/button.component";
import {SortableComponent} from "./sortable/sortable.component";
import {DndModule} from "ng2-dnd";
import {CKEditorModule} from "ng2-ckeditor";

@NgModule({
  imports:      [ CommonModule, ElasticModule,  DndModule.forRoot(), CKEditorModule],      // module dependencies
  declarations: [ ButtonComponent, SortableComponent ],                           // components and directives
  exports:      [ ButtonComponent, CommonModule, ElasticModule, SortableComponent, DndModule, CKEditorModule]
})
export class SharedModule { }