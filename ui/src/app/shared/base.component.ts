import {MatSnackBar, MatSnackBarRef} from "@angular/material/snack-bar";
import {Notification} from "./model/Notification";

export abstract class BaseComponent {

  private snackbarRef:MatSnackBarRef<any>;

  protected constructor(private title:string, public snackBar: MatSnackBar ) {

  }

  showNotification(notification:Notification) {
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
}
