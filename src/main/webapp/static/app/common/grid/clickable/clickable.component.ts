import {Component, Input, Output, EventEmitter} from "@angular/core";

@Component({
    selector: 'ag-clickable',
    template: `
    <button (click)="onClick($event)" value={{cell.reportId}} [disabled]="isValid(cell.date)">Download</button>
    `,
    styles: [`button {height: 20px}`]
})
export class ClickableComponent {
    @Input() cell:any;
    @Output() eventEmmiter = new EventEmitter<number>();

    isValid(actualDate: string): boolean {
        return !actualDate;
    }

    onClick(event) : void {
        event.preventDefault();
        this.eventEmmiter.emit(event.target.value);
    }
}