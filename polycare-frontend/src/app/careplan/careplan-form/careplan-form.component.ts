import { Component, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CareplanTask } from '../models/careplan';
import { ActivatedRoute, Router } from '@angular/router';
import { CareplanService } from '../services/careplan.service';

/**
 * Form to add a task to a Careplan
 */
@Component({
  selector: 'app-careplan-form',
  templateUrl: './careplan-form.component.html',
  styleUrls: ['./careplan-form.component.scss']
})
export class CareplanFormComponent implements OnInit {
  private form: FormGroup;
  private task: CareplanTask = new CareplanTask();

  constructor(private formBuilder: FormBuilder,
              private router: Router,
              private route: ActivatedRoute,
              private service: CareplanService) {
    this.form = formBuilder.group({
      id: [],
      title: ['', [
        Validators.required,
        Validators.maxLength(30),
        Validators.minLength(1)
      ]],
      start: [],
      end: [],
      posology: [],
      medication: formBuilder.array([])
    });
  }

  public ngOnInit() {
    let id = this.route.params.subscribe((params) => {
      let taskId = params['id'];
      if (!taskId) {
        return;
      }

      this.service.getTask(taskId)
        .subscribe((task) => {
          this.task = task;
          console.log(this.task);
          if (this.task.medication && this.task.medication.length > 0) {
            this.task.medication.forEach((med) => {
              this.addMedicationFormWithData(med);
            });
          }
        }, (resp) => {
          if (resp.status === 404) {
            alert('Could not retrieve entry from server');
          }
        });
    });
  }

  /**
   * Submitting of form to a server
   */
  public save() {
    let formValue = this.form.value;
    // if task contains an id, update, otherwise create new one
    let response = formValue.id ?
      this.service.updateTask(formValue) :
      this.service.addTask(formValue);
    // when added, navigate back to list of tasks
    response.subscribe((data) => this.router.navigate(['/careplan']),
      (err) => {
        window.alert('Could not submit the form: ' + err);
      });
  }

  /**
   * Adding medication form
   */
  public addMedicationFormToArray() {
    const control = <FormArray> this.form.controls['medication'];
    control.push(this.initMedicationForm());
  }

  /**
   * Initiailizes control and inserts an existing medication
   * @param medication
   */
  public addMedicationFormWithData(medication) {
    const control = <FormArray> this.form.controls['medication'];
    let medForm: FormGroup = this.initMedicationForm();
    control.push(medForm);
    medForm.setValue(medication);
  }

  /**
   * Initialize a medication form with validation rules
   * @returns {FormGroup}
   */
  private initMedicationForm() {
    return this.formBuilder.group({
      id: [],
      title: ['', [
        Validators.required,
        Validators.maxLength(30),
        Validators.minLength(1)
      ]]
    });
  }
}
