import {Component, EventEmitter, OnInit, Output, ViewChild} from '@angular/core';
import {MatToolbar} from '@angular/material';
import {RestService} from '../../rest.service';
import {DataService} from '../../data.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {

  @ViewChild('appToolbar', {static: true}) appToolbar: MatToolbar;

  @Output() public sidenavToggle = new EventEmitter();

  constructor(private rest: RestService, private dataService: DataService, private router: Router) {
  }

  ngOnInit() {
    this.loadProjects();
  }

  toggleNavigation() {
    this.sidenavToggle.emit();
  }

  loadProjects() {

    this.rest.request(null, 'project', 'GET')
      .then(projects => {
        //adding data to BehaviourSubject, subscriber get's the data in sidenav.component.ts
        this.dataService.projectDataSource.next(projects);
      })
      .catch(error => {
      });
  }

  logout($event: MouseEvent) {
    this.rest.logout();
  }
}
