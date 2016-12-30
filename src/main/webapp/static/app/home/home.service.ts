import { Injectable }    from '@angular/core';

import { SelectLabel }   from '../common/select/select.label.model';
import { Button }        from '../common/button/button.model';

@Injectable()
export class HomeService {
  private select_model: SelectLabel = { name: 'Project name' };
  private button_models: Button[] = [
    {name: 'Save', id:'user_send_button'}
  ];

  getSelect(): SelectLabel {
    return this.select_model;
  };

  getButton(): Button[]{
    return this.button_models;
  };
}
