import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { NzMessageService } from 'ng-zorro-antd';
import { AuthService } from '../../../service/auth.service';

@Component({
  selector: 'pld-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.less']
})
export class ChangePasswordComponent implements OnInit {
  visible = false;
  submitting = false;
  form = this.fb.group({
    oldPsw: [null, [Validators.required]],
    newPsw1: [null, [Validators.required]],
    newPsw2: [null, [Validators.required]],
  });

  constructor(private fb: FormBuilder
    , private messageService: NzMessageService
    , private authService: AuthService
  ) {
  }

  ngOnInit() {
  }

  open() {
    this.form.reset();
    this.visible = true;
  }

  submit() {
    if (this.form.valid) {
      const values = this.form.value;
      if (values.newPsw1 !== values.newPsw2) {
        this.messageService.error('两次新密码必须一致');
        return;
      }
      if (values.newPsw1.length < 6 && values.newPsw1.length > 20) {
        this.messageService.error('新密码长度必须是6至20位');
        return;
      }
      const data = {
        oldPsw: values.oldPsw,
        newPsw: values.newPsw1
      };
      this.submitting = true;
      this.authService.changePassword(data).subscribe(() => {
        this.messageService.success('新密码设置成功');
        this.cancel();
      }, err => {
        this.submitting = false;
      });
    } else {
      for (const controlKey of Object.keys(this.form.controls)) {
        console.log(controlKey, this.form.controls[controlKey].errors);
        this.form.controls[controlKey].markAsDirty();
        this.form.controls[controlKey].updateValueAndValidity();
      }
    }
  }

  cancel() {
    this.submitting = false;
    this.visible = false;
  }
}
