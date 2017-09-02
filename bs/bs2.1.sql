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

insert  into `book`(`id`,`name`,`author`,`price`,`image`,`description`,`category_id`,`sales`,`stock`) values ('123-245-351-564-1','数据结构','沈俊',44,'079784fb-fa66-493e-9f33-8cc87675ef48.jpg','优秀教材','4cf43b8f-92d7-4787-83f0-6c5b078640f8',7,0),('123-456-789-111-2','软件工程','张海番',39.5,'984e3bf9-7972-444b-8410-db0a8033adab.jpg','有用之书','3224762d-f8a0-4f3d-974d-f84f4179f9e5',3,48),('123-526-411-2235','数据结构2','沈俊',12,'e7b3ac29-724b-4be5-9a3f-5f7333ac2412.','哈哈哈','3224762d-f8a0-4f3d-974d-f84f4179f9e5',3,48),('253-125-654-123-5','概率论与数理统计','小金',25,'7721b0b7-7a53-4208-9c80-bc53f1c73218.jpg','概率论','59d8ce19-0d27-48d9-8ac5-997064e7a280',3,48),('26743491972','经济学原理 ','曼昆',21,'a46d5caf-79b4-412d-a26f-deff734d8451.jpg',NULL,'4cf43b8f-92d7-4787-83f0-6c5b078640f8',0,50),('564-123-456-785-1','高等数学','杨坚',15,'a039e400-6b4d-4688-82a9-c2ad12a861f4.jpg','好书啊！','4cf43b8f-92d7-4787-83f0-6c5b078640f8',2,49),('66463156465','经济学原理','昆猫',63,'http://123.206.130.92/images/2017/08/24/1503587116699246.jpg',NULL,'4cf43b8f-92d7-4787-83f0-6c5b078640f8',0,60),('978-712-123-245-2','离散数学','清华大学',39,'118420b7-6d6d-427b-9ef9-51009d5d5fd3.jpg','又是一本好书啊','3224762d-f8a0-4f3d-974d-f84f4179f9e5',2,0),('沙发','但是','二维',23,'http://123.206.130.92/images/2017/08/24/1503587684435203.jpg',NULL,'3224762d-f8a0-4f3d-974d-f84f4179f9e5',0,23);

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

insert  into `book_detail`(`id`,`desc`,`publisher`,`publish_time`) values ('66463156465','<h2>\r\n	好书\r\n</h2>','北京大学出版社','2015-05-31');

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

insert  into `category`(`id`,`name`,`description`) values ('3224762d-f8a0-4f3d-974d-f84f4179f9e5','计算机','计算机相关'),('4cf43b8f-92d7-4787-83f0-6c5b078640f8','科学','科学相关'),('59d8ce19-0d27-48d9-8ac5-997064e7a280','数学','数学相关');

/*Table structure for table `orderitem` */

DROP TABLE IF EXISTS `orderitem`;

CREATE TABLE `orderitem` (
  `id` varchar(40) NOT NULL,
  `quantity` int(11) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `order_id` varchar(40) DEFAULT NULL,
  `book_id` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `order_id_FK` (`order_id`),
  KEY `book_id_FK` (`book_id`),
  CONSTRAINT `book_id_FK` FOREIGN KEY (`book_id`) REFERENCES `book` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `orderitem` */

insert  into `orderitem`(`id`,`quantity`,`price`,`order_id`,`book_id`) values ('04ae4452-562d-45a5-b462-0dfd6b4a696a',73,2883.5,'824da619-24fc-4b3f-9bf2-209ba32cc954','123-456-789-111-2'),('1cc204be-cb25-4803-b7b9-e78a58bfdce8',50,1950,'94b95111-74d6-4aeb-9f5b-f3606aa29a40','978-712-123-245-2'),('26efbaf2-916c-4f0f-b765-7c8de9857188',1,39.5,'6c7e3ebf-f029-4000-8291-3f7ad216e3e6','123-456-789-111-2'),('4193b4be-3644-4d25-ba11-331424f4f782',1,44,'0f1905d2-7eff-42ee-8f03-c5850b1ca90a','123-245-351-564-1'),('4797770a-519e-4c3b-9fee-929ed3d0451c',2,88,'c0d01397-b1a3-4ca7-ab9f-be39cffd9c38','123-245-351-564-1'),('72bf68fa-2a81-479f-856e-aeed2dc5f471',5,220,'7fc39f7a-bb79-4371-a22a-ee647047a7ba','123-245-351-564-1'),('72c55558-c910-4a01-b9b9-c6d15de4356a',1,44,'fb66d51f-b337-4405-85d0-fc491af09d1c','123-245-351-564-1'),('87375989-6351-4ab0-b5dc-567560f90a81',1,12,'26749dba-bfd4-47b6-9f25-825eec9b1f93','123-526-411-2235'),('8aa252b1-a059-4ec9-8b18-91aa77175dac',1,39,'c341c9e1-404f-4cc7-a6f9-c06d041af273','978-712-123-245-2'),('acdf377b-7e0b-4c36-ba8c-9781ade31c8e',1,25,'a1a00233-260e-4752-a3ad-a87f1e41d028','253-125-654-123-5'),('b9734b10-bcd2-4ab1-a3ac-3c6665f54615',3,132,'6c7e3ebf-f029-4000-8291-3f7ad216e3e6','123-245-351-564-1'),('ba5c1be7-c16e-49b0-85c4-3668957a1441',1,39.5,'1942ce40-057d-4766-be73-856657ccf4a8','123-456-789-111-2'),('bb92e733-2bba-40b3-b545-820de922c34c',1,39.5,'26749dba-bfd4-47b6-9f25-825eec9b1f93','123-456-789-111-2'),('bba446bc-7e91-490e-be05-0087dc4bc381',39,1716,'37f5ffc4-d558-4b37-bb66-ba02dfb7fc52','123-245-351-564-1'),('c420ac5d-f674-437c-b046-a2b277287b6d',1,15,'f3f222f5-1f8c-4eb0-a00f-98f9118d3f7f','564-123-456-785-1'),('e66b5ddd-ff98-4365-bc91-d7b042031223',1,25,'8932233c-10d7-4e3b-92c6-244ff83ce0bb','253-125-654-123-5'),('e75d02c8-ac1b-4e66-a14c-df2a4aa40844',1,12,'8043405a-e1c0-4599-b877-0b04319f3835','123-526-411-2235'),('f901e12d-ed13-4d75-a2d5-b336cc1fe669',1,44,'824f2a6f-e541-42e9-b442-9321385764ca','123-245-351-564-1');

/*Table structure for table `orders` */

DROP TABLE IF EXISTS `orders`;

CREATE TABLE `orders` (
  `id` varchar(40) NOT NULL,
  `ordertime` datetime NOT NULL,
  `price` double NOT NULL,
  `state` tinyint(1) DEFAULT NULL,
  `user_id` varchar(40) DEFAULT NULL,
  `express_num` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id_FK` (`user_id`),
  CONSTRAINT `user_id_FK` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `orders` */

insert  into `orders`(`id`,`ordertime`,`price`,`state`,`user_id`,`express_num`) values ('26749dba-bfd4-47b6-9f25-825eec9b1f93','2017-07-10 10:37:20',51.5,1,'7dbf069a-0058-4ecd-af6e-c626910cc0e5',NULL),('37f5ffc4-d558-4b37-bb66-ba02dfb7fc52','2017-07-10 10:16:27',1716,0,'7ba61df8-7d80-496c-8e64-3d114e6515c4',NULL),('6c7e3ebf-f029-4000-8291-3f7ad216e3e6','2017-07-10 10:20:27',171.5,0,'7ba61df8-7d80-496c-8e64-3d114e6515c4',NULL),('7fc39f7a-bb79-4371-a22a-ee647047a7ba','2017-07-06 12:26:08',220,0,'7ba61df8-7d80-496c-8e64-3d114e6515c4',NULL),('8043405a-e1c0-4599-b877-0b04319f3835','2017-07-10 10:32:44',12,3,'7dbf069a-0058-4ecd-af6e-c626910cc0e5',NULL),('824f2a6f-e541-42e9-b442-9321385764ca','2017-07-06 12:29:04',44,0,'7ba61df8-7d80-496c-8e64-3d114e6515c4',NULL),('94b95111-74d6-4aeb-9f5b-f3606aa29a40','2017-07-06 12:34:01',1950,0,'7ba61df8-7d80-496c-8e64-3d114e6515c4',NULL),('f3f222f5-1f8c-4eb0-a00f-98f9118d3f7f','2017-07-05 14:44:47',15,3,'7ba61df8-7d80-496c-8e64-3d114e6515c4',NULL),('fb66d51f-b337-4405-85d0-fc491af09d1c','2017-07-06 12:34:29',44,0,'7ba61df8-7d80-496c-8e64-3d114e6515c4',NULL);

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

insert  into `user`(`id`,`username`,`password`,`phone`,`cellphone`,`email`,`address`) values ('18cf32b4-844b-4437-873b-40e74dac74f4','小金','123456','111','222','333','444'),('69b1a5a4-9047-413e-9333-097770e65da4','小陆','123','222','333','11','44'),('7ba61df8-7d80-496c-8e64-3d114e6515c4','jrc','1234','63531234','18958835351','337417702@qq.com','上海'),('7dbf069a-0058-4ecd-af6e-c626910cc0e5','jjj','1234','123','123','666','杭州'),('ec5e71c6-32fd-4fa4-a17b-805aa00c4f21','小王','123','111','222','333','111');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
