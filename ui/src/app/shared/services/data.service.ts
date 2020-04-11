import {Injectable} from '@angular/core';
import {BehaviorSubject, Observable} from "rxjs";
import {Project} from "../model/Project";

@Injectable({
  providedIn: 'root'
})
export class DataService {

  public projectDataSource: BehaviorSubject<Array<Project>>;
  public projectData$: Observable<Array<Project>>;

  private currentProjectSubject: BehaviorSubject<Project>;
  public currentProject$: Observable<Project>;

  constructor() {
    this.projectDataSource = new BehaviorSubject<Array<Project>>([]);
    this.projectData$ = this.projectDataSource.asObservable();

    this.currentProjectSubject = new BehaviorSubject<Project>(null);
    this.currentProject$ = this.currentProjectSubject.asObservable();
  }

  public getCurrentProjectValue(): Project {
    return this.currentProjectSubject.getValue();
  }

  public setCurrentProjectValue(project) {
    this.currentProjectSubject.next(project);
  }

  getProject(name: string) {
    return this.projectDataSource.getValue().find(value => value.name === name);
  }

}
