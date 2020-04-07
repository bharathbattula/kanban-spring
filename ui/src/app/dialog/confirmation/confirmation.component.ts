import {Component, OnInit} from '@angular/core';
import {MatDialogRef} from '@angular/material';

@Component({
  selector: 'confirmation',
  templateUrl: './confirmation.component.html',
  styleUrls: ['./confirmation.component.scss']
})
export class ConfirmationComponent implements OnInit {

  type:string;

  constructor(public dialogRef: MatDialogRef<ConfirmationComponent>) {
  }

  ngOnInit() {
  }

}
