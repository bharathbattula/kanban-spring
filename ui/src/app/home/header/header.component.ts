import {Component, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {MatToolbar} from '@angular/material';
import {RestService} from '../../shared/services/rest.service';
import {DataService} from '../../shared/services/data.service';
import {Router} from '@angular/router';
import {UserSession} from "../../shared/model/UserSession";
import {Subscription} from "rxjs";
import {Project} from "../../shared/model/Project";
import {MatSelectChange} from "@angular/material/select";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit, OnDestroy {

  @ViewChild('appToolbar', {static: true}) appToolbar: MatToolbar;

  private currentUser: UserSession;
  private currentUserSubscriber: Subscription;

  private projects: Project[] = [];
  private projectSubscriber: Subscription;

  constructor(private rest: RestService, private dataService: DataService, private router: Router) {
  }

  ngOnInit() {

    this.currentUserSubscriber = this.rest.currentUser.subscribe(user => this.currentUser = user);

    this.loadProjects();

    /*this.projectSubscriber = this.dataService.projectData$.subscribe(
      projects => {
        _.forEach(projects,
          project => {
            if (!_.find(this.projects, currentProject => currentProject.name === project.name)) {
              this.projects.push(project);
            }
          });
      },
      error => console.log(error)
    );*/

  }


  loadProjects() {

    this.rest.request(null, 'project', 'GET')
      .then(projects => {
        //adding data to BehaviourSubject, subscriber get's the data in sidenav.component.ts
        this.dataService.projectDataSource.next(projects);
        this.projects = projects;
      })
      .catch(error => {
      });
  }

  logout($event: MouseEvent) {
    this.rest.logout();
  }

  ngOnDestroy(): void {
    this.currentUserSubscriber.unsubscribe();
    // this.projectSubscriber.unsubscribe();
  }

  projectChange($event: MatSelectChange) {

    const project = $event.value;

    if (project && project.id) {

      this.dataService.setCurrentProjectValue(project);

      this.router.navigate(['app', 'project', project.name]);
    }
  }
}
