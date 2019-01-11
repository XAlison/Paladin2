import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable, of} from 'rxjs';
import {switchMap, tap} from 'rxjs/operators';


const api = {
  adminList: () => `/manage/sys/admin/list`,
  adminDel: (adminId) => `/manage/sys/admin/delete/${adminId}`,
  adminSave: (adminId) => adminId ? `/manage/sys/admin/save/${adminId}` : `/manage/sys/admin/save`,
  adminGet: (adminId) => `/manage/sys/admin/get/${adminId}`,
  roleList: () => `/manage/sys/role/list`,
  roleSet: (roleId) => roleId ? `/manage/sys/role/save?roleId=${roleId}` : `/manage/sys/role/save`,
  roleDel: (roleId) => `/manage/sys/role/delete/${roleId}`,
  rolePermissionSave: () => ``,
  rolePermissionGet: (roleId) => `/manage/sys/role/permission/${roleId}`,
  permissionTree: () => `/manage/sys/permission/tree`,
  permissionSave: () => `/manage/sys/permission/save`,
  permissionDel: (permission) => `/manage/sys/permission/delete/${permission}`,
  resourcesGet: (permission) => `/manage/sys/permission/resources/${permission}`,
  resourcesCreate: () => `/manage/sys/permission/resources/create`,
  resourcesDelete: () => `/manage/sys/permission/resources/delete`,
  resourcesApiUrls: () => `/manage/sys/permission/resources/all_api_urls`,
};

@Injectable({
  providedIn: 'root'
})
export class ConfigPermissionService {

  private apiUrls = null;

  constructor(
    private httpClient: HttpClient,
  ) {
  }

  getAdminList() {
    return this.httpClient.get<Array<any>>(api.adminList());
  }

  delAdmin(adminId: Number) {
    return this.httpClient.get(api.adminDel(adminId));
  }

  setAdmin(adminId = null, data: {
    account: string,
    password: string,
    nickName: string,
    roleIdList: Array<number>,
  }) {
    return this.httpClient.post(api.adminSave(adminId), data);
  }

  getAdmin(adminId) {
    return this.httpClient.get<any>(api.adminGet(adminId));
  }

  getRoleList() {
    return this.httpClient.get<any>(api.roleList());
  }

  saveRole(roleId, data: { title, des }) {
    return this.httpClient.post(api.roleSet(roleId), data);
  }

  deleteRole(roleId) {
    return this.httpClient.get<any>(api.roleDel(roleId));
  }

  getPermissionTree() {
    return this.httpClient.get<any>(api.permissionTree());
  }

  savePermission(data: { title: string, permission: string, path?: string, sort: Number }) {
    return this.httpClient.post<any>(api.permissionSave(), data);
  }

  deletePermission(permission) {
    return this.httpClient.get<any>(api.permissionDel(permission));
  }

  getPermissionResources(permission) {
    return this.httpClient.get<any>(api.resourcesGet(permission));
  }

  createPermissionResources(data: { permission: string, typeId: number, data: string }) {
    return this.httpClient.post<any>(api.resourcesCreate(), data);
  }

  deletePermissionResources(data: { permission: string, typeId: number, data: string }) {
    return this.httpClient.post<any>(api.resourcesDelete(), data);
  }

  getApiUrls(): Observable<Array<{ type: string, url: string }>> {
    return of(this.apiUrls).pipe(
      switchMap((urls) => {
        if (urls === null) {
          return this.httpClient.get<any>(api.resourcesApiUrls()).pipe(
            tap(res => this.apiUrls = res)
          );
        } else {
          return of(urls);
        }
      })
    );
  }

  saveRolePermission(data: { roleId: number, permissionList: Array<string> }) {
    return this.httpClient.post<any>(api.rolePermissionSave(), data);
  }

  getRolePermission(roleId: number) {
    return this.httpClient.get<any>(api.rolePermissionGet(roleId));
  }
}
