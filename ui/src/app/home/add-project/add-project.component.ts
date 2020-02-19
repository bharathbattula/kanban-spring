import {Component, OnInit} from '@angular/core';
import {MatDialogRef} from '@angular/material';
import {RestService} from '../../rest.service';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Project} from '../../model/Project';
import {DataService} from '../../data.service';

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
              public fb: FormBuilder,
              public dataService: DataService) {


    this.projectFrom = this.fb.group( {
      name: ['', Validators.required],
      description: ['', ]
    })
  }

  ngOnInit() {

  }

  saveProject(formValue: any) {
    this.savingProject = true;

    if (!formValue.name) {
      return;
    }

    const requestBody: Project = {
      name: formValue.name,
      description: formValue.description
    };

    this.rest.request(requestBody, 'project', 'POST')
      .then(result => {

        let currentValue = this.dataService.projectDataSource.value;
        let updatedValue = [...currentValue, result];
        this.dataService.projectDataSource.next(updatedValue);

        this.savingProject = false;
        this.closeDialog(result);
      })
      .catch(error => {
        console.log(error);
        this.savingProject = false;
        this.closeDialog(null);
      });
  }

  closeDialog(data: any) {
    this.dialogRef.close(data);
  }
}
