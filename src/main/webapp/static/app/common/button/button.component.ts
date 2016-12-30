import { Component, Input } from '@angular/core';

import { Button }           from './button.model';

@Component ({
    selector: 'button_model',
    template: require('./button.component.html'),
    styles: [require('./button.component.css')]
})

export class ButtonComponent {
    @Input() button_model: Button;

}