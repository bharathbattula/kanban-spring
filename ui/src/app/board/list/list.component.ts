import {Component, Input, OnInit} from '@angular/core';
import {animate, state, style, transition, trigger} from "@angular/animations";
import * as _ from 'lodash';
import {COMMA, ENTER} from "@angular/cdk/keycodes";
import {MatChipInputEvent} from "@angular/material/chips";
import {MatAutocompleteSelectedEvent} from "@angular/material/autocomplete";
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import * as moment from 'moment';
import {List} from "../../model/List";


@Component({
  selector: 'app-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.scss'],
  animations:[
    trigger('reveal', [
      state('hidden', style({
        display:'none',
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

  startDate = new Date();

  currentState="hidden";

  users:String[] = ['Bharat', 'Amit', 'Prashant', 'Rishi', 'DD'];
  taskParticipants:string[] = [];

  separatorKeysCodes: number[] = [COMMA, ENTER];

  taskForm:FormGroup;

  constructor(private fb:FormBuilder) { }

  ngOnInit() {

    this.taskForm = this.fb.group({
      title: new FormControl("", [Validators.min(8), Validators.max(50), Validators.required]),
      description: new FormControl("", [Validators.min(8), Validators.max(150), Validators.required]),
      deadline: new FormControl("", [Validators.required]),
      participants: new FormControl("")
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
    console.log(this.taskForm.getRawValue());

    let formData = this.taskForm.getRawValue();

    this.list.tasks.push({
      id: _.maxBy(this.list, task => task.id).id + 1,
      title: formData.title,
      description: formData.description,
      user: null,
      deadline: moment(formData.deadline).toDate(),
      participants: null
    });

    this.taskForm.reset();
  }
}
