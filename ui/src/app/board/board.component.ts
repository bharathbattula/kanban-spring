import {Component, OnInit} from '@angular/core';
import {myTasks1, myTasks2} from "../model/TaskMock";
import * as _ from 'lodash';

@Component({
  selector: 'app-board',
  templateUrl: './board.component.html',
  styleUrls: ['./board.component.scss']
})
export class BoardComponent implements OnInit {

  list = [
    myTasks1,
    myTasks2
  ]

  constructor() { }

  ngOnInit() {
    _.forEach(this.list, l => _.forEach(l, t => t.user.fullName = _.startCase(t.user.firstName + ' ' + t.user.lastName)));
  }

}
