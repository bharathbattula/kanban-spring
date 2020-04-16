import {Component, OnInit} from '@angular/core';
import {DataService} from "../shared/services/data.service";
import {Project} from "../shared/model/Project";

@Component({
  selector: 'app-project-setting',
  templateUrl: './project-setting.component.html',
  styleUrls: ['./project-setting.component.scss']
})
export class ProjectSettingComponent implements OnInit {

  project:Project;

  constructor(private dataService:DataService) { }

  ngOnInit() {
    this.project = this.dataService.getCurrentProjectValue();
  }

}
