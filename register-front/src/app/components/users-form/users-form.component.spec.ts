import { ComponentFixture, TestBed } from "@angular/core/testing";
import { UsersFormComponent } from "./users-form.component";
import { ReactiveFormsModule } from "@angular/forms";
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { UsersService } from "../../services/users.service";
import { MatFormFieldModule } from "@angular/material/form-field";
import { MatInputModule } from "@angular/material/input";
import { MatIconModule } from "@angular/material/icon";
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';


describe('UsersFormComponent', () => {
  let component: UsersFormComponent;
  let fixture: ComponentFixture<UsersFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [UsersFormComponent],
      imports: [ReactiveFormsModule, HttpClientTestingModule, MatFormFieldModule, MatInputModule, MatIconModule, BrowserAnimationsModule ],
      providers: [UsersService]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UsersFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });


  it('Deverá criar o UsersFormComponent com sucesso', () => {
    expect(component).toBeTruthy();
  });

  it('Deverá criar o form com todos os campos obrigatórios', () => {
    expect(component.userForm.contains('name')).toBeTruthy();
    expect(component.userForm.contains('email')).toBeTruthy();
    expect(component.userForm.contains('password')).toBeTruthy();
    expect(component.userForm.contains('password_confirmation')).toBeTruthy();
  });

  it('Deverá verificar o campo nome como obrigatório', () => {
    const control = component.userForm.get('name');
    control?.setValue('');
    expect(control?.valid).toBeFalsy();
    expect(control?.hasError('required')).toBeTruthy();
  });

  it('Deverá validar a quantidade minima de caracteres (3) no campo nome', () => {
    const control = component.userForm.get('name');
    control?.setValue('ab');
    expect(control?.valid).toBeFalsy();
    expect(control?.hasError('minlength')).toBeTruthy();
  });

  it('Deverá validar a quantidade máxima de caracteres (50) no campo nome', () => {
    const control = component.userForm.get('name');
    control?.setValue('abdefh ldskdjslkjdld skdlskjd  dlkds lkd sdkjkfjkdjlsfff skjslf slkffks lskfslkfj lkdjfslkf j lkjfskjjl sjlkjlkjljlsfjskljfslkfj');
    expect(control?.valid).toBeFalsy();
    expect(control?.hasError('maxlength')).toBeTruthy();
  });

  it('Deverá validar o formato do e-mail', () => {
    const control = component.userForm.get('email');
    control?.setValue('invalid-email');
    expect(control?.valid).toBeFalsy();
    expect(control?.hasError('email')).toBeTruthy();
  });

  it('Deverá validar a quantidade minima de caracteres (6) no campo senha', () => {
    const control = component.userForm.get('password');
    control?.setValue('123');
    expect(control?.valid).toBeFalsy();
    expect(control?.hasError('minlength')).toBeTruthy();
  });

  it('Deverá validar a quantidade máxima de caracteres (20) no campo senha', () => {
    const control = component.userForm.get('password');
    control?.setValue('123456789012345678901');
    expect(control?.valid).toBeFalsy();
    expect(control?.hasError('maxlength')).toBeTruthy();
  });

  it('Deverá validar a quantidade minima de caracteres (6) no campo confirmar_senha', () => {
    const control = component.userForm.get('password_confirmation');
    control?.setValue('123');
    expect(control?.valid).toBeFalsy();
    expect(control?.hasError('minlength')).toBeTruthy();
  });

  it('Deverá validar a quantidade máxima de caracteres (20) no campo confirmar_senhs', () => {
    const control = component.userForm.get('password_confirmation');
    control?.setValue('123456789012345678901');
    expect(control?.valid).toBeFalsy();
    expect(control?.hasError('maxlength')).toBeTruthy();
  });

  it('Deverá validar se a confirmação de senha é igual a senha', () => {
    component.userForm.setValue({
      name: 'User Test',
      email: 'test@example.com',
      password: '123456',
      password_confirmation: '654321'
    });

    component.onSubmit();
    expect(component.userForm.hasError('mismatch')).toBe(true);
  });

  it('Deverá validar se o formulário foi preenchido corretamente', () => {
    component.userForm.setValue({
      name: 'User Test',
      email: 'test@example.com',
      password: '123456',
      password_confirmation: '123456'
    });
    expect(component.userForm.valid).toBeTruthy();
  });

  it('Deverá validar se o emit é disparado quando o formulário for válido', () => {
    spyOn(component.userCreated, 'emit');

    component.userForm.setValue({
      name: 'User Test',
      email: 'test@example.com',
      password: '123456',
      password_confirmation: '123456'
    });

    component.onSubmit();
    expect(component.userCreated.emit).toHaveBeenCalledWith({
      name: 'User Test',
      email: 'test@example.com',
      password: '123456',
      password_confirmation: '123456'
    });
  });

  it('Não deverá disparar o emit quando o formulário for inválido', () => {
    spyOn(component.userCreated, 'emit');

    component.userForm.setValue({
      name: '',
      email: 'invalid-email',
      password: '123',
      password_confirmation: '123'
    });

    component.onSubmit();
    expect(component.userCreated.emit).not.toHaveBeenCalled();
  });
});
