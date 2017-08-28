import { ComponentFixture, getTestBed, TestBed } from '@angular/core/testing';

import { CareplanFormComponent } from './careplan-form.component';
import { FormArray, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MedicationFormComponent } from './medication-form/medication-form.component';
import { RouterTestingModule } from '@angular/router/testing';
import { CareplanService } from '../services/careplan.service';
import { MockHelper } from '../../shared/test/mock.helper';
import { Medication } from '../models/medication';
import { Observable } from 'rxjs/Observable';

describe('CareplanFormComponent', () => {
  let component: CareplanFormComponent;
  let fixture: ComponentFixture<CareplanFormComponent>;
  let service: CareplanService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        ReactiveFormsModule,
        FormsModule,
        RouterTestingModule.withRoutes([{
          path: 'careplan',
          component: CareplanFormComponent
        }]),
      ],
      providers: MockHelper.getProviders(CareplanService),
      declarations: [ CareplanFormComponent, MedicationFormComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CareplanFormComponent);
    component = fixture.componentInstance;
    component.ngOnInit();
    // set router
    fixture.detectChanges();

    const testbed = getTestBed();
    service = testbed.get(CareplanService);
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });

  /**
   * Whole form validity
   */
  it ('form invalid when it is empty', () => {
    expect(component['form'].valid).toBeFalsy();
  });

  /**
   * Individual field validity
   */
  it('no required fields are valid', () => {
    let form = component['form'];
    expect(form.controls['start'].valid).toBeTruthy();
    expect(form.controls['end'].valid).toBeTruthy();
    expect(form.controls['posology'].valid).toBeTruthy();
    expect(form.controls['medication'].valid).toBeTruthy();
  });

  it('empty title field is not valid', () => {
    let errors = {};
    let title = component['form'].controls['title'];
    errors = title.errors || {};
    expect(errors['required']).toBeTruthy();
    expect(title.valid).toBeFalsy();
  });

  it ('1 character title is valid', () => {
    let title = component['form'].controls['title'];
    title.setValue('#');
    expect(title.valid).toBeTruthy();
  });

  it('> 30 characeter title field is not valid', () => {
    let errors = {};
    let title = component['form'].controls['title'];
    title.setValue('#'.repeat(31));
    errors = title.errors || {};
    expect(errors['maxlength']).toBeTruthy();
    expect(title.valid).toBeFalsy();
  });

  it ('added medication with empty title is not valid', () => {
    component.addMedicationFormToArray();
    let errors = {};
    let medicationArray = <FormArray> component['form'].get('medication');
    let medicationField = new Medication();
    // patch to avoid exception with missing id
    medicationArray.controls[0].patchValue(medicationField);
    errors = medicationArray.controls[0].errors || {};
    expect(medicationArray.controls[0].valid).toBeFalsy();
  });

  it ('added medication with title > 1 characeter is valid', () => {
    component.addMedicationFormToArray();
    let medicationArray = <FormArray> component['form'].get('medication');
    let medicationField = new Medication();
    medicationField.title = '1';
    // patch to avoid exception with missing id
    medicationArray.controls[0].patchValue(medicationField);
    expect(medicationArray.controls[0].valid).toBeTruthy();
  });

  it ('submitting a form with id calls update', () => {
    expect(component['form'].valid).toBeFalsy();
    component['form'].setValue({id: 1, title: '#', start: null, end: null, posology: null, medication: []});
    expect(component['form'].valid).toBeTruthy();
    spyOn(service, 'updateTask')
      .and.returnValue(Observable.of([]));
    spyOn(component['router'], 'navigate');
    component.save();
    // check if update was called
    expect(service.updateTask).toHaveBeenCalledTimes(1);
    expect(component['router'].navigate).toHaveBeenCalledWith(['/careplan']);
  });

  it ('submitting a form without id calls create', () => {
    expect(component['form'].valid).toBeFalsy();
    component['form'].controls['title'].setValue('#');
    expect(component['form'].valid).toBeTruthy();
    spyOn(service, 'addTask')
      .and.returnValue(Observable.of([]));
    spyOn(component['router'], 'navigate');
    component.save();
    // check if create was called
    expect(service.addTask).toHaveBeenCalledTimes(1);
    expect(component['router'].navigate).toHaveBeenCalledWith(['/careplan']);
  });
});
