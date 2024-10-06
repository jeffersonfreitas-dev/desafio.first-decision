import { NgModule } from "@angular/core";
import { AngularMaterialModule } from "../angular-material/angular-material.module";
import { UsersListComponent } from './users-list/users-list.component';
import { BrowserModule } from "@angular/platform-browser";

@NgModule({
  declarations: [
    UsersListComponent
  ],
  imports: [
    BrowserModule,
    AngularMaterialModule
  ],
  exports: [
    UsersListComponent
  ]
})
export class ComponentsModule { }
