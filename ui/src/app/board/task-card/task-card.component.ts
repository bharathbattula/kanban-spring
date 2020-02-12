import {Component, Input, OnInit} from '@angular/core';
import {Task} from "../../model/Task";
import {ListComponent} from "../list/list.component";
import * as moment from 'moment';

@Component({
  selector: 'app-task-card',
  templateUrl: './task-card.component.html',
  styleUrls: ['./task-card.component.scss']
})
export class TaskCardComponent implements OnInit {

  @Input()
  task: Task;

  @Input()
  listComponent: ListComponent;

  constructor() {
  }

  ngOnInit() {
    this.task.deadLine = moment(this.task.deadLine).toDate();
  }

}
