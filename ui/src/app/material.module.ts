import {NgModule} from '@angular/core';
import {
  MatButtonModule,
  MatCardModule,
  MatFormFieldModule,
  MatIconModule,
  MatListModule,
  MatSidenavModule,
  MatTabsModule,
  MatToolbarModule
} from "@angular/material";
import {DragDropModule} from "@angular/cdk/drag-drop";


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
  ]
})
export class MaterialModule { }
