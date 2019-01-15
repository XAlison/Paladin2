import { Component, OnInit, ViewChild } from '@angular/core';
import { ConfigPermissionService } from '../../service/config-permission.service';
import { FormBuilder } from '@angular/forms';
import { PermissionConfigurationDrawerComponent } from './permission-configuration-drawer/permission-configuration-drawer.component';
import { PermissionConfigurationModalComponent } from './permission-configuration-modal/permission-configuration-modal.component';

@Component({
  selector: 'pld-permission-configuration',
  templateUrl: './permission-configuration.component.html',
  styleUrls: ['./permission-configuration.component.less']
})
export class PermissionConfigurationComponent implements OnInit {

  loading = false;
  permissionData = [];
  parentOptions = [];
  @ViewChild('modal') permissionModal: PermissionConfigurationModalComponent;
  @ViewChild('drawer') resourcesDrawer: PermissionConfigurationDrawerComponent;

  constructor(
    private fb: FormBuilder,
    private permissionService: ConfigPermissionService,
  ) {


  }

  ngOnInit() {
    this.loadData();
  }

  loadData() {
    this.loading = true;
    this.permissionService.getPermissionTree().subscribe(res => {
      this.loading = false;
      this.permissionData = res;
      this.flushParentOptions();
    });
  }

  showCreate() {
    this.flushParentOptions();
    this.permissionModal.showCreate();
  }

  showUpdate(permissionItem) {
    this.flushParentOptions();
    this.permissionModal.showUpdate(permissionItem);
  }

  modalSaveSuccess() {
    this.loadData();
  }

  modalDeleteSuccess() {
    this.loadData();
  }

  doUpdateInfo($event: any) {
    this.permissionModal.showUpdate($event);
  }

  doUpdateResource($event: any) {
    this.resourcesDrawer.load($event);
  }

  private flushParentOptions() {
    const options = [];
    for (const level_1 of this.permissionData) {
      const level_1_opt = {
        value: level_1.permission,
        label: level_1.title,
        children: [],
        isLeaf: level_1.children.length === 0,
      };
      if (level_1.children.length !== 0) {
        for (const level_2 of level_1.children) {
          level_1_opt.children.push({
            value: level_2.permission,
            label: level_2.title,
            isLeaf: true,
          });
        }
      }
      options.push(level_1_opt);
    }
    this.parentOptions = options;
  }
}
