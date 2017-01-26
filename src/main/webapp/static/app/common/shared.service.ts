import {Injectable, Output, EventEmitter} from "@angular/core";

@Injectable()
export class SharedService {

    @Output() reportLoadedEvent: EventEmitter<any> = new EventEmitter(true);
    @Output() aggregateEvent: EventEmitter<any> = new EventEmitter(true);

    loadReport(model:any) {
       let report= {
            review: model.reviewPart,
            issues: model.issuePart,
            plans: model.planPart,
            project: model.project,
            reportId:model.reportId,
        };
        this.reportLoadedEvent.emit(report);
    }

    changeVisibility(show:boolean){
        this.aggregateEvent.emit(show);
    }
}