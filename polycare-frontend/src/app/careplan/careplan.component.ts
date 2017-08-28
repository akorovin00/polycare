import { Component, OnInit } from '@angular/core';
import { CareplanTask } from './models/careplan';
import { CareplanService } from './services/careplan.service';

/**
 * Careplan representation with tasks as items
 */
@Component({
  selector: 'app-careplan',
  templateUrl: './careplan.component.html',
  styleUrls: ['./careplan.component.scss']
})
export class CareplanComponent implements OnInit {
  private tasks: CareplanTask[] = [];
  constructor(private service: CareplanService) { }

  public ngOnInit() {
    this.service.getTasks()
      .subscribe((data) => this.tasks = data);
  }

  /**
   * Removes a task on click in table of tasks
   * @param task
   */
  public deleteTask(task) {
    this.service.deleteTask(task.id)
      .subscribe((res) => {
        let index = this.tasks.indexOf(task);
        if (index > 0) {
          this.tasks.splice(index, 1);
        }
      }, (err) => {
        window.alert('Could not delete the task, ' + err);
      });
  }
}
