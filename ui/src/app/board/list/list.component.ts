import {Component, Input, OnInit} from '@angular/core';
import {animate, state, style, transition, trigger} from '@angular/animations';
import * as _ from 'lodash';
import {COMMA, ENTER} from '@angular/cdk/keycodes';
import {MatChipInputEvent} from '@angular/material/chips';
import {MatAutocompleteSelectedEvent} from '@angular/material/autocomplete';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import * as moment from 'moment';
import {List} from '../../shared/model/List';
import {RestService} from '../../shared/services/rest.service';
import {Project} from '../../shared/model/Project';
import {DataService} from "../../shared/services/data.service";
import {BaseComponent} from "../../shared/base.component";
import {MatSnackBar} from "@angular/material/snack-bar";
import {MatDialog} from "@angular/material/dialog";
import {CdkDragDrop, moveItemInArray, transferArrayItem} from "@angular/cdk/drag-drop";
import {User} from "../../shared/model/User";


@Component({
  selector: 'app-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.scss'],
  animations: [
    trigger('reveal', [
      state('hidden', style({
        display: 'none',
      })),
      state('show', style({
        display: 'inline-flex',
      })),
      transition('hidden=>show', animate('0.1s')),
      transition('show=>hidden', animate('0.1s')),
    ])
  ]
})
export class ListComponent extends BaseComponent implements OnInit {

  @Input()
  list: List;

  project: Project;

  startDate = new Date();

  currentState = 'hidden';

  users: User[] = [];
  taskParticipants: User[] = [];

  separatorKeysCodes: number[] = [COMMA, ENTER];

  taskForm: FormGroup;

  constructor(private fb: FormBuilder,
              private rest: RestService,
              private dataService: DataService,
              public snackBar: MatSnackBar,
              public matDialog: MatDialog) {
    super("list", snackBar, matDialog);
    this.project = this.dataService.getCurrentProjectValue();
    this.users = this.project.users;
  }

  ngOnInit() {

    this.taskForm = this.fb.group({
      title: new FormControl('', [Validators.min(8), Validators.max(50), Validators.required]),
      description: new FormControl('', [Validators.min(8), Validators.max(150), Validators.required]),
      deadline: new FormControl('', [Validators.required]),
      participants: new FormControl('')
    })
  }


  removeParticipant(user: User) {
    _.remove(this.taskParticipants, u => _.isEqual(u, user));
    this.users.push(user);
  }

  addParticipants($event: MatChipInputEvent) {
    if ($event.value.trim().length != 0) {
      // this.users.push($event.value);
    }
  }

  selected($event: MatAutocompleteSelectedEvent) {
    const user = _.find(this.users, {'username': $event.option.value});

    this.taskParticipants.push(user);
    _.remove(this.users, u => _.isEqual(u, user));
  }

  addTask() {

    let formData = this.taskForm.getRawValue();

    let newTask = {
      title: formData.title,
      description: formData.description,
      deadLine: moment(formData.deadline).format('YYYY-MM-DD'),
      participants: this.taskParticipants
    };

    console.log(newTask);

    this.rest.request(newTask, `project/${this.project.id}/list/${this.list.id}/task`, 'POST')
      .then(task => {
        console.log(task);
        this.list.tasks.push(task);
        console.log(this.list);
        this.taskForm.reset();
        this.currentState = 'hidden'
        this.showNotification({message: `${task.title} is created`, action: 'success'});
      })
      .catch(error => {
        console.log(error);
      });

  }

  deleteCallback(data: any) {

    this.rest.request(null, `project/${this.project.id}/list/${this.list.id}/task/${data.id}`, 'DELETE')
      .then(response => {
        _.remove(this.list.tasks, task => task.id === data.id);
        this.showNotification({message: `${data.title} is deleted successfully`});
      })
      .catch(error => {
        console.error(error);
      });

  }

  drop($event: CdkDragDrop<Task[]>) {
    if ($event.previousContainer === $event.container) {
      moveItemInArray($event.container.data, $event.previousIndex, $event.currentIndex);
    } else {
      transferArrayItem($event.previousContainer.data,
        $event.container.data,
        $event.previousIndex,
        $event.currentIndex);

      this.updateTaskPosition($event.container.id, $event.container.data[$event.currentIndex]);

    }
  }

  updateTaskPosition(listId: string, task: Task) {
    console.log(task);
    this.rest.request(task, `project/${this.project.id}/list/${listId}/task`, 'PUT')
      .then(task => {
        console.log(task);
      })
      .catch(error => {
        console.log(error);
      });
  }


}
