import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CareplanComponent } from './careplan.component';
import { CareplanFormComponent } from './careplan-form/careplan-form.component';
import { routes } from './careplan.routing';
import { RouterModule } from '@angular/router';
import { CareplanService } from './services/careplan.service';
import { MedicationFormComponent } from './careplan-form/medication-form/medication-form.component';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    RouterModule,
    RouterModule.forChild(routes)
  ],
  declarations: [
    CareplanComponent,
    CareplanFormComponent,
    MedicationFormComponent
  ],
  providers: [
    CareplanService
  ]
})
export class CareplanModule {
  public static routes = routes;
}
