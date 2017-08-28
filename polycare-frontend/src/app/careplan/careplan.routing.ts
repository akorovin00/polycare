import { CareplanComponent } from './careplan.component';
import { CareplanFormComponent } from './careplan-form/careplan-form.component';

export const routes = [
  {
    path: '',
    children: [
      { path: '', component: CareplanComponent, pathMatch: 'full' },
      { path: 'new', component: CareplanFormComponent },
      { path: ':id', component: CareplanFormComponent }
    ]
  }
];
