import {Component, OnInit} from '@angular/core';
import {DataService} from "../shared/services/data.service";
import {Project} from "../shared/model/Project";

@Component({
  selector: 'app-project-setting',
  templateUrl: './project-setting.component.html',
  styleUrls: ['./project-setting.component.scss']
})
export class ProjectSettingComponent implements OnInit {

  project: Project;

  users: any[] = [
    {
      name: "Bharath Battula",
      email: 'bharathbattula@gmail.com'
    }, {
      name: "Kent Simpson",
      email: 'kent@gmail.com'
    }, {
      name: 'Prashant',
      email: 'prashant@gmail.com'
    }];


  constructor(private dataService: DataService) {
  }

  ngOnInit() {
    this.project = this.dataService.getCurrentProjectValue();
  }

  goBack($event: MouseEvent) {
    //route back to previous page
  }

  deleteConfirm($event: MouseEvent, user: string) {
    //delete confirmation popup
  }

  addUser($event: MouseEvent) {
    //add new user to current project
  }
}
