import {Component, OnInit} from '@angular/core';
import {DataService} from "../shared/services/data.service";
import {Project} from "../shared/model/Project";
import {HttpClient} from "@angular/common/http";
import {catchError, debounceTime, finalize, map, startWith, switchMap, tap} from "rxjs/operators";
import {User} from "../shared/model/User";
import {Observable, of} from "rxjs";
import {FormControl, Validators} from "@angular/forms";
import * as _ from 'lodash';
import {RestService} from "../shared/services/rest.service";
import {BaseComponent} from "../shared/base.component";
import {MatDialog} from "@angular/material/dialog";
import {MatSnackBar} from "@angular/material/snack-bar";
import {Router} from "@angular/router";
import {Location} from '@angular/common';

@Component({
  selector: 'app-project-setting',
  templateUrl: './project-setting.component.html',
  styleUrls: ['./project-setting.component.scss']
})
export class ProjectSettingComponent extends BaseComponent implements OnInit {

  project: Project;

  filteredUsers: User[] = null;
  userAutoCompleteControl = new FormControl('', [Validators.email, Validators.required]);
  showSpinner: boolean = false;
  private selectedUser: User;

  constructor(private dataService: DataService,
              private http: HttpClient,
              private rest: RestService,
              private router: Router,
              private matDialog: MatDialog,
              private matSnackBar: MatSnackBar,
              private location: Location) {
    super('Project setting', matSnackBar, matDialog);
  }

  ngOnInit() {
    this.project = this.dataService.getCurrentProjectValue();

    this.userAutoCompleteControl.valueChanges.pipe(
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
    ).subscribe(users => this.filteredUsers = users);

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
    this.location.back();
  }

  deleteCallback(user: User) {

    if (user) {
      this.rest.request(user, `project/${this.project.id}/user/${user.id}`, 'DELETE')
        .then(value => {
          console.log(value);
          this.dataService.setCurrentProjectValue(value);
          this.project = value;
        })
        .catch(reason => console.error(reason));
    }

  }

  addUser($event: MouseEvent) {

    const formValue = this.userAutoCompleteControl.value;

    if (formValue) {
      const user = _.find(this.filteredUsers, {'email': formValue});

      if (user) {

        this.rest.request(user, `project/${this.project.id}/user`, 'POST')
          .then(value => {
            console.log(value);
            this.dataService.setCurrentProjectValue(value);
            this.project = value;
          })
          .catch(reason => console.error(reason));

      }
    }
  }

  search(query: string): Observable<User> {

    return this.http.get<User>('http://localhost:8080/api/user/search',
      {
        observe: 'body',
        params: {query: query}
      })
      .pipe(
        map(users => users)
      );
  }


}
