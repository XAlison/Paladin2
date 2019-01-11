import { FormBuilder, FormGroup, Validators } from '@angular/forms';

export class RoleModal {
  roleId = null;
  loading = false;
  visible = false;
  form: FormGroup = null;
  title: string;

  constructor(fb: FormBuilder) {
    this.form = fb.group({
      title: [null, [Validators.required]],
      des: [null],
    });
  }

  showCreate() {
    this.roleId = null;
    this.visible = true;
    this.title = '创建角色';
    this.form.reset({
      title: null,
      des: null,
    });
  }


  showUpdate(roleId: Number, roleData) {
    this.roleId = roleId;
    this.visible = true;
    this.title = '更新角色';
    this.form.reset({
      title: roleData.title,
      des: roleData.des,
    });
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
