# Host: 192.168.25.194  (Version: 5.5.27)
# Date: 2012-10-30 12:16:23
# Generator: MySQL-Front 5.3  (Build 1.0)

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE */;
/*!40101 SET SQL_MODE='STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES */;
/*!40103 SET SQL_NOTES='ON' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS */;
/*!40014 SET FOREIGN_KEY_CHECKS=0 */;

DROP DATABASE IF EXISTS `sflibrarydb`;
CREATE DATABASE `sflibrarydb` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `sflibrarydb`;

#
# Source for table "sl_book"
#

DROP TABLE IF EXISTS `sl_book`;
CREATE TABLE `sl_book` (
  `bookName` varchar(255) NOT NULL DEFAULT '' COMMENT '书名',
  `bookAuthor` varchar(255) NOT NULL DEFAULT '' COMMENT '作者',
  `bookISBN` varchar(20) NOT NULL DEFAULT '' COMMENT 'ISBN编号',
  `bookPublisher` varchar(255) NOT NULL DEFAULT '' COMMENT '出版商',
  `bookPublishDate` date NOT NULL DEFAULT '0000-00-00' COMMENT '出版时间',
  `bookLanguage` varchar(10) NOT NULL DEFAULT '中文' COMMENT '语言',
  `bookPrice` double(4,2) NOT NULL DEFAULT '0.00' COMMENT '价格RMB',
  `bookClass` varchar(10) NOT NULL DEFAULT '' COMMENT '类别',
  `bookContributor` varchar(50) NOT NULL DEFAULT '公司采购' COMMENT '贡献者',
  `bookIntro` text NOT NULL COMMENT '简介',
  `bookTotalQuantity` int(11) NOT NULL DEFAULT '0' COMMENT '库存总数（本）',
  `bookInStoreQuantity` int(11) NOT NULL DEFAULT '0' COMMENT '在库数量',
  `bookAvailableQuantity` int(11) NOT NULL DEFAULT '0' COMMENT '可借总数',
  `bookPicUrl` varchar(255) NOT NULL DEFAULT '/upload/picture/nopic.jpg' COMMENT '图片URL',
  `bookAddDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '图书入库日期',
  PRIMARY KEY (`bookISBN`),
  KEY `bookName` (`bookName`),
  KEY `bookClass` (`bookClass`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='书籍表';

#
# Data for table "sl_book"
#


#
# Source for table "sl_user"
#

DROP TABLE IF EXISTS `sl_user`;
CREATE TABLE `sl_user` (
  `userName` varchar(50) NOT NULL DEFAULT '' COMMENT '姓名',
  `userEmail` varchar(100) NOT NULL DEFAULT '' COMMENT '邮箱（PK）',
  `userPassword` varchar(255) NOT NULL DEFAULT '' COMMENT '登录密码',
  `userType` varchar(5) NOT NULL DEFAULT '读者' COMMENT '用户类型（管理员/读者）',
  `userDepartment` varchar(50) NOT NULL DEFAULT '' COMMENT '所属团队',
  PRIMARY KEY (`userEmail`),
  KEY `userType` (`userType`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';

#
# Data for table "sl_user"
#

INSERT INTO `sl_user` VALUES ('Alex Yan','ayan@successfactors.com','yanhao','管理员','3');

#
# Source for table "sl_order"
#

DROP TABLE IF EXISTS `sl_order`;
CREATE TABLE `sl_order` (
  `orderId` int(11) NOT NULL AUTO_INCREMENT,
  `userEmail` varchar(100) NOT NULL DEFAULT '' COMMENT '借书人Email',
  `bookISBN` varchar(20) NOT NULL DEFAULT '' COMMENT '图书ISBN',
  `orderDate` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '预定日期',
  `status` varchar(5) NOT NULL DEFAULT '排队中' COMMENT '排队中、已取消、已借到',
  PRIMARY KEY (`orderId`),
  KEY `userEmail` (`userEmail`),
  KEY `bookISBN` (`bookISBN`),
  CONSTRAINT `sl_order_ibfk_1` FOREIGN KEY (`userEmail`) REFERENCES `sl_user` (`userEmail`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `sl_order_ibfk_2` FOREIGN KEY (`bookISBN`) REFERENCES `sl_book` (`bookISBN`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='当前预订';

#
# Data for table "sl_order"
#


#
# Source for table "sl_borrow"
#

DROP TABLE IF EXISTS `sl_borrow`;
CREATE TABLE `sl_borrow` (
  `borrowId` int(11) NOT NULL AUTO_INCREMENT,
  `userEmail` varchar(100) NOT NULL DEFAULT '' COMMENT '借书人Email',
  `bookISBN` varchar(20) NOT NULL DEFAULT '' COMMENT '图书ISBN',
  `borrowDate` date NOT NULL DEFAULT '0000-00-00' COMMENT '借书日期',
  `shouldReturnDate` date NOT NULL DEFAULT '0000-00-00' COMMENT '应还日期',
  `returnDate` date DEFAULT NULL COMMENT '归还日期',
  `inStore` int(1) NOT NULL DEFAULT '1' COMMENT '是否仍在库',
  `overdue` int(1) NOT NULL DEFAULT '0' COMMENT '是否超期',
  `renewTimes` int(1) NOT NULL DEFAULT '0' COMMENT '续借次数',
  `status` varchar(5) NOT NULL DEFAULT '未归还' COMMENT '未归还，已归还，已超期',
  PRIMARY KEY (`borrowId`),
  KEY `bookISBN` (`bookISBN`),
  KEY `userEmail` (`userEmail`),
  CONSTRAINT `sl_borrow_ibfk_1` FOREIGN KEY (`bookISBN`) REFERENCES `sl_book` (`bookISBN`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `sl_borrow_ibfk_2` FOREIGN KEY (`userEmail`) REFERENCES `sl_user` (`userEmail`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='当前借阅';

#
# Data for table "sl_borrow"
#


/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
