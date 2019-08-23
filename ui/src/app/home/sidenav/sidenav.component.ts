import {Component, Input, OnInit} from '@angular/core';
import {Project} from "../../model/Project";
import {MatDialog} from "@angular/material";
import {AddProjectComponent} from "../add-project/add-project.component";

const PROJECTS:Project[] = [
  {id:1 , name:"Signage"},
  {id:2 , name:"FCC"}
]

@Component({
  selector: 'app-sidenav',
  templateUrl: './sidenav.component.html',
  styleUrls: ['./sidenav.component.scss']
})
export class SidenavComponent implements OnInit {

  @Input('bigScreen')
  changeWidth = true;

  projects = PROJECTS;

  constructor(public dialog: MatDialog) {
  }

  ngOnInit() {
  }

  addProjectDialog(): void {
    this.dialog.open(AddProjectComponent, {
      width: '450px'
    });
    this.dialog.afterAllClosed.subscribe(result => {
      console.log(`dialog closed ${result}`);
    });
  }


}
