import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { NzMessageService } from 'ng-zorro-antd';
import { RoleModal } from './role-modal';
import { ConfigPermissionService } from '../../service/config-permission.service';
import { PermissionRoleDrawerComponent } from '../permission-role-drawer/permission-role-drawer.component';


@Component({
  selector: 'pld-permission-role',
  templateUrl: './permission-role.component.html',
  styleUrls: ['./permission-role.component.less']
})
export class PermissionRoleComponent implements OnInit {
  roleList = [];
  loading = false;
  roleModal = new RoleModal(this.fb);
  @ViewChild('drawer') rolePermissionDrawer: PermissionRoleDrawerComponent;

  constructor(
    private fb: FormBuilder,
    private message: NzMessageService,
    private adminService: ConfigPermissionService
  ) {
  }

  ngOnInit() {
    this.loadList();
  }

  loadList() {
    this.loading = true;
    this.adminService.getRoleList().subscribe(res => {
      this.roleList = res;
      this.loading = false;
    });
  }


  showCreate() {
    this.roleModal.showCreate();
  }

  showUpdate(roleData) {
    this.roleModal.showUpdate(roleData.id, roleData);
  }

  saveRole() {
    if (!this.roleModal.form.valid) {
      for (const controlKey of Object.keys(this.roleModal.form.controls)) {
        this.roleModal.form.controls[controlKey].markAsDirty();
        this.roleModal.form.controls[controlKey].updateValueAndValidity();
      }
      return;
    }
    this.roleModal.saveStart();
    this.adminService.roleSave(this.roleModal.roleId, this.roleModal.form.value).subscribe(res => {
      this.loadList();
      this.roleModal.saveDone();
    }, err => {
      this.roleModal.saveError();
    });
  }

  deleteRole(roleId) {
    this.adminService.roleDelete(roleId).subscribe(res => {
      this.message.success('操作成功');
      this.loadList();
    });
  }

  openPermissionDrawer(roleId, name) {
    this.rolePermissionDrawer.open(roleId, name);
  }
}
