import {Component, OnInit} from '@angular/core';
import {MatDialogRef} from "@angular/material";
import {RestService} from "../../rest.service";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-add-project',
  templateUrl: './add-project.component.html',
  styleUrls: ['./add-project.component.scss']
})
export class AddProjectComponent implements OnInit {
  savingProject = false;

  projectFrom:FormGroup;

  constructor(public dialogRef: MatDialogRef<AddProjectComponent>,
              public rest: RestService,
              public fb: FormBuilder) {
    this.projectFrom = this.fb.group( {
      name: ['', Validators.required],
      description: ['', ]
    })
  }

  ngOnInit() {

  }

  saveProject() {
    this.savingProject = true;
    console.log(this.projectFrom.value);
    this.rest.request({}, "/project", "POST")
      .then(result => {
        console.log(result);
        this.savingProject = false;
        this.closeDialog(result);
      })
      .catch(error => {
        console.log(error);
        this.savingProject = false;
        this.closeDialog(error);
      });
  }

  closeDialog(data:any) {
    this.dialogRef.close(data);
  }
}
