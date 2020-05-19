import {Component, OnDestroy, OnInit} from '@angular/core';
import {RestService} from '../shared/services/rest.service';
import {DataService} from '../shared/services/data.service';
import {ActivatedRoute, Router} from '@angular/router';
import {Project} from '../shared/model/Project';
import {List} from '../shared/model/List';
import * as _ from 'lodash';
import {animate, state, style, transition, trigger} from '@angular/animations';
import {Subscription} from "rxjs";

@Component({
  selector: 'app-board',
  templateUrl: './board.component.html',
  styleUrls: ['./board.component.scss'],
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
export class BoardComponent implements OnInit, OnDestroy {

  list: List[];

  project: Project;
  currentState: string = 'hidden';
  addingList: boolean = false;

  subscriber: Subscription;

  constructor(private rest: RestService, private dataService: DataService, private router: Router, private activatedRoute: ActivatedRoute) {
  }

  ngOnInit() {
    this.activatedRoute.paramMap.subscribe(value => {

      if (!this.project) {

        this.project = this.dataService.getProject(value.get('name'));
        this.dataService.setCurrentProjectValue(this.project);
        this.loadProjectData();
      }
    });

    //subscription get the project details in case of page refresh
    this.subscriber = this.dataService.projectData$.subscribe(
      (projects) => {

        if (!this.project && projects.length > 0) {
          const urlSegment = this.router.url.split('/').pop();

          this.project = projects.find(project => project.name === urlSegment);
          this.dataService.setCurrentProjectValue(this.project);
          this.loadProjectData();
        }
      },
      (error) => console.log(error)
    );
  }

  loadProjectData() {

    this.rest.request(null, `project/${this.project.id}/list`, 'GET')
        .then(list => {
          this.list = list;
          console.log(this.list);
        })
        .catch(error => {
          if (error.status === 401) {
            this.router.navigateByUrl('/login');
          }
        });
  }

  addNewList(listForm: any) {

    this.addingList = true;
    console.log(listForm.value);

    if (_.find(this.list, l => _.isEqual(l.name, listForm.value))) {
      return;
    }

    const requestObj = {
      name: listForm.value
    }

    this.rest.request(requestObj, `project/${this.project.id}/list`, 'POST')
      .then(list => {
        console.log(list);
        this.list.push(list);

        this.addingList = true;
        this.currentState = 'hidden';
      })
      .catch(error => {
        console.log(error);
        this.addingList = true;
        this.currentState = 'hidden';
      });
  }

  ngOnDestroy(): void {
    this.subscriber.unsubscribe();
  }
}
