SET FOREIGN_KEY_CHECKS=0;

DROP TABLE IF EXISTS `authority`;
CREATE TABLE `authority` (
  `authority` varchar(50) NOT NULL,
  `name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`authority`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `authority` VALUES ('admin', '超级管理员');
INSERT INTO `authority` VALUES ('test', 'test1');
INSERT INTO `authority` VALUES ('user', '普通用户');

DROP TABLE IF EXISTS `authority_function`;
CREATE TABLE `authority_function` (
  `authority` varchar(50) NOT NULL COMMENT '权限',
  `function_id` varchar(50) NOT NULL COMMENT '功能名称',
  PRIMARY KEY (`authority`,`function_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `authority_function` VALUES ('admin', '99');
INSERT INTO `authority_function` VALUES ('admin', '9901');
INSERT INTO `authority_function` VALUES ('admin', '9902');
INSERT INTO `authority_function` VALUES ('admin', '9903');
INSERT INTO `authority_function` VALUES ('test', '99');
INSERT INTO `authority_function` VALUES ('test', '9901');
INSERT INTO `authority_function` VALUES ('test', '9902');
INSERT INTO `authority_function` VALUES ('test', '9903');
INSERT INTO `authority_function` VALUES ('user', '0101');
INSERT INTO `authority_function` VALUES ('user', '0102');
INSERT INTO `authority_function` VALUES ('user', '0103');

DROP TABLE IF EXISTS `function`;
CREATE TABLE `function` (
  `id` varchar(50) NOT NULL COMMENT '功能编号',
  `p_id` varchar(50) DEFAULT NULL COMMENT '父功能编号',
  `name` varchar(50) DEFAULT NULL COMMENT '功能名称',
  `url` varchar(50) DEFAULT NULL COMMENT '功能链接',
  `icon` varchar(50) DEFAULT NULL COMMENT '功能小图标class',
  `match_url` text COMMENT '功能匹配的urll列表',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `function` VALUES ('99', '0', '系统设置', '', 'am-icon-gears', '');
INSERT INTO `function` VALUES ('9901', '99', '用户管理', '/users', 'am-icon-user', '/users**');
INSERT INTO `function` VALUES ('9902', '99', '角色管理', '/authorities', 'am-icon-unlock-alt', '/authorities**');
INSERT INTO `function` VALUES ('9903', '99', '菜单管理', '/functions', 'am-icon-list', '/functions**');

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `password` varchar(100) NOT NULL,
  `enabled` tinyint(1) NOT NULL COMMENT '是否已启用',
  `name` varchar(50) DEFAULT NULL COMMENT '姓名',
  `phone` varchar(50) DEFAULT NULL COMMENT '手机号',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

INSERT INTO `user` VALUES ('1', 'wxd', '$2a$10$83Mzctqf5ThiEIKO5EYXKutD0xetLjACAhbulxAANMhACVl8APEku', '1', '吴先生', '', '');
INSERT INTO `user` VALUES ('2', 'rzc', '$2a$10$83Mzctqf5ThiEIKO5EYXKutD0xetLjACAhbulxAANMhACVl8APEku', '1', '任先生', '', '');

DROP TABLE IF EXISTS `user_authority`;
CREATE TABLE `user_authority` (
  `id` int(11) NOT NULL,
  `authority` varchar(50) NOT NULL,
  PRIMARY KEY (`id`,`authority`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `user_authority` VALUES ('1', 'admin');
INSERT INTO `user_authority` VALUES ('1', 'user');
INSERT INTO `user_authority` VALUES ('2', 'admin');
INSERT INTO `user_authority` VALUES ('2', 'user');
INSERT INTO `user_authority` VALUES ('18', 'admin');
INSERT INTO `user_authority` VALUES ('18', 'user');
INSERT INTO `user_authority` VALUES ('21', 'admin');
INSERT INTO `user_authority` VALUES ('21', 'test');
INSERT INTO `user_authority` VALUES ('21', 'user');
