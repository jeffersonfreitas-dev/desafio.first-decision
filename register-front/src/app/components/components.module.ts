import { NgModule } from "@angular/core";
import { AngularMaterialModule } from "../angular-material/angular-material.module";
import { UsersListComponent } from './users-list/users-list.component';
import { BrowserModule } from "@angular/platform-browser";
import { UsersFormComponent } from './users-form/users-form.component';
import { ReactiveFormsModule } from "@angular/forms";
import { UserDialogDeleteComponent } from './user-dialog-delete/user-dialog-delete.component';
import { MatCardModule } from '@angular/material/card';
import { UserDialogDetailComponent } from './user-dialog-detail/user-dialog-detail.component';

@NgModule({
  declarations: [
    UsersListComponent,
    UsersFormComponent,
    UserDialogDeleteComponent,
    UserDialogDetailComponent
  ],
  imports: [
    BrowserModule,
    AngularMaterialModule,
    ReactiveFormsModule,
    MatCardModule
  ],
  exports: [
    UsersListComponent,
    MatCardModule
  ]
})
export class ComponentsModule { }
