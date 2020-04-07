import {MatSnackBar, MatSnackBarRef} from "@angular/material/snack-bar";
import {Notification} from "./model/Notification";
import {MatDialog, MatDialogRef} from "@angular/material/dialog";
import {ConfirmationComponent} from "../dialog/confirmation/confirmation.component";

export abstract class BaseComponent {

  private snackbarRef: MatSnackBarRef<any>;

  private confirmDialogRef: MatDialogRef<ConfirmationComponent>;

  protected constructor(private title: string, public snackBar: MatSnackBar, public dialog: MatDialog) {
  }

  showNotification(notification: Notification) {
    this.snackbarRef = this.snackBar.open(notification.message, notification.action, {
      data: notification.data,
      duration: 5000
    });

    this.snackbarRef.afterDismissed().subscribe(value => {
      console.log(`Snackbar Dismissed ==> ${value}`);
    });

    this.snackbarRef.onAction().subscribe(value => {
      console.log(`Snackbar Action ==> ${value}`);
    })
  }

  abstract deleteCallback(data: any);

  deleteConfirmation(data: any, action: string = '') {

    this.confirmDialogRef = this.dialog.open(ConfirmationComponent, {
      disableClose: false,
      width: '',
      height: '',
      position: {
        top: '',
        right: '',
        bottom: '',
        left: ''
      }
    })

    this.confirmDialogRef.componentInstance.type = action;

    this.confirmDialogRef.afterClosed().subscribe(value => {
      if (value) {
        this.deleteCallback(data);
      }
      this.confirmDialogRef = null;
    })

  }

}
