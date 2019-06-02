/*
 Navicat Premium Data Transfer

 Source Server         : test
 Source Server Type    : MySQL
 Source Server Version : 80012
 Source Host           : localhost:3306
 Source Schema         : my_test

 Target Server Type    : MySQL
 Target Server Version : 80012
 File Encoding         : 65001

 Date: 03/06/2019 00:28:01
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for document_main
-- ----------------------------
DROP TABLE IF EXISTS `document_main`;
CREATE TABLE `document_main`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `main_code` char(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `KIND` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `fieldType_tag` tinyint(1) UNSIGNED NULL DEFAULT 0,
  `REASON` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `VALUE` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `VALUE1` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `topic` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `picture` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `role` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `object` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `range` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `reader` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `reference` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `comment` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `attachment1` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `attachment2` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `standard` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `attachment3` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `attachment4` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `mains` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `attachment5` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `attachment6` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `relation` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `attachment7` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `attachment8` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `function` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `attachment9` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `attachment10` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `filesystem` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `attachment11` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `attachment12` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `dbsystem` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `attachment13` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `attachment14` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `remark` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `lately_change_time` datetime(0) NULL DEFAULT '1800-01-01 00:00:00',
  `register_time` datetime(0) NULL DEFAULT '1800-01-01 00:00:00',
  `change_time` datetime(0) NULL DEFAULT '1800-01-01 00:00:00',
  `register` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `changer` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `change_amount` int(11) UNSIGNED NULL DEFAULT 0,
  `doc_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `doc_ver` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 83 CHARACTER SET = utf8 COLLATE = utf8_general_ci;

-- ----------------------------
-- Records of document_main
-- ----------------------------
BEGIN;
INSERT INTO `document_main` VALUES (21, '21', '模块', 1, 'homework', '作业管理', 'teach/教学管理', '', 'teach.jpg', 'normal', NULL, NULL, NULL, NULL, NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '1800-01-01 00:00:00', '1800-01-01 00:00:00', '1800-01-01 00:00:00', '', '', 0, '', ''), (29, '01', '模块', 0, 'crm', '客户关系', 'crm/客户关系', '', 'img1.jpg', 'manager', NULL, NULL, NULL, NULL, NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '1800-01-01 00:00:00', '1800-01-01 00:00:00', '1800-01-01 00:00:00', '', '', 0, '', ''), (30, '02', '模块', 0, 'design', '产品设计', 'design/产品设计', '', 'img1.jpg', 'manager', NULL, NULL, NULL, NULL, NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '1800-01-01 00:00:00', '1800-01-01 00:00:00', '1800-01-01 00:00:00', '', '', 0, '', ''), (31, '03', '模块', 0, 'manufacture', '生产管理', 'manufacture/生产管理', '', 'img1.jpg', 'manager', NULL, NULL, NULL, NULL, NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '1800-01-01 00:00:00', '1800-01-01 00:00:00', '1800-01-01 00:00:00', '', '', 0, '', ''), (32, '04', '模块', 0, 'stock', '库存管理', 'stock/库存管理', '', 'img1.jpg', 'manager', NULL, NULL, NULL, NULL, NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '1800-01-01 00:00:00', '1800-01-01 00:00:00', '1800-01-01 00:00:00', '', '', 0, '', ''), (33, '05', '模块', 0, 'purchase', '采购管理', 'purchase/采购管理', '', 'img1.jpg', 'manager', NULL, NULL, NULL, NULL, NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '1800-01-01 00:00:00', '1800-01-01 00:00:00', '1800-01-01 00:00:00', '', '', 0, '', ''), (34, '06', '模块', 0, 'intrmanufacture', '委外管理', 'intrmanufacture/委外管理', '', 'img1.jpg', 'manager', NULL, NULL, NULL, NULL, NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '1800-01-01 00:00:00', '1800-01-01 00:00:00', '1800-01-01 00:00:00', '', '', 0, '', ''), (35, '07', '模块', 0, 'fund', '资金控制', 'fund/资金控制', '', 'img1.jpg', 'manager', NULL, NULL, NULL, NULL, NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '1800-01-01 00:00:00', '1800-01-01 00:00:00', '1800-01-01 00:00:00', '', '', 0, '', ''), (36, '08', '模块', 0, 'finance', '财务系统', 'finance/财务系统', '', 'img1.jpg', 'manager', NULL, NULL, NULL, NULL, NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '1800-01-01 00:00:00', '1800-01-01 00:00:00', '1800-01-01 00:00:00', '', '', 0, '', ''), (37, '09', '模块', 0, 'hr', '人力资源', 'hr/人力资源', '', 'img1.jpg', 'manager', NULL, NULL, NULL, NULL, NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '1800-01-01 00:00:00', '1800-01-01 00:00:00', '1800-01-01 00:00:00', '', '', 0, '', ''), (38, '10', '模块', 0, 'oa', '协同办公', 'oa/协同办公', '', 'img1.jpg', 'manager', NULL, NULL, NULL, NULL, NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '1800-01-01 00:00:00', '1800-01-01 00:00:00', '1800-01-01 00:00:00', '', '', 0, '', ''), (39, '11', '模块', 0, 'ecommerce', '电子商务', 'ecommerce/电子商务', '', 'img1.jpg', 'manager', NULL, NULL, NULL, NULL, NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '1800-01-01 00:00:00', '1800-01-01 00:00:00', '1800-01-01 00:00:00', '', '', 0, '', ''), (40, '12', '模块', 0, 'logistics', '物流配送', 'logistics/物流配送', '', 'img1.jpg', 'manager', NULL, NULL, NULL, NULL, NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '1800-01-01 00:00:00', '1800-01-01 00:00:00', '1800-01-01 00:00:00', '', '', 0, '', ''), (41, '14', '模块', 0, 'qcs', '质量控制', 'qcs/质量控制', '', 'img1.jpg', 'manager', NULL, NULL, NULL, NULL, NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '1800-01-01 00:00:00', '1800-01-01 00:00:00', '1800-01-01 00:00:00', '', '', 0, '', ''), (42, '15', '模块', 1, 'security', '系统安全', 'security/系统安全', '', 'security.jpg', 'manager', NULL, NULL, NULL, NULL, NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '1800-01-01 00:00:00', '1800-01-01 00:00:00', '1800-01-01 00:00:00', '', '', 0, '', ''), (43, '16', '模块', 0, 'draft', '草稿箱', 'draft/草稿箱', '', 'img1.jpg', 'manager', NULL, NULL, NULL, NULL, NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '1800-01-01 00:00:00', '1800-01-01 00:00:00', '1800-01-01 00:00:00', '', '', 0, '', ''), (44, '17', '模块', 0, 'garbage', '垃圾箱', 'garbage/垃圾箱', '', 'img1.jpg', 'manager', NULL, NULL, NULL, NULL, NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '1800-01-01 00:00:00', '1800-01-01 00:00:00', '1800-01-01 00:00:00', '', '', 0, '', ''), (45, '18', '模块', 0, 'market', '市场信息', 'market/市场信息', '', 'img1.jpg', 'manager', NULL, NULL, NULL, NULL, NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '1800-01-01 00:00:00', '1800-01-01 00:00:00', '1800-01-01 00:00:00', '', '', 0, '', ''), (46, '19', '模块', 1, 'project', '项目管理', 'project/项目管理', '', 'img1.jpg', 'manager', NULL, NULL, NULL, NULL, NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '1800-01-01 00:00:00', '1800-01-01 00:00:00', '1800-01-01 00:00:00', '', '', 0, '', ''), (47, '20', '模块', 1, 'sports', '体育运动', 'sports/体育运动', '', 'img20.jpg', 'manager', NULL, NULL, NULL, NULL, NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '1800-01-01 00:00:00', '1800-01-01 00:00:00', '1800-01-01 00:00:00', '', '', 0, '', ''), (48, '21', '模块', 1, 'teach', '教学管理', 'teach/教学管理', '', 'teach.jpg', 'manager', NULL, NULL, NULL, NULL, NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '1800-01-01 00:00:00', '1800-01-01 00:00:00', '1800-01-01 00:00:00', '', '', 0, '', ''), (50, '23', '模块', 1, 'monitor', '系统监控', 'monitor/系统监控', '', 'img23.jpg', 'manager', NULL, NULL, NULL, NULL, NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '1800-01-01 00:00:00', '1800-01-01 00:00:00', '1800-01-01 00:00:00', '', '', 0, '', ''), (51, '24', '模块', 1, 'prison', '看守所', 'prison/看守所', '', 'img24.jpg', 'manager', NULL, NULL, NULL, NULL, NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '1800-01-01 00:00:00', '1800-01-01 00:00:00', '1800-01-01 00:00:00', '', '', 0, '', ''), (52, '25', '模块', 1, 'gis', '电子地图', 'gis/电子地图', '', 'gis.jpg', 'manager', NULL, NULL, NULL, NULL, NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '1800-01-01 00:00:00', '1800-01-01 00:00:00', '1800-01-01 00:00:00', '', '', 0, '', ''), (53, '26', '模块', 1, 'light', '交通信号', 'light/交通信号', '', 'light.jpg', 'manager', NULL, NULL, NULL, NULL, NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '1800-01-01 00:00:00', '1800-01-01 00:00:00', '1800-01-01 00:00:00', '', '', 0, '', ''), (54, '27', '模块', 1, 'gps', '警力资源', 'gps/警力资源', '', 'img27.jpg', 'manager', NULL, NULL, NULL, NULL, NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '1800-01-01 00:00:00', '1800-01-01 00:00:00', '1800-01-01 00:00:00', '', '', 0, '', ''), (55, '28', '模块', 1, 'user', '用户管理', 'user/用户管理', '', 'user.jpg', 'manager', NULL, NULL, NULL, NULL, NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '1800-01-01 00:00:00', '1800-01-01 00:00:00', '1800-01-01 00:00:00', '', '', 0, '', ''), (56, '21', '模块', 1, 'teach', '教学管理', 'teach/教学管理', '', 'teach.jpg', 'teacher', NULL, NULL, NULL, NULL, NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '1800-01-01 00:00:00', '1800-01-01 00:00:00', '1800-01-01 00:00:00', '', '', 0, '', ''), (57, '19', '模块', 1, 'project', '项目管理', 'project/项目管理', '', 'img1.jpg', 'teacher', NULL, NULL, NULL, NULL, NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '1800-01-01 00:00:00', '1800-01-01 00:00:00', '1800-01-01 00:00:00', '', '', 0, '', ''), (58, '20', '模块', 1, 'sports', '体育运动', 'sports/体育运动', '', 'img20.jpg', 'teacher', NULL, NULL, NULL, NULL, NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '1800-01-01 00:00:00', '1800-01-01 00:00:00', '1800-01-01 00:00:00', '', '', 0, '', ''), (60, '23', '模块', 1, 'monitor', '系统监控', 'monitor/系统监控', '', 'img23.jpg', 'teacher', NULL, NULL, NULL, NULL, NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '1800-01-01 00:00:00', '1800-01-01 00:00:00', '1800-01-01 00:00:00', '', '', 0, '', ''), (61, '21', '模块', 1, 'teach', '教学管理', 'teach/教学管理', '', 'teach.jpg', 'student', NULL, NULL, NULL, NULL, NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '1800-01-01 00:00:00', '1800-01-01 00:00:00', '1800-01-01 00:00:00', '', '', 0, '', ''), (62, '28', '模块', 1, 'user', '用户管理', 'user/用户管理', '', 'user.jpg', 'manager', NULL, NULL, NULL, NULL, NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '1800-01-01 00:00:00', '1800-01-01 00:00:00', '1800-01-01 00:00:00', '', '', 0, '', ''), (63, '29', '模块', 1, 'wechat', '微信管理', 'wechat/微信管理', '', 'wechat.jpg', 'manager', NULL, NULL, NULL, NULL, NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '1800-01-01 00:00:00', '1800-01-01 00:00:00', '1800-01-01 00:00:00', '', '', 0, '', ''), (64, '29', '模块', 1, 'travel', '旅游民宿', 'travel/旅游民宿', '', 'travel.jpg', 'manager', NULL, NULL, NULL, NULL, NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '1800-01-01 00:00:00', '1800-01-01 00:00:00', '1800-01-01 00:00:00', '', '', 0, '', ''), (65, '29', '模块', 1, 'user', '学生管理', 'travel/旅游民宿', '', 'travel.jpg', 'normal', NULL, NULL, NULL, NULL, NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '1800-01-01 00:00:00', '1800-01-01 00:00:00', '1800-01-01 00:00:00', '', '', 0, '', ''), (66, '29', '模块', 1, 'zakk', '治安卡口', 'zakk/治安卡口', '', 'zakk.jpg', 'manager', NULL, NULL, NULL, NULL, NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '1800-01-01 00:00:00', '1800-01-01 00:00:00', '1800-01-01 00:00:00', '', '', 0, '', ''), (67, '30', '模块', 1, 'vote', '投票管理', 'club/社团管理', '', 'club.jpg', 'normal', NULL, NULL, NULL, NULL, NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '1800-01-01 00:00:00', '1800-01-01 00:00:00', '1800-01-01 00:00:00', '', '', 0, '', ''), (68, '31', '模块', 1, 'help', '帮助系统', 'help/帮助系统', '', 'help.jpg', 'manager', NULL, NULL, NULL, NULL, NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '1800-01-01 00:00:00', '1800-01-01 00:00:00', '1800-01-01 00:00:00', '', '', 0, '', ''), (69, '32', '模块', 1, 'shop', '商铺管理', 'shop/商铺管理', '', 'shop.jpg', 'manager', NULL, NULL, NULL, NULL, NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '1800-01-01 00:00:00', '1800-01-01 00:00:00', '1800-01-01 00:00:00', '', '', 0, '', ''), (70, '32', '模块', 1, 'shop', '商铺管理', 'shop/商铺管理', '', 'shop.jpg', 'travel_shop_manager', NULL, NULL, NULL, NULL, NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '1800-01-01 00:00:00', '1800-01-01 00:00:00', '1800-01-01 00:00:00', '', '', 0, '', ''), (71, '29', '模块', 1, 'travel', '旅游民宿', 'travel/旅游民宿', '', 'travel.jpg', 'travel_shop_manager', NULL, NULL, NULL, NULL, NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '1800-01-01 00:00:00', '1800-01-01 00:00:00', '1800-01-01 00:00:00', '', '', 0, '', ''), (72, '28', '模块', 1, 'user', '用户管理', 'user/用户管理', '', 'user.jpg', 'travel_shop_manager', NULL, NULL, NULL, NULL, NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '1800-01-01 00:00:00', '1800-01-01 00:00:00', '1800-01-01 00:00:00', '', '', 0, '', ''), (73, '19', '模块', 1, 'investigation', '调查管理', 'project/调查管理', '', 'img1.jpg', 'normal', NULL, NULL, NULL, NULL, NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '1800-01-01 00:00:00', '1800-01-01 00:00:00', '1800-01-01 00:00:00', '', '', 0, '', ''), (74, '21', '模块', 1, 'teach', '教学管理', 'teach/教学管理', '', 'teach.jpg', 'wechat_guest', NULL, NULL, NULL, NULL, NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '1800-01-01 00:00:00', '1800-01-01 00:00:00', '1800-01-01 00:00:00', '', '', 0, '', ''), (75, '28', '模块', 1, 'user', '用户管理', 'user/用户管理', '', 'user.jpg', 'teacher', NULL, NULL, NULL, NULL, NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '1800-01-01 00:00:00', '1800-01-01 00:00:00', '1800-01-01 00:00:00', '', '', 0, '', ''), (76, '30', '模块', 1, 'club', '社团管理', 'club/社团管理', '', 'club.jpg', 'manager', NULL, NULL, NULL, NULL, NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '1800-01-01 00:00:00', '1800-01-01 00:00:00', '1800-01-01 00:00:00', '', '', 0, '', ''), (77, '30', '模块', 1, 'club', '社团管理', 'club/社团管理', '', 'club.jpg', 'teacher', NULL, NULL, NULL, NULL, NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '1800-01-01 00:00:00', '1800-01-01 00:00:00', '1800-01-01 00:00:00', '', '', 0, '', ''), (78, '30', '模块', 1, 'club', '社团管理', 'club/社团管理', '', 'club.jpg', 'student', NULL, NULL, NULL, NULL, NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '1800-01-01 00:00:00', '1800-01-01 00:00:00', '1800-01-01 00:00:00', '', '', 0, '', ''), (79, '30', '模块', 1, 'dzjc', '电子警察', 'dzjc/电子警察', '', 'dzjc.jpg', 'manager', NULL, NULL, NULL, NULL, NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '1800-01-01 00:00:00', '1800-01-01 00:00:00', '1800-01-01 00:00:00', '', '', 0, '', ''), (80, '30', '模块', 1, 'dzjc', '电子警察', 'dzjc/电子警察', '', 'dzjc.jpg', 'manager', NULL, NULL, NULL, NULL, NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '1800-01-01 00:00:00', '1800-01-01 00:00:00', '1800-01-01 00:00:00', '', '', 0, '', ''), (81, '30', '模块', 1, 'video', '视频监控', 'video/视频监控', '', 'club.jpg', 'manager', NULL, NULL, NULL, NULL, NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '1800-01-01 00:00:00', '1800-01-01 00:00:00', '1800-01-01 00:00:00', '', '', 0, '', ''), (82, '19', '模块', 1, 'file', '文件管理', 'forum/百家论坛', '', 'img1.jpg', 'normal', NULL, NULL, NULL, NULL, NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '1800-01-01 00:00:00', '1800-01-01 00:00:00', '1800-01-01 00:00:00', '', '', 0, '', ''), (83, '19', '模块', 1, 'forum', '百家论坛', 'forum/百家论坛', '', 'img1.jpg', 'teacher', NULL, NULL, NULL, NULL, NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '1800-01-01 00:00:00', '1800-01-01 00:00:00', '1800-01-01 00:00:00', '', '', 0, '', '');
COMMIT;

-- ----------------------------
-- Table structure for file_comment
-- ----------------------------
DROP TABLE IF EXISTS `file_comment`;
CREATE TABLE `file_comment`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `file_id` int(10) NULL DEFAULT NULL,
  `user_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `comment_content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `score` int(10) NULL DEFAULT NULL,
  `publish_date` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 33 CHARACTER SET = utf8 COLLATE = utf8_general_ci;

-- ----------------------------
-- Records of file_comment
-- ----------------------------
BEGIN;
INSERT INTO `file_comment` VALUES (20, 1, 'zhangsan', '好好好', 100, '2019-05-06 22:50:38'), (21, 1, 'zhangsan', '不错不错', 26, '2019-05-07 01:14:37'), (22, 1, '李四', '哈哈哈哈', 87, '2019-05-07 01:15:36'), (23, 1, 'zhangsan', '再接再厉', 16, '2019-05-07 01:16:16'), (24, 2, '李四', '支持楼主', 34, '2019-05-01 13:08:40'), (25, 4, '张三', '哈哈哈哈哈哈', 50, '2019-05-07 14:19:04'), (26, 4, '王麻子', '好好好', 50, '2019-05-07 14:23:43'), (27, 3, '张三', '233333', 50, '2019-05-07 14:24:43'), (28, 3, '李四', '楼主加油', 50, '2019-05-07 14:28:38'), (29, 3, '张三', '不错', 50, '2019-05-07 14:31:29'), (30, 3, '王麻子', '测试', 50, '2019-05-07 14:32:42'), (31, 4, '李四', 'test', 50, '2019-05-07 14:39:32'), (32, 4, '张三', 'dfhcgk44', 50, '2019-05-07 15:25:14'), (33, 1, '张三', '233333333', 50, '2019-05-07 19:28:17'), (34, 1, '1234', 'zjdhkk', 50, '2019-06-02 20:05:56');
COMMIT;

-- ----------------------------
-- Table structure for file_manage
-- ----------------------------
DROP TABLE IF EXISTS `file_manage`;
CREATE TABLE `file_manage`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `file_id` int(10) NULL DEFAULT NULL,
  `uploader_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `file_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `file_size` int(10) NULL DEFAULT NULL,
  `upload_time` datetime(0) NULL DEFAULT NULL,
  `download_link` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 27 CHARACTER SET = utf8 COLLATE = utf8_general_ci;

-- ----------------------------
-- Records of file_manage
-- ----------------------------
BEGIN;
INSERT INTO `file_manage` VALUES (20, 1, 'zhangsan', 'BBFlashBack.rar', 100, '2019-05-06 22:50:38', NULL), (21, 2, 'zhangsan', '张彭俊_第2周作业.rar', 2697647, '2019-05-07 01:14:37', '../../DownloadServlet?file_name=张彭俊_第2周作业.rar'), (22, 3, 'zhangsan', '六级准考证.pdf', 87816, '2019-05-07 01:15:36', '../../DownloadServlet?file_name=六级准考证.pdf'), (23, 4, 'zhangsan', '一带一路.docx', 16006, '2019-05-07 01:16:16', '../../DownloadServlet?file_name=一带一路.docx'), (24, 1, 'zhangsan', '浅谈石油化工安全.doc', 35328, '2019-05-07 19:27:37', '../../DownloadServlet?file_name=浅谈石油化工安全.doc'), (25, 1, 'zhangsan', '白色谷歌开机动画(卡刷包-乐橙).zip', 1824317, '2019-05-13 11:02:37', '../../DownloadServlet?file_name=白色谷歌开机动画(卡刷包-乐橙).zip'), (26, 1, 'zhangsan', '实战Java高并发程序设计@www.java1234.com.pdf', 83894684, '2019-05-13 11:10:16', '../../DownloadServlet?file_name=实战Java高并发程序设计@www.java1234.com.pdf'), (27, 1, 'zhangsan', '基于PC的语音信号处理.docx', 63321, '2019-05-14 09:32:14', '../../DownloadServlet?file_name=基于PC的语音信号处理.docx');
COMMIT;

-- ----------------------------
-- Table structure for homework_file_manage
-- ----------------------------
DROP TABLE IF EXISTS `homework_file_manage`;
CREATE TABLE `homework_file_manage`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `homework_id` int(10) NULL DEFAULT NULL,
  `uploader_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `file_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `file_size` int(10) NULL DEFAULT NULL,
  `upload_time` datetime(0) NULL DEFAULT NULL,
  `download_link` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `uploader_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 58 CHARACTER SET = utf8 COLLATE = utf8_general_ci;

-- ----------------------------
-- Records of homework_file_manage
-- ----------------------------
BEGIN;
INSERT INTO `homework_file_manage` VALUES (20, 23, '李四', 'BBFlashBack.rar', 100, '2019-05-06 22:50:38', NULL, NULL), (21, 23, '王麻子', '张彭俊_第2周作业.rar', 2697647, '2019-05-07 01:14:37', '../../DownloadServlet?file_name=张彭俊_第2周作业.rar', NULL), (22, 23, 'xxx', '六级准考证.pdf', 87816, '2019-05-07 01:15:36', '../../DownloadServlet?file_name=六级准考证.pdf', NULL), (23, 23, '哈哈哈', '一带一路.docx', 16006, '2019-05-07 01:16:16', '../../DownloadServlet?file_name=一带一路.docx', NULL), (24, 22, 'test', '浅谈石油化工安全.doc', 35328, '2019-05-07 19:27:37', '../../DownloadServlet?file_name=浅谈石油化工安全.doc', NULL), (25, 22, 'Maria', '白色谷歌开机动画(卡刷包-乐橙).zip', 1824317, '2019-05-13 11:02:37', '../../DownloadServlet?file_name=白色谷歌开机动画(卡刷包-乐橙).zip', NULL), (26, 22, 'Tom', '实战Java高并发程序设计@www.java1234.com.pdf', 83894684, '2019-05-13 11:10:16', '../../DownloadServlet?file_name=实战Java高并发程序设计@www.java1234.com.pdf', NULL), (27, 23, '张武', 'XM05-移动互动课堂-概要设计.doc', 1441280, '2019-05-14 09:50:58', '../../DownloadServlet?file_name=XM05-移动互动课堂-概要设计.doc', NULL), (58, 23, 'zhangsan', '2017141400000_张三_第二次作业.rar', 37334485, '2019-05-24 23:44:36', '../../DownloadServlet?file_name=2017141400000_张三_第二次作业.rar', 'zhangsan');
COMMIT;

-- ----------------------------
-- Table structure for homework_manage
-- ----------------------------
DROP TABLE IF EXISTS `homework_manage`;
CREATE TABLE `homework_manage`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `file_id` int(10) NULL DEFAULT NULL,
  `uploader_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `file_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `file_size` int(10) NULL DEFAULT NULL,
  `upload_time` datetime(0) NULL DEFAULT NULL,
  `download_link` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `deadline` datetime(0) NULL DEFAULT NULL,
  `file_format` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `homework_requirement` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 32 CHARACTER SET = utf8 COLLATE = utf8_general_ci;

-- ----------------------------
-- Records of homework_manage
-- ----------------------------
BEGIN;
INSERT INTO `homework_manage` VALUES (22, 3, 'xxx', '第一次作业', 87816, '2019-05-07 01:15:36', '../../DownloadServlet?file_name=六级准考证.pdf', '2019-05-10 12:59:09', '学号_姓名_作业名', 'xxxxxxxxxxxxxxxxxxxxxx'), (23, 4, '哈哈哈', '第二次作业', 16006, '2019-05-07 01:16:16', '../../DownloadServlet?file_name=一带一路.docx', '2019-05-30 12:59:27', '学号_姓名_作业名', 'hhhhhhhhhhhhhhhhhhhhhhhh');
COMMIT;

-- ----------------------------
-- Table structure for interactive_classroom_main
-- ----------------------------
DROP TABLE IF EXISTS `interactive_classroom_main`;
CREATE TABLE `interactive_classroom_main`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `main_code` char(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `KIND` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `fieldType_tag` tinyint(1) UNSIGNED NULL DEFAULT 0,
  `REASON` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `VALUE` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `VALUE1` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `topic` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `picture` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `role` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `object` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `range` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `reader` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `reference` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `comment` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `attachment1` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `attachment2` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `standard` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `attachment3` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `attachment4` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `mains` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `attachment5` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `attachment6` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `relation` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `attachment7` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `attachment8` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `function` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `attachment9` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `attachment10` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `filesystem` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `attachment11` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `attachment12` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `dbsystem` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `attachment13` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `attachment14` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `remark` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `lately_change_time` datetime(0) NULL DEFAULT '1800-01-01 00:00:00',
  `register_time` datetime(0) NULL DEFAULT '1800-01-01 00:00:00',
  `change_time` datetime(0) NULL DEFAULT '1800-01-01 00:00:00',
  `register` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `changer` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `change_amount` int(11) UNSIGNED NULL DEFAULT 0,
  `doc_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `doc_ver` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 84 CHARACTER SET = utf8 COLLATE = utf8_general_ci;

-- ----------------------------
-- Records of interactive_classroom_main
-- ----------------------------
BEGIN;
INSERT INTO `interactive_classroom_main` VALUES (21, '21', '模块', 1, 'homework', '作业管理', 'teach/教学管理', '', 'teach.jpg', 'student', NULL, NULL, NULL, NULL, NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '1800-01-01 00:00:00', '1800-01-01 00:00:00', '1800-01-01 00:00:00', '', '', 0, '', ''), (29, '01', '模块', 0, 'crm', '客户关系', 'crm/客户关系', '', 'img1.jpg', 'manager', NULL, NULL, NULL, NULL, NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '1800-01-01 00:00:00', '1800-01-01 00:00:00', '1800-01-01 00:00:00', '', '', 0, '', ''), (30, '02', '模块', 0, 'design', '产品设计', 'design/产品设计', '', 'img1.jpg', 'manager', NULL, NULL, NULL, NULL, NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '1800-01-01 00:00:00', '1800-01-01 00:00:00', '1800-01-01 00:00:00', '', '', 0, '', ''), (31, '03', '模块', 0, 'manufacture', '生产管理', 'manufacture/生产管理', '', 'img1.jpg', 'manager', NULL, NULL, NULL, NULL, NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '1800-01-01 00:00:00', '1800-01-01 00:00:00', '1800-01-01 00:00:00', '', '', 0, '', ''), (32, '04', '模块', 0, 'stock', '库存管理', 'stock/库存管理', '', 'img1.jpg', 'manager', NULL, NULL, NULL, NULL, NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '1800-01-01 00:00:00', '1800-01-01 00:00:00', '1800-01-01 00:00:00', '', '', 0, '', ''), (33, '05', '模块', 0, 'purchase', '采购管理', 'purchase/采购管理', '', 'img1.jpg', 'manager', NULL, NULL, NULL, NULL, NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '1800-01-01 00:00:00', '1800-01-01 00:00:00', '1800-01-01 00:00:00', '', '', 0, '', ''), (34, '06', '模块', 0, 'intrmanufacture', '委外管理', 'intrmanufacture/委外管理', '', 'img1.jpg', 'manager', NULL, NULL, NULL, NULL, NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '1800-01-01 00:00:00', '1800-01-01 00:00:00', '1800-01-01 00:00:00', '', '', 0, '', ''), (35, '07', '模块', 0, 'fund', '资金控制', 'fund/资金控制', '', 'img1.jpg', 'manager', NULL, NULL, NULL, NULL, NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '1800-01-01 00:00:00', '1800-01-01 00:00:00', '1800-01-01 00:00:00', '', '', 0, '', ''), (36, '08', '模块', 0, 'finance', '财务系统', 'finance/财务系统', '', 'img1.jpg', 'manager', NULL, NULL, NULL, NULL, NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '1800-01-01 00:00:00', '1800-01-01 00:00:00', '1800-01-01 00:00:00', '', '', 0, '', ''), (37, '09', '模块', 0, 'hr', '人力资源', 'hr/人力资源', '', 'img1.jpg', 'manager', NULL, NULL, NULL, NULL, NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '1800-01-01 00:00:00', '1800-01-01 00:00:00', '1800-01-01 00:00:00', '', '', 0, '', ''), (38, '10', '模块', 0, 'oa', '协同办公', 'oa/协同办公', '', 'img1.jpg', 'manager', NULL, NULL, NULL, NULL, NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '1800-01-01 00:00:00', '1800-01-01 00:00:00', '1800-01-01 00:00:00', '', '', 0, '', ''), (39, '11', '模块', 0, 'ecommerce', '电子商务', 'ecommerce/电子商务', '', 'img1.jpg', 'manager', NULL, NULL, NULL, NULL, NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '1800-01-01 00:00:00', '1800-01-01 00:00:00', '1800-01-01 00:00:00', '', '', 0, '', ''), (40, '12', '模块', 0, 'logistics', '物流配送', 'logistics/物流配送', '', 'img1.jpg', 'manager', NULL, NULL, NULL, NULL, NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '1800-01-01 00:00:00', '1800-01-01 00:00:00', '1800-01-01 00:00:00', '', '', 0, '', ''), (41, '14', '模块', 0, 'qcs', '质量控制', 'qcs/质量控制', '', 'img1.jpg', 'manager', NULL, NULL, NULL, NULL, NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '1800-01-01 00:00:00', '1800-01-01 00:00:00', '1800-01-01 00:00:00', '', '', 0, '', ''), (42, '15', '模块', 1, 'security', '系统安全', 'security/系统安全', '', 'security.jpg', 'manager', NULL, NULL, NULL, NULL, NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '1800-01-01 00:00:00', '1800-01-01 00:00:00', '1800-01-01 00:00:00', '', '', 0, '', ''), (43, '16', '模块', 0, 'draft', '草稿箱', 'draft/草稿箱', '', 'img1.jpg', 'manager', NULL, NULL, NULL, NULL, NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '1800-01-01 00:00:00', '1800-01-01 00:00:00', '1800-01-01 00:00:00', '', '', 0, '', ''), (44, '17', '模块', 0, 'garbage', '垃圾箱', 'garbage/垃圾箱', '', 'img1.jpg', 'manager', NULL, NULL, NULL, NULL, NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '1800-01-01 00:00:00', '1800-01-01 00:00:00', '1800-01-01 00:00:00', '', '', 0, '', ''), (45, '18', '模块', 0, 'market', '市场信息', 'market/市场信息', '', 'img1.jpg', 'manager', NULL, NULL, NULL, NULL, NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '1800-01-01 00:00:00', '1800-01-01 00:00:00', '1800-01-01 00:00:00', '', '', 0, '', ''), (46, '19', '模块', 1, 'project', '项目管理', 'project/项目管理', '', 'img1.jpg', 'manager', NULL, NULL, NULL, NULL, NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '1800-01-01 00:00:00', '1800-01-01 00:00:00', '1800-01-01 00:00:00', '', '', 0, '', ''), (47, '20', '模块', 1, 'sports', '体育运动', 'sports/体育运动', '', 'img20.jpg', 'manager', NULL, NULL, NULL, NULL, NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '1800-01-01 00:00:00', '1800-01-01 00:00:00', '1800-01-01 00:00:00', '', '', 0, '', ''), (48, '21', '模块', 1, 'teach', '教学管理', 'teach/教学管理', '', 'teach.jpg', 'manager', NULL, NULL, NULL, NULL, NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '1800-01-01 00:00:00', '1800-01-01 00:00:00', '1800-01-01 00:00:00', '', '', 0, '', ''), (50, '23', '模块', 1, 'monitor', '系统监控', 'monitor/系统监控', '', 'img23.jpg', 'manager', NULL, NULL, NULL, NULL, NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '1800-01-01 00:00:00', '1800-01-01 00:00:00', '1800-01-01 00:00:00', '', '', 0, '', ''), (51, '24', '模块', 1, 'prison', '看守所', 'prison/看守所', '', 'img24.jpg', 'manager', NULL, NULL, NULL, NULL, NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '1800-01-01 00:00:00', '1800-01-01 00:00:00', '1800-01-01 00:00:00', '', '', 0, '', ''), (52, '25', '模块', 1, 'gis', '电子地图', 'gis/电子地图', '', 'gis.jpg', 'manager', NULL, NULL, NULL, NULL, NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '1800-01-01 00:00:00', '1800-01-01 00:00:00', '1800-01-01 00:00:00', '', '', 0, '', ''), (53, '26', '模块', 1, 'light', '交通信号', 'light/交通信号', '', 'light.jpg', 'manager', NULL, NULL, NULL, NULL, NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '1800-01-01 00:00:00', '1800-01-01 00:00:00', '1800-01-01 00:00:00', '', '', 0, '', ''), (54, '27', '模块', 1, 'gps', '警力资源', 'gps/警力资源', '', 'img27.jpg', 'manager', NULL, NULL, NULL, NULL, NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '1800-01-01 00:00:00', '1800-01-01 00:00:00', '1800-01-01 00:00:00', '', '', 0, '', ''), (55, '28', '模块', 1, 'user', '用户管理', 'user/用户管理', '', 'user.jpg', 'manager', NULL, NULL, NULL, NULL, NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '1800-01-01 00:00:00', '1800-01-01 00:00:00', '1800-01-01 00:00:00', '', '', 0, '', ''), (56, '21', '模块', 1, 'teach', '教学管理', 'teach/教学管理', '', 'teach.jpg', 'teacher', NULL, NULL, NULL, NULL, NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '1800-01-01 00:00:00', '1800-01-01 00:00:00', '1800-01-01 00:00:00', '', '', 0, '', ''), (57, '19', '模块', 1, 'project', '项目管理', 'project/项目管理', '', 'img1.jpg', 'teacher', NULL, NULL, NULL, NULL, NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '1800-01-01 00:00:00', '1800-01-01 00:00:00', '1800-01-01 00:00:00', '', '', 0, '', ''), (58, '20', '模块', 1, 'sports', '体育运动', 'sports/体育运动', '', 'img20.jpg', 'teacher', NULL, NULL, NULL, NULL, NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '1800-01-01 00:00:00', '1800-01-01 00:00:00', '1800-01-01 00:00:00', '', '', 0, '', ''), (60, '23', '模块', 1, 'monitor', '系统监控', 'monitor/系统监控', '', 'img23.jpg', 'teacher', NULL, NULL, NULL, NULL, NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '1800-01-01 00:00:00', '1800-01-01 00:00:00', '1800-01-01 00:00:00', '', '', 0, '', ''), (61, '21', '模块', 1, 'teach', '教学管理', 'teach/教学管理', '', 'teach.jpg', 'student1', NULL, NULL, NULL, NULL, NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '1800-01-01 00:00:00', '1800-01-01 00:00:00', '1800-01-01 00:00:00', '', '', 0, '', ''), (62, '28', '模块', 1, 'user', '用户管理', 'user/用户管理', '', 'user.jpg', 'manager', NULL, NULL, NULL, NULL, NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '1800-01-01 00:00:00', '1800-01-01 00:00:00', '1800-01-01 00:00:00', '', '', 0, '', ''), (63, '29', '模块', 1, 'wechat', '微信管理', 'wechat/微信管理', '', 'wechat.jpg', 'manager', NULL, NULL, NULL, NULL, NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '1800-01-01 00:00:00', '1800-01-01 00:00:00', '1800-01-01 00:00:00', '', '', 0, '', ''), (64, '29', '模块', 1, 'travel', '旅游民宿', 'travel/旅游民宿', '', 'travel.jpg', 'manager', NULL, NULL, NULL, NULL, NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '1800-01-01 00:00:00', '1800-01-01 00:00:00', '1800-01-01 00:00:00', '', '', 0, '', ''), (65, '29', '模块', 1, 'user', '学生管理', 'travel/旅游民宿', '', 'travel.jpg', 'student', NULL, NULL, NULL, NULL, NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '1800-01-01 00:00:00', '1800-01-01 00:00:00', '1800-01-01 00:00:00', '', '', 0, '', ''), (66, '29', '模块', 1, 'zakk', '治安卡口', 'zakk/治安卡口', '', 'zakk.jpg', 'manager', NULL, NULL, NULL, NULL, NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '1800-01-01 00:00:00', '1800-01-01 00:00:00', '1800-01-01 00:00:00', '', '', 0, '', ''), (67, '30', '模块', 1, 'vote', '投票管理', 'club/社团管理', '', 'club.jpg', 'student', NULL, NULL, NULL, NULL, NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '1800-01-01 00:00:00', '1800-01-01 00:00:00', '1800-01-01 00:00:00', '', '', 0, '', ''), (68, '31', '模块', 1, 'help', '帮助系统', 'help/帮助系统', '', 'help.jpg', 'manager', NULL, NULL, NULL, NULL, NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '1800-01-01 00:00:00', '1800-01-01 00:00:00', '1800-01-01 00:00:00', '', '', 0, '', ''), (69, '32', '模块', 1, 'shop', '商铺管理', 'shop/商铺管理', '', 'shop.jpg', 'manager', NULL, NULL, NULL, NULL, NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '1800-01-01 00:00:00', '1800-01-01 00:00:00', '1800-01-01 00:00:00', '', '', 0, '', ''), (70, '32', '模块', 1, 'shop', '商铺管理', 'shop/商铺管理', '', 'shop.jpg', 'travel_shop_manager', NULL, NULL, NULL, NULL, NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '1800-01-01 00:00:00', '1800-01-01 00:00:00', '1800-01-01 00:00:00', '', '', 0, '', ''), (71, '29', '模块', 1, 'travel', '旅游民宿', 'travel/旅游民宿', '', 'travel.jpg', 'travel_shop_manager', NULL, NULL, NULL, NULL, NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '1800-01-01 00:00:00', '1800-01-01 00:00:00', '1800-01-01 00:00:00', '', '', 0, '', ''), (72, '28', '模块', 1, 'user', '用户管理', 'user/用户管理', '', 'user.jpg', 'travel_shop_manager', NULL, NULL, NULL, NULL, NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '1800-01-01 00:00:00', '1800-01-01 00:00:00', '1800-01-01 00:00:00', '', '', 0, '', ''), (73, '19', '模块', 1, 'investigation', '调查管理', 'project/调查管理', '', 'img1.jpg', 'student', NULL, NULL, NULL, NULL, NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '1800-01-01 00:00:00', '1800-01-01 00:00:00', '1800-01-01 00:00:00', '', '', 0, '', ''), (74, '21', '模块', 1, 'teach', '教学管理', 'teach/教学管理', '', 'teach.jpg', 'wechat_guest', NULL, NULL, NULL, NULL, NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '1800-01-01 00:00:00', '1800-01-01 00:00:00', '1800-01-01 00:00:00', '', '', 0, '', ''), (75, '28', '模块', 1, 'user', '用户管理', 'user/用户管理', '', 'user.jpg', 'teacher', NULL, NULL, NULL, NULL, NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '1800-01-01 00:00:00', '1800-01-01 00:00:00', '1800-01-01 00:00:00', '', '', 0, '', ''), (76, '30', '模块', 1, 'club', '社团管理', 'club/社团管理', '', 'club.jpg', 'manager', NULL, NULL, NULL, NULL, NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '1800-01-01 00:00:00', '1800-01-01 00:00:00', '1800-01-01 00:00:00', '', '', 0, '', ''), (77, '30', '模块', 1, 'club', '社团管理', 'club/社团管理', '', 'club.jpg', 'teacher', NULL, NULL, NULL, NULL, NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '1800-01-01 00:00:00', '1800-01-01 00:00:00', '1800-01-01 00:00:00', '', '', 0, '', ''), (78, '30', '模块', 1, 'club', '社团管理', 'club/社团管理', '', 'club.jpg', 'student1', NULL, NULL, NULL, NULL, NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '1800-01-01 00:00:00', '1800-01-01 00:00:00', '1800-01-01 00:00:00', '', '', 0, '', ''), (79, '30', '模块', 1, 'dzjc', '电子警察', 'dzjc/电子警察', '', 'dzjc.jpg', 'manager', NULL, NULL, NULL, NULL, NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '1800-01-01 00:00:00', '1800-01-01 00:00:00', '1800-01-01 00:00:00', '', '', 0, '', ''), (80, '30', '模块', 1, 'dzjc', '电子警察', 'dzjc/电子警察', '', 'dzjc.jpg', 'manager', NULL, NULL, NULL, NULL, NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '1800-01-01 00:00:00', '1800-01-01 00:00:00', '1800-01-01 00:00:00', '', '', 0, '', ''), (81, '30', '模块', 1, 'video', '视频监控', 'video/视频监控', '', 'club.jpg', 'manager', NULL, NULL, NULL, NULL, NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '1800-01-01 00:00:00', '1800-01-01 00:00:00', '1800-01-01 00:00:00', '', '', 0, '', ''), (82, '19', '模块', 1, 'file', '文件管理', 'forum/百家论坛', '', 'img1.jpg', 'student', NULL, NULL, NULL, NULL, NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '1800-01-01 00:00:00', '1800-01-01 00:00:00', '1800-01-01 00:00:00', '', '', 0, '', ''), (83, '19', '模块', 1, 'forum', '百家论坛', 'forum/百家论坛', '', 'img1.jpg', 'teacher', NULL, NULL, NULL, NULL, NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '', '', NULL, '1800-01-01 00:00:00', '1800-01-01 00:00:00', '1800-01-01 00:00:00', '', '', 0, '', '');
COMMIT;

-- ----------------------------
-- Table structure for investigation_manage
-- ----------------------------
DROP TABLE IF EXISTS `investigation_manage`;
CREATE TABLE `investigation_manage`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `parent_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `user_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `end_time` datetime(0) NULL DEFAULT NULL,
  `creator` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `create_time` datetime(0) NULL DEFAULT NULL,
  `link` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci;

-- ----------------------------
-- Records of investigation_manage
-- ----------------------------
BEGIN;
INSERT INTO `investigation_manage` VALUES (4, 'kfjwwxpt', 'zhangsan', '食堂饭菜满意度调查', '调查学生对学校食堂饭菜的满意度调查', '2015-11-29 00:00:00', '张三', '2017-04-23 14:50:37', 'https://www.wjx.cn'), (10, 'PRJ_20161210020741', 'zhangsan', '下周三是否有空', '调查学生周三是否有空', '2019-04-18 23:58:17', '辛卫', '2017-04-29 14:50:37', 'https://www.wjx.cn'), (11, 'PRJ_20161207052455', 'zhangsan', '意见收集', '收集学生对课堂教学的意见', '2019-04-02 23:58:24', '辛卫', '2017-05-01 14:50:37', 'https://www.wjx.cn'), (13, 'PRJ_20161022023027', 'zhangsan', '教学满意度调查', '调查学生对老师教学情况', '2019-04-19 23:58:27', '张三', '2017-05-11 14:34:27', 'https://www.wjx.cn'), (15, 'PRJ_20161022023027', 'zhangsan', '考试考试', '考试快来看看看看看', '2019-05-01 23:58:31', '张三', '2017-05-11 14:52:06', 'https://www.wjx.cn');
COMMIT;

-- ----------------------------
-- Table structure for project_core
-- ----------------------------
DROP TABLE IF EXISTS `project_core`;
CREATE TABLE `project_core`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `parent_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `user_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `type` int(10) NULL DEFAULT NULL,
  `limit_time` datetime(0) NULL DEFAULT NULL,
  `end_tag` int(10) NULL DEFAULT 0,
  `begin_time` datetime(0) NULL DEFAULT NULL,
  `end_time` datetime(0) NULL DEFAULT NULL,
  `executor` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `status` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `attachment_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `attachment_size` int(10) NULL DEFAULT 0,
  `priority` int(10) NULL DEFAULT NULL,
  `check_tag` int(10) NULL DEFAULT 0,
  `checker` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `check_time` datetime(0) NULL DEFAULT NULL,
  `excel_tag` int(10) NULL DEFAULT 0,
  `gar_tag` int(10) NULL DEFAULT 0,
  `used_tag` int(10) NULL DEFAULT 1,
  `creator` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `create_time` datetime(0) NULL DEFAULT NULL,
  `link` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8 COLLATE = utf8_general_ci;

-- ----------------------------
-- Records of project_core
-- ----------------------------
BEGIN;
INSERT INTO `project_core` VALUES (3, 'kfjwwxpt', 'zhangsan', '关于作业完成情况的查询', '调查学生作业完成情况', NULL, '2017-04-21 14:50:37', 0, '2016-05-24 00:00:00', '2016-05-25 00:00:00', '张三', '准备执行', NULL, 0, NULL, 0, NULL, NULL, 0, 0, 1, '', '2017-04-21 14:50:37', 'https://www.wjx.cn'), (4, 'kfjwwxpt', 'zhangsan', '食堂饭菜满意度调查', '调查学生对学校食堂饭菜的满意度调查', NULL, '2017-04-23 14:50:37', 0, '2015-11-29 00:00:00', '2015-11-29 00:00:00', '张三', '准备执行', NULL, 0, NULL, 0, NULL, NULL, 0, 0, 1, '', '2017-04-23 14:50:37', 'https://www.wjx.cn'), (10, 'PRJ_20161210020741', 'zhangsan', '下周三是否有空', '调查学生周三是否有空', NULL, '2017-04-29 14:50:37', 0, NULL, '2019-04-18 23:58:17', '', '', NULL, 0, NULL, 0, NULL, NULL, 0, 0, 1, '辛卫', '2017-04-29 14:50:37', 'https://www.wjx.cn'), (11, 'PRJ_20161207052455', 'zhangsan', '意见收集', '收集学生对课堂教学的意见', NULL, '2017-05-01 14:50:37', 0, NULL, '2019-04-02 23:58:24', '', '', NULL, 0, NULL, 0, NULL, NULL, 0, 0, 1, '辛卫', '2017-05-01 14:50:37', 'https://www.wjx.cn'), (13, 'PRJ_20161022023027', 'zhangsan', '教学满意度调查', '调查学生对老师教学情况', NULL, '2017-05-18 14:50:59', 0, NULL, '2019-04-19 23:58:27', '', '', NULL, 0, NULL, 0, NULL, NULL, 0, 0, 1, '张三', '2017-05-11 14:34:27', 'https://www.wjx.cn'), (15, 'PRJ_20161022023027', 'zhangsan', '考试考试', '考试快来看看看看看', NULL, '2017-05-11 14:52:06', 0, NULL, '2019-05-01 23:58:31', '', '', NULL, 0, NULL, 0, NULL, NULL, 0, 0, 1, '张三', '2017-05-11 14:52:06', 'https://www.wjx.cn'), (19, 'PRJ_20161022023027', 'zhangsan', 'dihfgdl', 'shgxlk', NULL, '2019-04-29 23:59:59', 0, NULL, '2019-04-29 23:59:59', '', '', NULL, 0, NULL, 0, NULL, NULL, 0, 0, 1, '张三', '2019-04-29 22:59:10', 'www.baidu.com');
COMMIT;

-- ----------------------------
-- Table structure for project_file
-- ----------------------------
DROP TABLE IF EXISTS `project_file`;
CREATE TABLE `project_file`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `project_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `project_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `project_nick` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `project_class` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `project_source` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `project_describe` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `project_manager_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `project_manager_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `apply_money` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `approval_money` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `group_member` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `start_time` datetime(0) NULL DEFAULT NULL,
  `end_time` datetime(0) NULL DEFAULT NULL,
  `superior_unit` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `superior_manager` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0.00',
  `attachment_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `open_level` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `check_tag` int(10) NULL DEFAULT 0,
  `checker` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `check_time` datetime(0) NULL DEFAULT NULL,
  `price_alarm_tag` int(10) NULL DEFAULT 0,
  `price_change_tag` int(10) NULL DEFAULT 0,
  `creator` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `modifier` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `modify_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 33 CHARACTER SET = utf8 COLLATE = utf8_general_ci;

-- ----------------------------
-- Records of project_file
-- ----------------------------
BEGIN;
INSERT INTO `project_file` VALUES (14, 'XM04', '基于OpenCV的交通视频质量诊断系统', '视频质量诊断', '三级[1,5)', NULL, '对交通视频进行质量检测是智能交通监控系统正常运行的保障。它能及时有效地发现电路摄像头损坏，线路损坏，信号收到干扰等问题并进行报警，提醒工作人员尽快对其进行修复。对交通视频的质量检测目的为实现所有道路上的监控录像都能高清有效，有利于我们更好的交通管理和问题处理。', NULL, '邹江华', '10000', '10000', '邹江华\r\n黄益明\r\n马普朝', '2014-03-13 17:48:30', '2014-06-18 17:48:38', '项目管理中心', '韦东鑫', 'XM111000000382XM11企业资源计划项目管理子系统-第八周.doc', 'all', 1, 'admin', '2014-06-04 01:56:02', 0, 0, 'admin', '2014-04-29 17:46:55', NULL, NULL), (15, 'XM15', '医院信息管理系统-病案管理', '', '四级[0.3,1)', NULL, '随着信息技术的发展，利用信息技术对医院进行信息化建设，提高医院的管理水平，已经成为了医院信息化的重要发展方向。作为医院重要的信息节点之一，病案信息的管理对医院管理决策的制定、医疗信息的利用、司法资料的取证等具有重要的作用。传统的手工操作和纯粹手工操作计算机化的病案管理模式，直接影响了医院信息化管理发展的进程。因此，对病案这一重要信息枢纽实行信息化管理，对医院内、医院间、乃至全球的信息交流与应用，以及对医院的现代化管理及医学科学的发展等都具有重要的现实意义和实用价值。改变了手工操作和纯粹手工操作计算机化的病案管理模式，实现了计算机的一体化管理；提高了病案管理的工作效率；扩大了病案信息资料计算机的存储、检索及利用；及时反馈了医疗信息的统计数据；保证了病案流通病案资料保存的完整性。', NULL, '李虎', '5000', '5000', '李虎  2012141461027\r\n徐方婧2012141461077\r\n马锐  2012141461187', '2014-03-12 19:17:43', '2014-06-25 19:17:48', '项目管理中心', '任倩', 'XM111000000382XM11企业资源计划项目管理子系统-第八周.doc', 'all', 1, 'admin', '2014-04-29 22:22:00', 0, 0, 'admin', '2014-04-29 19:14:22', NULL, NULL), (16, 'XM05', '非接触式读写应用', '非接触式读写应用', '三级[1,5)', NULL, '研究各种非接触式读写应用。', NULL, '吴惠惠', '5000', '5000', '吴惠惠\r\n杨晓玲\r\n于佳铨', '2014-03-12 19:21:08', '2014-06-25 19:25:05', '项目管理中心', '韦东鑫', 'XM051000000379XM05-非接触式读写应用-项目周报-第九周.doc', 'all', 1, 'admin', '2014-04-29 21:46:05', 0, 0, 'admin', '2014-04-29 19:19:21', NULL, NULL), (18, 'XM11', '企业资源计划项目管理子系统', '项目管理子系统', '一级(20万及以上)', NULL, '项目管理子系统', NULL, '高岩', '100', '100', '高岩\r\n苏建松', '2014-03-17 20:31:32', '2014-06-16 20:31:37', '研究与开发实践', '辛卫', 'XM111000000382XM11企业资源计划项目管理子系统-第八周.doc', 'all', 1, 'admin', '2014-04-29 21:45:23', 0, 0, 'admin', '2014-04-29 20:30:29', NULL, NULL), (19, 'XM07', '基于GeoServer的GIS研发', 'GIS', '三级[1,5)', NULL, '基于GeoServer的GIS研发：使用GeoServer搭建自己的地图层，并在其基础上实现定位、修改、查看等应用。并将地图供第八组使用。', NULL, '张翔', '10000', '10000', '张翔、张原嘉、张鹏', '2014-04-01 21:05:08', '2014-06-15 21:05:13', '项目管理中心', '韦东鑫', 'XM071000000384XM07-基于GeoServer的GIS研发.doc', 'all', 1, 'admin', '2014-04-29 21:31:59', 0, 0, 'admin', '2014-04-29 21:04:12', NULL, NULL), (20, 'XM16', '医院信息管理系统-门诊、住院部药房管理', '药房管理', '三级[1,5)', NULL, '包括门诊、住院部药房管理系统', NULL, '曾蕊', '50000', '45000', '曾蕊、白静宜、李梦媛、任倩', '2014-03-26 21:39:03', '2014-06-11 21:39:00', '项目管理中心', '任倩', 'XM111000000382XM11企业资源计划项目管理子系统-第八周.doc', 'all', 1, 'admin', '2014-04-29 22:22:14', 0, 0, 'admin', '2014-04-29 21:37:46', NULL, NULL), (21, 'XM09', '市场信息管理', '市场信息', '四级[0.3,1)', NULL, '市场信息管理', NULL, '宋琦', '10000', '10000', '黄圳 徐健', '2014-03-13 22:00:07', '2014-06-10 22:01:00', '项目管理中心', '任倩', 'XM111000000382XM11企业资源计划项目管理子系统-第八周.doc', 'all', 1, 'admin', '2014-04-29 22:16:52', 0, 0, 'admin', '2014-04-29 21:59:06', NULL, NULL), (22, 'XM06', '汽配会员制门户网站', '门户网站', '一级(20万及以上)', NULL, '', NULL, '刘晨航', '1', '1', '马驰\r\n李聪', '2014-03-12 22:27:54', '2014-06-30 22:28:57', '项目管理中心', '韦东鑫', 'XM111000000382XM11企业资源计划项目管理子系统-第八周.doc', 'all', 1, 'admin', '2014-04-29 22:32:21', 0, 0, 'admin', '2014-04-29 22:26:50', NULL, NULL), (24, 'XM02', '关于视频人脸识别的项目', '人脸', '一级(20万及以上)', NULL, '人脸识别应用与交通道路监控', NULL, '张三', '15000', '15000', '张三、李四、王五', '2014-04-30 01:24:20', '2014-04-30 01:24:20', '项目管理中心', '韦东鑫', 'XM021000000380', 'all', 1, 'admin', '2014-04-30 21:04:21', 0, 0, 'admin', '2014-04-30 01:24:20', NULL, NULL), (30, 'PRJ_20161022002245', '物联网农业大棚项目', '农业大棚', '10万', '自拟研发', '这是一个农业大棚项目，兰陵县自然条件优越，农产品资源丰富，是全国蔬菜生产大县，是上海市蔬菜直供基地、北京市蔬菜外埠基地。常年蔬菜种植面积110万亩，其中2015年全县蔬菜收获面积112万亩，总产量420万吨，总产值达85亿元，被誉为“中国蔬菜之乡”。兰陵县也是冬暖式大棚发源地，自上世纪八十年代初，在向城镇苏圈开始试种植反季节温室棚黄瓜成功，经过30多年的蔬菜生产经营探索改革，兰陵县的蔬菜产业正走向规模化种植、标准化生产、产业化经营、品牌化营销的发展道路，成为拉动当地经济发展的支柱产业。', 'ylx_admin', '超级管理员', '10万', '9万', '张三、李四王五', '2016-10-20 00:00:00', '2017-10-20 00:00:00', '项目管理中心', '陈光标', '', 'all', 1, 'checker', '2016-10-21 00:00:00', 1, 2, '超级管理员', '2016-10-22 00:22:45', NULL, NULL), (32, 'PRJ_20161022020045', '高速公路气候监测系统', '气候监测', 'large', 'client', '高速公路自动气象监测系统专门为监测高速公路气象参数而研制。该系统实时监测高速公路的天气状况，可自动收集气象信息，自动进行处理并及时上传到监控中心，供交通管制系统参考，以便根据天气状况及时提出最佳监控案。  高速公路自动气象监测系统由传感器、气象数据采集系统和数据通讯装置三个主要部分组成。气象数据采集器由多路开关、频率测量、V/F变换、高速计数器、控制微机、数据存储器、串行接口、电源等组成。', NULL, '超级管理员', '500', '400', '张三李四王五', '2016-10-20 00:00:00', '2017-10-20 00:00:00', 'department', '陈东升', '', 'school_inner', 1, 'checker', '2016-10-21 00:00:00', 0, 0, '超级管理员', '2016-10-22 02:00:45', NULL, NULL), (33, 'PRJ_20161022023027', '物联网安防项目', '安防', 'medium', 'client', '这是一个安防项目', NULL, '超级管理员', '40万元', '38万元', 'lightmd.ddk.eet，懒人', '2016-10-20 00:00:00', '2017-10-20 00:00:00', 'department', '尔冬升', 'dldlld.zip', 'school_inner', 1, 'checker', '2016-10-21 00:00:00', 0, 0, '超级管理员', '2016-10-22 02:30:27', NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for project_member
-- ----------------------------
DROP TABLE IF EXISTS `project_member`;
CREATE TABLE `project_member`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `project_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `member_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `member_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `member_role` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `start_time` datetime(0) NULL DEFAULT NULL,
  `end_time` datetime(0) NULL DEFAULT NULL,
  `creator` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `modifier` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `modify_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 56 CHARACTER SET = utf8 COLLATE = utf8_general_ci;

-- ----------------------------
-- Records of project_member
-- ----------------------------
BEGIN;
INSERT INTO `project_member` VALUES (54, 'PRJ_20161022023027', 'zhangsan', '张三', 'member', '../../assets/module/img/security/user/avatar/avatar.jpg', NULL, NULL, NULL, NULL, NULL, NULL), (55, 'PRJ_20161022002245', 'zhangsan', '张三', 'member', '../../assets/module/img/security/user/avatar/avatar.jpg', NULL, NULL, NULL, NULL, NULL, NULL), (56, 'PRJ_20161022020045', 'zhangsan', '张三', 'member', '../../assets/module/img/security/user/avatar/avatar.jpg', NULL, NULL, NULL, NULL, NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for project_statistic
-- ----------------------------
DROP TABLE IF EXISTS `project_statistic`;
CREATE TABLE `project_statistic`  (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `colTime` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `colCount` int(10) NULL DEFAULT NULL,
  `colType` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `colTitle` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci;

-- ----------------------------
-- Table structure for project_tree
-- ----------------------------
DROP TABLE IF EXISTS `project_tree`;
CREATE TABLE `project_tree`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `MODULE_NAME` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `reason` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `CATEGORY_ID` int(20) NULL DEFAULT 0,
  `PARENT_CATEGORY_ID` int(20) NULL DEFAULT 0,
  `ACTIVE_STATUS` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'Y',
  `HREFLINK` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `FILE_ID` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `FILE_NAME` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `DETAILS_TAG` int(2) NULL DEFAULT 0,
  `FILE_PATH` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `CHAIN_NAME` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `PICTURE` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `WORKFLOW_TAG` int(1) UNSIGNED NULL DEFAULT 0,
  `module_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `parent_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `action` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `menu_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `menu_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `creator` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `index`(`FILE_ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 86 CHARACTER SET = utf8 COLLATE = utf8_general_ci;

-- ----------------------------
-- Records of project_tree
-- ----------------------------
BEGIN;
INSERT INTO `project_tree` VALUES (2, '0201 客户化设置', 'config', 1201, 0, 'Y', '', '1201', '客户化设置', 1, 'project/config', '项目管理--客户化设置', 'ylx1.png', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), (3, '0202 项目基本信息管理', 'file', 1202, 0, 'Y', '', '1202', '项目基本信息管理', 1, 'project/file', '项目管理--项目基本信息管理', 'ylx2.png', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), (5, '0203 资金调整管理', 'price_change', 1203, 0, 'Y', '', '1203', '资金调整管理', 1, 'project/price_change', '项目管理--项目资金调整管理', 'ylx3.png', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), (6, '0204 项目通知', 'notice', 1204, 0, 'Y', '', '1204', '项目通知', 1, 'project/notice', '项目管理--产品物料组成设计', 'ylx4.png', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), (7, '0205 数据报表', 'export', 1205, 0, 'Y', '', '1205', '数据报表', 1, 'project/export', '项目管理--标准数据报表', 'ylx5.png', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), (9, '020101 项目基本管理设置', 'file', 120101, 1201, 'Y', '', '120101', '项目基本管理设置', 1, 'project/config/file', '项目管理--客户化设置--项目基本管理设置', 'ylx6.png', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), (10, '020102 项目资金调整管理', 'price_change', 120102, 1201, 'Y', '', '120102', '项目资金调整管理', 1, 'project/config/price_change', '项目管理--客户化设置--项目资金调整管理', 'ylx7.png', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), (11, '020103 关键字设置', 'key', 120103, 1201, 'Y', '', '120103', '关键字设置', 1, 'project/config/key', '项目管理--客户化设置--关键字设置', 'ylx8.png', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), (12, '02010101 项目分类设置', '', 12010101, 120101, 'Y', 'fileKind.jsp', '12010101', '项目分类设置', 0, 'project/config/file/', '项目管理--客户化设置--项目基本管理设置--项目分类设置', 'ylx9.png', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), (16, '02010105 项目级别设置', '', 12010105, 120101, 'Y', 'strategyClass.jsp', '12010105', '项目级别设置', 0, 'project/config/file/', '项目管理--客户化设置--项目基本管理设置--项目级别设置', 'ylx11.png', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), (18, '02010201 项目预警设置', '', 12010201, 120102, 'Y', 'priceAlarm.jsp', '12010201', '项目预警设置', 0, 'project/config/price_change/', '项目管理--客户化设置--项目资金调整管理--项目预警设置', 'ylx13.png', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), (19, '02010301 查询设置', '', 12010301, 120103, 'Y', 'key.jsp', '12010301', '查询设置', 0, 'project/config/key/', '项目管理--客户化设置--关键字设置--查询设置', 'ylx14.png', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), (22, '020201 项目登记', '', 120201, 1202, 'Y', 'project_list.jsp', '120201', '项目档案', 0, 'project/file/', '项目管理--基本管理--项目登记', 'ylx16.png', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), (23, '020202 项目审核', '', 120202, 1202, 'Y', 'check_list.jsp', '120202', '项目审核', 0, 'project/file/', '项目管理--基本管理--项目审核', 'ylx17.png', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), (24, '020203 项目查询', '', 120203, 1202, 'Y', 'query_list.jsp', '120203', '项目查询', 0, 'project/file/', '项目管理--基本管理--项目查询', 'ylx18.png', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), (25, '020204 项目变更', '', 120204, 1202, 'Y', 'change_list.jsp', '120204', '项目变更', 0, 'project/file/', '项目管理--基本管理--项目变更', 'ylx19.png', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), (30, '020301 资金调整登记', '', 120301, 1203, 'Y', 'register_list.jsp', '120301', '资金调整登记', 0, 'project/price_change/', '项目管理--资金调整管理--资金调整登记', 'ylx20.png', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), (31, '020302 资金调整登记审核', '', 120302, 1203, 'Y', 'check_list.jsp', '120302', '资金调整登记审核', 0, 'project/price_change/', '项目管理--资金调整管理--资金调整登记审核', 'ylx21.png', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), (32, '020303 资金调整查询', '', 120303, 1203, 'Y', 'query_list.jsp', '120303', '资金调整查询', 0, 'project/price_change/', '项目管理--资金调整管理--资金调整查询', 'ylx22.png', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), (33, '020401 起草通知', '', 120401, 1204, 'Y', 'register.jsp', '120401', '起草通知', 0, 'project/notice/', '产品设计--产品物料组成设计--制定物料组成设计单', 'ylx23.png', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), (34, '020402 通知审核', '', 120402, 1204, 'Y', 'check_list.jsp', '120402', '通知审核', 0, 'project/notice/', '产品设计--产品物料组成设计--物料组成设计单审核', 'ylx24.png', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), (35, '020403 通知查询', '', 120403, 1204, 'Y', 'query_list.jsp', '120403', '通知查询', 0, 'project/notice/', '产品设计--产品物料组成设计--物料组成设计单查询', 'ylx25.png', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), (36, '020404 通知变更', '', 120404, 1204, 'Y', 'change_list.jsp', '120404', '通知变更', 0, 'project/notice/', '产品设计--产品物料组成设计--物料组成设计单变更', 'ylx26.png', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), (37, '020501 EXCEL标准数据报表', '', 120501, 1205, 'Y', 'excel.jsp', '120501', 'EXCEL标准数据报表', 0, 'project/export/', '产品设计--标准数据报表--EXCEL标准数据报表', 'ylx27.png', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), (38, '020502 pdf标准数据报表', '', 120502, 1205, 'Y', 'pdf.jsp', '120502', 'pdf标准数据报表', 0, 'project/export/', '产品设计--标准数据报表--pdf标准数据报表', 'ylx28.png', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), (39, '020503 xml标准数据报表', '', 120503, 1205, 'Y', 'xml.jsp', '120503', 'xml标准数据报表', 0, 'project/export/', '产品设计--标准数据报表--xml标准数据报表', 'ylx29.png', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), (40, '020104 审核工作流设置', 'workflow', 120104, 1201, 'Y', '', '120104', '审核工作流设置', 1, 'project/config/workflow', '项目管理--客户化设置--审核工作流设置', 'ylx30.png', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), (41, '02010401 项目审核工作流设置', '', 12010401, 120104, 'Y', 'file.jsp', '12010401', '项目信息审核工作流设置', 0, 'project/config/workflow/', '项目管理--客户化设置--审核工作流设置--项目审核工作流设置', 'ylx31.png', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), (43, '02010402 项目资金调整审核工作流设置', '', 12010402, 120104, 'Y', 'price.jsp', '12010402', '项目资金调整审核设置', 0, 'project/config/workflow/', '项目管理--客户化设置--审核工作流设置--项目资金调整审核工作流设置', 'ylx32.png', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), (44, '02010403 项目文档审核工作流设置', '', 12010403, 120104, 'Y', 'document.jsp', '12010403', '项目文档审核工作流设置', 0, 'project/config/workflow/', '项目管理--客户化设置--项目文档审核工作流设置', 'ylx33.png', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), (45, '020205 进度统计', '', 120205, 1202, 'Y', 'tongji.jsp', '120205', '进度统计', 0, 'project/file/', '项目管理--项目管理--进度统计', 'ylx34.png', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), (46, '020206 项目登记', '', 120206, 1202, 'Y', 'project_add.jsp', '120206', '项目登记', 0, 'project/file/', '项目--项目管理--项目进度', 'ylx35.png', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), (47, '0206 项目文档管理', 'file1', 1206, 0, 'Y', '', '1206', '项目文档管理', 1, 'project/document', '项目文档管理', 'ylx36.png', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), (48, '020601  项目文档登记', '', 120601, 1206, 'Y', 'document_add.jsp', '120601', '项目文档登记', 0, 'project/document/', '项目文档管理-项目文档登记', 'ylx37.png', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), (49, '020602 项目文档登记审核', '', 120602, 1206, 'Y', 'check_list.jsp', '120602', '项目文档登记审核', 0, 'project/document/', '项目文档管理-项目文档登记审核', 'ylx38.png', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), (50, '020603 项目文档查询', '', 120603, 1206, 'Y', 'document_query.jsp', '120603', '项目文档查询', 0, 'project/document/', '项目文档管理-项目文档查询', 'ylx39.png', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), (51, '020605 项目文档变更', '', 120605, 1206, 'Y', 'change_list.jsp', '120605', '项目文档变更', 0, 'project/document/', '项目文档管理-项目文档变更', 'ylx40.png', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), (52, '0207 会议管理', 'meeting', 1207, 0, 'Y', '', '1207', '会议管理', 1, 'project/meeting', '会议管理', 'ylx41.png', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), (53, '020701 议题准备', '', 120701, 1207, 'Y', 'subject_register.jsp', '120701', '议题准备', 0, 'project/meeting/', '会议管理-议题准备', 'ylx42.png', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), (54, '020702 开会通知', '', 120702, 1207, 'Y', 'inform_list.jsp', '120702', '开会通知', 0, 'project/meeting/', '会议管理-开会通知', 'ylx43.png', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), (55, '020703 会议纪要', '', 120703, 1207, 'Y', 'recorder_list.jsp', '120703', '会议纪要', 0, 'project/meeting/', '会议管理-会议纪要', 'ylx44.png', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), (56, '020704 会议纪要处理', '', 120704, 1207, 'Y', 'dealwith_list.jsp', '120704', '会议纪要处理', 0, 'project/meeting/', '会议管理-会议纪要处理', 'ylx45.png', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), (57, '020705 会议查询', '', 120705, 1207, 'Y', 'query_list.jsp', '120705', '会议查询', 0, 'project/meeting/', '会议管理-会议查询', 'ylx46.png', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), (58, '0208 待办事项管理', '', 1208, 0, 'Y', '', '1208', '待办事项管理', 1, 'project/todo', '待办事项管理', 'ylx46.png', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), (59, '020801 待办事项添加', '', 120801, 1208, 'Y', 'todo_add.jsp', '120801', '待办事项添加', 0, 'project/todo/', '待办事项管理--待办事项添加', 'ylx46.png', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), (60, '020803 待办事项查询', '', 120803, 1208, 'Y', 'todo_query.jsp', '120803', '待办事项查询', 0, 'project/todo/', '待办事项管理--待办事项查询', 'ylx46.png', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), (61, '020804 待办事项更改', '', 120804, 1208, 'Y', 'todo_list.jsp', '120804', '待办事项管理', 0, 'project/todo/', '待办事项管理--待办事项更改', 'ylx46.png', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), (62, '020806 统计打印', '', 120806, 1208, 'Y', 'todo_statistic.jsp', '120806', '待办事项统计', 0, 'project/todo/', '待办事项管理--待办事项统计打印', 'ylx46.png', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), (64, '0209 统计打印', '', 1209, 0, 'Y', '', '1209', '统计打印', 1, 'project/statistic', '统计打印', 'ylx46.png', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), (65, '020901 待办事项统计', '', 120901, 1209, 'Y', 'todo_statistic.jsp', '120901', '统计打印', 0, 'project/statistic/', '统计打印--待办事项统计', 'ylx46.png', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), (66, '0210 通知事项管理', '', 1210, 0, 'Y', '', '1210', '通知事项管理', 1, 'project/notice', '通知事项管理', 'ylx46.png', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), (67, '021001 通知事项添加', '', 121001, 1210, 'Y', 'register.jsp', '121001', '通知事项添加', 0, 'project/notice/', '通知事项管理--通知事项添加', 'ylx46.png', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), (68, '021002 通知事项查询', '', 121002, 1210, 'Y', 'query_list.jsp', '121002', '通知事项查询', 0, 'project/notice/', '通知事项管理--通知事项查询', 'ylx46.png', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), (69, '021003 通知事项更改', '', 121003, 1210, 'Y', 'change_list.jsp', '121003', '通知事项更改', 0, 'project/notice/', '通知事项管理--通知事项更改', 'ylx46.png', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), (70, '021004 通知事项统计', '', 121004, 1210, 'Y', 'statistic.jsp', '121004', '通知事项统计', 0, 'project/notice/', '通知事项管理--统计打印', 'ylx46.png', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), (72, '02010404 项目待办事项审核工作流设置', '', 12010404, 120104, 'Y', 'todo.jsp', '12010404', '项目待办事项审核工作流设置', 0, 'project/config/workflow/', '项目管理--客户化设置--项目待办事项审核工作流设置', 'ylx33.png', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), (74, '021101 复杂事项添加', '', 121101, 1211, 'Y', 'register_more.jsp', '121101', '复杂事项添加', 0, 'project/todo_more/', '待办事项管理--复杂事项添加', 'ylx46.png', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), (75, '021102 复杂事项审核', '', 121102, 1211, 'Y', 'check_list_more.jsp', '121102', '复杂事项审核', 0, 'project/todo_more/', '待办事项管理--复杂事项查询', 'ylx46.png', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), (76, '021103 复杂事项查询', '', 121103, 1211, 'Y', 'query_list_more.jsp', '121103', '复杂事项查询', 0, 'project/todo_more/', '待办事项管理--复杂事项查询', 'ylx46.png', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), (77, '021104 复杂事项更改', '', 121104, 1211, 'Y', 'change_list_more.jsp', '121104', '复杂事项更改', 0, 'project/todo_more/', '待办事项管理--复杂事项更改', 'ylx46.png', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), (79, '0211 复杂事项管理', '', 1211, 0, 'Y', '', '1211', '复杂事项管理', 1, 'project/todo_more', '待办事项管理', 'ylx46.png', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), (80, '020802 待办事项审核', '', 120802, 1208, 'Y', 'record_list.jsp', '120802', '待办事项列表', 0, 'project/todo/', '待办事项管理--待办事项审核', 'ylx46.png', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), (84, '1212', '', 1212, 0, 'Y', '', '1212', '项目日志', 1, 'project/diary', '项目日志', 'ylx46.png', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), (85, '121201', '', 121201, 1212, 'Y', 'diary_add.jsp', '121201', '添加日志', 0, 'project/diary/', '添加日志', 'ylx46.png', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), (86, '121202', '', 121202, 1212, 'Y', 'diary_list.jsp', '121202', '日志列表', 0, 'project/diary/', '日志列表', 'ylx46.png', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for project_view
-- ----------------------------
DROP TABLE IF EXISTS `project_view`;
CREATE TABLE `project_view`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `file_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `role_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 312 CHARACTER SET = utf8 COLLATE = utf8_general_ci;

-- ----------------------------
-- Records of project_view
-- ----------------------------
BEGIN;
INSERT INTO `project_view` VALUES (203, '210101', 'student'), (204, '210201', 'student'), (205, '210202', 'student'), (206, '12010101', 'manager'), (207, '12010105', 'manager'), (208, '12010201', 'manager'), (209, '12010301', 'manager'), (210, '12010401', 'manager'), (211, '12010402', 'manager'), (212, '12010403', 'manager'), (213, '12010404', 'manager'), (214, '120101', 'manager'), (215, '120102', 'manager'), (216, '120103', 'manager'), (217, '120104', 'manager'), (218, '120201', 'manager'), (219, '120202', 'manager'), (220, '120203', 'manager'), (221, '120204', 'manager'), (222, '120205', 'manager'), (223, '120206', 'manager'), (224, '120301', 'manager'), (225, '120302', 'manager'), (226, '120303', 'manager'), (227, '120401', 'manager'), (228, '120402', 'manager'), (229, '120403', 'manager'), (230, '120404', 'manager'), (231, '120501', 'manager'), (232, '120502', 'manager'), (233, '120503', 'manager'), (234, '120601', 'manager'), (235, '120602', 'manager'), (236, '120603', 'manager'), (237, '120605', 'manager'), (238, '120701', 'manager'), (239, '120702', 'manager'), (240, '120703', 'manager'), (241, '120704', 'manager'), (242, '120705', 'manager'), (243, '120801', 'manager'), (244, '120802', 'manager'), (245, '120803', 'manager'), (246, '120804', 'manager'), (247, '120806', 'manager'), (248, '120807', 'manager'), (249, '120808', 'manager'), (250, '120809', 'manager'), (251, '120901', 'manager'), (252, '121001', 'manager'), (253, '121002', 'manager'), (254, '121003', 'manager'), (255, '121004', 'manager'), (256, '121101', 'manager'), (257, '121102', 'manager'), (258, '121103', 'manager'), (259, '121104', 'manager'), (260, '1201', 'manager'), (261, '1202', 'manager'), (262, '1203', 'manager'), (263, '1204', 'manager'), (264, '1205', 'manager'), (265, '1206', 'manager'), (266, '1207', 'manager'), (267, '1208', 'manager'), (268, '1209', 'manager'), (269, '1210', 'manager'), (270, '1211', 'manager'), (292, '1202', 'normal'), (293, '120201', 'normal'), (294, '120202', 'normal'), (295, '120203', 'normal'), (296, '120204', 'normal'), (297, '120205', 'normal'), (298, '120206', 'normal'), (299, '1206', 'normal'), (300, '120601', 'normal'), (301, '120602', 'normal'), (302, '120603', 'normal'), (303, '120605', 'normal'), (304, '1208', 'normal'), (305, '120801', 'normal'), (306, '120802', 'normal'), (307, '120803', 'normal'), (308, '120804', 'normal'), (309, '120806', 'normal'), (310, '120807', 'normal'), (311, '120808', 'normal'), (312, '120809', 'normal');
COMMIT;

-- ----------------------------
-- Table structure for project_vote
-- ----------------------------
DROP TABLE IF EXISTS `project_vote`;
CREATE TABLE `project_vote`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `parent_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `user_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `type` int(10) NULL DEFAULT NULL,
  `limit_time` datetime(0) NULL DEFAULT NULL,
  `end_tag` int(10) NULL DEFAULT 0,
  `begin_time` datetime(0) NULL DEFAULT NULL,
  `end_time` datetime(0) NULL DEFAULT NULL,
  `executor` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `status` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `attachment_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `attachment_size` int(10) NULL DEFAULT 0,
  `priority` int(10) NULL DEFAULT NULL,
  `check_tag` int(10) NULL DEFAULT 0,
  `checker` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `check_time` datetime(0) NULL DEFAULT NULL,
  `excel_tag` int(10) NULL DEFAULT 0,
  `gar_tag` int(10) NULL DEFAULT 0,
  `used_tag` int(10) NULL DEFAULT 1,
  `creator` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `create_time` datetime(0) NULL DEFAULT NULL,
  `link` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8 COLLATE = utf8_general_ci;

-- ----------------------------
-- Records of project_vote
-- ----------------------------
BEGIN;
INSERT INTO `project_vote` VALUES (3, 'kfjwwxpt', 'zhangsan', '关于作业完成情况的查询', '调查学生作业完成情况', NULL, '2017-04-21 14:50:37', 0, '2016-05-24 00:00:00', '2016-05-25 00:00:00', '张三', '准备执行', NULL, 0, NULL, 0, NULL, NULL, 0, 0, 1, '', '2017-04-21 14:50:37', 'https://www.wjx.cn'), (4, 'kfjwwxpt', 'zhangsan', '食堂饭菜满意度调查', '调查学生对学校食堂饭菜的满意度调查', NULL, '2017-04-23 14:50:37', 0, '2015-11-29 00:00:00', '2015-11-29 00:00:00', '张三', '准备执行', NULL, 0, NULL, 0, NULL, NULL, 0, 0, 1, '', '2017-04-23 14:50:37', 'https://www.wjx.cn'), (10, 'PRJ_20161210020741', 'zhangsan', '下周三是否有空', '调查学生周三是否有空', NULL, '2017-04-29 14:50:37', 0, NULL, '2019-04-18 23:58:17', '', '', NULL, 0, NULL, 0, NULL, NULL, 0, 0, 1, '辛卫', '2017-04-29 14:50:37', 'https://www.wjx.cn'), (11, 'PRJ_20161207052455', 'zhangsan', '意见收集', '收集学生对课堂教学的意见', NULL, '2017-05-01 14:50:37', 0, NULL, '2019-04-02 23:58:24', '', '', NULL, 0, NULL, 0, NULL, NULL, 0, 0, 1, '辛卫', '2017-05-01 14:50:37', 'https://www.wjx.cn'), (13, 'PRJ_20161022023027', 'zhangsan', '教学满意度调查', '调查学生对老师教学情况', NULL, '2017-05-18 14:50:59', 0, NULL, '2019-04-19 23:58:27', '', '', NULL, 0, NULL, 0, NULL, NULL, 0, 0, 1, '张三', '2017-05-11 14:34:27', 'https://www.wjx.cn'), (15, 'PRJ_20161022023027', 'zhangsan', '考试考试', '考试快来看看看看看', NULL, '2017-05-11 14:52:06', 0, NULL, '2019-05-01 23:58:31', '', '', NULL, 0, NULL, 0, NULL, NULL, 0, 0, 1, '张三', '2017-05-11 14:52:06', 'https://www.wjx.cn'), (19, 'PRJ_20161022023027', 'zhangsan', 'dihfgdl', 'shgxlk', NULL, '2019-04-29 23:59:59', 0, NULL, '2019-04-29 23:59:59', '', '', NULL, 0, NULL, 0, NULL, NULL, 0, 0, 1, '张三', '2019-04-29 22:59:10', 'www.baidu.com');
COMMIT;

-- ----------------------------
-- Table structure for public_log
-- ----------------------------
DROP TABLE IF EXISTS `public_log`;
CREATE TABLE `public_log`  (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `colTime` datetime(0) NULL DEFAULT NULL,
  `colType` int(10) NULL DEFAULT NULL,
  `colContent` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `colOperation` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `colUserId` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `colModule` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 50 CHARACTER SET = utf8 COLLATE = utf8_general_ci;

-- ----------------------------
-- Records of public_log
-- ----------------------------
BEGIN;
INSERT INTO `public_log` VALUES (1, '2017-05-11 14:34:27', 0, '用户 张三 于 2017-05-11 14:34:27 添加了 [project][todo] 记录', '添加记录', 'zhangsan', 'project'), (2, '2017-05-11 14:48:57', 0, '用户 张三 于 2017-05-11 14:48:56 添加了 [project][todo] 记录', '添加记录', 'zhangsan', 'project'), (3, '2017-05-11 14:52:07', 0, '用户 张三 于 2017-05-11 14:52:06 添加了 [project][todo] 记录', '添加记录', 'zhangsan', 'project'), (4, '2017-05-11 14:52:17', 0, '用户 张三 于 2017-05-11 14:52:17 删除了 [project][todo] 记录', '删除记录', 'zhangsan', 'project'), (5, '2019-04-29 22:01:31', 0, '用户 张三 于 2019-04-29 22:01:30 修改了 [project][todo] 记录', '修改记录', 'zhangsan', 'project'), (6, '2019-04-29 22:21:39', 0, '用户 张三 于 2019-04-29 22:21:39 添加了 [project][core] 记录', '添加记录', 'zhangsan', 'project'), (7, '2019-04-29 22:30:02', 0, '用户 张三 于 2019-04-29 22:30:02 添加了 [project][core] 记录', '添加记录', 'zhangsan', 'project'), (8, '2019-04-29 22:38:03', 0, '用户 张三 于 2019-04-29 22:38:03 添加了 [project][core] 记录', '添加记录', 'zhangsan', 'project'), (9, '2019-04-29 22:38:29', 0, '用户 张三 于 2019-04-29 22:38:29 删除了 [project][core] 记录', '删除记录', 'zhangsan', 'project'), (10, '2019-04-29 22:53:14', 0, '用户 张三 于 2019-04-29 22:53:14 删除了 [project][core] 记录', '删除记录', 'zhangsan', 'project'), (11, '2019-04-29 22:53:20', 0, '用户 张三 于 2019-04-29 22:53:20 删除了 [project][core] 记录', '删除记录', 'zhangsan', 'project'), (12, '2019-04-29 22:59:10', 0, '用户 张三 于 2019-04-29 22:59:10 添加了 [project][core] 记录', '添加记录', 'zhangsan', 'project'), (13, '2019-05-07 15:45:44', 0, '用户 张三 于 2019-05-07 15:45:44 添加了 [investigation][core] 记录', '添加记录', 'zhangsan', 'investigation'), (14, '2019-05-07 18:35:54', 0, '用户 张三 于 2019-05-07 18:35:54 添加了 [investigation][core] 记录', '添加记录', 'zhangsan', 'investigation'), (15, '2019-05-07 18:38:11', 0, '用户 张三 于 2019-05-07 18:38:11 添加了 [investigation][core] 记录', '添加记录', 'zhangsan', 'investigation'), (16, '2019-05-07 18:51:32', 0, '用户 张三 于 2019-05-07 18:51:32 添加了 [user][info] 记录', '添加记录', 'zhangsan', 'user'), (17, '2019-05-07 19:00:40', 0, '用户 张三 于 2019-05-07 19:00:40 添加了 [vote][file] 记录', '添加记录', 'zhangsan', 'vote'), (18, '2019-05-07 19:01:14', 0, '用户 张三 于 2019-05-07 19:01:14 删除了 [user][info] 记录', '删除记录', 'zhangsan', 'user'), (19, '2019-05-07 19:15:04', 0, '用户 张三 于 2019-05-07 19:15:04 删除了 [user][info] 记录', '删除记录', 'zhangsan', 'user'), (20, '2019-05-07 19:15:04', 0, '用户 张三 于 2019-05-07 19:15:04 删除了 [user][info] 记录', '删除记录', 'zhangsan', 'user'), (21, '2019-05-07 19:15:25', 0, '用户 张三 于 2019-05-07 19:15:25 删除了 [investigation][core] 记录', '删除记录', 'zhangsan', 'investigation'), (22, '2019-05-07 19:25:44', 0, '用户 张三 于 2019-05-07 19:25:44 删除了 [user][info] 记录', '删除记录', 'zhangsan', 'user'), (23, '2019-05-07 19:25:44', 0, '用户 张三 于 2019-05-07 19:25:44 删除了 [user][info] 记录', '删除记录', 'zhangsan', 'user'), (24, '2019-05-14 10:53:28', 0, '用户 张三 于 2019-05-14 10:53:28 删除了 [homework][core] 记录', '删除记录', 'zhangsan', 'homework'), (25, '2019-05-14 14:59:33', 0, '用户 张三 于 2019-05-14 14:59:33 删除了 [homework][core] 记录', '删除记录', 'zhangsan', 'homework'), (26, '2019-05-14 15:17:35', 0, '用户 张三 于 2019-05-14 15:17:35 删除了 [homework][core] 记录', '删除记录', 'zhangsan', 'homework'), (27, '2019-05-14 15:18:07', 0, '用户 张三 于 2019-05-14 15:18:07 删除了 [homework][core] 记录', '删除记录', 'zhangsan', 'homework'), (28, '2019-05-14 15:29:28', 0, '用户 张三 于 2019-05-14 15:29:28 删除了 [homework][core] 记录', '删除记录', 'zhangsan', 'homework'), (29, '2019-05-14 15:57:20', 0, '用户 张三 于 2019-05-14 15:57:20 删除了 [homework][core] 记录', '删除记录', 'zhangsan', 'homework'), (30, '2019-05-14 16:00:24', 0, '用户 张三 于 2019-05-14 16:00:24 删除了 [homework][core] 记录', '删除记录', 'zhangsan', 'homework'), (31, '2019-05-14 16:02:04', 0, '用户 张三 于 2019-05-14 16:02:04 删除了 [homework][core] 记录', '删除记录', 'zhangsan', 'homework'), (32, '2019-05-14 16:27:19', 0, '用户 张三 于 2019-05-14 16:27:19 删除了 [homework][core] 记录', '删除记录', 'zhangsan', 'homework'), (33, '2019-05-14 16:58:10', 0, '用户 张三 于 2019-05-14 16:58:10 删除了 [homework][core] 记录', '删除记录', 'zhangsan', 'homework'), (34, '2019-05-14 17:00:14', 0, '用户 张三 于 2019-05-14 17:00:14 删除了 [homework][core] 记录', '删除记录', 'zhangsan', 'homework'), (35, '2019-05-14 17:07:08', 0, '用户 张三 于 2019-05-14 17:07:08 删除了 [homework][core] 记录', '删除记录', 'zhangsan', 'homework'), (36, '2019-05-14 17:09:24', 0, '用户 张三 于 2019-05-14 17:09:24 删除了 [homework][core] 记录', '删除记录', 'zhangsan', 'homework'), (37, '2019-05-14 17:11:43', 0, '用户 张三 于 2019-05-14 17:11:43 删除了 [homework][core] 记录', '删除记录', 'zhangsan', 'homework'), (38, '2019-05-14 17:13:21', 0, '用户 张三 于 2019-05-14 17:13:21 删除了 [homework][core] 记录', '删除记录', 'zhangsan', 'homework'), (39, '2019-05-14 17:14:42', 0, '用户 张三 于 2019-05-14 17:14:42 删除了 [homework][core] 记录', '删除记录', 'zhangsan', 'homework'), (40, '2019-05-14 17:22:50', 0, '用户 张三 于 2019-05-14 17:22:50 删除了 [homework][core] 记录', '删除记录', 'zhangsan', 'homework'), (41, '2019-05-14 17:25:44', 0, '用户 张三 于 2019-05-14 17:25:44 删除了 [homework][core] 记录', '删除记录', 'zhangsan', 'homework'), (42, '2019-05-24 23:19:02', 0, '用户 张三 于 2019-05-24 23:19:02 删除了 [homework][core] 记录', '删除记录', 'zhangsan', 'homework'), (43, '2019-05-24 23:19:14', 0, '用户 张三 于 2019-05-24 23:19:14 删除了 [homework][core] 记录', '删除记录', 'zhangsan', 'homework'), (44, '2019-05-24 23:19:18', 0, '用户 张三 于 2019-05-24 23:19:18 删除了 [homework][core] 记录', '删除记录', 'zhangsan', 'homework'), (45, '2019-05-24 23:41:13', 0, '用户 张三 于 2019-05-24 23:41:13 删除了 [homework][core] 记录', '删除记录', 'zhangsan', 'homework'), (46, '2019-05-24 23:44:00', 0, '用户 张三 于 2019-05-24 23:43:59 删除了 [homework][core] 记录', '删除记录', 'zhangsan', 'homework'), (47, '2019-05-27 13:17:05', 0, '用户 张三 于 2019-05-27 13:17:05 添加了 [user][info] 记录', '添加记录', 'zhangsan', 'user'), (48, '2019-05-28 00:38:31', 0, '用户 root 于 2019-05-28 00:38:31 添加了 [user][info] 记录', '添加记录', 'root', 'user'), (49, '2019-05-28 01:33:08', 0, '用户 root 于 2019-05-28 01:33:08 删除了 [user][info] 记录', '删除记录', 'root', 'user'), (50, '2019-05-28 01:33:08', 0, '用户 root 于 2019-05-28 01:33:08 删除了 [user][info] 记录', '删除记录', 'root', 'user'), (51, '2019-06-02 22:53:02', 0, '用户 root 于 2019-06-02 22:53:02 修改了 [user][info] 记录', '修改记录', '27', 'user');
COMMIT;

-- ----------------------------
-- Table structure for security_users
-- ----------------------------
DROP TABLE IF EXISTS `security_users`;
CREATE TABLE `security_users`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `HUMAN_MAJOR_FIRST_KIND_ID` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0',
  `HUMAN_MAJOR_FIRST_KIND_NAME` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `HUMAN_MAJOR_SECOND_KIND_ID` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0',
  `HUMAN_MAJOR_SECOND_KIND_NAME` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `HUMAN_ID` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `HUMAN_NAME` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `nick_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `NAME` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `PASSWD` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `TAGC` int(11) NULL DEFAULT 0,
  `TAG` int(1) NULL DEFAULT 0,
  `FORBID_TIME` datetime(0) NULL DEFAULT NULL,
  `FORBID_TAG` int(1) NULL DEFAULT 0,
  `ORDER_DISCOUNT` double(15, 2) NULL DEFAULT 0.00,
  `RETAIL_DISCOUNT` double(15, 2) NULL DEFAULT 0.00,
  `CREDIT_DISCOUNT` double(15, 2) NULL DEFAULT 0.00,
  `type` int(11) NULL DEFAULT 0,
  `language` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `question` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `answer` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `CHAIN_ID` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `CHAIN_NAME` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `group_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `mobile` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `wechat_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `role` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `authority` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `register` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `register_time` datetime(0) NULL DEFAULT NULL,
  `fans_count` int(10) NULL DEFAULT NULL,
  `follow_count` int(10) NULL DEFAULT NULL,
  `avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `qq` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 85 CHARACTER SET = utf8 COLLATE = utf8_general_ci;

-- ----------------------------
-- Records of security_users
-- ----------------------------
BEGIN;
INSERT INTO `security_users` VALUES (85, '0', '', '0', '', '302983098409', '张三', 'zhangsan', '', '', 0, 0, NULL, 0, 0.00, 0.00, 0.00, 0, '', '', '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'normal', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for survey_admins
-- ----------------------------
DROP TABLE IF EXISTS `survey_admins`;
CREATE TABLE `survey_admins`  (
  `a_id` int(11) NOT NULL AUTO_INCREMENT,
  `a_user` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `a_pass` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `a_email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `a_phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`a_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci;

-- ----------------------------
-- Records of survey_admins
-- ----------------------------
BEGIN;
INSERT INTO `survey_admins` VALUES (1, 'root', '1234', 'xxxxx', '110');
COMMIT;

-- ----------------------------
-- Table structure for survey_all
-- ----------------------------
DROP TABLE IF EXISTS `survey_all`;
CREATE TABLE `survey_all`  (
  `s_id` int(255) NOT NULL AUTO_INCREMENT,
  `s_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `s_desc` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `s_author` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `s_img` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `s_createdate` datetime(0) NULL DEFAULT NULL,
  `s_password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `s_isopen` tinyint(10) NULL DEFAULT NULL,
  `s_expiredate` datetime(0) NULL DEFAULT NULL,
  `s_isaudited` tinyint(10) NULL DEFAULT NULL,
  `s_usehits` int(255) NULL DEFAULT NULL,
  `s_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`s_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci;

-- ----------------------------
-- Records of survey_all
-- ----------------------------
BEGIN;
INSERT INTO `survey_all` VALUES (1, '作业调查', 'xxxxxxxxxxxx', 'xx', NULL, '2019-05-16 00:00:00', NULL, 1, '2019-06-15 00:00:00', 1, 2, 'investigation'), (3, '哈哈哈哈哈哈哈哈哈哈', '密密麻麻', 'zpj', NULL, '2019-05-18 00:00:00', NULL, 1, '2019-06-17 00:00:00', 0, 3, 'investigation'), (4, '问题1', '问题一描述', 'zpj', NULL, '2019-05-19 00:00:00', NULL, 1, '2019-06-18 00:00:00', 1, 0, 'investigation'), (8, '是的日光和地方', 'hhhhhhhhhhhhh', 'zpj', NULL, '2019-05-22 00:00:00', NULL, 1, '2019-05-31 00:00:00', 1, 0, 'vote'), (10, 'dghf', 'srdfg', '生日歌s', NULL, '2019-05-26 00:00:00', NULL, 1, '2019-06-25 00:00:00', 0, 0, 'investigation');
COMMIT;

-- ----------------------------
-- Table structure for survey_answersheet
-- ----------------------------
DROP TABLE IF EXISTS `survey_answersheet`;
CREATE TABLE `survey_answersheet`  (
  `as_id` int(11) NOT NULL AUTO_INCREMENT,
  `s_id` int(11) NULL DEFAULT NULL,
  `as_result` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `as_postdate` datetime(0) NULL DEFAULT NULL,
  `as_userip` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`as_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci;

-- ----------------------------
-- Records of survey_answersheet
-- ----------------------------
BEGIN;
INSERT INTO `survey_answersheet` VALUES (1, 1, '3:as=0;', '2019-05-16 00:00:00', '0:0:0:0:0:0:0:1'), (2, 1, '3:as=3;', '2019-05-16 00:00:00', '0:0:0:0:0:0:0:1'), (3, 3, 'dlchlk4', '2019-05-19 00:00:00', '0:0:0:0:0:0:0:1'), (4, 3, '你的，cblrd.fhkgnj', '2019-05-19 00:00:00', '0:0:0:0:0:0:0:1'), (5, 3, '人生如果对方承诺将', '2019-05-19 00:00:00', '0:0:0:0:0:0:0:1');
COMMIT;

-- ----------------------------
-- Table structure for survey_config
-- ----------------------------
DROP TABLE IF EXISTS `survey_config`;
CREATE TABLE `survey_config`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `c_siteName` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `c_siteUrl` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `c_isOpen` tinyint(10) NULL DEFAULT NULL,
  `c_closeWord` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `copyright` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci;

-- ----------------------------
-- Records of survey_config
-- ----------------------------
BEGIN;
INSERT INTO `survey_config` VALUES (1, 'xxxxx', 'www.baidu.com', 1, 'closeword', 'copyright');
COMMIT;

-- ----------------------------
-- Table structure for survey_link
-- ----------------------------
DROP TABLE IF EXISTS `survey_link`;
CREATE TABLE `survey_link`  (
  `l_id` int(11) NOT NULL AUTO_INCREMENT,
  `l_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `l_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `l_img` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `l_info` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `l_islock` tinyint(10) NULL DEFAULT NULL,
  `l_addtime` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`l_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci;

-- ----------------------------
-- Table structure for survey_question
-- ----------------------------
DROP TABLE IF EXISTS `survey_question`;
CREATE TABLE `survey_question`  (
  `q_id` int(11) NOT NULL AUTO_INCREMENT,
  `s_id` int(11) NULL DEFAULT NULL,
  `q_type` int(11) NULL DEFAULT NULL,
  `q_head` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `q_body` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `q_result` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `q_img` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `q_jdtz` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `q_order` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`q_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci;

-- ----------------------------
-- Records of survey_question
-- ----------------------------
BEGIN;
INSERT INTO `survey_question` VALUES (3, 1, 1, 'xxxx问题', '2222222222&$$&111&$$&2333333&$$&5555', '1,0,0,1', NULL, 'null&null&null&null', 0), (4, 3, 2, '问题', '233333&$$&6778888&$$&ertuyy&$$&80890', '1,1,1,2', NULL, 'null&null&null&null', 0), (5, 3, 5, '简答题题目', NULL, '4', NULL, '', 0), (11, 4, 1, '这是一个测试问题', '答案1&$$&答案2', '0,0', NULL, 'null&null', 0), (12, 4, 2, '这是一个多选题', '1111&$$&2222&$$&33333&$$&4444444&$$&555555555', '0,0,0,0,0', NULL, 'null&null&null&null&null', 0), (13, 4, 5, '这是一个简答题', NULL, NULL, NULL, '', 0), (26, 8, 1, '关于hhhhhhhh的投票', '是&$$&是', '0,0', NULL, 'null&null', 0);
COMMIT;

-- ----------------------------
-- Table structure for survey_tree
-- ----------------------------
DROP TABLE IF EXISTS `survey_tree`;
CREATE TABLE `survey_tree`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `MODULE_NAME` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `reason` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `CATEGORY_ID` int(20) NULL DEFAULT 0,
  `PARENT_CATEGORY_ID` int(20) NULL DEFAULT 0,
  `ACTIVE_STATUS` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'Y',
  `HREFLINK` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `FILE_ID` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `FILE_NAME` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `DETAILS_TAG` int(2) NULL DEFAULT 0,
  `FILE_PATH` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `CHAIN_NAME` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `PICTURE` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `WORKFLOW_TAG` int(1) UNSIGNED NULL DEFAULT 0,
  `module_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `parent_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `action` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `menu_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `menu_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `creator` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `index`(`FILE_ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 86 CHARACTER SET = utf8 COLLATE = utf8_general_ci;

-- ----------------------------
-- Records of survey_tree
-- ----------------------------
BEGIN;
INSERT INTO `survey_tree` VALUES (2, '0201 客户化设置', 'config', 1201, 0, 'Y', '', '1201', '客户化设置', 1, 'project/config', '项目管理--客户化设置', 'ylx1.png', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), (3, '0202 项目基本信息管理', 'file', 1202, 0, 'Y', '', '1202', '项目基本信息管理', 1, 'project/file', '项目管理--项目基本信息管理', 'ylx2.png', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), (5, '0203 资金调整管理', 'price_change', 1203, 0, 'Y', '', '1203', '资金调整管理', 1, 'project/price_change', '项目管理--项目资金调整管理', 'ylx3.png', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), (6, '0204 项目通知', 'notice', 1204, 0, 'Y', '', '1204', '项目通知', 1, 'project/notice', '项目管理--产品物料组成设计', 'ylx4.png', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), (7, '0205 数据报表', 'export', 1205, 0, 'Y', '', '1205', '数据报表', 1, 'project/export', '项目管理--标准数据报表', 'ylx5.png', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), (9, '020101 项目基本管理设置', 'file', 120101, 1201, 'Y', '', '120101', '项目基本管理设置', 1, 'project/config/file', '项目管理--客户化设置--项目基本管理设置', 'ylx6.png', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), (10, '020102 项目资金调整管理', 'price_change', 120102, 1201, 'Y', '', '120102', '项目资金调整管理', 1, 'project/config/price_change', '项目管理--客户化设置--项目资金调整管理', 'ylx7.png', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), (11, '020103 关键字设置', 'key', 120103, 1201, 'Y', '', '120103', '关键字设置', 1, 'project/config/key', '项目管理--客户化设置--关键字设置', 'ylx8.png', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), (12, '02010101 项目分类设置', '', 12010101, 120101, 'Y', 'fileKind.jsp', '12010101', '项目分类设置', 0, 'project/config/file/', '项目管理--客户化设置--项目基本管理设置--项目分类设置', 'ylx9.png', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), (16, '02010105 项目级别设置', '', 12010105, 120101, 'Y', 'strategyClass.jsp', '12010105', '项目级别设置', 0, 'project/config/file/', '项目管理--客户化设置--项目基本管理设置--项目级别设置', 'ylx11.png', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), (18, '02010201 项目预警设置', '', 12010201, 120102, 'Y', 'priceAlarm.jsp', '12010201', '项目预警设置', 0, 'project/config/price_change/', '项目管理--客户化设置--项目资金调整管理--项目预警设置', 'ylx13.png', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), (19, '02010301 查询设置', '', 12010301, 120103, 'Y', 'key.jsp', '12010301', '查询设置', 0, 'project/config/key/', '项目管理--客户化设置--关键字设置--查询设置', 'ylx14.png', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), (22, '020201 项目登记', '', 120201, 1202, 'Y', 'project_list.jsp', '120201', '项目档案', 0, 'project/file/', '项目管理--基本管理--项目登记', 'ylx16.png', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), (23, '020202 项目审核', '', 120202, 1202, 'Y', 'check_list.jsp', '120202', '项目审核', 0, 'project/file/', '项目管理--基本管理--项目审核', 'ylx17.png', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), (24, '020203 项目查询', '', 120203, 1202, 'Y', 'query_list.jsp', '120203', '项目查询', 0, 'project/file/', '项目管理--基本管理--项目查询', 'ylx18.png', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), (25, '020204 项目变更', '', 120204, 1202, 'Y', 'change_list.jsp', '120204', '项目变更', 0, 'project/file/', '项目管理--基本管理--项目变更', 'ylx19.png', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), (30, '020301 资金调整登记', '', 120301, 1203, 'Y', 'register_list.jsp', '120301', '资金调整登记', 0, 'project/price_change/', '项目管理--资金调整管理--资金调整登记', 'ylx20.png', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), (31, '020302 资金调整登记审核', '', 120302, 1203, 'Y', 'check_list.jsp', '120302', '资金调整登记审核', 0, 'project/price_change/', '项目管理--资金调整管理--资金调整登记审核', 'ylx21.png', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), (32, '020303 资金调整查询', '', 120303, 1203, 'Y', 'query_list.jsp', '120303', '资金调整查询', 0, 'project/price_change/', '项目管理--资金调整管理--资金调整查询', 'ylx22.png', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), (33, '020401 起草通知', '', 120401, 1204, 'Y', 'register.jsp', '120401', '起草通知', 0, 'project/notice/', '产品设计--产品物料组成设计--制定物料组成设计单', 'ylx23.png', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), (34, '020402 通知审核', '', 120402, 1204, 'Y', 'check_list.jsp', '120402', '通知审核', 0, 'project/notice/', '产品设计--产品物料组成设计--物料组成设计单审核', 'ylx24.png', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), (35, '020403 通知查询', '', 120403, 1204, 'Y', 'query_list.jsp', '120403', '通知查询', 0, 'project/notice/', '产品设计--产品物料组成设计--物料组成设计单查询', 'ylx25.png', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), (36, '020404 通知变更', '', 120404, 1204, 'Y', 'change_list.jsp', '120404', '通知变更', 0, 'project/notice/', '产品设计--产品物料组成设计--物料组成设计单变更', 'ylx26.png', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), (37, '020501 EXCEL标准数据报表', '', 120501, 1205, 'Y', 'excel.jsp', '120501', 'EXCEL标准数据报表', 0, 'project/export/', '产品设计--标准数据报表--EXCEL标准数据报表', 'ylx27.png', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), (38, '020502 pdf标准数据报表', '', 120502, 1205, 'Y', 'pdf.jsp', '120502', 'pdf标准数据报表', 0, 'project/export/', '产品设计--标准数据报表--pdf标准数据报表', 'ylx28.png', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), (39, '020503 xml标准数据报表', '', 120503, 1205, 'Y', 'xml.jsp', '120503', 'xml标准数据报表', 0, 'project/export/', '产品设计--标准数据报表--xml标准数据报表', 'ylx29.png', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), (40, '020104 审核工作流设置', 'workflow', 120104, 1201, 'Y', '', '120104', '审核工作流设置', 1, 'project/config/workflow', '项目管理--客户化设置--审核工作流设置', 'ylx30.png', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), (41, '02010401 项目审核工作流设置', '', 12010401, 120104, 'Y', 'file.jsp', '12010401', '项目信息审核工作流设置', 0, 'project/config/workflow/', '项目管理--客户化设置--审核工作流设置--项目审核工作流设置', 'ylx31.png', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), (43, '02010402 项目资金调整审核工作流设置', '', 12010402, 120104, 'Y', 'price.jsp', '12010402', '项目资金调整审核设置', 0, 'project/config/workflow/', '项目管理--客户化设置--审核工作流设置--项目资金调整审核工作流设置', 'ylx32.png', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), (44, '02010403 项目文档审核工作流设置', '', 12010403, 120104, 'Y', 'document.jsp', '12010403', '项目文档审核工作流设置', 0, 'project/config/workflow/', '项目管理--客户化设置--项目文档审核工作流设置', 'ylx33.png', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), (45, '020205 进度统计', '', 120205, 1202, 'Y', 'tongji.jsp', '120205', '进度统计', 0, 'project/file/', '项目管理--项目管理--进度统计', 'ylx34.png', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), (46, '020206 项目登记', '', 120206, 1202, 'Y', 'project_add.jsp', '120206', '项目登记', 0, 'project/file/', '项目--项目管理--项目进度', 'ylx35.png', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), (47, '0206 项目文档管理', 'file1', 1206, 0, 'Y', '', '1206', '问卷管理', 1, 'project/document', '项目文档管理', 'ylx36.png', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), (48, '020601  项目文档登记', '', 120601, 1206, 'Y', 'SurveyAdmin.jsp', '120601', '问卷设计', 0, 'project/document/', '项目文档管理-项目文档登记', 'ylx37.png', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), (49, '020602 项目文档登记审核', '', 120602, 1206, 'Y', 'SurveyAdd.jsp', '120602', '问卷添加', 0, 'project/document/', '项目文档管理-项目文档登记审核', 'ylx38.png', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), (50, '020603 项目文档查询', '', 120603, 1206, 'Y', 'SurveyAudi.jsp', '120603', '问卷审核', 0, 'project/document/', '项目文档管理-项目文档查询', 'ylx39.png', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), (51, '020605 项目文档变更', '', 120605, 1206, 'Y', 'SurveyStatis.jsp', '120605', '问卷统计', 0, 'project/document/', '项目文档管理-项目文档变更', 'ylx40.png', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), (52, '0207 会议管理', 'meeting', 1207, 0, 'Y', '', '1207', '会议管理', 1, 'project/meeting', '会议管理', 'ylx41.png', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), (53, '020701 议题准备', '', 120701, 1207, 'Y', 'subject_register.jsp', '120701', '议题准备', 0, 'project/meeting/', '会议管理-议题准备', 'ylx42.png', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), (54, '020702 开会通知', '', 120702, 1207, 'Y', 'inform_list.jsp', '120702', '开会通知', 0, 'project/meeting/', '会议管理-开会通知', 'ylx43.png', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), (55, '020703 会议纪要', '', 120703, 1207, 'Y', 'recorder_list.jsp', '120703', '会议纪要', 0, 'project/meeting/', '会议管理-会议纪要', 'ylx44.png', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), (56, '020704 会议纪要处理', '', 120704, 1207, 'Y', 'dealwith_list.jsp', '120704', '会议纪要处理', 0, 'project/meeting/', '会议管理-会议纪要处理', 'ylx45.png', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), (57, '020705 会议查询', '', 120705, 1207, 'Y', 'query_list.jsp', '120705', '会议查询', 0, 'project/meeting/', '会议管理-会议查询', 'ylx46.png', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), (58, '0208 待办事项管理', '', 1208, 0, 'Y', '', '1208', '待办事项管理', 1, 'project/todo', '待办事项管理', 'ylx46.png', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), (59, '020801 待办事项添加', '', 120801, 1208, 'Y', 'todo_add.jsp', '120801', '待办事项添加', 0, 'project/todo/', '待办事项管理--待办事项添加', 'ylx46.png', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), (60, '020803 待办事项查询', '', 120803, 1208, 'Y', 'todo_query.jsp', '120803', '待办事项查询', 0, 'project/todo/', '待办事项管理--待办事项查询', 'ylx46.png', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), (61, '020804 待办事项更改', '', 120804, 1208, 'Y', 'todo_list.jsp', '120804', '待办事项管理', 0, 'project/todo/', '待办事项管理--待办事项更改', 'ylx46.png', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), (62, '020806 统计打印', '', 120806, 1208, 'Y', 'todo_statistic.jsp', '120806', '待办事项统计', 0, 'project/todo/', '待办事项管理--待办事项统计打印', 'ylx46.png', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), (64, '0209 统计打印', '', 1209, 0, 'Y', '', '1209', '统计打印', 1, 'project/statistic', '统计打印', 'ylx46.png', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), (65, '020901 待办事项统计', '', 120901, 1209, 'Y', 'todo_statistic.jsp', '120901', '统计打印', 0, 'project/statistic/', '统计打印--待办事项统计', 'ylx46.png', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), (66, '0210 通知事项管理', '', 1210, 0, 'Y', '', '1210', '通知事项管理', 1, 'project/notice', '通知事项管理', 'ylx46.png', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), (67, '021001 通知事项添加', '', 121001, 1210, 'Y', 'register.jsp', '121001', '通知事项添加', 0, 'project/notice/', '通知事项管理--通知事项添加', 'ylx46.png', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), (68, '021002 通知事项查询', '', 121002, 1210, 'Y', 'query_list.jsp', '121002', '通知事项查询', 0, 'project/notice/', '通知事项管理--通知事项查询', 'ylx46.png', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), (69, '021003 通知事项更改', '', 121003, 1210, 'Y', 'change_list.jsp', '121003', '通知事项更改', 0, 'project/notice/', '通知事项管理--通知事项更改', 'ylx46.png', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), (70, '021004 通知事项统计', '', 121004, 1210, 'Y', 'statistic.jsp', '121004', '通知事项统计', 0, 'project/notice/', '通知事项管理--统计打印', 'ylx46.png', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), (72, '02010404 项目待办事项审核工作流设置', '', 12010404, 120104, 'Y', 'todo.jsp', '12010404', '项目待办事项审核工作流设置', 0, 'project/config/workflow/', '项目管理--客户化设置--项目待办事项审核工作流设置', 'ylx33.png', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), (74, '021101 复杂事项添加', '', 121101, 1211, 'Y', 'register_more.jsp', '121101', '复杂事项添加', 0, 'project/todo_more/', '待办事项管理--复杂事项添加', 'ylx46.png', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), (75, '021102 复杂事项审核', '', 121102, 1211, 'Y', 'check_list_more.jsp', '121102', '复杂事项审核', 0, 'project/todo_more/', '待办事项管理--复杂事项查询', 'ylx46.png', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), (76, '021103 复杂事项查询', '', 121103, 1211, 'Y', 'query_list_more.jsp', '121103', '复杂事项查询', 0, 'project/todo_more/', '待办事项管理--复杂事项查询', 'ylx46.png', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), (77, '021104 复杂事项更改', '', 121104, 1211, 'Y', 'change_list_more.jsp', '121104', '复杂事项更改', 0, 'project/todo_more/', '待办事项管理--复杂事项更改', 'ylx46.png', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), (79, '0211 复杂事项管理', '', 1211, 0, 'Y', '', '1211', '复杂事项管理', 1, 'project/todo_more', '待办事项管理', 'ylx46.png', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), (80, '020802 待办事项审核', '', 120802, 1208, 'Y', 'record_list.jsp', '120802', '待办事项列表', 0, 'project/todo/', '待办事项管理--待办事项审核', 'ylx46.png', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), (84, '1212', '', 1212, 0, 'Y', '', '1212', '项目日志', 1, 'project/diary', '项目日志', 'ylx46.png', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), (85, '121201', '', 121201, 1212, 'Y', 'diary_add.jsp', '121201', '添加日志', 0, 'project/diary/', '添加日志', 'ylx46.png', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), (86, '121202', '', 121202, 1212, 'Y', 'diary_list.jsp', '121202', '日志列表', 0, 'project/diary/', '日志列表', 'ylx46.png', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for survey_view
-- ----------------------------
DROP TABLE IF EXISTS `survey_view`;
CREATE TABLE `survey_view`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `file_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `role_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 312 CHARACTER SET = utf8 COLLATE = utf8_general_ci;

-- ----------------------------
-- Records of survey_view
-- ----------------------------
BEGIN;
INSERT INTO `survey_view` VALUES (206, '12010101', 'manager'), (207, '12010105', 'manager'), (208, '12010201', 'manager'), (209, '12010301', 'manager'), (210, '12010401', 'manager'), (211, '12010402', 'manager'), (212, '12010403', 'manager'), (213, '12010404', 'manager'), (214, '120101', 'manager'), (215, '120102', 'manager'), (216, '120103', 'manager'), (217, '120104', 'manager'), (218, '120201', 'manager'), (219, '120202', 'manager'), (220, '120203', 'manager'), (221, '120204', 'manager'), (222, '120205', 'manager'), (223, '120206', 'manager'), (224, '120301', 'manager'), (225, '120302', 'manager'), (226, '120303', 'manager'), (227, '120401', 'manager'), (228, '120402', 'manager'), (229, '120403', 'manager'), (230, '120404', 'manager'), (231, '120501', 'manager'), (232, '120502', 'manager'), (233, '120503', 'manager'), (234, '120601', 'manager'), (235, '120602', 'manager'), (236, '120603', 'manager'), (237, '120605', 'manager'), (238, '120701', 'manager'), (239, '120702', 'manager'), (240, '120703', 'manager'), (241, '120704', 'manager'), (242, '120705', 'manager'), (243, '120801', 'manager'), (244, '120802', 'manager'), (245, '120803', 'manager'), (246, '120804', 'manager'), (247, '120806', 'manager'), (248, '120807', 'manager'), (249, '120808', 'manager'), (250, '120809', 'manager'), (251, '120901', 'manager'), (252, '121001', 'manager'), (253, '121002', 'manager'), (254, '121003', 'manager'), (255, '121004', 'manager'), (256, '121101', 'manager'), (257, '121102', 'manager'), (258, '121103', 'manager'), (259, '121104', 'manager'), (260, '1201', 'manager'), (261, '1202', 'manager'), (262, '1203', 'manager'), (263, '1204', 'manager'), (264, '1205', 'manager'), (265, '1206', 'manager'), (266, '1207', 'manager'), (267, '1208', 'manager'), (268, '1209', 'manager'), (269, '1210', 'manager'), (270, '1211', 'manager'), (299, '1206', 'normal'), (300, '120601', 'normal'), (301, '120602', 'normal'), (302, '120603', 'normal'), (303, '120605', 'normal'), (304, '1206', 'student'), (305, '120601', 'student'), (306, '120602', 'student'), (307, '120603', 'student'), (308, '120605', 'student');
COMMIT;

-- ----------------------------
-- Table structure for user_file
-- ----------------------------
DROP TABLE IF EXISTS `user_file`;
CREATE TABLE `user_file`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `parent_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `object_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `user_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `user_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `role_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `used_tag` bit(1) NULL DEFAULT b'1',
  `creator` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `create_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 87 CHARACTER SET = utf8 COLLATE = utf8_general_ci;

-- ----------------------------
-- Records of user_file
-- ----------------------------
BEGIN;
INSERT INTO `user_file` VALUES (85, NULL, NULL, 'USER_201903220600000001', '张三', NULL, 'normal', b'1', 'admin', '2019-03-22 00:00:00'), (86, NULL, NULL, 'USER_201903220600000002', '李四', NULL, 'normal', b'1', 'admin', '2019-03-22 00:00:00'), (87, NULL, NULL, 'USER_201903220600000003', '王五', NULL, 'manager', b'1', 'admin', '2019-03-22 00:00:00');
COMMIT;

-- ----------------------------
-- Table structure for user_manage
-- ----------------------------
DROP TABLE IF EXISTS `user_manage`;
CREATE TABLE `user_manage`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `user_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sex` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0',
  `phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `user_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `wechat` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `grade` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `class` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `faculty` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0',
  `student_num` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `register_date` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 30 CHARACTER SET = utf8 COLLATE = utf8_general_ci;

-- ----------------------------
-- Records of user_manage
-- ----------------------------
BEGIN;
INSERT INTO `user_manage` VALUES (2, 'lisi', '呵呵呵11', '12345678', '李四', '男', '114651@qq.com', '120', 'student', 'sjdf', '大一', '2班', '计算机', '123456738459', '2019-05-07 16:25:29'), (22, 'zhangsan', 'zpj', '1111', '张三', '男', 'redhfdrtf@qq.com', NULL, 'student', 'dfhbgkjfl', '大三', '2班', '计算机', '20171414', '2019-05-27 13:17:05'), (27, NULL, 'root', '1234', '王麻子', '', 'redhfdrtf@qq.com', NULL, 'student', '', '', '', 'null', '', '2019-05-30 23:27:00'), (30, NULL, '1234', '1234', '张三', '', 'null', NULL, 'student', '', '', '', 'null', '', '2019-06-02 15:27:22'), (31, NULL, '呵呵呵11', '1234', '李四', '男', '114651@qq.com', '120', 'student', 'sjdf', '大一', '', '计算机', '123456738459', '2019-06-02 16:25:57');
COMMIT;

-- ----------------------------
-- Table structure for vote_manage
-- ----------------------------
DROP TABLE IF EXISTS `vote_manage`;
CREATE TABLE `vote_manage`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `vote_id` int(255) NULL DEFAULT NULL,
  `user_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `course_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `publish_date` datetime(6) NULL DEFAULT NULL,
  `deadline` datetime(6) NULL DEFAULT NULL,
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8 COLLATE = utf8_general_ci;

-- ----------------------------
-- Records of vote_manage
-- ----------------------------
BEGIN;
INSERT INTO `vote_manage` VALUES (1, 1, 'zhangsan', '1', '2019-05-07 16:55:08.000000', '2019-05-31 16:55:12.000000', '关于上课时间的投票', '选取上课时间'), (2, 1, 'zhangsan', '1', '2019-05-07 16:55:08.000000', '2019-05-31 16:55:12.000000', '关于上课教室的投票', '选取上课地点'), (20, 1, 'zhangsan', '1', '2019-05-07 00:00:21.000000', '2019-05-31 00:00:21.000000', 'test', 'test');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
