/*
Navicat MySQL Data Transfer

Source Server         : 127.0.0.1
Source Server Version : 50715
Source Host           : localhost:3306
Source Database       : boot

Target Server Type    : MYSQL
Target Server Version : 50715
File Encoding         : 65001

Date: 2017-03-20 16:55:36
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for qt_tag
-- ----------------------------
DROP TABLE IF EXISTS `qt_tag`;
CREATE TABLE `qt_tag` (
  `tag_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '标签id',
  `tag_name` char(20) NOT NULL COMMENT '标签名称，不超过5个字符',
  `tag_num` int(11) DEFAULT '0' COMMENT '该标签下文章的数量,当文章添加或删除时使用触发器自动修改',
  `add_time` bigint(20) DEFAULT '0' COMMENT '标签添加时间',
  PRIMARY KEY (`tag_id`),
  UNIQUE KEY `UNIQUE_QT_TAG_NAME` (`tag_name`) USING HASH
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8 COMMENT='标签表:\r\n1.当添加文章时，如果添加了标签，如果标签不存在就需要新增标签到此表，当文章保存成功后，通过触发器修改对应标签的tag_num，以及在文章标签关联表中添加两者的关联记录(v)\r\n2.当修改文章时，如果标签没有改变，则不做处理，存在修改则需要删除该文章与标签的对应关系，并且重新添加关联记录，另外文章本来就存在的标签对应的tag_num不做修改，新增的标签需要修改tag_num\r\n3.当文章删除时，通过触发器，删除文章与标签的关联记录，修改对应标签的tag_num(v)';

-- ----------------------------
-- Records of qt_tag
-- ----------------------------
INSERT INTO `qt_tag` VALUES ('34', '黔东南', '1', '1479482475203');
INSERT INTO `qt_tag` VALUES ('35', '旅行', '1', '1479482475204');
INSERT INTO `qt_tag` VALUES ('38', '贵州', '1', '1479483639272');
INSERT INTO `qt_tag` VALUES ('39', '云南十八怪', '1', '1479487057825');
INSERT INTO `qt_tag` VALUES ('40', '云南', '0', '1480158659503');
INSERT INTO `qt_tag` VALUES ('41', '梅里雪山', '0', '1480158659504');

-- ----------------------------
-- Table structure for sys_group
-- ----------------------------
DROP TABLE IF EXISTS `sys_group`;
CREATE TABLE `sys_group` (
  `group_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '分组id',
  `group_name` varchar(50) NOT NULL,
  `group_desc` varchar(200) DEFAULT '',
  `group_pid` int(11) DEFAULT '0' COMMENT '分组的父级分组',
  `group_state` tinyint(4) DEFAULT '1' COMMENT '状态:1:正常',
  PRIMARY KEY (`group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='分组表';

-- ----------------------------
-- Records of sys_group
-- ----------------------------

-- ----------------------------
-- Table structure for sys_group_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_group_role`;
CREATE TABLE `sys_group_role` (
  `group_id` int(11) NOT NULL,
  `role_no` varchar(32) NOT NULL,
  PRIMARY KEY (`group_id`,`role_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='分组角色表';

-- ----------------------------
-- Records of sys_group_role
-- ----------------------------

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `menu_id` int(11) NOT NULL AUTO_INCREMENT,
  `menu_name` varchar(50) NOT NULL,
  `menu_url` varchar(100) DEFAULT '',
  `menu_desc` varchar(200) DEFAULT '',
  `menu_pid` int(11) DEFAULT '0',
  `menu_state` tinyint(4) NOT NULL DEFAULT '1' COMMENT '状态:1 正常',
  `menu_level` tinyint(4) NOT NULL DEFAULT '0' COMMENT '菜单级别:0 一级菜单(model类),1 二级菜单,2 三级菜单',
  `menu_seq` int(11) NOT NULL DEFAULT '0' COMMENT '菜单排序',
  `menu_icon` varchar(200) DEFAULT '' COMMENT '菜单图标',
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8 COMMENT='系统菜单';

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES ('1', '工作台', '/admin/forward/desk.html', '用户的工作台', '0', '1', '0', '0', 'icon-dashboard');
INSERT INTO `sys_menu` VALUES ('2', '模型管理', '', null, '0', '1', '0', '1', 'icon-coffee');
INSERT INTO `sys_menu` VALUES ('3', '模块设计', '', 'm', '0', '1', '0', '2', 'icon-laptop');
INSERT INTO `sys_menu` VALUES ('4', '常用工具', '', null, '0', '1', '0', '3', 'icon-glass');
INSERT INTO `sys_menu` VALUES ('5', '文件管理', '', null, '0', '1', '0', '4', 'icon-desktop');
INSERT INTO `sys_menu` VALUES ('6', '系统设计', '', null, '0', '1', '0', '5', ' icon-cogs');
INSERT INTO `sys_menu` VALUES ('7', '管理信息', '', null, '2', '1', '1', '0', '');
INSERT INTO `sys_menu` VALUES ('8', '其他设置', '', null, '2', '1', '1', '1', '');
INSERT INTO `sys_menu` VALUES ('9', '编辑器', '', null, '2', '1', '1', '2', '');
INSERT INTO `sys_menu` VALUES ('10', '日期管理', '', null, '2', '1', '1', '3', '');
INSERT INTO `sys_menu` VALUES ('11', '首页模版', '/admin/desk', null, '7', '1', '2', '0', 'icon-laptop');
INSERT INTO `sys_menu` VALUES ('12', '首页模版2', '/admin/desk', null, '7', '1', '2', '1', '');
INSERT INTO `sys_menu` VALUES ('13', '文件数据', '/admin/desk', null, '11', '1', '2', '0', '');
INSERT INTO `sys_menu` VALUES ('14', '测试菜单', '/admin/cfg/tpl/14.html', null, '11', '1', '2', '0', '');
INSERT INTO `sys_menu` VALUES ('15', '权限管理', '', null, '6', '1', '1', '0', '');
INSERT INTO `sys_menu` VALUES ('16', '菜单管理', '/admin/forward/menu.html', null, '15', '1', '2', '0', 'icon-list');

-- ----------------------------
-- Table structure for sys_menu_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu_config`;
CREATE TABLE `sys_menu_config` (
  `menu_id` int(11) NOT NULL COMMENT '菜单id，外键',
  `cfg_search` varchar(1000) DEFAULT '' COMMENT '搜索表单配置部分，json格式存放数据',
  `cfg_grid` varchar(2000) NOT NULL DEFAULT '' COMMENT '搜索表单配置，json格式存放',
  `cfg_object` varchar(2000) DEFAULT '' COMMENT '对象表单配置，json',
  `cfg_global` varchar(5000) DEFAULT '' COMMENT '全局配置，json',
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='菜单页面资源配置表';

-- ----------------------------
-- Records of sys_menu_config
-- ----------------------------
INSERT INTO `sys_menu_config` VALUES ('14', '{\"cols\":[{\"label\":\"菜单编号\",\"name\":\"menu_id\",\"column\":\"menu_id\",\"type\":\"int\",\"widget\":\"text\",\"vali\":\"validate[custom[integer]]\",\"source\":\"\",\"value\":\"\"},{\"label\":\"菜单名称\",\"name\":\"menu_name\",\"column\":\"menu_name\",\"type\":\"varchar\",\"widget\":\"text\",\"vali\":\"\",\"source\":\"\",\"value\":\"\"},{\"label\":\"菜单状态\",\"name\":\"menu_state\",\"column\":\"menu_state\",\"type\":\"tinyint\",\"widget\":\"text\",\"vali\":\"\",\"source\":\"\",\"value\":\"\"}]}', '{\"url\":{\"list\":\"/admin/menu/list\",\"add\":\"\",\"del\":\"\",\"edit\":\"\"},\"option\":{\"colNames\":\"[\'菜单编号\', \'菜单名称\',\'菜单地址\', \'操作\']\",\"colModel\":\"[//config\\n\\t\\t\\t\\t\\t\\t\\t{name:\'menu_id\',index:\'menu_id\', width:60, sorttype:\\\"int\\\", editable: false},\\n\\t\\t\\t\\t\\t\\t\\t{name:\'menu_name\',index:\'menu_name\',width:90, editable:true},\\n\\t\\t\\t\\t\\t\\t\\t{name:\'menu_url\',index:\'menu_url\', width:150, editable: true, editoptions:{maxlength:\\\"30\\\"}},\\n\\t\\t\\t\\t\\t\\t\\t{name:\'myac\',index:\'\', width:80, fixed:true, sortable:false, resize:false,\\n\\t\\t\\t\\t\\t\\t\\t\\tformatter:\'actions\', \\n\\t\\t\\t\\t\\t\\t\\t\\tformatoptions:{\\n\\t\\t\\t\\t\\t\\t\\t\\t\\tkeys:true\\n\\t\\t\\t\\t\\t\\t\\t\\t}\\n\\t\\t\\t\\t\\t\\t\\t}\\n\\t\\t\\t\\t\\t\\t]\"}}', '[{\"label\":\"菜单名称\",\"type\":\"varchar\",\"widget\":\"text\",\"vali\":\"验证规则\",\"source\":\"\",\"value\":\"\"},{\"label\":\"菜单链接\",\"type\":\"varchar\",\"widget\":\"text\",\"vali\":\"验证规则\",\"source\":\"\",\"value\":\"\"},{\"label\":\"菜单描述\",\"type\":\"varchar\",\"widget\":\"text\",\"vali\":\"验证规则\",\"source\":\"\",\"value\":\"\"},{\"label\":\"父级菜单\",\"type\":\"int\",\"widget\":\"text\",\"vali\":\"验证规则\",\"source\":\"\",\"value\":\"\"},{\"label\":\"菜单排序\",\"type\":\"int\",\"widget\":\"text\",\"vali\":\"验证规则\",\"source\":\"\",\"value\":\"\"},{\"label\":\"菜单图标\",\"type\":\"varchar\",\"widget\":\"text\",\"vali\":\"验证规则\",\"source\":\"\",\"value\":\"\"}]', '{\"js\":\"var ad = null;function setAd(){    ad = \\\"nihao\\\";}\",\"html\":\"<a href=\\\"#\\\">你好</a>\",\"jssource\":\"<p>var ad = null;</p><p>function setAd(){</p><p>&nbsp; &nbsp; ad = \\\"nihao\\\";<br></p><p>}</p><p><br></p>\",\"hlsource\":\"<pre style=\\\"max-width:100%;overflow-x:auto;\\\"><code class=\\\"html hljs xml\\\" codemark=\\\"1\\\"><span class=\\\"hljs-tag\\\">&lt;<span class=\\\"hljs-name\\\">a</span> <span class=\\\"hljs-attr\\\">href</span>=<span class=\\\"hljs-string\\\">\\\"#\\\"</span>&gt;</span>你好<span class=\\\"hljs-tag\\\">&lt;/<span class=\\\"hljs-name\\\">a</span>&gt;</span></code></pre><p><br></p>\"}');
INSERT INTO `sys_menu_config` VALUES ('16', '{\"cols\":[{\"label\":\"菜单编号\",\"name\":\"menu_id\",\"column\":\"menu_id\",\"type\":\"int\",\"widget\":\"text\",\"vali\":\"\",\"source\":\"\",\"value\":\"\"},{\"label\":\"菜单名称\",\"name\":\"menu_name\",\"column\":\"menu_name\",\"type\":\"varchar\",\"widget\":\"text\",\"vali\":\"\",\"source\":\"\",\"value\":\"\"},{\"label\":\"菜单状态\",\"name\":\"menu_state\",\"column\":\"menu_state\",\"type\":\"tinyint\",\"widget\":\"text\",\"vali\":\"\",\"source\":\"\",\"value\":\"\"}]}', '', '[{\"label\":\"菜单名称\",\"type\":\"varchar\",\"widget\":\"text\",\"vali\":\"验证规则\",\"source\":\"\",\"value\":\"\"},{\"label\":\"菜单链接\",\"type\":\"varchar\",\"widget\":\"text\",\"vali\":\"验证规则\",\"source\":\"\",\"value\":\"\"},{\"label\":\"菜单描述\",\"type\":\"varchar\",\"widget\":\"text\",\"vali\":\"验证规则\",\"source\":\"\",\"value\":\"\"},{\"label\":\"父级菜单\",\"type\":\"int\",\"widget\":\"text\",\"vali\":\"验证规则\",\"source\":\"\",\"value\":\"\"},{\"label\":\"菜单排序\",\"type\":\"int\",\"widget\":\"text\",\"vali\":\"验证规则\",\"source\":\"\",\"value\":\"\"},{\"label\":\"菜单图标\",\"type\":\"varchar\",\"widget\":\"text\",\"vali\":\"验证规则\",\"source\":\"\",\"value\":\"\"}]', '{\"js\":\"\",\"html\":\"\",\"jssource\":\"<p><br></p>\"}');

-- ----------------------------
-- Table structure for sys_opt
-- ----------------------------
DROP TABLE IF EXISTS `sys_opt`;
CREATE TABLE `sys_opt` (
  `opt_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '操作id',
  `opt_code` varchar(20) NOT NULL DEFAULT '' COMMENT '操作编号',
  `opt_label` varchar(50) NOT NULL DEFAULT '',
  `opt_desc` varchar(200) DEFAULT '',
  `opt_state` tinyint(4) NOT NULL COMMENT '操作状态: 1 正常',
  `menu_id` int(11) NOT NULL COMMENT 'FK对应的菜单',
  `opt_seq` int(11) NOT NULL DEFAULT '0' COMMENT '操作排序',
  `opt_icon` varchar(200) DEFAULT '' COMMENT '操作图标',
  `opt_class` varchar(100) DEFAULT '' COMMENT '操作css类名',
  `is_show` tinyint(4) NOT NULL DEFAULT '1' COMMENT '是否显示:1 是, 0 否',
  `opt_type` varchar(20) DEFAULT '' COMMENT '操作分类',
  `opt_suburl` varchar(200) DEFAULT '' COMMENT '点击链接',
  `opt_handler` varchar(50) DEFAULT '' COMMENT '点击时调用的js方法',
  PRIMARY KEY (`opt_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_opt
-- ----------------------------
INSERT INTO `sys_opt` VALUES ('1', 'add', '新增', '', '1', '14', '0', 'icon-plus', 'btn-success', '1', 'outer', '', 'admin.addDto();');
INSERT INTO `sys_opt` VALUES ('2', 'edit', '编辑', '', '1', '14', '0', 'icon-edit', 'btn-danger', '1', 'outer', '', 'admin.editDto();');

-- ----------------------------
-- Table structure for sys_persistent_logins
-- ----------------------------
DROP TABLE IF EXISTS `sys_persistent_logins`;
CREATE TABLE `sys_persistent_logins` (
  `series` varchar(64) NOT NULL,
  `username` varchar(64) NOT NULL,
  `token` varchar(64) NOT NULL,
  `last_used` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`series`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='spring security 记住我时持久化登录用户';

-- ----------------------------
-- Records of sys_persistent_logins
-- ----------------------------

-- ----------------------------
-- Table structure for sys_right
-- ----------------------------
DROP TABLE IF EXISTS `sys_right`;
CREATE TABLE `sys_right` (
  `role_no` varchar(32) NOT NULL,
  `menu_id` int(11) NOT NULL,
  `opt_id` int(11) NOT NULL,
  `rt_type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '权限分类:1: 可分配给子角色, 0: 不可分配给子角色',
  PRIMARY KEY (`role_no`,`menu_id`,`opt_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统权限表';

-- ----------------------------
-- Records of sys_right
-- ----------------------------

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `role_no` varchar(32) NOT NULL,
  `role_name` varchar(200) NOT NULL,
  `role_pno` varchar(32) DEFAULT '' COMMENT '角色的父级角色',
  `role_desc` varchar(400) DEFAULT NULL,
  `role_state` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`role_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色表';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('super', '超级管理员', '', '超级管理员，拥有最高权限', '1');
INSERT INTO `sys_role` VALUES ('test', '测试', '', '测试角色', '1');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户标识，使用uuid设置',
  `user_pwd` varchar(32) NOT NULL,
  `user_salt` varchar(32) NOT NULL,
  `user_name` varchar(50) NOT NULL COMMENT '用户登录名，唯一约束，用于登录系统',
  `user_state` int(11) NOT NULL DEFAULT '0' COMMENT '用户状态，-1：删除，0：不可用，限制登陆，1：正常',
  `is_super` tinyint(4) DEFAULT '0' COMMENT '是否为超级管理源:0 否, 1 是',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `FK_SYS_USER_USER_NAME` (`user_name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=10002 DEFAULT CHARSET=utf8 COMMENT='系统用户主表';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('10000', 'BB855C106B50CEA148E553154E3DF151', 'yang', 'admin', '1', '0');
INSERT INTO `sys_user` VALUES ('10001', 'BB855C106B50CEA148E553154E3DF151', 'yang', 'test', '1', '0');

-- ----------------------------
-- Table structure for sys_user_info
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_info`;
CREATE TABLE `sys_user_info` (
  `user_id` int(11) NOT NULL COMMENT '系统用户唯一标识，关联SYS_USER',
  `real_name` varchar(50) NOT NULL,
  `user_avatar` varchar(1000) DEFAULT '/upload/image/avatar/default.gif' COMMENT '用户头像',
  `user_gender` tinyint(4) DEFAULT '1' COMMENT '0：女，1：男',
  `add_time` bigint(20) DEFAULT NULL,
  `update_time` bigint(20) DEFAULT NULL,
  `last_login_time` bigint(20) DEFAULT NULL,
  `last_login_ip` varchar(50) DEFAULT NULL,
  `user_phone` varchar(11) DEFAULT NULL,
  `user_email` varchar(100) DEFAULT NULL,
  `user_address` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  CONSTRAINT `FK_SYS_USER_INFO_USER_ID` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户详细信息表';

-- ----------------------------
-- Records of sys_user_info
-- ----------------------------

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `user_id` int(11) NOT NULL,
  `role_no` varchar(32) NOT NULL,
  PRIMARY KEY (`user_id`,`role_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户与角色关联表';

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------

-- ----------------------------
-- Procedure structure for pro_role_menus
-- ----------------------------
DROP PROCEDURE IF EXISTS `pro_role_menus`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `pro_role_menus`(in role varchar(36))
BEGIN 
  select group_concat(menu_scort) into @menus from sys_menu where menu_no in (select DISTINCT a.menu_no from sys_operate a left join sys_role_operate b on a.menu_no = b.menu_no and a.opt_no = b.opt_no where a.opt_state <> -1 and b.role_no = role) and menu_state = 1 group by menu_state;
  select a.menu_no,a.menu_fno,a.menu_name,a.menu_desc,a.menu_seq,a.menu_state,a.menu_level,a.menu_icon,a.menu_param,a.menu_scort from sys_menu a where FIND_IN_SET(a.menu_no, @menus) > 0;
END
;;
DELIMITER ;
