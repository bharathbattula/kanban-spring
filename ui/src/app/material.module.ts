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
  MatTabsModule,
  MatToolbarModule
} from "@angular/material";
import {DragDropModule} from "@angular/cdk/drag-drop";
import {MatChipsModule} from "@angular/material/chips";


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
    MatChipsModule
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
    MatChipsModule
  ]
})
export class MaterialModule { }
