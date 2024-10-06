import { Component } from '@angular/core';
import { INewUser, IUser } from '../../interfaces/users.interfaces';
import { UserList } from '../../data/UserList';
import { UsersService } from '../../services/users.service';

@Component({
  selector: 'app-users-list',
  templateUrl: './users-list.component.html',
  styleUrl: './users-list.component.scss'
})
export class UsersListComponent {

  constructor(private apiService: UsersService) {}

  usersList: IUser[] = UserList;
  displayedColumns: string[] = ['name', 'email', 'actions'];

  onRemove(element: IUser) {

    const newUser: INewUser = {
      name: 'Jeffinho new',
      email: 'jeffinhonew@gmail.com',
      password: 'abcABC123',
      password_confirmation: 'abcABC123'
    }

    this.apiService.add(newUser).subscribe({
      next: (resp) => console.log(resp),
      error: (err) => console.error(err.error.message)
    })
  }
}
