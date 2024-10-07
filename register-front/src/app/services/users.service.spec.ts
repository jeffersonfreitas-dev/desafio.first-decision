import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { UsersService } from './users.service';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { INewUser, IUser } from '../interfaces/users.interfaces';

describe('UsersService', () => {
  let service: UsersService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, MatSnackBarModule],
      providers: [UsersService]
    });
    service = TestBed.inject(UsersService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('Deverá criar o service com sucesso', () => {
    expect(service).toBeTruthy();
  });

  it('Deverá adicionar um novo usuário com sucesso', () => {
    const mockNewUser: INewUser = { name: 'Test User', email: 'test@example.com', password: '123456', password_confirmation: '123456' };
    const mockUserResponse: IUser = { uuid: 'abcd123', name: 'Test User', email: 'test@example.com' };

    service.add(mockNewUser).subscribe((user) => {
      expect(user).toEqual(mockUserResponse);
    });

    const req = httpMock.expectOne(`${service['baseUrl']}`);
    expect(req.request.method).toBe('POST');
    req.flush(mockUserResponse);
  });


  it('Deverá retornar vazio quando não houver registros salvos', () => {
    const mockUsers: IUser[] = [];

    service.fetchAll().subscribe((users) => {
      expect(users.length).toBe(0);
      expect(users).toEqual(mockUsers);
    });

    const req = httpMock.expectOne(`${service['baseUrl']}`);
    expect(req.request.method).toBe('GET');
    req.flush(mockUsers);
  });


  it('Deverá trazer todos os registros salvos (2)', () => {
    const mockUsers: IUser[] = [
      { uuid: 'abcd123', name: 'User One', email: 'user1@example.com' },
      { uuid: 'dcba321', name: 'User Two', email: 'user2@example.com' }
    ];

    service.fetchAll().subscribe((users) => {
      expect(users.length).toBe(2);
      expect(users).toEqual(mockUsers);
    });

    const req = httpMock.expectOne(`${service['baseUrl']}`);
    expect(req.request.method).toBe('GET');
    req.flush(mockUsers);
  });


  it('Deverá retornar o registro pelo ID', () => {
    const mockUser: IUser = { uuid: 'abcd123', name: 'User One', email: 'user1@example.com' };

    service.getById('abcd123').subscribe((user) => {
      expect(user).toEqual(mockUser);
    });

    const req = httpMock.expectOne(`${service['baseUrl']}/abcd123`);
    expect(req.request.method).toBe('GET');
    req.flush(mockUser);
  });


  it('Deverá deletar um registro por ID', () => {
    service.deleteById('abcd123').subscribe((response) => {
      expect(response).toBeUndefined();
    });

    const req = httpMock.expectOne(`${service['baseUrl']}/abcd123`);
    expect(req.request.method).toBe('DELETE');
  });


  // Teste para erro de requisição
  it('Deverá validar o erro no cadastro de um novo registro', () => {
    const mockErrorResponse = { status: 500, statusText: 'Internal Server Error' };
    const errorMessage = 'Ocorreu um erro ao tentar realizar a requisição.';

    spyOn(service['snackBar'], 'open');

    service.add({ name: 'Test', email: 'test@example.com', password: '123456', password_confirmation: '123456' }).subscribe({
      next: () => fail('should have failed with 500 error'),
      error: (error) => {
        expect(error.message).toBe(errorMessage);
      }
    });

    const req = httpMock.expectOne(`${service['baseUrl']}`);
    req.flush({ message: errorMessage }, mockErrorResponse);
    expect(service['snackBar'].open).toHaveBeenCalledWith(errorMessage, 'X', {
      duration: 5000,
      verticalPosition: 'top',
      horizontalPosition: 'center'
    });
  });
});
