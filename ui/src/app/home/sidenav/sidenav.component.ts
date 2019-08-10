import {Component, Input, OnInit} from '@angular/core';
import {Project} from "../../model/Project";

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

  constructor() { }

  ngOnInit() {
  }

}
