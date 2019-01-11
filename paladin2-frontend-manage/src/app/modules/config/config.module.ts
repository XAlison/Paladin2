import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ConfigRoutingModule } from './config-routing.module';
import { NgZorroAntdModule } from 'ng-zorro-antd';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { SharedModule } from '../shared/shared.module';
import { PermissionComponent } from './permission/permission.component';
import { PermissionAdminComponent } from './permission/permission-admin/permission-admin.component';
import { PermissionRoleComponent } from './permission/permission-role/permission-role.component';
import { PermissionRoleDrawerComponent } from './permission/permission-role-drawer/permission-role-drawer.component';
import { PermissionConfigurationModule } from './permission/permission-configuration/permission-configuration.module';

@NgModule({
  declarations: [
    PermissionComponent,
    PermissionAdminComponent,
    PermissionRoleComponent,
    PermissionRoleDrawerComponent,
  ],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    SharedModule,
    ConfigRoutingModule,
    NgZorroAntdModule,
    PermissionConfigurationModule,
  ]
})
export class ConfigModule {
}
