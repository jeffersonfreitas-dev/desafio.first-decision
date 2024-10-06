import { Component, EventEmitter, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { UsersService } from '../../services/users.service';
import { INewUser } from '../../interfaces/users.interfaces';

@Component({
  selector: 'app-users-form',
  templateUrl: './users-form.component.html',
  styleUrl: './users-form.component.scss'
})
export class UsersFormComponent {

  passwordShow: boolean = true;
  confirmationShow: boolean = true;
  userForm: FormGroup;
  @Output() userCreated = new EventEmitter<any>();


  constructor(private fb: FormBuilder, private apiService: UsersService) {
    this.userForm = this.fb.group({
      name: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(50)]],
      email: ['', [Validators.required, Validators.email, Validators.maxLength(120)]],
      password: ['', [Validators.required, Validators.minLength(6), Validators.maxLength(20)]],
      password_confirmation: ['', [Validators.required, Validators.minLength(6), Validators.maxLength(20)]]
    });
  }

  onSubmit(): void {
    if (this.userForm.valid) {
      this.userCreated.emit(this.userForm.value),
      this.userForm.reset()
    } else {
      console.log('O formulário contém erros');
    }
  }

  onPasswordShow() {
    return !this.passwordShow
  }

  onConfirmationShow() {
    return !this.confirmationShow
  }

}
