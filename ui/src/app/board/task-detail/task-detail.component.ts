import {Component, OnInit} from '@angular/core';
import {RestService} from "../../shared/services/rest.service";
import {DataService} from "../../shared/services/data.service";
import {Location} from '@angular/common';
import {BaseComponent} from "../../shared/base.component";
import {MatSnackBar} from "@angular/material/snack-bar";
import {MatDialog} from "@angular/material/dialog";
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {MatChipInputEvent} from "@angular/material/chips";
import {MatAutocompleteSelectedEvent} from "@angular/material/autocomplete";

import * as _ from 'lodash';
import {COMMA, ENTER} from "@angular/cdk/keycodes";

@Component({
  selector: 'app-task-detail',
  templateUrl: './task-detail.component.html',
  styleUrls: ['./task-detail.component.scss']
})
export class TaskDetailComponent extends BaseComponent implements OnInit {
  private taskForm: FormGroup;

  startDate = new Date();

  users: String[] = ['Bharat', 'Amit', 'Prashant', 'Rishi', 'DD'];
  taskParticipants: string[] = [];

  separatorKeysCodes: number[] = [COMMA, ENTER];

  constructor(private rest: RestService,
              private dataService: DataService,
              private location: Location,
              private matSnackBar: MatSnackBar,
              private matDialog: MatDialog,
              private fb: FormBuilder) {
    super('', matSnackBar, matDialog);
  }

  ngOnInit() {

    this.taskForm = this.fb.group({
      title: new FormControl('', [Validators.min(8), Validators.max(50), Validators.required]),
      description: new FormControl('', [Validators.min(8), Validators.max(150), Validators.required]),
      deadline: new FormControl('', [Validators.required]),
      participants: new FormControl('')
    })

  }

  goBack($event: MouseEvent) {
    this.location.back();
  }

  deleteCallback(data: any) {
  }

  removeParticipant(user: string) {
    _.remove(this.taskParticipants, u => u === user);
    this.users.push(user);
  }

  addParticipants($event: MatChipInputEvent) {
    if ($event.value.trim().length != 0) {
      // this.users.push($event.value);
    }
  }

  selected($event: MatAutocompleteSelectedEvent) {
    this.taskParticipants.push($event.option.value);
    _.remove(this.users, u => u === $event.option.value);
  }

}
