import { async, getTestBed, TestBed, inject } from '@angular/core/testing';

import { CareplanService } from './careplan.service';

// ...http imports
import {
  MockBackend
} from '@angular/http/testing';
import { CareplanTask } from '../models/careplan';
import { MockHelper } from '../../shared/test/mock.helper';

describe('CareplanService', () => {
  let backend: MockBackend;
  let service: CareplanService;
  const API = 'careplan';
  // async - ensures that setup resolved before running the test
  beforeEach(async(() => {
    TestBed.configureTestingModule({
      providers: MockHelper.getProviders(CareplanService)
    });

    const testbed = getTestBed();
    backend = testbed.get(MockBackend);
    service = testbed.get(CareplanService);
  }));

  it('should be created', inject([CareplanService], (careplanService: CareplanService) => {
    expect(careplanService).toBeTruthy();
  }));

  it ('should return the list of tasks from the backend on success', () => {
    MockHelper.initConnection(this.API_URL, backend, {
      status: 200,
      body: [
        {
          id: 1,
          title: 'careplan1',
          start: '2013-12-12',
          end: '2013-05-05',
          medication: [
            {
              id: 1,
              title: 'med1'
            },
            {
              id: 2,
              title: 'med2'
            }
          ]
        },
        {
          id: 2,
          title: 'careplan2',
          start: '2014-12-12',
          end: '2014-05-05',
          medication: [
            {
              id: 3,
              title: 'med2'
            }
          ]
        }
      ]
    });
    service.getTasks().subscribe((data: CareplanTask[]) => {
      expect(data.length).toBe(2);
      expect(data[0].id).toBe(1);
      expect(data[0].title).toBe('careplan1');
      expect(data[0].medication.length).toBe(2);
      expect(data[1].id).toBe(2);
      expect(data[1].title).toBe('careplan2');
      expect(data[1].medication.length).toBe(1);
    });
    // TODO POST
    // TODO PUT
    // TODO DELETE
    // TODO GET ONE
  });
});
