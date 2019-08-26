import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {Project} from "../../model/Project";
import {MatDialog} from "@angular/material";
import {AddProjectComponent} from "../add-project/add-project.component";
import {DataService} from "../../data.service";
import {Subscription} from "rxjs";

import * as _ from 'lodash';

@Component({
  selector: 'app-sidenav',
  templateUrl: './sidenav.component.html',
  styleUrls: ['./sidenav.component.scss']
})
export class SidenavComponent implements OnInit, OnDestroy {

  @Input('bigScreen')
  changeWidth = true;
  
  projects: Project[] = [];

  subscriber: Subscription;

  constructor(public dialog: MatDialog,
              public dataService: DataService) {
  }

  ngOnInit() {
    this.subscriber = this.dataService.projectData$.subscribe(
      projects => {
        _.forEach(projects,
          project => {
            if (!_.find(this.projects, currentProject => currentProject.name === project.name)) {
              this.projects.push(project);
            }
          });
      },
      error => console.log(error)
    )
    ;
  }

  addProjectDialog(): void {
    this.dialog.open(AddProjectComponent, {
      width: '450px'
    });

    this.dialog.afterAllClosed.subscribe(result => {
      console.log('Add project dialog closed')
    });
  }

  ngOnDestroy(): void {
    this.subscriber.unsubscribe();
  }


}
