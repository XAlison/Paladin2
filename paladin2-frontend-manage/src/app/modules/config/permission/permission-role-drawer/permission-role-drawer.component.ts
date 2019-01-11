import { Component, OnInit, ViewChild } from '@angular/core';
import { PermissionNode } from '../../../../model/permission-node';
import { PermissionTreeNode } from '../../../../model/permission-tree-node';
import { NzMessageService, NzTreeComponent } from 'ng-zorro-antd';
import { ConfigPermissionService } from '../../service/config-permission.service';
import { forkJoin } from 'rxjs';

@Component({
  selector: 'pld-permission-role-drawer',
  templateUrl: './permission-role-drawer.component.html',
  styleUrls: ['./permission-role-drawer.component.less']
})
export class PermissionRoleDrawerComponent implements OnInit {

  visible = false;
  loading = false;
  roleId: number;
  name: string;
  permissionData: Array<PermissionNode>;
  permissionTree: Array<PermissionTreeNode>;
  defaultCheckedKeys: Array<string>;
  @ViewChild('tree') treeElement: NzTreeComponent;

  constructor(
    private  permissionService: ConfigPermissionService,
    private  messageService: NzMessageService,
  ) {
  }

  ngOnInit() {
  }

  loadData() {
    this.loading = true;
    const request1 = this.permissionService.getPermissionTree();
    const request2 = this.permissionService.roleGetPermission(this.roleId);
    forkJoin([request1, request2]).subscribe(res => {
      const treeData = res[0];
      const checkedData = res[1];
      this.permissionData = treeData;
      this.permissionTree = this.getTreeNodes(treeData);
      this.defaultCheckedKeys = checkedData;
      this.loading = false;
    });
  }

  savePermission() {
    const data = {
      permissionList: this.treeElement.getCheckedNodeList().map(node => {
        return node.key;
      }),
    };
    this.loading = true;
    this.permissionService.roleSavePermission(this.roleId, data).subscribe(res => {
      this.messageService.success('保存成功');
      this.loading = false;
    });
  }

  clickTest(res) {
    console.log(res);
  }

  open(roleId: number, name: string) {
    this.roleId = roleId;
    this.visible = true;
    this.name = name;
    this.loadData();
  }

  close() {
    this.visible = false;
    this.permissionData = [];
    this.permissionTree = [];
  }

  private getTreeNodes(data: Array<PermissionNode>) {
    const nodes = [];
    for (const dataItem of data) {
      const node = new PermissionTreeNode();
      node.title = dataItem.title;
      node.key = dataItem.permission;
      if (dataItem.children.length === 0) {
        node.isLeaf = true;
      } else {
        node.children = this.getTreeNodes(dataItem.children);
      }
      nodes.push(node);
    }
    return nodes;
  }
}
