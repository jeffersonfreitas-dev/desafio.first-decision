import { Component, Inject, inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialog, MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-user-dialog-delete',
  templateUrl: './user-dialog-delete.component.html',
  styleUrl: './user-dialog-delete.component.scss'
})
export class UserDialogDeleteComponent {

  constructor(
    public dialogRef: MatDialogRef<UserDialogDeleteComponent>,
    @Inject(MAT_DIALOG_DATA) public data: string,
  ) {}

  onConfirm(result: boolean): void {
    this.dialogRef.close(result);
  }

}
