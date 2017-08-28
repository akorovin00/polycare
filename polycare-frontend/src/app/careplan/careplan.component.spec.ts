import { async, ComponentFixture, getTestBed, TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';

import { CareplanComponent } from './careplan.component';
import { CareplanService } from './services/careplan.service';
import { MockHelper } from '../shared/test/mock.helper';
import { CareplanTask } from './models/careplan';
import { Observable } from 'rxjs/Observable';

describe('CareplanComponent', () => {
  let component: CareplanComponent;
  let fixture: ComponentFixture<CareplanComponent>;
  let many: CareplanTask[] = [];
  let one: CareplanTask;
  let service: CareplanService;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CareplanComponent ],
      imports: [
        RouterTestingModule.withRoutes([])
      ],
      providers: MockHelper.getProviders(CareplanService)
    })
    .compileComponents();
  }));

  function initFixturesMany() {
    let careplan1 = new CareplanTask();
    careplan1.id = 1;
    careplan1.title = 'careplan1';
    one = new CareplanTask();
    one.id = 2;
    one.title = 'careplan2';
    return [careplan1, one];
  }

  beforeEach(() => {
    fixture = TestBed.createComponent(CareplanComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();

    many = initFixturesMany();
    const testbed = getTestBed();
    service = testbed.get(CareplanService);
    spyOn(service, 'getTasks')
      .and.returnValue(Observable.of(many));
    component.ngOnInit();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });

  it('should build a correct list when initialized', () => {
    const tasks = component['tasks'];
    expect(tasks).not.toBeNull();
    expect(tasks.length).toBe(2);
    expect(tasks[0].title).toBe(many[0].title);
    expect(tasks[0].id).toBe(many[0].id);
    expect(tasks[1].title).toBe(many[1].title);
    expect(tasks[1].id).toBe(many[1].id);
    expect(service.getTasks).toHaveBeenCalledTimes(1);
  });

  it('should remove a task that is not null', () => {
    spyOn(service, 'deleteTask')
      .and.returnValue(Observable.of({status: 200}));
    // remove an element with id = 2
    component.deleteTask(one);
    expect(component['tasks'].length).toBe(1);
    expect(component['tasks'][0].id).toBe(1);
    expect(service.deleteTask).toHaveBeenCalledWith(one.id);
  });

  it('should alert when removing a task that does not exist', () => {
    spyOn(service, 'deleteTask')
      .and.returnValue(Observable.throw({status: 404}));
    spyOn(window, 'alert');
    component.deleteTask(one);
    expect(service.deleteTask).toHaveBeenCalledWith(one.id);
    expect(window.alert).toHaveBeenCalledTimes(1);
  });
});
