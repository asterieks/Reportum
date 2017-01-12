import { Injectable, Output, EventEmitter }  from '@angular/core';

import { Report }      from './report/report.model';

@Injectable()
export class SharedService {

    @Output() reportLoadedEvent: EventEmitter<any> = new EventEmitter(true);

    loadReport(model:any) {
       let report= {
            review: model.reviewPart,
            issues: model.issuePart,
            plans: model.planPart,
            project: model.projectId.projectId,
            reportId:model.reportId,
        };
        this.reportLoadedEvent.emit(report);
    }
}