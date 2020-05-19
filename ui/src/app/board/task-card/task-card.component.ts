import {Component, Input, OnInit} from '@angular/core';
import {Task} from '../../shared/model/Task';
import {ListComponent} from '../list/list.component';
import * as moment from 'moment';
import {User} from "../../shared/model/User";
import {DataService} from "../../shared/services/data.service";

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

  constructor(private dataService: DataService) {
  }

  ngOnInit() {
    this.task.deadLine = moment(this.task.deadLine).toDate();

    this.task.creator.initials = this.returnInitials(this.task.creator);
    this.task.participants.forEach(value => value.initials = this.returnInitials(value));
  }

  returnInitials(user: User) {
    return `${user.firstName ? user.firstName.charAt(0).toUpperCase() : ''}${user.lastName ? user.lastName.charAt(0).toUpperCase() : ''}`;
  }

  openTaskDetailDrawer($event: MouseEvent) {
    this.dataService.setCurrentTask(this.task);
  }
}
