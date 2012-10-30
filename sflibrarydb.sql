# Host: 192.168.25.194  (Version: 5.5.27)
# Date: 2012-10-30 15:50:10
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

INSERT INTO `sl_book` VALUES ('无后为大','颜景豪','123456987','同济大学','2012-10-30','中文',99.00,'小说/文学','颜景豪','无后为大简介',1,1,1,'IMG1351575421315001','2012-10-30 13:37:52');

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

INSERT INTO `sl_user` VALUES ('David (Wei) Zhang',' wzhang@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者','PM/GM'),('April (Fangxue) Mei','amei@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者',''),('Anthony (YuanDong) Chen','anthonychen@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者',''),('Angela (Dan) Wu','awu@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者','GM'),('Zhaoyu(Alex) Xu','axu@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者','API'),('Tiechen (Achilles) Xue','axue@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者','PM/GM/360'),('Alex (Jing Hao) Yan','ayan@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','管理员',''),('Alex (Hui) Zhang','azhang@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者','Performance'),('Ada (Ting) Zhong','azhong@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者',''),('Benny (Benduo) Huang','bhuang@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者',''),('You (Bryan) Li','bli@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者','Platform'),('Betty(Le) PENG','bpeng@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者','Employee Center '),('Bridget (Zhao Hui) Yin','byin@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者',''),('Ryan(Chao) Deng','cdeng@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','管理员',''),('Clyde (Ruo Jun) Meng','cmeng@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者',''),('Chuyan (Echo) SONG','csong@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者','PM/GM/360/BPA'),('Jeff (cui) Wang','cuiwang@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者',''),('Chris (Bo) Wang','cwang@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者','GM'),('Carter (Tianliang) Xie','cxie@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者','Comp'),('Cherie (Huiying) Zhou','czhou@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者','Calibration'),('Zoe (Chengyan) Zhu','czhu@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者',''),('Dongqing Hu','dhu@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者',''),('Edwin (Dewei) Liu','dliu@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者',''),('Dongmei (Sharon) WU','dswu@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者',''),('Dan (Daniel) WANG','dwang@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者',''),('Daniel (Xudong) Wei','dwei@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','管理员',''),('Dawei(David) XING','dxing@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者',''),('Daniel (Da) Xu','dxu@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者',''),('Delia (Juncheng) Yang','dyang@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者',''),('Da Zhang','dzhang@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者',''),('Mao (Eric) GENG','egeng@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者','Perf'),('Eugene (Yijun) Jiang','ejiang@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者','Automation'),('Emil (Yunfeng) Jiang','emiljiang@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者',''),('Eric (Wei) Wang','ewang@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者','PM'),('Fei (Jackie) CHENG','fcheng@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者',''),('Yvonne (Fan) Pan','fpan@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者',''),('Fan Wang','fwang@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者',''),('Fan (Fred) YANG','fyan@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者','CDP'),('Leo (Gang) Chen','gchen@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者','Platform'),('George (Kui) Sun','gsun@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者','GM'),('Grace(Xia) Yu','gyu@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者','API'),('Gequn (Emen) Zhao','gzhao@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者','PM/GM'),('Haiming Li','haimingli@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','管理员',''),('Haixun (Tim) Lu','haixunlu@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者',''),('Caddy (Hanbing) Yang','hanbingyang@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者',''),('Hong (Chris) CHENG','hcheng@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者','PM/GM/360/BPA'),('Handle (Xiaosheng) Huang','hhuang@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者','PM'),('Helen(Hong) Li','hli@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者','Special Project'),('Huawei Liu','hliu@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者',''),('Hua(Howard) Xie','hxie@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者',''),('Hong YUAN','hyuan@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者',''),('Irena (Yi) Sun','isun@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者',''),('Jason (Shengjie) Wang','jawang@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者','Platform'),('Chen Jin','jchen@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者',''),('Dennis (Ji) Wu','jdwu@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者',''),('Zhe (Jeff) Liu','jeffliu@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者',''),('Jessie (JiaZhu) Chen','jessiechen@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者',''),('Jessie (Jieying) Zhang','jessiezhang@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者',''),('Jianping (Lynn) FAN ','jfan@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者',''),('Jen (Jie) Han','jhan@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者','Platform'),('Jing He','jhe@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','管理员',''),('Jon (Jun) Hu','jhu@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者',''),('JingJiang Huang','jhuang@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者','PM'),('Jian (Gene) WU','jian@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者',''),('Jie Bao','jiebao@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者',''),('Jing Chen','jingchen@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者','mobile'),('Jin (Joe) Yang','jinyang@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者',''),('Break (Jing) Liu','jliu@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者','SMB'),('Jacob (Jiqi) Mee','jmee@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者',''),('Xieyin(Jovi)Shen','jshen@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者','Compensation'),('Jiaping (Justine) Huang','juhuang@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者','Employee Center'),('Junfei (Jennifer) Wang','juwang@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者','TGM'),('Jeremy (Jiajun) Xie','jxie@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者',''),('Joice (Yinqiu) Yu','jyu@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者','Employee Central'),('Goddy (Jing) Zhao','jzhao@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者','Platform'),('Kalvin (Yuanming) Ge','kge@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者','Varpay '),('Keqin Liu','kliu@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者',''),('Keying Xu','kxu@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者','Platform'),('Kenny (Xiao Qiu) Xu','kxxu@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者','ECT'),('Kejun (Harvey) ZHU','kzhu@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者','CDP '),('Lynn (Yan) Hu','lhu@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者',''),('Bailey (Liyu) Xu','liyuxu@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','管理员',''),('Li Zhu','lizhu@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者',''),('Lu (Michael) LUO','lluo@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者','Platform'),('Jiandong (Luke) SHI','lshi@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者',''),('XianGang(Luke) Wen','lwen@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者','CDP'),('Ling (Wesley) ZHOU','lzhou@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者','Recruiting'),('Megan (Minyue) Wang','meganwang@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','管理员',''),('Mark (Xin) Feng','mfeng@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者',''),('Mike (Sheng) Gao','mgao@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者','Special Project'),('Miranda He','mhe@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者',''),('Michael (Hao) Lin','mlin@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者','Platform'),('Liang(Miracle)Ouyang','mouyang@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者','CDP'),('Yang (Mike) Peng','mpeng@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者','API'),('Zalman (Ming) Zeng','mzeng@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者',''),('Ming Zhang','mzhang@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者',''),('Guanglei (Mark) Zhu','mzhu@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者','Varpay '),('Nina (Jun) Guo','nguo@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者',''),('Danshi (Nickel) HE','nhe@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者',''),('Jessie (Ni) Zhang','nzhang@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者',''),('Payne (Xu) Chen','pchen@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者',''),('Peihui (Cherry) LI','pli@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者','PM/BPA'),('Pengkai Qin','pqin@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者','Platform'),('Peter (Yue) Zhao','pzhao@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者','Platform '),('Ben (Bin) Qian','qbin@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者',''),('Cao Qi (Kim)','qcao@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者',''),('Qiuyan (Evelyn) GONG','qgong@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者',''),('Qiao (Qiaofeng) Qin','qqin@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','管理员',''),('Richard (Geng) QIN','rqin@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者',''),('Jun (Remy) WANG','rwang@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者','API'),('Roy (Jia) Zhang','rzhang@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者','Performance'),('Song Bai','sbai@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者',''),('Sarah (Chen) Cao','scao@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者',''),('Sarah (Yajun) Hu','shu@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者',''),('Simin (Simon) Chen','siminchen@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者',''),('Shawn (Jinhao) Shao','sshao@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者',''),('Shuangshuang SHI','sshi@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者',''),('Shangwang (Sherwin) WANG','swang@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者','Perf'),('Sally(YingJie) Weng','sweng@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者',''),('Shan (Sam) XIAO','sxiao@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者','Varpay '),('Steven (Yulong) Yang','syang@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者',''),('Jack (Siming) Yao','syao@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者',''),('Shenyi (Sandra) YIN','syin@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者',''),('Sara (Jinjuan) You','syou@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者','PM'),('Shijia Yu','syu@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者',''),('Sky(Yun) Wang','sywang@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者',''),('Ted (Jia) Feng','tfeng@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者','Platform'),('Tina (Yu) Qian','tqian@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者',''),('Tommy (Yijun) Xu','txu@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者','SMB'),('Terry (Zhi) Zhang','tzhang@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者','Platform'),('Vicky (Yiling) Chen','vchen@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者','N/A'),('Vivian (Lan) Li','vli@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者',''),('Vito LIU','vliu@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者',''),('Bryan (Wei) Chen','wchen@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者','Performance'),('Kelvin (Kang) Wei','wkang@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者',''),('Wright (Kun) Liu','wkliu@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者',''),('Cong (Willy) LIU','wliu@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者',''),('Wills (Yang) Tan','wtan@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者',''),('William (Yu) Wu','wwu@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者',''),('Xiaodong Bi','xbi@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者',''),('Xiaoxiao (Jerome) GU','xgu@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','管理员','TBD'),('Xiao Han','xhan@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者','Platform'),('Xiaotong Li','xiaotongli@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者',''),('Xinhua (Steven) LI','xli@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者',''),('Xuesong Luo','xluo@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者','Platform'),('Xiaoyi (Shane) Wang','xwang@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者','Varpay '),('Xudong Wu','xwu@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者',''),('Xiaofei (Bill) XU','xxu@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者','PM/GM/360/BPA'),('Lily (Yajun) Chen','yajunchen@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者',''),('Yanxi HOU','yanxihou@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者',''),('Yijing (Anderson) CUI','ycui@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者','Platform'),('Amy (Ye) Liu','yeliu@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者',''),('Yuna (Elle) Hou','yhou@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者','Platform'),('You, La','yla@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者','CORP IT'),('Yu Le','yle@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者','ECT'),('Yi (Eric) LIN','ylin@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者',''),('Yichang TIAN','ytian@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者','CDP'),('Yannian (Ian) WU','ywu@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者',''),('Yang Yuanhao','yyang@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者','Platform'),('Jacky (Yi) Zhang','yzhang@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者','Platform'),('Yixin (Derek) ZHU','yzhu@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者','SM '),('Eddie (ZhaoJian) Dou','zdou@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者','Mobile'),('Erik (Zuxing) Wang','zwang@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者',''),('ZongHan Wu','zwu@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者',''),('Carol (Zhenzhi) Xu','zxu@successfactors.com','E10ADC3949BA59ABBE56E057F20F883E','读者','');

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
