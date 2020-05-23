import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {RestService} from "../../shared/services/rest.service";
import {DataService} from "../../shared/services/data.service";
import {Subscription} from "rxjs";
import {MatDrawer} from "@angular/material/sidenav";
import {User} from "../../shared/model/User";
import {MatChipInputEvent} from "@angular/material/chips";
import {MatAutocompleteSelectedEvent} from "@angular/material/autocomplete";
import {COMMA, ENTER} from "@angular/cdk/keycodes";

import * as _ from 'lodash';
import {Project} from "../../shared/model/Project";
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {Task} from "../../shared/model/Task";
import * as moment from "moment";

@Component({
  selector: 'app-task-detail',
  templateUrl: './task-detail.component.html',
  styleUrls: ['./task-detail.component.scss']
})
export class TaskDetailComponent implements OnInit, OnDestroy {

  task: Task;

  @Input()
  drawer: MatDrawer;

  private taskInDetailSubscriber: Subscription;

  project: Project;

  users: User[] = [];
  participants: User[] = [];

  separatorKeysCodes: number[] = [COMMA, ENTER];

  taskForm: FormGroup;

  constructor(private rest: RestService,
              private dataService: DataService,
              private fb: FormBuilder) {
    this.project = this.dataService.getCurrentProjectValue();
    this.users = this.project.users;

    this.taskForm = this.fb.group({
      title: new FormControl('', [Validators.min(8), Validators.max(50), Validators.required]),
      description: new FormControl('', [Validators.min(8), Validators.max(150), Validators.required]),
      deadline: new FormControl('', [Validators.required]),
      participants: new FormControl(''),
      creator: new FormControl('', [Validators.required])
    });

  }

  ngOnInit() {
    this.taskInDetailSubscriber = this.dataService.currentTaskInDetailWindow.subscribe(value => {

      if (value) {

        this.task = value;
        this.participants.push(...this.task.participants);

        this.taskForm.setValue({
          title: this.task.title,
          description: this.task.description,
          deadline: this.task.deadLine,
          participants: this.participants,
          creator: this.task.creator
        });

        this.task.creator.initials = this.returnInitials(this.task.creator);
        this.task.participants.forEach(value => value.initials = this.returnInitials(value));
        console.log(this.task);

        _.forEach(this.participants, value => {
          _.remove(this.users, u => u.id === value.id);
        });

        this.drawer.open();
      }
    });

  }

  returnInitials(user: User) {
    return `${user.firstName ? user.firstName.charAt(0).toUpperCase() : ''}${user.lastName ? user.lastName.charAt(0).toUpperCase() : ''}`;
  }

  removeParticipant(user: User) {
    _.remove(this.participants, u => _.isEqual(u, user));
    this.users.push(user);
  }

  addParticipants($event: MatChipInputEvent) {
    if ($event.value.trim().length != 0) {
      // this.users.push($event.value);
    }
  }

  selected($event: MatAutocompleteSelectedEvent) {
    const user = _.find(this.users, {'username': $event.option.value});

    this.participants.push(user);
    _.remove(this.users, u => _.isEqual(u, user));
  }


  goBack() {
    this.task.edit = false;
    this.task = null;
    this.participants = [];
    this.taskForm.reset();
    this.drawer.close();
  }

  ngOnDestroy(): void {
    this.taskInDetailSubscriber.unsubscribe();
  }

  update($event: any) {

    const formData = this.taskForm.getRawValue();

    const updatedTask = _.omit(this.task, ['edit', 'listId']);

    updatedTask.title = formData.title;
    updatedTask.description = formData.description;
    updatedTask.participants = this.participants;
    updatedTask.deadLine = moment(formData.deadline).format('YYYY-MM-DD');
    updatedTask.creator = formData.creator;

    this.rest.request(updatedTask, `project/${this.project.id}/list/${this.task.listId}/task`, 'PUT')
      .then(value => this.task = value)
      .catch(reason => console.log(reason));

  }
}
