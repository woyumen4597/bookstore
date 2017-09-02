-- MySQL dump 10.13  Distrib 5.7.18, for Win32 (AMD64)
--
-- Host: localhost    Database: bookstore
-- ------------------------------------------------------
-- Server version	5.7.18-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `book`
--

DROP TABLE IF EXISTS `book`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `book` (
  `id` varchar(40) NOT NULL,
  `name` varchar(100) NOT NULL,
  `author` varchar(100) NOT NULL,
  `price` double NOT NULL,
  `image` varchar(40) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `category_id` varchar(40) DEFAULT NULL,
  `sales` int(11) DEFAULT '0',
  `stock` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `category_id_FK` (`category_id`),
  CONSTRAINT `category_id_FK` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `book`
--

LOCK TABLES `book` WRITE;
/*!40000 ALTER TABLE `book` DISABLE KEYS */;
INSERT INTO `book` VALUES ('123-245-351-564-1','数据结构','沈俊',44,'079784fb-fa66-493e-9f33-8cc87675ef48.jpg','优秀教材','4cf43b8f-92d7-4787-83f0-6c5b078640f8',0,52),('123-456-789-111-2','软件工程','张海番',39.5,'984e3bf9-7972-444b-8410-db0a8033adab.jpg','有用之书','3224762d-f8a0-4f3d-974d-f84f4179f9e5',0,49),('253-125-654-123-5','概率论与数理统计','小金',25,'7721b0b7-7a53-4208-9c80-bc53f1c73218.jpg','概率论','59d8ce19-0d27-48d9-8ac5-997064e7a280',0,50),('564-123-456-785-1','高等数学','杨坚',15,'a039e400-6b4d-4688-82a9-c2ad12a861f4.jpg','好书啊！','4cf43b8f-92d7-4787-83f0-6c5b078640f8',0,50),('978-712-123-245-2','离散数学','清华大学',39,'118420b7-6d6d-427b-9ef9-51009d5d5fd3.jpg','又是一本好书啊','3224762d-f8a0-4f3d-974d-f84f4179f9e5',2,50);
/*!40000 ALTER TABLE `book` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `category` (
  `id` varchar(40) NOT NULL,
  `name` varchar(100) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES ('3224762d-f8a0-4f3d-974d-f84f4179f9e5','计算机','计算机相关'),('4cf43b8f-92d7-4787-83f0-6c5b078640f8','科学','科学相关'),('59d8ce19-0d27-48d9-8ac5-997064e7a280','数学','数学相关');
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orderitem`
--

DROP TABLE IF EXISTS `orderitem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orderitem`
--

LOCK TABLES `orderitem` WRITE;
/*!40000 ALTER TABLE `orderitem` DISABLE KEYS */;
INSERT INTO `orderitem` VALUES ('8aa252b1-a059-4ec9-8b18-91aa77175dac',1,39,'c341c9e1-404f-4cc7-a6f9-c06d041af273','978-712-123-245-2');
/*!40000 ALTER TABLE `orderitem` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES ('13d0ec38-5cc1-410a-872b-13ab933d6e57','2017-07-02 21:16:22',20,2,'7ba61df8-7d80-496c-8e64-3d114e6515c4',NULL),('6cce14c8-e4a9-4393-a3fd-32f09ec40b79','2017-07-02 21:03:49',20,2,'7ba61df8-7d80-496c-8e64-3d114e6515c4',NULL),('85f9f619-747f-4d48-ad23-682da4fdf5fc','2017-07-02 21:19:37',20,3,'7ba61df8-7d80-496c-8e64-3d114e6515c4',NULL),('c341c9e1-404f-4cc7-a6f9-c06d041af273','2017-07-02 20:31:42',39,2,'7ba61df8-7d80-496c-8e64-3d114e6515c4',NULL);
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('18cf32b4-844b-4437-873b-40e74dac74f4','小金','123456','111','222','333','444'),('69b1a5a4-9047-413e-9333-097770e65da4','小陆','123','222','333','11','44'),('7ba61df8-7d80-496c-8e64-3d114e6515c4','jrc','1234','63531234','18958835351','337417702@qq.com','上海'),('ec5e71c6-32fd-4fa4-a17b-805aa00c4f21','小王','123','111','222','333','111');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-07-02 22:51:37
