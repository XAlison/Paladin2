import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ConfigPermissionService } from '../../../service/config-permission.service';
import { NzMessageService } from 'ng-zorro-antd';

@Component({
  selector: 'pld-permission-configuration-modal',
  templateUrl: './permission-configuration-modal.component.html',
  styleUrls: ['./permission-configuration-modal.component.less']
})
export class PermissionConfigurationModalComponent implements OnInit {
  @Output() onSaveSuccess = new EventEmitter();
  @Output() onDeleteSuccess = new EventEmitter();
  @Input() parentOptions = [];

  status: string;
  visible: boolean;
  title: string;
  saveLoading: boolean;
  deleteLoading: boolean;
  form: FormGroup;
  parentPermission;

  constructor(
    private fb: FormBuilder,
    private permissionService: ConfigPermissionService,
    private messageService: NzMessageService,
  ) {
    this.form = fb.group({
      parent: [[], []],
      permission: [null, [Validators.required, Validators.pattern('[a-z][a-z\\d]*')]],
      title: [null, [Validators.required]],
      path: [null, []],
      sort: [100, [Validators.required]],
    });
    this.form.get('parent').valueChanges.subscribe(res => {
      if (res && res instanceof Array && res.length > 0) {
        this.parentPermission = (res[res.length - 1] + ':').replace(/:/g, ' : ');
      } else {
        this.parentPermission = false;
      }
    });
  }

  ngOnInit() {
  }

  showCreate() {
    this.status = 'create';
    this.visible = true;
    this.title = '创建';
    this.form.reset({
      parent: { value: null, disabled: false },
      permission: { value: '', disabled: false },
      title: null,
      path: null,
      sort: 100
    });
  }

  showUpdate(permissionItem) {
    this.status = 'update';
    this.visible = true;
    this.title = '创建';
    const fullPermission = permissionItem.permission;
    const lastColon = fullPermission.lastIndexOf(':');
    const finalParent = [];
    let finalPermission = fullPermission;
    if (lastColon !== -1) {
      finalPermission = fullPermission.substring(lastColon + 1, fullPermission.length);
      const directParent = fullPermission.substring(0, lastColon);
      const parentRoute = directParent.split(':');
      for (let i = 0; i < parentRoute.length; i++) {
        const thisPathArr = [];
        for (let j = 0; j < i + 1; j++) {
          thisPathArr.push(parentRoute[j]);
        }
        finalParent.push(thisPathArr.join(':'));
      }
    }
    const resetData = {
      parent: { value: finalParent, disabled: true },
      permission: { value: finalPermission, disabled: true },
      title: permissionItem.title,
      path: permissionItem.navPath,
      sort: permissionItem.sort,
    };
    this.form.reset(resetData);
  }

  saveStart() {
    this.saveLoading = true;
  }

  saveError() {
    this.saveLoading = false;
  }

  saveDone() {
    this.saveLoading = false;
    this.visible = false;
  }

  deleteStart() {
    this.deleteLoading = true;
  }

  deleteError() {
    this.deleteLoading = false;
  }

  deleteDone() {
    this.deleteLoading = false;
    this.visible = false;
  }

  deletePermission() {
    const values = this.form.getRawValue();
    const deletePermission = this.getSavePermission(values);
    if (deletePermission) {
      this.deleteStart();
      this.permissionService.permissionDelete(deletePermission).subscribe(res => {
        this.messageService.success('删除成功');
        this.deleteDone();
        this.onDeleteSuccess.emit();
      }, err => {
        this.deleteError();
      });
    }
  }

  savePermission() {
    if (this.form.valid) {
      const values = this.form.getRawValue();
      const data = {
        title: values.title,
        permission: this.getSavePermission(values),
        path: values.path,
        sort: values.sort
      };
      this.saveStart();
      this.permissionService.permissionSave(data).subscribe(res => {
        this.messageService.success('保存成功');
        this.saveDone();
        this.onSaveSuccess.emit();
      }, err => {
        this.saveError();
      });
    } else {
      for (const controlKey of Object.keys(this.form.controls)) {
        this.form.controls[controlKey].markAsDirty();
        this.form.controls[controlKey].updateValueAndValidity();
      }
    }
  }

  private getSavePermission(formValues): string {
    let savePermission: string;
    if (formValues.parent && formValues.parent instanceof Array && formValues.parent.length > 0) {
      savePermission = formValues.parent[formValues.parent.length - 1] + ':' + formValues.permission;
    } else {
      savePermission = formValues.permission;
    }
    return savePermission;
  }
}
