import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';


const api = {
  adminList: () => `/manage/sys/admin/list`,
  adminCreate: () => `/manage/sys/admin/create`,
  adminDelete: (adminId) => `/manage/sys/admin/${ adminId }/delete`,
  adminUpdate: (adminId) => `/manage/sys/admin/${ adminId }/update`,
  adminGet: (adminId) => `/manage/sys/admin/${ adminId }`,

  permissionTree: () => `/manage/sys/permission/tree`,

  roleList: () => `/manage/sys/role/list`,
  roleCreate: () => `/manage/sys/role/create`,
  roleDelete: (roleId) => `/manage/sys/role/${ roleId }/delete`,
  roleUpdate: (roleId) => `/manage/sys/role/${ roleId }/update`,
  rolePermission: (roleId) => `/manage/sys/role/${ roleId }/permission`,

  permissionSave: () => `/manage/sys/permission/save`,
  permissionDel: (permission) => `/manage/sys/permission/delete/${ permission }`,
  resourcesGet: (permission) => `/manage/sys/permission/resources/${ permission }`,
  resourcesCreate: () => `/manage/sys/permission/resources/create`,
  resourcesDelete: () => `/manage/sys/permission/resources/delete`,
  resourcesApiUrls: () => `/manage/sys/permission/resources/all_api_urls`,
};

@Injectable({
  providedIn: 'root'
})
export class ConfigPermissionService {

  constructor(
    private httpClient: HttpClient,
  ) {
  }

  getAdminList() {
    return this.httpClient.get<Array<any>>(api.adminList());
  }

  delAdmin(adminId: Number) {
    return this.httpClient.get(api.adminDelete(adminId));
  }

  setAdmin(adminId = null, data: {
    account: string,
    password: string,
    nickName: string,
    roleIdList: Array<number>,
  }) {
    return this.httpClient.post(adminId ? api.adminUpdate(adminId) : api.adminCreate(), data);
  }

  getAdmin(adminId) {
    return this.httpClient.get<any>(api.adminGet(adminId));
  }

  getRoleList() {
    return this.httpClient.get<any>(api.roleList());
  }

  roleSave(roleId, data: { title, des }) {
    return this.httpClient.post(roleId ? api.roleUpdate(roleId) : api.roleCreate(), data);
  }

  roleDelete(roleId) {
    return this.httpClient.get<any>(api.roleDelete(roleId));
  }

  roleSavePermission(roleId: number, data: { permissionList: Array<string> }) {
    return this.httpClient.post<any>(api.rolePermission(roleId), data);
  }

  roleGetPermission(roleId: number) {
    return this.httpClient.get<any>(api.rolePermission(roleId));
  }

  getPermissionTree() {
    return this.httpClient.get<any>(api.permissionTree());
  }
}
