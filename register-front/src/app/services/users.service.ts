import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, map, Observable, throwError } from 'rxjs';
import { INewUser, IUser } from '../interfaces/users.interfaces';
import { MatSnackBar } from '@angular/material/snack-bar';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class UsersService {

  private baseUrl = `${environment.apiUrl}/users`

  constructor(private http: HttpClient, private snackBar: MatSnackBar) { }


  add(newUser: INewUser): Observable<IUser>{
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http.post<IUser>(this.baseUrl, newUser, {headers}).pipe(
      map(resp => resp),
      catchError(this.handleError.bind(this))
    )
  }


  fetchAll(): Observable<IUser[]>{
    return this.http.get<IUser[]>(this.baseUrl).pipe(
      map(resp => resp),
      catchError(this.handleError.bind(this))
    )
  }

  getById(id: string): Observable<IUser>{
    return this.http.get<IUser>(`${this.baseUrl}/${id}`).pipe(
      map(resp => resp),
      catchError(this.handleError.bind(this))
    )
  }

  deleteById(id: string): Observable<void>{
    return this.http.delete<void>(`${this.baseUrl}/${id}`).pipe(
      catchError(this.handleError.bind(this))
    )
  }

  private handleError(error: any): Observable<never> {
    let errorMessage = '';

    if(error.status === 0) {
      errorMessage = "Ocorreu um erro ao tentar conexão com o servidor. Por favor, tente mais tarde."
    }else {
      errorMessage = error.error?.message || 'Ocorreu um erro ao tentar realizar a requisição.';
    }

    this.snackBar.open(errorMessage, 'X', {
      duration: 5000,
      verticalPosition: 'top',
      horizontalPosition: 'center'
    });
    return throwError(() => new Error(errorMessage));
  }
}
