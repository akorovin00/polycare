import { Component, Input, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';

/**
 * Medication form to add medication
 * to Medication Careplan
 */
@Component({
  selector: 'app-medication-form',
  templateUrl: './medication-form.component.html',
  styleUrls: ['./medication-form.component.scss']
})
export class MedicationFormComponent {
  @Input('medicationForm')
  public medicationForm: FormGroup;
}
