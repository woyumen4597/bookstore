/*
SQLyog Ultimate - MySQL GUI v8.2 
MySQL - 5.7.18-log : Database - bookstore
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`bookstore` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `bookstore`;

/*Table structure for table `book` */

DROP TABLE IF EXISTS `book`;

CREATE TABLE `book` (
  `id` varchar(40) NOT NULL,
  `name` varchar(100) NOT NULL,
  `author` varchar(100) NOT NULL,
  `price` double NOT NULL,
  `image` varchar(100) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `category_id` varchar(40) DEFAULT NULL,
  `sales` int(11) DEFAULT '0',
  `stock` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `category_id_FK` (`category_id`),
  CONSTRAINT `category_id_FK` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `book` */

insert  into `book`(`id`,`name`,`author`,`price`,`image`,`description`,`category_id`,`sales`,`stock`) values ('123-245-351-564-1','数据结构','沈俊',44,'http://123.206.130.92/images/2017/08/25/1665643264656464.jpg','优秀教材','4cf43b8f-92d7-4787-83f0-6c5b078640f8',56,49),('123-456-789-111-2','软件工程','张海番',39.5,'http://123.206.130.92/images/2017/08/25/3.jpg','有用之书','3224762d-f8a0-4f3d-974d-f84f4179f9e5',4,49),('123-526-411-2235','数据结构2','沈俊',12,'http://123.206.130.92/images/2017/08/25/1665643264656464.jpg','哈哈哈','3224762d-f8a0-4f3d-974d-f84f4179f9e5',3,48),('253-125-654-123-5','概率论与数理统计','小金',25,'http://123.206.130.92/images/2017/08/25/5.jpg','概率论','59d8ce19-0d27-48d9-8ac5-997064e7a280',3,48),('25613235645','经济学原理 ','曼昆',52,'http://123.206.130.92/images/2017/08/25/1503632447380333.jpg',NULL,'d4688010-a1dd-4125-b3cc-fd07e44b964d',5,45),('3149613146','营销管理','菲利普·科特勒（Philip Kotler',57.6,'http://123.206.130.92/images/2017/08/30/1504067756765052.jpg',NULL,'d4688010-a1dd-4125-b3cc-fd07e44b964d',0,50),('561364623949','我们内心的冲突','卡伦·霍妮',33.2,'http://123.206.130.92/images/2017/08/25/1503640580498631.jpg',NULL,'c6bc6331-5e48-4f82-b5f9-a5d5bb9b9e59',1,50),('978-712-123-245-2','离散数学','清华大学',39,'http://123.206.130.92/images/2017/08/25/6.jpg','又是一本好书啊','3224762d-f8a0-4f3d-974d-f84f4179f9e5',52,50);

/*Table structure for table `book_detail` */

DROP TABLE IF EXISTS `book_detail`;

CREATE TABLE `book_detail` (
  `id` varchar(40) NOT NULL,
  `desc` text,
  `publisher` varchar(40) NOT NULL,
  `publish_time` varchar(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `book_detail` */

insert  into `book_detail`(`id`,`desc`,`publisher`,`publish_time`) values ('123-245-351-564-1','难得的好书！','上海大学出版社','2014-09-01'),('123-456-789-111-2','计算机领域实用的书籍！','工业出版社','2014-03-01'),('123-526-411-2235','同样的好书！','上海大学出版社','2014-09-01'),('25613235645','上大<img src=\"http://123.206.130.92/images/2017/08/25/1503632478992616.jpg\" alt=\"\" />','北京大学出版社','2015-05-11'),('3149613146','<h2 style=\"font-size:16px;font-weight:normal;color:#646464;font-family:Verdana, &quot;background-color:#FFFFFF;\">\r\n	<span class=\"head_title_name\" style=\"color:#323232;\">面向移动互联网时代的营销圣经| 科特勒《营销管理》在中国独家合法授权的版本 | 这就是大家口口相传的“营销圣经”，被列入各种管理类图书的必读书单 |作者科特勒被誉为“营销教父”，启迪了一代又一代营销人</span>\r\n</h2>','格致出版社','2016年07月'),('561364623949','<h2 style=\"font-size:16px;font-weight:normal;color:#646464;font-family:Verdana, &quot;background-color:#FFFFFF;\">\r\n	<span class=\"head_title_name\" style=\"color:#323232;\">苏芩强力推荐，国际著名精神分析领军人物，豆瓣评分高达9分的经典之作，深刻揭秘矛盾复杂的心理真相，让你对号入座、脊背发凉之后，重建人生自信的心理学，活出完整、成熟、内在安宁的自己！&nbsp;</span>\r\n</h2>\r\n<img src=\"http://123.206.130.92/images/2017/08/25/1503640613799459.jpg\" alt=\"\" />','长江文艺出版社','2017-01-05'),('66463156465','<h2>\r\n	好书\r\n</h2>','北京大学出版社','2015-05-31'),('978-712-123-245-2','实用之书！','清华大学出版社','2015-06-01');

/*Table structure for table `category` */

DROP TABLE IF EXISTS `category`;

CREATE TABLE `category` (
  `id` varchar(40) NOT NULL,
  `name` varchar(100) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `category` */

insert  into `category`(`id`,`name`,`description`) values ('3224762d-f8a0-4f3d-974d-f84f4179f9e5','计算机','计算机相关'),('4cf43b8f-92d7-4787-83f0-6c5b078640f8','科学','科学相关'),('59d8ce19-0d27-48d9-8ac5-997064e7a280','数学','数学相关'),('c6bc6331-5e48-4f82-b5f9-a5d5bb9b9e59','心理学','心理学相关'),('d4688010-a1dd-4125-b3cc-fd07e44b964d','经济学','经济学相关');

/*Table structure for table `comment` */

DROP TABLE IF EXISTS `comment`;

CREATE TABLE `comment` (
  `id` varchar(40) NOT NULL,
  `book_id` varchar(40) NOT NULL,
  `comment` text NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `user_name` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `comment` */

insert  into `comment`(`id`,`book_id`,`comment`,`create_time`,`user_name`) values ('134646','123-245-351-564-1','这本书很有用的','2015-03-08 21:33:18','jrc'),('150372432297238','123-245-351-564-1','这本书真好看！','2017-08-26 13:12:03','jrc');

/*Table structure for table `orderitem` */

DROP TABLE IF EXISTS `orderitem`;

CREATE TABLE `orderitem` (
  `id` varchar(40) NOT NULL,
  `quantity` int(11) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `order_id` varchar(40) DEFAULT NULL,
  `book_id` varchar(40) DEFAULT NULL,
  `address` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `order_id_FK` (`order_id`),
  KEY `book_id_FK` (`book_id`),
  CONSTRAINT `book_id_FK` FOREIGN KEY (`book_id`) REFERENCES `book` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `orderitem` */

insert  into `orderitem`(`id`,`quantity`,`price`,`order_id`,`book_id`,`address`) values ('804c1f06-249a-4b1c-9c2a-8ad28aed3f19',1,52,'74d236f2-9272-4ddd-837e-2a0d33326fa4','25613235645','上海');

/*Table structure for table `orders` */

DROP TABLE IF EXISTS `orders`;

CREATE TABLE `orders` (
  `id` varchar(40) NOT NULL,
  `ordertime` datetime NOT NULL,
  `price` double NOT NULL,
  `state` tinyint(1) DEFAULT NULL COMMENT '0:未发货 1:已发货 2:已取消 3:已完成',
  `user_id` varchar(40) DEFAULT NULL,
  `express_num` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id_FK` (`user_id`),
  CONSTRAINT `user_id_FK` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `orders` */

insert  into `orders`(`id`,`ordertime`,`price`,`state`,`user_id`,`express_num`) values ('74d236f2-9272-4ddd-837e-2a0d33326fa4','2017-08-27 12:42:12',52,0,'7ba61df8-7d80-496c-8e64-3d114e6515c4',NULL);

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` varchar(40) NOT NULL,
  `username` varchar(40) NOT NULL,
  `password` varchar(40) NOT NULL,
  `phone` varchar(40) NOT NULL,
  `cellphone` varchar(40) NOT NULL,
  `email` varchar(40) NOT NULL,
  `address` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `user` */

insert  into `user`(`id`,`username`,`password`,`phone`,`cellphone`,`email`,`address`) values ('18cf32b4-844b-4437-873b-40e74dac74f4','小金','123456','111','222','333','444'),('69b1a5a4-9047-413e-9333-097770e65da4','小陆','123','222','333','11','44'),('7ba61df8-7d80-496c-8e64-3d114e6515c4','jrc','1234','63531234','18958835351','337417702@qq.com','上海,温州'),('7dbf069a-0058-4ecd-af6e-c626910cc0e5','jjj','1234','123','123','666','杭州'),('ec5e71c6-32fd-4fa4-a17b-805aa00c4f21','小王','123','111','222','333','111');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
