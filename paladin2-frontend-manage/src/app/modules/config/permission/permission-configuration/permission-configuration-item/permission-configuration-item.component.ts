import { Component, EventEmitter, Input, OnInit, Output, ViewChild } from '@angular/core';
import { PermissionConfigurationModalComponent } from '../permission-configuration-modal/permission-configuration-modal.component';

@Component({
  selector: 'pld-permission-configuration-item',
  templateUrl: './permission-configuration-item.component.html',
  styleUrls: ['./permission-configuration-item.component.less']
})
export class PermissionConfigurationItemComponent implements OnInit {
  @Input() data;
  @Input() deep: number;
  @Input() parentOptions = [];
  @Input() parentTitleRoute = [];
  @Output() updateInfo = new EventEmitter<any>();
  @Output() updateResource = new EventEmitter<any>();
  @ViewChild('modal') permissionModal: PermissionConfigurationModalComponent;
  titleRoute = [];

  constructor() {
  }

  ngOnInit() {
    this.titleRoute = [...this.parentTitleRoute, this.data.title];
  }

  doUpdateInfo(evt) {
    this.updateInfo.emit(evt);
  }

  doUpdateResource(evt: { permission: string, title: string }) {
    this.updateResource.emit(evt);
  }
}
