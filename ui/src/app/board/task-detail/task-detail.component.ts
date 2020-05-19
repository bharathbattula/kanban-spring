import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {RestService} from "../../shared/services/rest.service";
import {DataService} from "../../shared/services/data.service";
import {Subscription} from "rxjs";
import {MatDrawer} from "@angular/material/sidenav";

@Component({
  selector: 'app-task-detail',
  templateUrl: './task-detail.component.html',
  styleUrls: ['./task-detail.component.scss']
})
export class TaskDetailComponent implements OnInit, OnDestroy {

  task: any;

  @Input()
  drawer: MatDrawer;

  private taskInDetailSubscriber: Subscription;

  constructor(private rest: RestService,
              private dataService: DataService) {
  }

  ngOnInit() {
    this.taskInDetailSubscriber = this.dataService.currentTaskInDetailWindow.subscribe(value => {

      if (value) {
        this.task = value;
        console.log(this.task);
        this.drawer.open();
      }
    });
  }

  goBack($event: MouseEvent) {
    this.drawer.close();
  }

  ngOnDestroy(): void {
    this.taskInDetailSubscriber.unsubscribe();
  }

}
