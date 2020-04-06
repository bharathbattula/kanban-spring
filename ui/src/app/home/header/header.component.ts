import {Component, EventEmitter, OnDestroy, OnInit, Output, ViewChild} from '@angular/core';
import {MatToolbar} from '@angular/material';
import {RestService} from '../../shared/services/rest.service';
import {DataService} from '../../shared/services/data.service';
import {Router} from '@angular/router';
import {UserSession} from "../../shared/model/UserSession";
import {Subscription} from "rxjs";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit, OnDestroy {

  @ViewChild('appToolbar', {static: true}) appToolbar: MatToolbar;

  @Output() public sidenavToggle = new EventEmitter();

  private currentUser: UserSession;
  private currentUserSubscriber: Subscription;

  constructor(private rest: RestService, private dataService: DataService, private router: Router) {
    this.currentUserSubscriber = this.rest.currentUser.subscribe(user => this.currentUser = user);
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

  ngOnDestroy(): void {
    this.currentUserSubscriber.unsubscribe();
  }
}
