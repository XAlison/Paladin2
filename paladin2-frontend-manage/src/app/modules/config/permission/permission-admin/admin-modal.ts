import { FormBuilder, FormGroup, Validators } from '@angular/forms';

export
class AdminModal {
  adminId = null;
  visible: boolean;
  loading: boolean;
  form: FormGroup = null;
  title: string;
  passwordPlaceholder: string;
  passwordRequire: boolean;

  constructor(fb: FormBuilder) {
    this.form = fb.group({
      account: [null, [Validators.required, Validators.minLength(4), Validators.maxLength(20)]],
      password: [null, [Validators.required, Validators.minLength(6), Validators.maxLength(20)]],
      nickName: [null, [Validators.required]],
      roles: [null, [Validators.required]],
    });
  }

  showCreate(roleOptions) {
    this.adminId = null;
    this.visible = true;
    this.title = '创建管理员';
    this.passwordRequire = true;
    this.passwordPlaceholder = '请输入管理员密码';
    this.form.reset({
      account: {value: null, disabled: false},
      password: null,
      nickName: null,
      roles: roleOptions,
    });
    this.form.get('password').setValidators([Validators.required, Validators.minLength(6), Validators.maxLength(20)]);
  }

  showUpdate(adminId: Number, adminData) {
    this.adminId = adminId;
    this.visible = true;
    this.title = '更新管理员';
    this.passwordRequire = false;
    this.passwordPlaceholder = '若无需修改，请留空';
    this.form.reset({
      account: {value: adminData.account, disabled: true},
      password: null,
      nickName: adminData.nickName,
      roles: adminData.roles,
    });
    this.form.get('password').setValidators([Validators.minLength(6), Validators.maxLength(20)]);
  }

  saveStart() {
    this.loading = true;
  }

  saveDone() {
    this.loading = false;
    this.visible = false;
  }

  saveError() {
    this.loading = false;
  }

}
