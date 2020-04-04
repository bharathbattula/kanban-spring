import {Component, Input, OnInit} from '@angular/core';
import {animate, state, style, transition, trigger} from '@angular/animations';
import * as _ from 'lodash';
import {COMMA, ENTER} from '@angular/cdk/keycodes';
import {MatChipInputEvent} from '@angular/material/chips';
import {MatAutocompleteSelectedEvent} from '@angular/material/autocomplete';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import * as moment from 'moment';
import {List} from '../../model/List';
import {RestService} from '../../rest.service';
import {Project} from '../../model/Project';
import {DataService} from "../../data.service";


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
export class ListComponent implements OnInit {

  @Input()
  list: List;

  project: Project;

  startDate = new Date();

  currentState = 'hidden';

  users: String[] = ['Bharat', 'Amit', 'Prashant', 'Rishi', 'DD'];
  taskParticipants: string[] = [];

  separatorKeysCodes: number[] = [COMMA, ENTER];

  taskForm: FormGroup;

  constructor(private fb: FormBuilder, private rest: RestService, private dataService: DataService) {
    this.project = this.dataService.getCurrentProjectValue();
  }

  ngOnInit() {

    this.taskForm = this.fb.group({
      title: new FormControl('', [Validators.min(8), Validators.max(50), Validators.required]),
      description: new FormControl('', [Validators.min(8), Validators.max(150), Validators.required]),
      deadline: new FormControl('', [Validators.required]),
      participants: new FormControl('')
    })
  }

  removeParticipant(user: string) {
    _.remove(this.taskParticipants, u => u === user);
    this.users.push(user);
  }

  addParticipants($event: MatChipInputEvent) {
    if($event.value.trim().length != 0) {
      // this.users.push($event.value);
    }
  }

  selected($event: MatAutocompleteSelectedEvent) {
      this.taskParticipants.push($event.option.value);
      _.remove(this.users, u => u === $event.option.value);
  }

  addTask() {

    let formData = this.taskForm.getRawValue();

    let newTask = {
      title: formData.title,
      description: formData.description,
      deadLine: moment(formData.deadline).format('YYYY-MM-DD'),
    }
    console.log(newTask);

    this.rest.request(newTask, `project/${this.project.id}/list/${this.list.id}/task`, 'POST')
      .then(task => {
        console.log(task);
        this.list.tasks.push(task);
        console.log(this.list);
        this.taskForm.reset();
        this.currentState = 'hidden'
      })
      .catch(error => {
        console.log(error);
      });

  }

}
