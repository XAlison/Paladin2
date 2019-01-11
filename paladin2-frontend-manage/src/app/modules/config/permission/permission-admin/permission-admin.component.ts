import { Component, OnInit } from '@angular/core';
import { NzMessageService } from 'ng-zorro-antd';
import { FormBuilder } from '@angular/forms';
import { ConfigPermissionService } from '../../service/config-permission.service';
import { AdminModal } from './admin-modal';

@Component({
  selector: 'pld-permission-admin',
  templateUrl: './permission-admin.component.html',
  styleUrls: ['./permission-admin.component.less']
})
export class PermissionAdminComponent implements OnInit {
  listLoading = false;
  adminList = [];
  adminModal = new AdminModal(this.fb);
  roleList = [];
  roleOptions: [];

  constructor(
    private fb: FormBuilder,
    private message: NzMessageService,
    private adminService: ConfigPermissionService
  ) {
  }

  ngOnInit() {
    this.listLoading = true;
    this.adminService.getRoleList().subscribe(res => {
      this.roleList = res;
      this.loadList();
    });
  }

  loadList() {
    this.listLoading = true;
    this.adminService.getAdminList().subscribe(res => {
      this.adminList = res.map(adminItem =>
        Object.assign(adminItem, {
          roles: this.roleList
            .filter(item => adminItem.roleIdList.indexOf(item.id) >= 0)
            .map(item => item.title).join(',')
        })
      );
      this.listLoading = false;
    });
  }

  deleteAdmin(adminId) {
    this.adminService.delAdmin(adminId).subscribe(res => {
      this.message.success('操作成功');
      this.loadList();
    });
  }

  showCreate() {
    this.adminModal.showCreate(this.getRoleOptions([]));
  }

  shopUpdate(adminId: number) {
    this.adminService.getAdmin(adminId).subscribe(adminData => {
      console.log(adminData);
      adminData.roles = this.getRoleOptions(adminData.roleIdList);
      this.adminModal.showUpdate(adminId, adminData);
    });
  }

  saveAdmin() {
    if (this.adminModal.form.valid) {
      this.adminModal.saveStart();
      const formValues = this.adminModal.form.getRawValue();
      this.adminService.setAdmin(this.adminModal.adminId, {
        account: formValues.account,
        password: formValues.password,
        nickName: formValues.nickName,
        roleIdList: formValues.roles.filter(item => item.checked).map(item => item.value),
      }).subscribe(res => {
        this.message.success('保存成功');
        this.adminModal.saveDone();
        this.loadList();
      }, err => {
        this.adminModal.saveError();
      });
    } else {
      for (const controlKey of Object.keys(this.adminModal.form.controls)) {
        this.adminModal.form.controls[controlKey].markAsDirty();
        this.adminModal.form.controls[controlKey].updateValueAndValidity();
      }
    }
  }

  private getRoleOptions(checkedData) {
    return this.roleList.map(item => {
      return Object.assign({ label: item.title, value: item.id }, { checked: checkedData.indexOf(item.id) >= 0 });
    });
  }
}
