import { Component, Input, OnInit } from '@angular/core';
import { INewUser, IUser } from '../../interfaces/users.interfaces';
import { UsersService } from '../../services/users.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatDialog } from '@angular/material/dialog';
import { UserDialogDeleteComponent } from '../user-dialog-delete/user-dialog-delete.component';
import { UserDialogDetailComponent } from '../user-dialog-detail/user-dialog-detail.component';


@Component({
  selector: 'app-users-list',
  templateUrl: './users-list.component.html',
  styleUrl: './users-list.component.scss'
})
export class UsersListComponent implements OnInit {

  constructor(private apiService: UsersService, private snackBar: MatSnackBar, public dialog: MatDialog) {}
  usersList: IUser[] = [];
  displayedColumns: string[] = ['name', 'email', 'delete', 'detail'];
  loading: boolean = false;
  @Input() loadingAdd: boolean = this.loading

  ngOnInit(): void {
    this.onFetchAll()
  }

  onUserCreate(user: INewUser) {
    this.loading = true;
    this.apiService.add(user).subscribe({
      next: () => {
        this.loading = false,
        this.onFetchAll(),
        this.snackBar.open("Usuário salvo com sucesso", 'X', {
          duration: 5000,
          verticalPosition: 'top',
          horizontalPosition: 'center'
        });
      },
      error: () =>  this.loading = false
    });
  }

  onFetchAll() {
    this.loading = true
    this.apiService.fetchAll().subscribe({
      next: (resp) => {this.usersList = resp,  this.loading = false},
      error: () =>  this.loading = false
    })
  }

  onRemove(element: IUser) {
    const dialogRef = this.dialog.open(UserDialogDeleteComponent, {
      data: element.name,
    });

    dialogRef.afterClosed().subscribe(result => {
      if(result) {
        this.loading = true
        this.apiService.deleteById(element.uuid).subscribe({
          next: () => {this.onFetchAll(), this.loading = false},
          error: () =>  this.loading = false
        })
      }
    });
  }


  onDetail(element: IUser) {
    this.dialog.open(UserDialogDetailComponent, {
      data: element,
    });
  }
}
