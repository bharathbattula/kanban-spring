import {NgModule} from '@angular/core';
import {
  MatButtonModule,
  MatCardModule,
  MatDialogModule,
  MatFormFieldModule,
  MatIconModule,
  MatListModule,
  MatMenuModule,
  MatSidenavModule,
  MatSnackBarModule,
  MatTabsModule,
  MatToolbarModule
} from '@angular/material';
import {DragDropModule} from '@angular/cdk/drag-drop';
import {MatChipsModule} from '@angular/material/chips';
import {MatDatepickerModule} from '@angular/material/datepicker';
import {MatNativeDateModule} from '@angular/material/core';
import {MatTooltipModule} from '@angular/material/tooltip';
import {MatProgressSpinnerModule} from '@angular/material/progress-spinner';
import {MatSelectModule} from "@angular/material/select";

@NgModule({
  declarations: [],
  imports: [
    MatButtonModule,
    MatCardModule,
    DragDropModule,
    MatTabsModule,
    MatFormFieldModule,
    MatSidenavModule,
    MatToolbarModule,
    MatIconModule,
    MatListModule,
    MatMenuModule,
    MatDialogModule,
    MatChipsModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatTooltipModule,
    MatProgressSpinnerModule,
    MatSnackBarModule,
    MatSelectModule,
  ],
  exports : [
    MatButtonModule,
    MatCardModule,
    DragDropModule,
    MatTabsModule,
    MatFormFieldModule,
    MatSidenavModule,
    MatToolbarModule,
    MatIconModule,
    MatListModule,
    MatMenuModule,
    MatDialogModule,
    MatChipsModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatTooltipModule,
    MatProgressSpinnerModule,
    MatSnackBarModule,
    MatSelectModule
  ]
})
export class MaterialModule { }
