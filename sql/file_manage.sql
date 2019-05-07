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

 Date: 07/05/2019 20:29:48
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

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
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of file_manage
-- ----------------------------
INSERT INTO `file_manage` VALUES (20, 1, 'zhangsan', 'BBFlashBack.rar', 100, '2019-05-06 22:50:38', NULL);
INSERT INTO `file_manage` VALUES (21, 2, 'zhangsan', '张彭俊_第2周作业.rar', 2697647, '2019-05-07 01:14:37', '../../DownloadServlet?file_name=张彭俊_第2周作业.rar');
INSERT INTO `file_manage` VALUES (22, 3, 'zhangsan', '六级准考证.pdf', 87816, '2019-05-07 01:15:36', '../../DownloadServlet?file_name=六级准考证.pdf');
INSERT INTO `file_manage` VALUES (23, 4, 'zhangsan', '一带一路.docx', 16006, '2019-05-07 01:16:16', '../../DownloadServlet?file_name=一带一路.docx');
INSERT INTO `file_manage` VALUES (24, 1, 'zhangsan', '浅谈石油化工安全.doc', 35328, '2019-05-07 19:27:37', '../../DownloadServlet?file_name=浅谈石油化工安全.doc');

SET FOREIGN_KEY_CHECKS = 1;
