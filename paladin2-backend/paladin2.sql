/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50724
 Source Host           : localhost:3306
 Source Schema         : paladin2

 Target Server Type    : MySQL
 Target Server Version : 50724
 File Encoding         : 65001

 Date: 10/01/2019 17:44:53
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_admin
-- ----------------------------
DROP TABLE IF EXISTS `sys_admin`;
CREATE TABLE `sys_admin`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '账号',
  `account` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '账号',
  `password` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
  `nick_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `mobile` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系电话',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `login_time` datetime(0) NULL DEFAULT NULL COMMENT '登录时间',
  `login_count` int(11) NOT NULL DEFAULT 0 COMMENT '登录次数',
  `remark` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `status` tinyint(2) NOT NULL DEFAULT 0 COMMENT '状态',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `unique_username`(`account`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统管理员' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_admin
-- ----------------------------
INSERT INTO `sys_admin` VALUES (1, 'boss', '4e58ef9a8e457251dd8b50a04ab07c1a', ' 超级管理员', NULL, '2018-09-14 01:24:18', NULL, NULL, 0, NULL, 0);

-- ----------------------------
-- Table structure for sys_admin_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_admin_role`;
CREATE TABLE `sys_admin_role`  (
  `admin_id` bigint(20) UNSIGNED NOT NULL,
  `role_id` bigint(20) UNSIGNED NOT NULL,
  PRIMARY KEY (`admin_id`, `role_id`) USING BTREE,
  UNIQUE INDEX `only`(`admin_id`, `role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission`  (
  `permission` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '权限表达式',
  `title` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '中文显示项',
  `parent` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '父级权限表达式',
  `sort` int(200) NULL DEFAULT NULL COMMENT '排序',
  `nav_path` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '前端路径（只有一二级有）',
  PRIMARY KEY (`permission`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统-权限表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
INSERT INTO `sys_permission` VALUES ('config', '配置', '', 107, '/config');
INSERT INTO `sys_permission` VALUES ('config:order', '订单配置', 'config', 100, '/config/order');
INSERT INTO `sys_permission` VALUES ('config:permission', '权限配置', 'config', 100, '/config/permission');
INSERT INTO `sys_permission` VALUES ('config:permission:admin', '管理员', 'config:permission', 101, NULL);
INSERT INTO `sys_permission` VALUES ('config:permission:resource', '资源', 'config:permission', 103, '');
INSERT INTO `sys_permission` VALUES ('config:permission:role', '角色', 'config:permission', 102, NULL);
INSERT INTO `sys_permission` VALUES ('config:share', '分享配置', 'config', 100, '/config/share');
INSERT INTO `sys_permission` VALUES ('config:shop', '店铺配置', 'config', 100, '/config/shop');
INSERT INTO `sys_permission` VALUES ('config:shop:course', '教程编辑', 'config:shop', 100, '/config/shop/course/edit');
INSERT INTO `sys_permission` VALUES ('config:user', '用户配置', 'config', 100, '/config/user');
INSERT INTO `sys_permission` VALUES ('goods', '商品', '', 106, '/goods');
INSERT INTO `sys_permission` VALUES ('goods:category', '分类列表', 'goods', 100, '/goods/category');
INSERT INTO `sys_permission` VALUES ('goods:collocation', '搭配列表', 'goods', 100, '/goods/collocation');
INSERT INTO `sys_permission` VALUES ('goods:collocation:edit', '搭配编辑', 'goods:collocation', 100, '/goods/collocation/edit');
INSERT INTO `sys_permission` VALUES ('goods:comment', '评论管理', 'goods', 101, '/goods/comment');
INSERT INTO `sys_permission` VALUES ('goods:comment:edit', '评论编辑', 'goods:comment', 100, '/goods/comment/edit');
INSERT INTO `sys_permission` VALUES ('goods:list', '商品列表', 'goods', 100, '/goods/list');
INSERT INTO `sys_permission` VALUES ('goods:list:edit', '商品编辑', 'goods:list', 100, '/goods/list/edit');
INSERT INTO `sys_permission` VALUES ('goods:setting', '商品设置', 'goods', 100, '/goods/setting');
INSERT INTO `sys_permission` VALUES ('goods:setting:label', '标签设置', 'goods:setting', 100, NULL);
INSERT INTO `sys_permission` VALUES ('goods:setting:property', '商品属性设置', 'goods:setting', 100, NULL);
INSERT INTO `sys_permission` VALUES ('home', '首页', '', 101, '/home');
INSERT INTO `sys_permission` VALUES ('order', '订单', '', 105, '/order');
INSERT INTO `sys_permission` VALUES ('order:config', '订单配置', 'order', 100, '/config/order');
INSERT INTO `sys_permission` VALUES ('order:list', '订单列表', 'order', 100, '/order/list');
INSERT INTO `sys_permission` VALUES ('order:list:detail', '订单详情', 'order:list', 100, '/order/list/detail');
INSERT INTO `sys_permission` VALUES ('order:refund', '退款列表', 'order', 100, '/order/refund');
INSERT INTO `sys_permission` VALUES ('order:refund:detail', '退款详情', 'order:refund', 100, '/order/refund/detail');
INSERT INTO `sys_permission` VALUES ('shop', '店铺', '', 103, '/shop');
INSERT INTO `sys_permission` VALUES ('shop:advice', '意见列表', 'shop', 100, '/shop/advice');
INSERT INTO `sys_permission` VALUES ('shop:config', '店铺配置', 'shop', 100, '/config/shop');
INSERT INTO `sys_permission` VALUES ('shop:list', '申请列表', 'shop', 100, '/shop/list');
INSERT INTO `sys_permission` VALUES ('shop:list:detail', '申请店铺详情', 'shop:list', 100, '/shop/list/detail');
INSERT INTO `sys_permission` VALUES ('shop:staff', '客服列表', 'shop', 100, '/shop/staff');
INSERT INTO `sys_permission` VALUES ('user', '用户', '', 102, '/user');
INSERT INTO `sys_permission` VALUES ('user:config', '用户配置', 'user', 100, '/config/user');
INSERT INTO `sys_permission` VALUES ('user:list', '用户列表', 'user', 100, '/user/list');
INSERT INTO `sys_permission` VALUES ('user:withdraw', '提现申请', 'user', 100, '/user/withdraw');

-- ----------------------------
-- Table structure for sys_permission_resource
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission_resource`;
CREATE TABLE `sys_permission_resource`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `permission` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '权限表达式',
  `type_id` int(11) NULL DEFAULT NULL COMMENT '资源类型:0=导航；1=接口；2=UI路由；3=UI元素',
  `data` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '资源数据',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `only`(`permission`, `type_id`, `data`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 106 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_permission_resource
-- ----------------------------
INSERT INTO `sys_permission_resource` VALUES (48, 'config:permission', 2, '/config/permission');
INSERT INTO `sys_permission_resource` VALUES (7, 'config:permission:admin', 1, '/manage/sys/admin/del');
INSERT INTO `sys_permission_resource` VALUES (34, 'config:permission:admin', 1, '/manage/sys/admin/get');
INSERT INTO `sys_permission_resource` VALUES (6, 'config:permission:admin', 1, '/manage/sys/admin/list');
INSERT INTO `sys_permission_resource` VALUES (8, 'config:permission:admin', 1, '/manage/sys/admin/set');
INSERT INTO `sys_permission_resource` VALUES (35, 'config:permission:admin', 1, '/manage/sys/role/list');
INSERT INTO `sys_permission_resource` VALUES (37, 'config:permission:resource', 1, '/manage/sys/permission/delete/{permission}');
INSERT INTO `sys_permission_resource` VALUES (45, 'config:permission:resource', 1, '/manage/sys/permission/resources/all_api_urls');
INSERT INTO `sys_permission_resource` VALUES (17, 'config:permission:resource', 1, '/manage/sys/permission/resources/create');
INSERT INTO `sys_permission_resource` VALUES (18, 'config:permission:resource', 1, '/manage/sys/permission/resources/delete');
INSERT INTO `sys_permission_resource` VALUES (39, 'config:permission:resource', 1, '/manage/sys/permission/resources/{permission}');
INSERT INTO `sys_permission_resource` VALUES (14, 'config:permission:resource', 1, '/manage/sys/permission/save');
INSERT INTO `sys_permission_resource` VALUES (13, 'config:permission:resource', 1, '/manage/sys/permission/tree');
INSERT INTO `sys_permission_resource` VALUES (23, 'config:permission:role', 1, '/manage/sys/permission/tree');
INSERT INTO `sys_permission_resource` VALUES (40, 'config:permission:role', 1, '/manage/sys/role/delete/{roleId}');
INSERT INTO `sys_permission_resource` VALUES (10, 'config:permission:role', 1, '/manage/sys/role/list');
INSERT INTO `sys_permission_resource` VALUES (41, 'config:permission:role', 1, '/manage/sys/role/permission/{roleId}');
INSERT INTO `sys_permission_resource` VALUES (11, 'config:permission:role', 1, '/manage/sys/role/save');
INSERT INTO `sys_permission_resource` VALUES (96, 'config:shop', 1, '/manage/advice_cfg/get');
INSERT INTO `sys_permission_resource` VALUES (97, 'config:shop', 1, '/manage/advice_cfg/set');
INSERT INTO `sys_permission_resource` VALUES (83, 'config:shop', 1, '/manage/apply_shop/cfg/get');
INSERT INTO `sys_permission_resource` VALUES (84, 'config:shop', 1, '/manage/apply_shop/cfg/set');
INSERT INTO `sys_permission_resource` VALUES (75, 'config:shop', 1, '/manage/charge_cfg/get');
INSERT INTO `sys_permission_resource` VALUES (74, 'config:shop', 1, '/manage/charge_cfg/set');
INSERT INTO `sys_permission_resource` VALUES (73, 'config:shop', 1, '/manage/company_cfg/get');
INSERT INTO `sys_permission_resource` VALUES (72, 'config:shop', 1, '/manage/company_cfg/set');
INSERT INTO `sys_permission_resource` VALUES (81, 'config:shop', 1, '/manage/config/withdraw/get');
INSERT INTO `sys_permission_resource` VALUES (82, 'config:shop', 1, '/manage/config/withdraw/save');
INSERT INTO `sys_permission_resource` VALUES (80, 'config:shop', 1, '/manage/coupon_type/add');
INSERT INTO `sys_permission_resource` VALUES (79, 'config:shop', 1, '/manage/coupon_type/list');
INSERT INTO `sys_permission_resource` VALUES (92, 'config:shop', 1, '/manage/course/del');
INSERT INTO `sys_permission_resource` VALUES (91, 'config:shop', 1, '/manage/course/list');
INSERT INTO `sys_permission_resource` VALUES (71, 'config:shop', 1, '/manage/logo/upload');
INSERT INTO `sys_permission_resource` VALUES (99, 'config:shop', 2, '/manage/config/shop');
INSERT INTO `sys_permission_resource` VALUES (88, 'config:shop:course', 1, '/manage/course/get');
INSERT INTO `sys_permission_resource` VALUES (90, 'config:shop:course', 1, '/manage/course/set');
INSERT INTO `sys_permission_resource` VALUES (89, 'config:shop:course', 1, '/manage/course_pic/upload');
INSERT INTO `sys_permission_resource` VALUES (87, 'config:shop:course', 2, '/config/shop/course/edit');
INSERT INTO `sys_permission_resource` VALUES (105, 'config:user', 1, '/manage/ad_pic/del');
INSERT INTO `sys_permission_resource` VALUES (103, 'config:user', 1, '/manage/ad_pic/get');
INSERT INTO `sys_permission_resource` VALUES (102, 'config:user', 1, '/manage/ad_pic/list');
INSERT INTO `sys_permission_resource` VALUES (101, 'config:user', 1, '/manage/ad_pic/set');
INSERT INTO `sys_permission_resource` VALUES (104, 'config:user', 1, '/manage/ad_pic/upload');
INSERT INTO `sys_permission_resource` VALUES (85, 'config:user', 1, '/manage/coinMission_cfg/get');
INSERT INTO `sys_permission_resource` VALUES (86, 'config:user', 1, '/manage/coinMission_cfg/set');
INSERT INTO `sys_permission_resource` VALUES (93, 'config:user', 1, '/manage/coupon_type/list');
INSERT INTO `sys_permission_resource` VALUES (95, 'config:user', 1, '/manage/review_cfg/get');
INSERT INTO `sys_permission_resource` VALUES (94, 'config:user', 1, '/manage/review_cfg/set');
INSERT INTO `sys_permission_resource` VALUES (20, 'config:user', 2, '/config/user');
INSERT INTO `sys_permission_resource` VALUES (44, 'goods:category', 1, '/manage/category/delete/{id}');
INSERT INTO `sys_permission_resource` VALUES (27, 'goods:category', 1, '/manage/category/list');
INSERT INTO `sys_permission_resource` VALUES (28, 'goods:category', 1, '/manage/category/set');
INSERT INTO `sys_permission_resource` VALUES (31, 'goods:category', 1, '/manage/category/upload');
INSERT INTO `sys_permission_resource` VALUES (43, 'goods:category', 1, '/manage/category/{id}');
INSERT INTO `sys_permission_resource` VALUES (25, 'goods:category', 2, '/goods/category');
INSERT INTO `sys_permission_resource` VALUES (33, 'goods:list', 1, '/manage/goods/page');
INSERT INTO `sys_permission_resource` VALUES (24, 'goods:list', 2, '/goods/list');
INSERT INTO `sys_permission_resource` VALUES (60, 'goods:list:edit', 1, '/common/options/goods_recommend_type');
INSERT INTO `sys_permission_resource` VALUES (59, 'goods:list:edit', 1, '/manage/category/list');
INSERT INTO `sys_permission_resource` VALUES (63, 'goods:list:edit', 1, '/manage/goods/create');
INSERT INTO `sys_permission_resource` VALUES (62, 'goods:list:edit', 1, '/manage/goods/update/{goodsId}');
INSERT INTO `sys_permission_resource` VALUES (64, 'goods:list:edit', 1, '/manage/goods/upload_main_pic');
INSERT INTO `sys_permission_resource` VALUES (42, 'goods:list:edit', 1, '/manage/goods/{id}');
INSERT INTO `sys_permission_resource` VALUES (61, 'goods:list:edit', 1, '/manage/property/list');
INSERT INTO `sys_permission_resource` VALUES (65, 'goods:list:edit', 1, '/manage/sku_pic/upload');
INSERT INTO `sys_permission_resource` VALUES (66, 'goods:list:edit', 1, '/manage/tag/list');
INSERT INTO `sys_permission_resource` VALUES (51, 'goods:list:edit', 2, '/goods/list/edit');
INSERT INTO `sys_permission_resource` VALUES (50, 'goods:setting', 2, '/goods/setting');
INSERT INTO `sys_permission_resource` VALUES (57, 'goods:setting:label', 1, '/manage/tag/create/{title}');
INSERT INTO `sys_permission_resource` VALUES (58, 'goods:setting:label', 1, '/manage/tag/delete/{tagId}');
INSERT INTO `sys_permission_resource` VALUES (56, 'goods:setting:label', 1, '/manage/tag/list');
INSERT INTO `sys_permission_resource` VALUES (53, 'goods:setting:property', 1, '/manage/property/add');
INSERT INTO `sys_permission_resource` VALUES (52, 'goods:setting:property', 1, '/manage/property/list');
INSERT INTO `sys_permission_resource` VALUES (55, 'goods:setting:property', 1, '/manage/property_value/del');
INSERT INTO `sys_permission_resource` VALUES (54, 'goods:setting:property', 1, '/manage/property_value/set');
INSERT INTO `sys_permission_resource` VALUES (47, 'home', 2, '/home');
INSERT INTO `sys_permission_resource` VALUES (98, 'shop:advice', 2, '/shop/advice');
INSERT INTO `sys_permission_resource` VALUES (100, 'shop:list', 2, '/shop/list');
INSERT INTO `sys_permission_resource` VALUES (78, 'user:list', 2, '/user/list');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` bigint(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `title` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '名称',
  `des` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '描述',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '商品管理员', '管理商品');
INSERT INTO `sys_role` VALUES (2, '超級管理員', '無限制');
INSERT INTO `sys_role` VALUES (3, '角色管理员', '只能管理角色');
INSERT INTO `sys_role` VALUES (4, '权限管理员', '后台权限配置均可访问');

-- ----------------------------
-- Table structure for sys_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission`  (
  `role_id` bigint(20) UNSIGNED NOT NULL,
  `permission` varbinary(255) NOT NULL,
  UNIQUE INDEX `only`(`role_id`, `permission`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_permission
-- ----------------------------
INSERT INTO `sys_role_permission` VALUES (1, 0x636F6E6669673A7065726D697373696F6E3A726F6C65);
INSERT INTO `sys_role_permission` VALUES (1, 0x636F6E6669673A75736572);
INSERT INTO `sys_role_permission` VALUES (1, 0x676F6F64733A6C697374);
INSERT INTO `sys_role_permission` VALUES (1, 0x6F72646572);
INSERT INTO `sys_role_permission` VALUES (1, 0x73686F70);
INSERT INTO `sys_role_permission` VALUES (2, 0x636F6E666967);
INSERT INTO `sys_role_permission` VALUES (2, 0x676F6F6473);
INSERT INTO `sys_role_permission` VALUES (2, 0x686F6D65);
INSERT INTO `sys_role_permission` VALUES (2, 0x6F72646572);
INSERT INTO `sys_role_permission` VALUES (2, 0x73686F70);
INSERT INTO `sys_role_permission` VALUES (2, 0x75736572);
INSERT INTO `sys_role_permission` VALUES (3, 0x636F6E6669673A7065726D697373696F6E3A726F6C65);
INSERT INTO `sys_role_permission` VALUES (4, 0x636F6E6669673A7065726D697373696F6E3A7265736F75726365);
INSERT INTO `sys_role_permission` VALUES (4, 0x636F6E6669673A7065726D697373696F6E3A726F6C65);
INSERT INTO `sys_role_permission` VALUES (4, 0x686F6D65);
INSERT INTO `sys_role_permission` VALUES (4, 0x75736572);

SET FOREIGN_KEY_CHECKS = 1;
