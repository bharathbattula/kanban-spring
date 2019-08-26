import {Injectable} from '@angular/core';
import {BehaviorSubject} from "rxjs";
import {Project} from "./model/Project";

@Injectable({
  providedIn: 'root'
})
export class DataService {

  public projectDataSource = new BehaviorSubject<Array<Project>>([]);

  public projectData$ = this.projectDataSource.asObservable();

  constructor() { }
}
