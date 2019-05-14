-- MySQL dump 10.13  Distrib 5.5.53, for Win32 (AMD64)
--
-- Host: localhost    Database: simplect
-- ------------------------------------------------------
-- Server version	5.5.53

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
-- Table structure for table `account`
--

DROP TABLE IF EXISTS `account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `account` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) NOT NULL,
  `mail` varchar(255) DEFAULT NULL,
  `password` varchar(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=35 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account`
--

LOCK TABLES `account` WRITE;
/*!40000 ALTER TABLE `account` DISABLE KEYS */;
INSERT INTO `account` VALUES (1,'admin',NULL,'admin'),(2,'Ning',NULL,'1275886165'),(3,'test',NULL,'23333'),(4,'test1','aavb','123'),(5,'test2','test@email.com','123'),(6,'test3','abc@mail.com','123'),(7,'dwa','dwa','dwa'),(8,'test5','cs','123'),(9,'test6','cs','123'),(10,'test7','dwa','123'),(11,'WuYu','742663518@qq.com','568510984.'),(12,'3220644098','3220644098','3220644098'),(13,'l201840','1489927067','l1870.'),(14,'Salted_fish','1293222603@qq.com','wsd6316'),(15,'Ning1','123','123'),(16,'Klop_shizi','328370133@qq.com','asd123..'),(17,'2671085715','2671085715@qq.com','123456'),(18,'水墨画','1764479816@qq.com','12320040522zhhl/'),(19,'1764479816','1764479816@qq.com','12320040522zhhl/'),(20,'Nothingness_Void','1744914510@qq.com','zhaojiaceng233'),(21,'mcxh','842902728@qq.com','1624715304'),(22,'tianye','3520645758@qq.com','h20071026'),(23,'lms20070505','3278799064@qq.com','lms20070505*'),(24,'lijie233','2766791548@qq.com','lijie233'),(25,'qingchen','3074991712@qq.com','geoff331216'),(26,'Big_Jls','921447735@qq.com','saint.2017'),(27,'shengshuo','1','123456'),(28,'tianyemc','3520645758@qq.co','h20071026'),(29,'zz1764479816','1764479816','12320040522zhhl/'),(30,'w1624715304','842902728@qq.com','1624715304'),(31,'wuhui','17741222@qq.com','5201314sqn'),(32,'wxb7820222','920523335@qq.com','7820222'),(33,'Canobe','296624@qq.com','123456'),(34,'w3528563','470145423@qq.com','w3528563');
/*!40000 ALTER TABLE `account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transmissionportsetting`
--

DROP TABLE IF EXISTS `transmissionportsetting`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `transmissionportsetting` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `TransmissionPortStart` int(11) DEFAULT NULL,
  `TransmissionPortEnd` int(11) DEFAULT NULL,
  `CurrTransmissionPort` varchar(255) DEFAULT NULL COMMENT '下一次使用该端口',
  PRIMARY KEY (`Id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transmissionportsetting`
--

LOCK TABLES `transmissionportsetting` WRITE;
/*!40000 ALTER TABLE `transmissionportsetting` DISABLE KEYS */;
INSERT INTO `transmissionportsetting` VALUES (1,60000,65535,'60054');
/*!40000 ALTER TABLE `transmissionportsetting` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tunnle`
--

DROP TABLE IF EXISTS `tunnle`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tunnle` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `applicationname` varchar(20) NOT NULL DEFAULT 'TCP应用',
  `tunnleport` int(11) NOT NULL,
  `transmissionPort` int(11) NOT NULL,
  `username` varchar(20) NOT NULL,
  `DomainName` varchar(60) NOT NULL,
  `Localaddress` varchar(60) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=66 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tunnle`
--

LOCK TABLES `tunnle` WRITE;
/*!40000 ALTER TABLE `tunnle` DISABLE KEYS */;
INSERT INTO `tunnle` VALUES (46,'我的世界服务器',17795,60034,'tianye','10mc.win','127.0.0.1:25565'),(53,'mc',129,60041,'tianyemc','10mc.win','25565:123'),(52,'mc',43568,60040,'Big_Jls','mc.com','127.0.0.1:25565'),(41,'eee',30999,60029,'mcxh','10mc.win','192.168.2.104:25565'),(50,'MC',1314,60038,'l201840','10mc.win','127.0.0.1:25565'),(51,'MC',2384,60039,'l201840','10mc.win','127.0.0.1:25565'),(49,'MC',1303,60037,'l201840','10mc.win','127.0.0.1:25565'),(36,'mc',25560,60024,'1764479816','10win.win','192.168.0.9:25565'),(37,'mc',25555,60025,'1764479816','10mc.win','127.0.0.1:25565'),(54,'minecraft',123,60042,'tianyemc','10mc.win','25565:111'),(35,'MC服务器',25566,60023,'1764479816','10win.win','127.0.0.1:25565'),(34,'Mc',56018,60022,'水墨画','10win.win','192.168.0.5:56018'),(33,'Mc',25565,60021,'水墨画','10win.win','192.168.0.5:25565'),(32,'test1',1255,60020,'test3','10mc.win','localhost:25565'),(31,'test5',1279,60019,'test2','10mc.win','192.168.3.40:25565'),(30,'MC',49693,60018,'WuYu','10mc.win','192.168.31.197:49693'),(29,'11',30555,60017,'Klop_shizi','10mc.win','127.0.0.1:25565'),(28,'Test1',5412,60016,'Klop_shizi','10mc.win','127.0.0.1:25565'),(27,'test2',1277,60015,'test2','10mc.win','localhost:25565'),(26,'test1',1276,60014,'test2','10mc.win','127.0.0.1:25565'),(25,'test',1275,60013,'test2','10mc.win','localhost:25565'),(47,'我的世界',23891,60035,'lms20070505','10mc.win','127.0.0.1:25563'),(48,'MC1',49692,60036,'WuYu','10mc.win','10.13.131.11:49693'),(55,'fuwuqi',12767,60043,'tianyemc','10mc.win','127.0.0.1:25565'),(56,'minesaver',16725,60044,'tianyemc','10mc.win','127.0.0.1:25565'),(57,'mine',16723,60045,'tianyemc','10mc.win','127.0.0.1:25565'),(58,'mine',12141,60046,'tianyemc','10mc.win','127.0.0.1:25565'),(59,'mc2',17798,60047,'tianye','10mc.win','127.0.0.1:25565'),(60,'MC2',49691,60048,'WuYu','10mc.win','10.13.131.14:49693'),(61,'MC',30857,60049,'w1624715304','mc110.cn','192.168.2.100:25565'),(62,'MC',5678,60050,'wuhui','','192.168.0.102:25565'),(63,'asd',2700,60051,'wxb7820222','www.mc.com','127.0.0.1:25565'),(64,'1234',2345,60052,'Canobe','','127.0.0.1:25565'),(65,'1',43960,60053,'w3528563','','127.0.0.1:25565');
/*!40000 ALTER TABLE `tunnle` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-04-18 18:31:41
