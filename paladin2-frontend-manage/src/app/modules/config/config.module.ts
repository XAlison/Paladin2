import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ConfigRoutingModule } from './config-routing.module';
import { NgZorroAntdModule } from 'ng-zorro-antd';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { SharedModule } from '../shared/shared.module';
import { PermissionComponent } from './permission/permission.component';
import { PermissionAdminComponent } from './permission/permission-admin/permission-admin.component';

@NgModule({
  declarations: [
    PermissionComponent,
    PermissionAdminComponent,
  ],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    SharedModule,
    ConfigRoutingModule,
    NgZorroAntdModule,
  ]
})
export class ConfigModule {
}
