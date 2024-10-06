import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, map, Observable } from 'rxjs';
import { INewUser, IUser } from '../interfaces/users.interfaces';
import { IError } from '../interfaces/errors.interfaces';

@Injectable({
  providedIn: 'root'
})
export class UsersService {

  private baseUrl = 'http://localhost:8080/users';

  constructor(private http: HttpClient) { }


  add(newUser: INewUser): Observable<IUser>{
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http.post<IUser>(this.baseUrl, newUser, {headers}).pipe(
      map(resp => resp),
      catchError(this.handleError)
    )
  }


  fetchAll(): Observable<IUser[]>{
    return this.http.get<IUser[]>(this.baseUrl).pipe(
      map(resp => resp),
      catchError(this.handleError)
    )
  }

  getById(id: string): Observable<IUser>{
    return this.http.get<IUser>(`${this.baseUrl}/${id}`).pipe(
      map(resp => resp),
      catchError(this.handleError)
    )
  }

  deleteById(id: string): Observable<void>{
    return this.http.delete<void>(`${this.baseUrl}/${id}`).pipe(
      catchError(this.handleError)
    )
  }

  private handleError(error: IError): Observable<never> {
    console.error('Ocorreu um erro na requisição', error);
    throw error;
  }
}
