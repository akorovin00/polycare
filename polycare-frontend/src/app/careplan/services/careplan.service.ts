import { Injectable } from '@angular/core';
import { Http, RequestOptions, Headers } from '@angular/http';
import { CareplanTask } from '../models/careplan';

import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/do';
import 'rxjs/add/operator/catch';

/**
 * Service for interacting with careplan REST endpoint
 */
@Injectable()
export class CareplanService {
  private API_URL = 'http://localhost:8090/api/careplan/';
  private headers: Headers = new Headers({ 'Content-Type': 'application/json' });
  private options: RequestOptions = new RequestOptions({ headers: this.headers });
  constructor(private http: Http) { }

  /**
   * Fetching all careplan tasks
   * @returns {Observable<R>}
   */
  public getTasks() {
    return this.http.get(this.API_URL)
      .map((resp) => resp.json() as CareplanTask[]);
  }

  /**
   * Fetches a careplan task by id
   * @param id Task id
   * @returns {Observable<R>}
   */
  public getTask(id) {
    return this.http.get(this.getTaskUrl(id))
      .map((resp) => resp.json() as CareplanTask);
  }

  /**
   * Adds a careplan task with medication
   * @param task
   * @returns {Observable<Response>}
   */
  public addTask(task) {
    return this.http.post(this.API_URL, JSON.stringify(task), this.options);
  }

  /**
   * Updates a careplan task
   * @param task
   * @returns {Observable<Response>}
   */
  public updateTask(task) {
    return this.http.put(this.API_URL, JSON.stringify(task), this.options);
  }

  /**
   * Removes a task by id
   * @param id Task id
   * @returns {Observable<Response>}
   */
  public deleteTask(id) {
    return this.http.delete(this.getTaskUrl(id));
  }

  /**
   * Forms a url with id
   * @param id
   * @returns {string}
   */
  private getTaskUrl(id) {
    return this.API_URL + id;
  }
}
