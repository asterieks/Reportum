import { Component, Input, AfterViewInit, ElementRef } from '@angular/core';

import { Button }              from './button.model';

import { SharedService }       from '../shared.service';

@Component ({
    selector: 'button_component',
    template: require('./button.component.html'),
    styles: [require('./button.component.css')]
})

export class ButtonComponent {
    show : boolean = true;
    @Input() button_model: Button;

    constructor(private elementRef:ElementRef, private sharedService: SharedService){}

    ngAfterViewInit() {
        let el=this.elementRef.nativeElement;
        let id=el.children[0].getAttribute('id');
        if(id==='manager_aggregate_button'){
            this.elementRef.nativeElement.addEventListener('click', this.clicked.bind(this));
        }
    }

    clicked(event) {
        event.preventDefault();
        this.show = !this.show;
        this.sharedService.changeVisibility(this.show);
    }
}