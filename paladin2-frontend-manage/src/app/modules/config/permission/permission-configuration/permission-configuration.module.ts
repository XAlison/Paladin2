import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PermissionConfigurationDrawerComponent } from './permission-configuration-drawer/permission-configuration-drawer.component';
import { PermissionConfigurationModalComponent } from './permission-configuration-modal/permission-configuration-modal.component';
import { PermissionConfigurationItemComponent } from './permission-configuration-item/permission-configuration-item.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { SharedModule } from '../../../shared/shared.module';
import { NgZorroAntdModule } from 'ng-zorro-antd';
import { PermissionConfigurationComponent } from './permission-configuration.component';


@NgModule({
  declarations: [
    PermissionConfigurationComponent,
    PermissionConfigurationDrawerComponent,
    PermissionConfigurationModalComponent,
    PermissionConfigurationItemComponent,
  ],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    SharedModule,
    NgZorroAntdModule,
  ],
  exports: [
    PermissionConfigurationComponent
  ]
})
export class PermissionConfigurationModule {
}
