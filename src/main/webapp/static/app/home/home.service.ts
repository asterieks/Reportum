import { Injectable }    from '@angular/core';

import { SelectLabel }   from '../common/select/select_label.model';
import { Button }        from '../common/button/button.model';

@Injectable()
export class HomeService {
    private select_label: SelectLabel = { name: 'Project name' };
    private button_models: Button[] = [{name: 'Send', id:'user_send_button'}];

    getSelect(): SelectLabel {
      return this.select_label;
    };

    getButton(): Button[]{
      return this.button_models;
    };
}
