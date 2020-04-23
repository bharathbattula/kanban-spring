import {Component, OnInit} from '@angular/core';
import {DataService} from "../shared/services/data.service";
import {Project} from "../shared/model/Project";
import {HttpClient} from "@angular/common/http";
import {catchError, debounceTime, finalize, map, startWith, switchMap, tap} from "rxjs/operators";
import {User} from "../shared/model/User";
import {Observable, of} from "rxjs";
import {FormControl} from "@angular/forms";

@Component({
  selector: 'app-project-setting',
  templateUrl: './project-setting.component.html',
  styleUrls: ['./project-setting.component.scss']
})
export class ProjectSettingComponent implements OnInit {

  project: Project;

  userAutoComplete$: Observable<User> = null;
  userAutoCompleteControl = new FormControl();
  showSpinner: boolean = false;

  constructor(private dataService: DataService, private http: HttpClient) {
  }

  ngOnInit() {
    this.project = this.dataService.getCurrentProjectValue();

    this.userAutoComplete$ = this.userAutoCompleteControl.valueChanges.pipe(
      startWith(''),
      // delay emits
      debounceTime(300),

      tap(() => this.showSpinner = true),
      // use switch map so as to cancel previous subscribed events, before creating new once
      switchMap(value => {
        if (value !== '') {
          // lookup from github
          return this.lookup(value);
        } else {
          // if no value is present, return null
          return of(null);
        }
      }),
      finalize(() => this.showSpinner = false),
      /*pipe(
        finalize(() => this.showSpinner = false),
      )*/
    );

  }

  lookup(query: string): Observable<User> {
    return this.search(query)
      .pipe(
        map(users => users),
        catchError(err => {
          return of(null);
        })
      )
  }

  goBack($event: MouseEvent) {
    //route back to previous page
  }

  deleteConfirm($event: MouseEvent, user: User) {
    //delete confirmation popup
  }

  addUser($event: MouseEvent) {
    //add new user to current project
  }

  search(query: string): Observable<User> {

    return this.http.get<User>("http://localhost:8080/api/user/search",
      {
        observe: "body",
        params: {query: query}
      })
      .pipe(
        map(users => users)
      );
  }

}
