import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, map, Observable, throwError } from 'rxjs';
import { INewUser, IUser } from '../interfaces/users.interfaces';
import { IError } from '../interfaces/errors.interfaces';
import { MatSnackBar } from '@angular/material/snack-bar';

@Injectable({
  providedIn: 'root'
})
export class UsersService {

  private baseUrl = 'http://localhost:8080/users';

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

  private handleError(error: IError): Observable<never> {
    const errorErrors = error.error.errors.length > 0 ? error.error.errors.join(";") : "";
    let errorMessage = error.error?.message || 'Ocorreu um erro ao tentar realizar a requisição.';

    if(errorErrors) errorMessage + '\n' + errorErrors;

    this.snackBar.open(errorMessage, 'X', {
      duration: 5000,
      verticalPosition: 'top',
      horizontalPosition: 'center'
    });
    return throwError(() => new Error(errorMessage));
  }
}
