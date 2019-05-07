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

 Date: 07/05/2019 20:29:34
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

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
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of vote_manage
-- ----------------------------
INSERT INTO `vote_manage` VALUES (1, 1, 'zhangsan', '1', '2019-05-07 16:55:08.000000', '2019-05-31 16:55:12.000000', '关于上课时间的投票', '选取上课时间');
INSERT INTO `vote_manage` VALUES (2, 1, 'zhangsan', '1', '2019-05-07 16:55:08.000000', '2019-05-31 16:55:12.000000', '关于上课教室的投票', '选取上课地点');
INSERT INTO `vote_manage` VALUES (20, 1, 'zhangsan', '1', '2019-05-07 00:00:21.000000', '2019-05-31 00:00:21.000000', 'test', 'test');

SET FOREIGN_KEY_CHECKS = 1;
