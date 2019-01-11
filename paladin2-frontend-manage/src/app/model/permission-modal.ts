import {FormBuilder, FormGroup, Validators} from '@angular/forms';

export class PermissionModal {
  status: string;
  visible: boolean;
  title: string;
  saveLoading: boolean;
  deleteLoading: boolean;
  form: FormGroup;
  parentPermission;

  constructor(fb: FormBuilder) {
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

  showCreate() {
    this.status = 'create';
    this.visible = true;
    this.title = '创建';
    this.form.reset({
      parent: {value: null, disabled: false},
      permission: {value: '', disabled: false},
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
      parent: {value: finalParent, disabled: true},
      permission: {value: finalPermission, disabled: true},
      title: permissionItem.title,
      path: permissionItem.path,
      sort: permissionItem.sort,
    };
    this.form.reset(resetData);
  }

  saveStart() {
    this.saveLoading = true;
  }

  saveDone() {
    this.saveLoading = false;
    this.visible = false;
  }

  deleteStart() {
    this.deleteLoading = true;
  }

  deleteDone() {
    this.deleteLoading = false;
    this.visible = false;
  }

}
