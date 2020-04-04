import {Component, OnInit} from '@angular/core';
import {RestService} from '../rest.service';
import {DataService} from '../data.service';
import {ActivatedRoute, Router} from '@angular/router';
import {Project} from '../model/Project';
import {List} from '../model/List';
import * as _ from 'lodash';
import {animate, state, style, transition, trigger} from '@angular/animations';

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
export class BoardComponent implements OnInit {

  list: List[];

  project: Project;
  currentState: string = 'hidden';
  addingList: boolean = false;


  constructor(private rest: RestService, private dataService: DataService, private router: Router, private activatedRoute: ActivatedRoute) {
  }

  ngOnInit() {
    this.activatedRoute.paramMap.subscribe(value => {

      this.project = this.dataService.getProject(value.get('name'));
      this.dataService.setCurrentProjectValue(this.project);

      this.loadProjectData();
    })
    // _.forEach(this.list, l => _.forEach(l, t => t.user.fullName = _.startCase(t.user.firstName + ' ' + t.user.lastName)));
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
}
