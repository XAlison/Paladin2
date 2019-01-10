import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PageGuard } from '../../core/gurad/page.guard';
import { ConfigPermissionService } from './service/config-permission.service';
import { PermissionComponent } from './permission/permission.component';


const routes: Routes = [
  { path: 'permission', component: PermissionComponent, canActivate: [PageGuard] },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
  providers: [
    ConfigPermissionService,
  ]
})
export class ConfigRoutingModule {
}
