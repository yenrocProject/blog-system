/*
Navicat MySQL Data Transfer

Source Server         : yenroc.top
Source Server Version : 50648
Source Host           : yenroc.top:3306
Source Database       : ALBUM_DB

Target Server Type    : MYSQL
Target Server Version : 50648
File Encoding         : 65001

Date: 2021-05-15 18:32:47
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for album_instance
-- ----------------------------
DROP TABLE IF EXISTS `album_instance`;
CREATE TABLE `album_instance` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `album_template_id` int(255) DEFAULT NULL,
  `user_id` int(255) DEFAULT NULL COMMENT '模板的预览引用',
  `album_name` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL,
  `album_desc` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '该模板可设置的图片数量',
  `private_view` tinyint(4) DEFAULT '0' COMMENT '是否私有,0公开,1私有,默认公开',
  `private_key` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '当相册设置成私有模式,则需要该key密钥进行访问',
  `album_style_css` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL,
  `default_view_photo` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '默认显示的图片',
  `created_by` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL,
  `created_date` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `last_modified_by` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL,
  `last_modified_date` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `is_del` tinyint(4) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf32;

-- ----------------------------
-- Records of album_instance
-- ----------------------------
INSERT INTO `album_instance` VALUES ('1', '1', null, '3Dの自由的模板相册', '3d相册让你体验身临其', '0', null, 'ih-item square effect6 from_left_and_right', '\\20210514\\202105141323571dde6.jpg', 'default', '2021-05-14 16:49:01', 'default', '2021-05-14 16:49:01', '0');
INSERT INTO `album_instance` VALUES ('2', '1', '2', '和彦鹏测试的3D自由', '我自己测试是不是真的3D自由', '1', '11111', 'ih-item square effect2', '\\20210514\\20210514150906aa2fe.jpg', 'default', '2021-05-15 07:19:24', 'default', '2021-05-15 07:19:24', '0');
INSERT INTO `album_instance` VALUES ('3', '2', null, '旋转魔方', '旋转中的魔方旋转出不一样的美', '0', null, 'ih-item square effect13 left_to_right', '\\20210514\\2021051421403068b9b.jpg', null, '2021-05-14 17:17:02', null, '2021-05-14 17:17:02', null);
INSERT INTO `album_instance` VALUES ('4', '3', null, '迷幻圆盘', '你真的以为你看清了它的旋转方向了吗?', '0', null, 'ih-item square colored effect14 bottom_to_top', '\\20210514\\20210514150906aa2fe.jpg', 'default', '2021-05-14 17:16:40', 'default', '2021-05-14 17:16:40', null);

-- ----------------------------
-- Table structure for album_photo_instance
-- ----------------------------
DROP TABLE IF EXISTS `album_photo_instance`;
CREATE TABLE `album_photo_instance` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `album_instance_id` int(255) DEFAULT NULL,
  `user_id` int(255) DEFAULT NULL COMMENT '模板的预览引用',
  `template_id` int(255) DEFAULT NULL,
  `template_photo_id` int(11) DEFAULT NULL COMMENT '该模板可设置的图片数量',
  `file_id` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '文件Id',
  `created_by` varchar(255) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `last_modified_by` varchar(255) DEFAULT NULL,
  `last_modified_date` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `is_del` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf32;

-- ----------------------------
-- Records of album_photo_instance
-- ----------------------------
INSERT INTO `album_photo_instance` VALUES ('1', '1', null, '1', '1', '3c132f01-1e60-4afe-bdda-179fb659504d', 'default', '2021-05-14 16:52:41', 'default', '2021-05-14 16:52:41', '0');
INSERT INTO `album_photo_instance` VALUES ('2', '1', null, '1', '2', '68b9bfd5-5c63-4956-a0a6-93f1f7b94459', 'default', '2021-05-14 16:52:42', 'default', '2021-05-14 16:52:42', '0');
INSERT INTO `album_photo_instance` VALUES ('3', '1', null, '1', '3', '3c132f01-1e60-4afe-bdda-179fb659504d', 'default', '2021-05-14 16:52:43', 'default', '2021-05-14 16:52:43', '0');
INSERT INTO `album_photo_instance` VALUES ('4', '1', null, '1', '4', '3c132f01-1e60-4afe-bdda-179fb659504d', 'default', '2021-05-14 16:52:44', 'default', '2021-05-14 16:52:44', '0');
INSERT INTO `album_photo_instance` VALUES ('5', '1', null, '1', '5', '3c132f01-1e60-4afe-bdda-179fb659504d', 'default', '2021-05-14 16:52:45', 'default', '2021-05-14 16:52:45', '0');
INSERT INTO `album_photo_instance` VALUES ('6', '1', null, '1', '6', '3c132f01-1e60-4afe-bdda-179fb659504d', 'default', '2021-05-14 16:52:46', 'default', '2021-05-14 16:52:46', '0');
INSERT INTO `album_photo_instance` VALUES ('7', '1', null, '1', '7', '68b9bfd5-5c63-4956-a0a6-93f1f7b94459', 'default', '2021-05-14 16:52:48', 'default', '2021-05-14 16:52:48', '0');
INSERT INTO `album_photo_instance` VALUES ('8', '1', null, '1', '8', '68b9bfd5-5c63-4956-a0a6-93f1f7b94459', 'default', '2021-05-14 16:52:49', 'default', '2021-05-14 16:52:49', '0');
INSERT INTO `album_photo_instance` VALUES ('9', '1', null, '1', '9', '3c132f01-1e60-4afe-bdda-179fb659504d', 'default', '2021-05-14 16:52:50', 'default', '2021-05-14 16:52:50', '0');
INSERT INTO `album_photo_instance` VALUES ('10', '1', null, '1', '10', '3c132f01-1e60-4afe-bdda-179fb659504d', 'default', '2021-05-14 16:52:53', 'default', '2021-05-14 16:52:53', '0');
INSERT INTO `album_photo_instance` VALUES ('11', '1', null, '1', '11', '3c132f01-1e60-4afe-bdda-179fb659504d', 'default', '2021-05-14 16:52:55', 'default', '2021-05-14 16:52:55', '0');
INSERT INTO `album_photo_instance` VALUES ('12', '1', null, '1', '12', '3c132f01-1e60-4afe-bdda-179fb659504d', 'default', '2021-05-14 16:52:56', 'default', '2021-05-14 16:52:56', '0');
INSERT INTO `album_photo_instance` VALUES ('13', '2', '2', '1', '1', '3c132f01-1e60-4afe-bdda-179fb659504d', 'default', '2021-05-14 16:52:57', 'default', '2021-05-14 16:52:57', '0');
INSERT INTO `album_photo_instance` VALUES ('14', '2', '2', '1', '2', '68b9bfd5-5c63-4956-a0a6-93f1f7b94459', 'default', '2021-05-14 16:52:58', 'default', '2021-05-14 16:52:58', '0');
INSERT INTO `album_photo_instance` VALUES ('15', '2', '2', '1', '3', '3c132f01-1e60-4afe-bdda-179fb659504d', 'default', '2021-05-14 16:52:59', 'default', '2021-05-14 16:52:59', '0');
INSERT INTO `album_photo_instance` VALUES ('16', '2', '2', '1', '4', '68b9bfd5-5c63-4956-a0a6-93f1f7b94459', 'default', '2021-05-14 16:53:00', 'default', '2021-05-14 16:53:00', '0');
INSERT INTO `album_photo_instance` VALUES ('17', '2', '2', '1', '5', '3c132f01-1e60-4afe-bdda-179fb659504d', 'default', '2021-05-14 16:53:01', 'default', '2021-05-14 16:53:01', '0');
INSERT INTO `album_photo_instance` VALUES ('18', '2', '2', '1', '6', '68b9bfd5-5c63-4956-a0a6-93f1f7b94459', 'default', '2021-05-14 16:53:02', 'default', '2021-05-14 16:53:02', '0');
INSERT INTO `album_photo_instance` VALUES ('19', '2', '2', '1', '7', '3c132f01-1e60-4afe-bdda-179fb659504d', 'default', '2021-05-14 16:53:03', 'default', '2021-05-14 16:53:03', '0');
INSERT INTO `album_photo_instance` VALUES ('20', '2', '2', '1', '8', '68b9bfd5-5c63-4956-a0a6-93f1f7b94459', 'default', '2021-05-14 16:53:05', 'default', '2021-05-14 16:53:05', '0');
INSERT INTO `album_photo_instance` VALUES ('21', '2', '2', '1', '9', '3c132f01-1e60-4afe-bdda-179fb659504d', 'default', '2021-05-14 16:53:06', 'default', '2021-05-14 16:53:06', '0');
INSERT INTO `album_photo_instance` VALUES ('22', '2', '2', '1', '10', '68b9bfd5-5c63-4956-a0a6-93f1f7b94459', 'default', '2021-05-14 16:53:09', 'default', '2021-05-14 16:53:09', '0');
INSERT INTO `album_photo_instance` VALUES ('23', '2', '2', '1', '11', 'aa2fe0c7-883c-4adb-b658-f730b7c18cf4', 'default', '2021-05-14 16:53:10', 'default', '2021-05-14 16:53:10', '0');
INSERT INTO `album_photo_instance` VALUES ('24', '2', '2', '1', '12', 'aa2fe0c7-883c-4adb-b658-f730b7c18cf4', 'default', '2021-05-14 16:53:11', 'default', '2021-05-14 16:53:11', '0');
INSERT INTO `album_photo_instance` VALUES ('25', '3', null, '2', '13', 'aa2fe0c7-883c-4adb-b658-f730b7c18cf4', 'default', '2021-05-14 16:54:42', 'default', '2021-05-14 16:54:42', '0');
INSERT INTO `album_photo_instance` VALUES ('26', '3', null, '2', '14', 'aa2fe0c7-883c-4adb-b658-f730b7c18cf4', 'default', '2021-05-14 16:54:42', 'default', '2021-05-14 16:54:42', '0');
INSERT INTO `album_photo_instance` VALUES ('27', '3', null, '2', '15', 'aa2fe0c7-883c-4adb-b658-f730b7c18cf4', 'default', '2021-05-14 16:54:43', 'default', '2021-05-14 16:54:43', '0');
INSERT INTO `album_photo_instance` VALUES ('28', '3', null, '2', '16', 'aa2fe0c7-883c-4adb-b658-f730b7c18cf4', 'default', '2021-05-14 16:54:43', 'default', '2021-05-14 16:54:43', '0');
INSERT INTO `album_photo_instance` VALUES ('29', '3', null, '2', '17', 'aa2fe0c7-883c-4adb-b658-f730b7c18cf4', 'default', '2021-05-14 16:54:43', 'default', '2021-05-14 16:54:43', '0');
INSERT INTO `album_photo_instance` VALUES ('30', '3', null, '2', '18', 'aa2fe0c7-883c-4adb-b658-f730b7c18cf4', 'default', '2021-05-14 16:54:44', 'default', '2021-05-14 16:54:44', '0');
INSERT INTO `album_photo_instance` VALUES ('31', '3', null, '2', '19', 'aa2fe0c7-883c-4adb-b658-f730b7c18cf4', 'default', '2021-05-14 16:54:44', 'default', '2021-05-14 16:54:44', '0');
INSERT INTO `album_photo_instance` VALUES ('32', '3', null, '2', '20', 'aa2fe0c7-883c-4adb-b658-f730b7c18cf4', 'default', '2021-05-14 16:54:46', 'default', '2021-05-14 16:54:46', '0');
INSERT INTO `album_photo_instance` VALUES ('33', '3', null, '2', '21', 'aa2fe0c7-883c-4adb-b658-f730b7c18cf4', 'default', '2021-05-14 16:54:45', 'default', '2021-05-14 16:54:45', '0');
INSERT INTO `album_photo_instance` VALUES ('34', '3', null, '2', '22', 'aa2fe0c7-883c-4adb-b658-f730b7c18cf4', 'default', '2021-05-14 16:54:47', 'default', '2021-05-14 16:54:47', '0');
INSERT INTO `album_photo_instance` VALUES ('35', '3', null, '2', '23', 'aa2fe0c7-883c-4adb-b658-f730b7c18cf4', 'default', '2021-05-14 16:54:47', 'default', '2021-05-14 16:54:47', '0');
INSERT INTO `album_photo_instance` VALUES ('36', '3', null, '2', '24', 'aa2fe0c7-883c-4adb-b658-f730b7c18cf4', 'default', '2021-05-14 16:54:48', 'default', '2021-05-14 16:54:48', '0');
INSERT INTO `album_photo_instance` VALUES ('37', '4', null, '3', '25', '68b9bfd5-5c63-4956-a0a6-93f1f7b94459', 'default', '2021-05-14 16:56:22', 'default', '2021-05-14 16:56:22', '0');
INSERT INTO `album_photo_instance` VALUES ('38', '4', null, '3', '26', '68b9bfd5-5c63-4956-a0a6-93f1f7b94459', 'default', '2021-05-14 16:56:23', 'default', '2021-05-14 16:56:23', '0');
INSERT INTO `album_photo_instance` VALUES ('39', '4', null, '3', '27', '68b9bfd5-5c63-4956-a0a6-93f1f7b94459', 'default', '2021-05-14 16:56:24', 'default', '2021-05-14 16:56:24', '0');
INSERT INTO `album_photo_instance` VALUES ('40', '4', null, '3', '28', '68b9bfd5-5c63-4956-a0a6-93f1f7b94459', 'default', '2021-05-14 16:56:25', 'default', '2021-05-14 16:56:25', '0');
INSERT INTO `album_photo_instance` VALUES ('41', '4', null, '3', '29', '68b9bfd5-5c63-4956-a0a6-93f1f7b94459', 'default', '2021-05-14 16:56:26', 'default', '2021-05-14 16:56:26', '0');
INSERT INTO `album_photo_instance` VALUES ('42', '4', null, '3', '30', '68b9bfd5-5c63-4956-a0a6-93f1f7b94459', 'default', '2021-05-14 16:56:27', 'default', '2021-05-14 16:56:27', '0');
INSERT INTO `album_photo_instance` VALUES ('43', '4', null, '3', '31', '68b9bfd5-5c63-4956-a0a6-93f1f7b94459', 'default', '2021-05-14 16:56:28', 'default', '2021-05-14 16:56:28', '0');
INSERT INTO `album_photo_instance` VALUES ('44', '4', null, '3', '32', '68b9bfd5-5c63-4956-a0a6-93f1f7b94459', 'default', '2021-05-14 16:56:28', 'default', '2021-05-14 16:56:28', '0');
INSERT INTO `album_photo_instance` VALUES ('45', '4', null, '3', '33', '68b9bfd5-5c63-4956-a0a6-93f1f7b94459', 'default', '2021-05-14 16:56:29', 'default', '2021-05-14 16:56:29', '0');
INSERT INTO `album_photo_instance` VALUES ('46', '4', null, '3', '34', '68b9bfd5-5c63-4956-a0a6-93f1f7b94459', 'default', '2021-05-14 16:56:30', 'default', '2021-05-14 16:56:30', '0');

-- ----------------------------
-- Table structure for album_style_css
-- ----------------------------
DROP TABLE IF EXISTS `album_style_css`;
CREATE TABLE `album_style_css` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `option_name` varchar(255) DEFAULT NULL COMMENT '选项显示值',
  `option_value` varchar(255) DEFAULT NULL COMMENT '选项值',
  `created_by` varchar(255) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `last_modified_by` varchar(255) DEFAULT NULL,
  `last_modified_date` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `is_del` tinyint(4) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of album_style_css
-- ----------------------------
INSERT INTO `album_style_css` VALUES ('1', 'effect1 left_and_right', 'ih-item square effect1 left_and_right', null, '2021-05-15 07:49:01', null, '2021-05-15 07:49:01', '0');
INSERT INTO `album_style_css` VALUES ('2', 'colored effect2', 'ih-item square colored effect2', null, '2021-05-15 07:49:01', null, '2021-05-15 07:49:01', '0');
INSERT INTO `album_style_css` VALUES ('3', 'colored effect1 left_and_right', 'ih-item square colored effect1 left_and_right', null, '2021-05-15 07:49:01', null, '2021-05-15 07:49:01', '0');
INSERT INTO `album_style_css` VALUES ('4', 'effect1 top_to_bottom', 'ih-item square effect1 top_to_bottom', null, '2021-05-15 07:49:01', null, '2021-05-15 07:49:01', '0');
INSERT INTO `album_style_css` VALUES ('5', 'effect1 bottom_to_top', 'ih-item square effect1 bottom_to_top', null, '2021-05-15 07:49:01', null, '2021-05-15 07:49:01', '0');
INSERT INTO `album_style_css` VALUES ('6', 'effect2', 'ih-item square effect2', null, '2021-05-15 07:49:01', null, '2021-05-15 07:49:01', '0');

-- ----------------------------
-- Table structure for album_template
-- ----------------------------
DROP TABLE IF EXISTS `album_template`;
CREATE TABLE `album_template` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `template_name` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL,
  `template_desc` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL,
  `default_instance_id` int(255) DEFAULT NULL COMMENT '模板的预览引用',
  `template_photo_size` int(11) DEFAULT NULL COMMENT '该模板可设置的图片数量',
  `template_html_path` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL,
  `template_style_css` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '模板显示的样式',
  `created_by` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL,
  `created_date` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `last_modified_by` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL,
  `last_modified_date` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `is_del` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf32;

-- ----------------------------
-- Records of album_template
-- ----------------------------
INSERT INTO `album_template` VALUES ('1', '3Dの自由', '3d相册让你体验身临其境的感受', '1', '12', '/temp1/index.html', 'ih-item square effect6 from_left_and_right', 'default', '2021-05-14 13:25:14', 'default', '2021-05-14 13:25:14', '0');
INSERT INTO `album_template` VALUES ('2', '旋转魔方', '旋转中的魔方旋转出不一样的美', '3', '12', '/temp2/index.html', 'ih-item square colored effect14 bottom_to_top', 'default', '2021-05-14 17:17:38', 'default', '2021-05-14 17:17:38', '0');
INSERT INTO `album_template` VALUES ('3', '迷幻圆盘', '你真的以为你看清了它的旋转方向了吗?', '4', '10', '/temp3/index.html', 'ih-item square effect13 left_to_right', 'default', '2021-05-14 17:17:35', 'default', '2021-05-14 17:17:35', '0');

-- ----------------------------
-- Table structure for album_template_photo_config
-- ----------------------------
DROP TABLE IF EXISTS `album_template_photo_config`;
CREATE TABLE `album_template_photo_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `album_template_id` int(11) DEFAULT NULL,
  `photo_config_name` varchar(255) DEFAULT NULL,
  `photo_desc` varchar(255) DEFAULT NULL,
  `photo_width` int(11) DEFAULT NULL,
  `photo_height` int(11) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `last_modified_by` varchar(255) DEFAULT NULL,
  `last_modified_date` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `is_del` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf32;

-- ----------------------------
-- Records of album_template_photo_config
-- ----------------------------
INSERT INTO `album_template_photo_config` VALUES ('1', '1', null, null, null, null, 'default', '2021-05-14 16:50:57', 'default', '2021-05-14 16:50:57', '0');
INSERT INTO `album_template_photo_config` VALUES ('2', '1', null, null, null, null, 'default', '2021-05-14 16:50:59', 'default', '2021-05-14 16:50:59', '0');
INSERT INTO `album_template_photo_config` VALUES ('3', '1', null, null, null, null, 'default', '2021-05-14 16:50:59', 'default', '2021-05-14 16:50:59', '0');
INSERT INTO `album_template_photo_config` VALUES ('4', '1', null, null, null, null, 'default', '2021-05-14 16:50:59', 'default', '2021-05-14 16:50:59', '0');
INSERT INTO `album_template_photo_config` VALUES ('5', '1', null, null, null, null, 'default', '2021-05-14 16:51:00', 'default', '2021-05-14 16:51:00', '0');
INSERT INTO `album_template_photo_config` VALUES ('6', '1', null, null, null, null, 'default', '2021-05-14 16:51:00', 'default', '2021-05-14 16:51:00', '0');
INSERT INTO `album_template_photo_config` VALUES ('7', '1', null, null, null, null, 'default', '2021-05-14 16:51:01', 'default', '2021-05-14 16:51:01', '0');
INSERT INTO `album_template_photo_config` VALUES ('8', '1', null, null, null, null, 'default', '2021-05-14 16:51:17', 'default', '2021-05-14 16:51:17', '0');
INSERT INTO `album_template_photo_config` VALUES ('9', '1', null, null, null, null, 'default', '2021-05-14 16:51:02', 'default', '2021-05-14 16:51:02', '0');
INSERT INTO `album_template_photo_config` VALUES ('10', '1', null, null, null, null, 'default', '2021-05-14 16:51:02', 'default', '2021-05-14 16:51:02', '0');
INSERT INTO `album_template_photo_config` VALUES ('11', '1', null, null, null, null, 'default', '2021-05-14 16:51:03', 'default', '2021-05-14 16:51:03', '0');
INSERT INTO `album_template_photo_config` VALUES ('12', '1', null, null, null, null, 'default', '2021-05-14 16:51:04', 'default', '2021-05-14 16:51:04', '0');
INSERT INTO `album_template_photo_config` VALUES ('13', '2', null, null, null, null, 'default', '2021-05-14 16:51:04', 'default', '2021-05-14 16:51:04', '0');
INSERT INTO `album_template_photo_config` VALUES ('14', '2', null, null, null, null, 'default', '2021-05-14 16:51:04', 'default', '2021-05-14 16:51:04', '0');
INSERT INTO `album_template_photo_config` VALUES ('15', '2', null, null, null, null, 'default', '2021-05-14 16:51:04', 'default', '2021-05-14 16:51:04', '0');
INSERT INTO `album_template_photo_config` VALUES ('16', '2', null, null, null, null, 'default', '2021-05-14 16:51:04', 'default', '2021-05-14 16:51:04', '0');
INSERT INTO `album_template_photo_config` VALUES ('17', '2', null, null, null, null, 'default', '2021-05-14 16:51:04', 'default', '2021-05-14 16:51:04', '0');
INSERT INTO `album_template_photo_config` VALUES ('18', '2', null, null, null, null, 'default', '2021-05-14 16:51:04', 'default', '2021-05-14 16:51:04', '0');
INSERT INTO `album_template_photo_config` VALUES ('19', '2', null, null, null, null, 'default', '2021-05-14 16:51:04', 'default', '2021-05-14 16:51:04', '0');
INSERT INTO `album_template_photo_config` VALUES ('20', '2', null, null, null, null, 'default', '2021-05-14 16:51:04', 'default', '2021-05-14 16:51:04', '0');
INSERT INTO `album_template_photo_config` VALUES ('21', '2', null, null, null, null, 'default', '2021-05-14 16:51:04', 'default', '2021-05-14 16:51:04', '0');
INSERT INTO `album_template_photo_config` VALUES ('22', '2', null, null, null, null, 'default', '2021-05-14 16:51:04', 'default', '2021-05-14 16:51:04', '0');
INSERT INTO `album_template_photo_config` VALUES ('23', '2', null, null, null, null, 'default', '2021-05-14 16:51:04', 'default', '2021-05-14 16:51:04', '0');
INSERT INTO `album_template_photo_config` VALUES ('24', '2', null, null, null, null, 'default', '2021-05-14 16:51:04', 'default', '2021-05-14 16:51:04', '0');
INSERT INTO `album_template_photo_config` VALUES ('25', '3', null, null, null, null, 'default', '2021-05-14 16:51:04', 'default', '2021-05-14 16:51:04', '0');
INSERT INTO `album_template_photo_config` VALUES ('26', '3', null, null, null, null, 'default', '2021-05-14 16:51:04', 'default', '2021-05-14 16:51:04', '0');
INSERT INTO `album_template_photo_config` VALUES ('27', '3', null, null, null, null, 'default', '2021-05-14 16:51:04', 'default', '2021-05-14 16:51:04', '0');
INSERT INTO `album_template_photo_config` VALUES ('28', '3', null, null, null, null, 'default', '2021-05-14 16:51:04', 'default', '2021-05-14 16:51:04', '0');
INSERT INTO `album_template_photo_config` VALUES ('29', '3', null, null, null, null, 'default', '2021-05-14 16:51:04', 'default', '2021-05-14 16:51:04', '0');
INSERT INTO `album_template_photo_config` VALUES ('30', '3', null, null, null, null, 'default', '2021-05-14 16:51:04', 'default', '2021-05-14 16:51:04', '0');
INSERT INTO `album_template_photo_config` VALUES ('31', '3', null, null, null, null, 'default', '2021-05-14 16:51:04', 'default', '2021-05-14 16:51:04', '0');
INSERT INTO `album_template_photo_config` VALUES ('32', '3', null, null, null, null, 'default', '2021-05-14 16:51:04', 'default', '2021-05-14 16:51:04', '0');
INSERT INTO `album_template_photo_config` VALUES ('33', '3', null, null, null, null, 'default', '2021-05-14 16:51:04', 'default', '2021-05-14 16:51:04', '0');
INSERT INTO `album_template_photo_config` VALUES ('34', '3', null, null, null, null, 'default', '2021-05-14 16:51:04', 'default', '2021-05-14 16:51:04', '0');

-- ----------------------------
-- Table structure for album_template_text_config
-- ----------------------------
DROP TABLE IF EXISTS `album_template_text_config`;
CREATE TABLE `album_template_text_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `album_template_id` int(11) DEFAULT NULL,
  `text_code_key` varchar(255) DEFAULT NULL,
  `text_style` varchar(255) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `last_modified_by` varchar(255) DEFAULT NULL,
  `last_modified_date` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `is_del` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf32;

-- ----------------------------
-- Records of album_template_text_config
-- ----------------------------

-- ----------------------------
-- Table structure for file
-- ----------------------------
DROP TABLE IF EXISTS `file`;
CREATE TABLE `file` (
  `id` varchar(36) CHARACTER SET utf8mb4 NOT NULL,
  `fileName` varchar(255) NOT NULL COMMENT '文件名',
  `fileOriginalName` varchar(255) DEFAULT NULL COMMENT '文件原始名',
  `fileFullPath` varchar(255) DEFAULT NULL COMMENT '文件全路径',
  `thumbFullPath` varchar(255) DEFAULT NULL COMMENT '缩略图地址',
  `fileSize` int(11) DEFAULT NULL COMMENT '文件大小',
  `created_by` varchar(255) DEFAULT NULL COMMENT '创建人',
  `created_date` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `last_modified_by` varchar(255) DEFAULT NULL COMMENT '最后更新者',
  `last_modified_date` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  `is_del` tinyint(2) DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf32;

-- ----------------------------
-- Records of file
-- ----------------------------
INSERT INTO `file` VALUES ('1dde61c3-7a1f-47ac-83ce-e2c23eb73472', '202105141323571dde6.jpg', '7.jpg', '\\20210514\\202105141323571dde6.jpg', '\\20210514\\202105141323571dde6_Thumbnail.jpg', '50330', 'default', '2021-05-14 07:10:18', 'default', '2021-05-14 07:10:18', '0');
INSERT INTO `file` VALUES ('3c132f01-1e60-4afe-bdda-179fb659504d', '202105141324113c132.jpg', '7.jpg', '\\20210514\\202105141324113c132.jpg', '\\20210514\\202105141324113c132_Thumbnail.jpg', '50330', 'default', '2021-05-14 07:10:25', 'default', '2021-05-14 07:10:25', '0');
INSERT INTO `file` VALUES ('68b9bfd5-5c63-4956-a0a6-93f1f7b94459', '2021051421403068b9b.jpg', '9.jpg', '\\20210514\\2021051421403068b9b.jpg', '\\20210514\\2021051421403068b9b_Thumbnail.jpg', '29641', 'default', '2021-05-14 13:40:30', 'default', '2021-05-14 13:40:30', '0');
INSERT INTO `file` VALUES ('aa2fe0c7-883c-4adb-b658-f730b7c18cf4', '20210514150906aa2fe.jpg', '11.jpg', '\\20210514\\20210514150906aa2fe.jpg', '\\20210514\\20210514150906aa2fe_Thumbnail.jpg', '137987', 'default', '2021-05-14 07:09:07', 'default', '2021-05-14 07:09:07', '0');

-- ----------------------------
-- Table structure for sys_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config` (
  `id` int(11) NOT NULL,
  `code` varchar(255) DEFAULT NULL,
  `value` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf32;

-- ----------------------------
-- Records of sys_config
-- ----------------------------

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `nick_name` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `last_modified_by` varchar(255) DEFAULT NULL,
  `last_modified_date` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `is_del` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf32;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'a', '%8609d%6194P%1EF4%B8F%6BD1%76B%2EF6%B4F%7BD0%E0F', null, '0', 'default', '2021-05-13 13:27:02', 'default', '2021-05-13 13:27:02', '0');
INSERT INTO `user` VALUES ('2', 'qq', '202cb962ac59075b964b07152d234b70', null, '0', 'default', '2021-05-13 13:30:31', 'default', '2021-05-13 13:30:31', '0');
