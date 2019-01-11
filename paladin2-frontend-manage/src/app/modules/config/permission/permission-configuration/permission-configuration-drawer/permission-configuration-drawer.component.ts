import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ConfigPermissionService } from '../../../service/config-permission.service';
import { NzMessageService } from 'ng-zorro-antd';

@Component({
  selector: 'pld-permission-configuration-drawer',
  templateUrl: './permission-configuration-drawer.component.html',
  styleUrls: ['./permission-configuration-drawer.component.less']
})
export class PermissionConfigurationDrawerComponent implements OnInit {

  title = false;
  visible = false;
  dataLoading = false;
  apiArr = [];
  uiPathArr = [];
  uiElementArr = [];
  permission: string;
  form: FormGroup;
  formVisible = false;
  formSubmitting = false;
  formVisibleTitle = '';
  apiOptions = [];

  constructor(
    private fb: FormBuilder,
    private permissionService: ConfigPermissionService,
    private nzMessageService: NzMessageService,
  ) {
    this.form = this.fb.group({
      permission: [null, [Validators.required]],
      typeId: [null, [Validators.required]],
      data: [null, [Validators.required]],
    });
  }

  ngOnInit() {
  }

  load(permission, title) {
    this.visible = false;
    this.dataLoading = false;
    this.formVisible = false;
    this.formSubmitting = false;
    this.formVisibleTitle = '';
    this.title = title;
    this.visible = true;
    this.permission = permission;
    this.reload();
  }

  reload() {
    this.apiArr = [];
    this.uiPathArr = [];
    this.uiElementArr = [];
    this.dataLoading = true;
    this.permissionService.permissionResource(this.permission).subscribe(list => {
      for (const item of list) {
        switch (item.typeId) {
          case 1:
            this.apiArr.push(item);
            break;
          case 2:
            this.uiPathArr.push(item);
            break;
          case 3:
            this.uiElementArr.push(item);
            break;
        }
      }
      this.dataLoading = false;
    });
  }

  close() {
    this.visible = false;
  }

  formShowCreate(typeId: number) {
    this.form.reset({
      permission: this.permission,
      typeId: typeId,
      data: null
    });
    this.formVisible = true;
    this.formSubmitting = false;
    this.apiOptions = [];
  }

  formSave() {
    if (this.form.valid) {
      this.formSubmitting = true;
      this.permissionService.permissionResourceCreate(this.form.value).subscribe(res => {
        this.nzMessageService.success('保存成功');
        this.formVisible = false;
        this.reload();
      }, () => {
        this.formSubmitting = false;
      });
    } else {
      for (const controlKey of Object.keys(this.form.controls)) {
        this.form.controls[controlKey].markAsDirty();
        this.form.controls[controlKey].updateValueAndValidity();
      }
    }
  }

  delete(typeId, data) {
    this.dataLoading = true;
    this.permissionService.permissionResourceDelete({
      permission: this.permission,
      typeId: typeId,
      data: data,
    }).subscribe(() => {
      this.nzMessageService.success('删除成功');
      this.reload();
    });
  }

  onResourceInput(typeId: number, value: string): void {
    if (typeId === 1) {
      if (!value) {
        this.apiOptions = [];
      } else {
        this.permissionService.getApiUrls().subscribe(apiItemList => {
          this.apiOptions = apiItemList.filter(item => item.url.indexOf(value) > 0);
        });
      }
    } else {
      this.apiOptions = [];
    }
  }

}
