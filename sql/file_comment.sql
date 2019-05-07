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

 Date: 07/05/2019 20:29:58
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

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
) ENGINE = InnoDB AUTO_INCREMENT = 24 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of file_comment
-- ----------------------------
INSERT INTO `file_comment` VALUES (20, 1, 'zhangsan', '好好好', 100, '2019-05-06 22:50:38');
INSERT INTO `file_comment` VALUES (21, 1, 'zhangsan', '不错不错', 26, '2019-05-07 01:14:37');
INSERT INTO `file_comment` VALUES (22, 1, '李四', '哈哈哈哈', 87, '2019-05-07 01:15:36');
INSERT INTO `file_comment` VALUES (23, 1, 'zhangsan', '再接再厉', 16, '2019-05-07 01:16:16');
INSERT INTO `file_comment` VALUES (24, 2, '李四', '支持楼主', 34, '2019-05-01 13:08:40');
INSERT INTO `file_comment` VALUES (25, 4, '张三', '哈哈哈哈哈哈', 50, '2019-05-07 14:19:04');
INSERT INTO `file_comment` VALUES (26, 4, '王麻子', '好好好', 50, '2019-05-07 14:23:43');
INSERT INTO `file_comment` VALUES (27, 3, '张三', '233333', 50, '2019-05-07 14:24:43');
INSERT INTO `file_comment` VALUES (28, 3, '李四', '楼主加油', 50, '2019-05-07 14:28:38');
INSERT INTO `file_comment` VALUES (29, 3, '张三', '不错', 50, '2019-05-07 14:31:29');
INSERT INTO `file_comment` VALUES (30, 3, '王麻子', '测试', 50, '2019-05-07 14:32:42');
INSERT INTO `file_comment` VALUES (31, 4, '李四', 'test', 50, '2019-05-07 14:39:32');
INSERT INTO `file_comment` VALUES (32, 4, '张三', 'dfhcgk44', 50, '2019-05-07 15:25:14');
INSERT INTO `file_comment` VALUES (33, 1, '张三', '233333333', 50, '2019-05-07 19:28:17');

SET FOREIGN_KEY_CHECKS = 1;
