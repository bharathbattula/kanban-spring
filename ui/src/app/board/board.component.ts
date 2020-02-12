import {Component, OnInit} from '@angular/core';
import {RestService} from "../rest.service";
import {DataService} from "../data.service";
import {ActivatedRoute, Router} from "@angular/router";
import {Project} from "../model/Project";
import {List} from "../model/List";

@Component({
  selector: 'app-board',
  templateUrl: './board.component.html',
  styleUrls: ['./board.component.scss']
})
export class BoardComponent implements OnInit {

  list: List[]; /*= [
    myTasks1,
    myTasks2
  ]*/

  project: Project;

  constructor(private rest: RestService, private dataService: DataService, private router: Router, private activatedRoute: ActivatedRoute) {
  }

  ngOnInit() {
    this.activatedRoute.paramMap.subscribe(value => {
      this.project = this.dataService.getProject(value.get('name'));
      this.loadProjectData();
    })

    // _.forEach(this.list, l => _.forEach(l, t => t.user.fullName = _.startCase(t.user.firstName + ' ' + t.user.lastName)));
  }

  loadProjectData() {

    this.rest.request(null, `project/9/list`, 'GET')
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
}
