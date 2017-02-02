import {Component, Input} from "@angular/core";
import {Button} from "./button.model";

@Component ({
    selector: 'button_component',
    template: require('./button.component.html'),
    styles: [require('./button.component.css')]
})
//will be removed whole directory
export class ButtonComponent {
    @Input() button_model: Button;
}