import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { IUser } from '../../interfaces/users.interfaces';

@Component({
  selector: 'app-user-dialog-detail',
  templateUrl: './user-dialog-detail.component.html',
  styleUrl: './user-dialog-detail.component.scss'
})
export class UserDialogDetailComponent {

  constructor(
    public dialogRef: MatDialogRef<UserDialogDetailComponent>,
    @Inject(MAT_DIALOG_DATA) public data: IUser,
  ) {}

}
