import { Injectable }    from '@angular/core';

import { SelectModel }   from '../common/select/select.fragment.model';
import { ButtonModel }   from '../common/button/button.model';

@Injectable()
export class HomeService {
  private select_model: SelectModel = { name: 'Project name' };
  private button_models: ButtonModel [] = [
    {name: 'Save', id:'user_send_button'}
  ];

  getSelect(): SelectModel {
    return this.select_model;
  };

  getButton(): ButtonModel[]{
    return this.button_models;
  };
}
