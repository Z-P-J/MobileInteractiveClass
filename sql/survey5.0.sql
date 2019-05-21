/*
 Navicat Premium Data Transfer

 Source Server         : test
 Source Server Type    : MySQL
 Source Server Version : 80012
 Source Host           : localhost:3306
 Source Schema         : survey

 Target Server Type    : MySQL
 Target Server Version : 50099
 File Encoding         : 65001

 Date: 21/05/2019 11:44:04
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admins
-- ----------------------------
DROP TABLE IF EXISTS `admins`;
CREATE TABLE `admins`  (
  `a_id` int(11) NOT NULL AUTO_INCREMENT,
  `a_user` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `a_pass` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `a_email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `a_phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY USING BTREE (`a_id`)
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of admins
-- ----------------------------
BEGIN;
INSERT INTO `admins` VALUES (1, 'root', '1234', 'xxxxx', '110');
COMMIT;

-- ----------------------------
-- Table structure for answersheet
-- ----------------------------
DROP TABLE IF EXISTS `answersheet`;
CREATE TABLE `answersheet`  (
  `as_id` int(11) NOT NULL AUTO_INCREMENT,
  `s_id` int(11) NULL DEFAULT NULL,
  `as_result` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `as_postdate` datetime NULL DEFAULT NULL,
  `as_userip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY USING BTREE (`as_id`)
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of answersheet
-- ----------------------------
BEGIN;
INSERT INTO `answersheet` VALUES (1, 1, '3:as=0;', '2019-05-16 00:00:00', '0:0:0:0:0:0:0:1'), (2, 1, '3:as=3;', '2019-05-16 00:00:00', '0:0:0:0:0:0:0:1'), (3, 3, 'dlchlk4', '2019-05-19 00:00:00', '0:0:0:0:0:0:0:1'), (4, 3, '你的，cblrd.fhkgnj', '2019-05-19 00:00:00', '0:0:0:0:0:0:0:1'), (5, 3, '人生如果对方承诺将', '2019-05-19 00:00:00', '0:0:0:0:0:0:0:1');
COMMIT;

-- ----------------------------
-- Table structure for config
-- ----------------------------
DROP TABLE IF EXISTS `config`;
CREATE TABLE `config`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `c_siteName` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `c_siteUrl` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `c_isOpen` tinyint(10) NULL DEFAULT NULL,
  `c_closeWord` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `copyright` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY USING BTREE (`id`)
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of config
-- ----------------------------
BEGIN;
INSERT INTO `config` VALUES (1, 'xxxxx', 'www.baidu.com', 1, 'closeword', 'copyright');
COMMIT;

-- ----------------------------
-- Table structure for link
-- ----------------------------
DROP TABLE IF EXISTS `link`;
CREATE TABLE `link`  (
  `l_id` int(11) NOT NULL AUTO_INCREMENT,
  `l_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `l_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `l_img` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `l_info` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `l_islock` tinyint(10) NULL DEFAULT NULL,
  `l_addtime` datetime NULL DEFAULT NULL,
  PRIMARY KEY USING BTREE (`l_id`)
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for question
-- ----------------------------
DROP TABLE IF EXISTS `question`;
CREATE TABLE `question`  (
  `q_id` int(11) NOT NULL AUTO_INCREMENT,
  `s_id` int(11) NULL DEFAULT NULL,
  `q_type` int(11) NULL DEFAULT NULL,
  `q_head` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `q_body` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `q_result` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `q_img` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `q_jdtz` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `q_order` int(11) NULL DEFAULT NULL,
  PRIMARY KEY USING BTREE (`q_id`)
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of question
-- ----------------------------
BEGIN;
INSERT INTO `question` VALUES (3, 1, 1, 'xxxx问题', '2222222222&$$&111&$$&2333333&$$&5555', '1,0,0,1', NULL, 'null&null&null&null', 0), (4, 3, 2, '问题', '233333&$$&6778888&$$&ertuyy&$$&80890', '1,1,1,2', NULL, 'null&null&null&null', 0), (5, 3, 5, '简答题题目', NULL, '4', NULL, '', 0), (11, 4, 1, '这是一个测试问题', '答案1&$$&答案2', '0,0', NULL, 'null&null', 0), (12, 4, 2, '这是一个多选题', '1111&$$&2222&$$&33333&$$&4444444&$$&555555555', '0,0,0,0,0', NULL, 'null&null&null&null&null', 0), (13, 4, 5, '这是一个简答题', NULL, NULL, NULL, '', 0);
COMMIT;

-- ----------------------------
-- Table structure for survey
-- ----------------------------
DROP TABLE IF EXISTS `survey`;
CREATE TABLE `survey`  (
  `s_id` int(255) NOT NULL AUTO_INCREMENT,
  `s_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `s_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `s_author` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `s_img` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `s_createdate` datetime NULL DEFAULT NULL,
  `s_password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `s_isopen` tinyint(10) NULL DEFAULT NULL,
  `s_expiredate` datetime NULL DEFAULT NULL,
  `s_isaudited` tinyint(10) NULL DEFAULT NULL,
  `s_usehits` int(255) NULL DEFAULT NULL,
  `s_type` int(255) NULL DEFAULT NULL,
  PRIMARY KEY USING BTREE (`s_id`)
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of survey
-- ----------------------------
BEGIN;
INSERT INTO `survey` VALUES (1, '作业调查', 'xxxxxxxxxxxx', 'xx', NULL, '2019-05-16 00:00:00', NULL, 1, '2019-06-15 00:00:00', 1, 2, 1), (3, '哈哈哈哈哈哈哈哈哈哈', '密密麻麻', 'zpj', NULL, '2019-05-18 00:00:00', NULL, 1, '2019-06-17 00:00:00', 1, 3, 1), (4, '问题1', '问题一描述', 'zpj', NULL, '2019-05-19 00:00:00', NULL, 1, '2019-06-18 00:00:00', 0, 0, 1);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
