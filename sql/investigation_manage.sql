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

 Date: 07/05/2019 20:15:35
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

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
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of investigation_manage
-- ----------------------------
INSERT INTO `investigation_manage` VALUES (4, 'kfjwwxpt', 'zhangsan', '食堂饭菜满意度调查', '调查学生对学校食堂饭菜的满意度调查', '2015-11-29 00:00:00', '张三', '2017-04-23 14:50:37', 'https://www.wjx.cn');
INSERT INTO `investigation_manage` VALUES (10, 'PRJ_20161210020741', 'zhangsan', '下周三是否有空', '调查学生周三是否有空', '2019-04-18 23:58:17', '辛卫', '2017-04-29 14:50:37', 'https://www.wjx.cn');
INSERT INTO `investigation_manage` VALUES (11, 'PRJ_20161207052455', 'zhangsan', '意见收集', '收集学生对课堂教学的意见', '2019-04-02 23:58:24', '辛卫', '2017-05-01 14:50:37', 'https://www.wjx.cn');
INSERT INTO `investigation_manage` VALUES (13, 'PRJ_20161022023027', 'zhangsan', '教学满意度调查', '调查学生对老师教学情况', '2019-04-19 23:58:27', '张三', '2017-05-11 14:34:27', 'https://www.wjx.cn');
INSERT INTO `investigation_manage` VALUES (15, 'PRJ_20161022023027', 'zhangsan', '考试考试', '考试快来看看看看看', '2019-05-01 23:58:31', '张三', '2017-05-11 14:52:06', 'https://www.wjx.cn');

SET FOREIGN_KEY_CHECKS = 1;
