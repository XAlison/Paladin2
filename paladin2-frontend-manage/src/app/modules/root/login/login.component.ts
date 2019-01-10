import { Component, OnInit } from '@angular/core';
import { NzMessageService } from 'ng-zorro-antd';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../../../service/auth.service';
import { LoginSuccessInfo } from '../../../model/login-success-info';

@Component({
  selector: 'pld-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.less']
})
export class LoginComponent implements OnInit {
  loading = false;
  validateForm: FormGroup = this.fb.group({
    account: [null, [Validators.required]],
    password: [null, [Validators.required]],
  });

  constructor(private fb: FormBuilder
    , private router: Router
    , private message: NzMessageService
    , private authService: AuthService) {
  }

  ngOnInit(): void {
  }

  submitForm(): void {
    this.loading = true;
    if (this.validateForm.valid) {
      this.authService.login(this.validateForm.value).subscribe({
        next: ({ token }: LoginSuccessInfo) => {
          this.authService.cleanUiPermission();
          this.router.navigate(['/']);
        },
        error: () => {
          this.loading = false;
        },
        complete: () => {
          this.loading = false;
        }
      });
    } else {
      for (const controlKey of Object.keys(this.validateForm.controls)) {
        this.validateForm.controls[controlKey].markAsDirty();
        this.validateForm.controls[controlKey].updateValueAndValidity();
      }
      this.loading = false;
    }
  }

}
