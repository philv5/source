-- MySQL dump 10.16  Distrib 10.2.9-MariaDB, for Win64 (AMD64)
--
-- Host: localhost    Database: jimin
-- ------------------------------------------------------
-- Server version	10.2.9-MariaDB

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
-- Current Database: `jimin`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `jimin` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `jimin`;

--
-- Table structure for table `admins`
--

DROP TABLE IF EXISTS `admins`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `admins` (
  `admin_id` varchar(16) NOT NULL,
  `admin_pw` varchar(16) NOT NULL,
  `admin_Fname` varchar(16) NOT NULL,
  `admin_Lname` varchar(16) NOT NULL,
  PRIMARY KEY (`admin_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admins`
--

LOCK TABLES `admins` WRITE;
/*!40000 ALTER TABLE `admins` DISABLE KEYS */;
INSERT INTO `admins` VALUES ('philv5','opop12','Jimin','Lee'),('root','password','Root','Root');
/*!40000 ALTER TABLE `admins` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `albums`
--

DROP TABLE IF EXISTS `albums`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `albums` (
  `album_id` varchar(16) NOT NULL,
  `album_title` varchar(16) NOT NULL,
  `agency` varchar(16) DEFAULT NULL,
  `album_rdate` date DEFAULT NULL,
  PRIMARY KEY (`album_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `albums`
--

LOCK TABLES `albums` WRITE;
/*!40000 ALTER TABLE `albums` DISABLE KEYS */;
INSERT INTO `albums` VALUES ('001','album1','Maria','1995-10-14'),('002','album2','Maria',NULL),('003','album3','SQLsever',NULL);
/*!40000 ALTER TABLE `albums` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `artists`
--

DROP TABLE IF EXISTS `artists`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `artists` (
  `artist_id` varchar(16) NOT NULL,
  `artist_name` varchar(16) NOT NULL,
  `division` char(1) DEFAULT NULL,
  `agency` varchar(16) DEFAULT NULL,
  PRIMARY KEY (`artist_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `artists`
--

LOCK TABLES `artists` WRITE;
/*!40000 ALTER TABLE `artists` DISABLE KEYS */;
INSERT INTO `artists` VALUES ('001','artist1','G','Gyanees'),('002','artist2','G','YG'),('003','artist3','G','JYP'),('004','artist4','S','LeeKimBakery'),('005','artist5','S','LeeKimBakery');
/*!40000 ALTER TABLE `artists` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `composers`
--

DROP TABLE IF EXISTS `composers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `composers` (
  `comp_id` varchar(16) NOT NULL,
  `comp_name` varchar(16) NOT NULL,
  `agency` varchar(16) DEFAULT NULL,
  PRIMARY KEY (`comp_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `composers`
--

LOCK TABLES `composers` WRITE;
/*!40000 ALTER TABLE `composers` DISABLE KEYS */;
INSERT INTO `composers` VALUES ('001','comp1','Maria'),('002','comp2','MySQL'),('003','comp3','Oracle'),('004','comp4','Oracle'),('005','comp5','Maria');
/*!40000 ALTER TABLE `composers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `id_mng`
--

DROP TABLE IF EXISTS `id_mng`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `id_mng` (
  `id_name` varchar(16) NOT NULL,
  `id_no` int(11) NOT NULL,
  PRIMARY KEY (`id_name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `id_mng`
--

LOCK TABLES `id_mng` WRITE;
/*!40000 ALTER TABLE `id_mng` DISABLE KEYS */;
INSERT INTO `id_mng` VALUES ('album_id',100),('artist_id',100),('comp_id',100),('music_id',100),('plist_id',100),('swriter_id',100);
/*!40000 ALTER TABLE `id_mng` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `musics`
--

DROP TABLE IF EXISTS `musics`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `musics` (
  `music_id` varchar(16) NOT NULL,
  `music_title` varchar(16) NOT NULL,
  `artist_id` varchar(16) DEFAULT NULL,
  `comp_id` varchar(16) DEFAULT NULL,
  `swriter_id` varchar(16) DEFAULT NULL,
  `album_id` varchar(16) DEFAULT NULL,
  `genre` varchar(16) DEFAULT NULL,
  PRIMARY KEY (`music_id`),
  KEY `music_FK_artistid` (`artist_id`),
  KEY `music_FK_compid` (`comp_id`),
  KEY `music_FK_swriterid` (`swriter_id`),
  KEY `music_FK_albumid` (`album_id`),
  CONSTRAINT `music_FK_albumid` FOREIGN KEY (`album_id`) REFERENCES `albums` (`album_id`),
  CONSTRAINT `music_FK_artistid` FOREIGN KEY (`artist_id`) REFERENCES `artists` (`artist_id`),
  CONSTRAINT `music_FK_compid` FOREIGN KEY (`comp_id`) REFERENCES `composers` (`comp_id`),
  CONSTRAINT `music_FK_swriterid` FOREIGN KEY (`swriter_id`) REFERENCES `songwriters` (`swriter_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `musics`
--

LOCK TABLES `musics` WRITE;
/*!40000 ALTER TABLE `musics` DISABLE KEYS */;
INSERT INTO `musics` VALUES ('001','music1','001','001','001','001','Rock'),('002','music2','002','002','002','002','Dance'),('003','music3','003','003','003','003','Hip Hop'),('004','music4','004','004','004','003','Pop'),('005','music5','005','005','005','002','Classical');
/*!40000 ALTER TABLE `musics` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `playlist_registrations`
--

DROP TABLE IF EXISTS `playlist_registrations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `playlist_registrations` (
  `plist_id` varchar(16) NOT NULL,
  `music_id` varchar(16) NOT NULL,
  PRIMARY KEY (`plist_id`,`music_id`),
  KEY `playlist_FK_musicid` (`music_id`),
  CONSTRAINT `playlist_FK_musicid` FOREIGN KEY (`music_id`) REFERENCES `musics` (`music_id`),
  CONSTRAINT `playlist_FK_plistid` FOREIGN KEY (`plist_id`) REFERENCES `playlists` (`plist_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `playlist_registrations`
--

LOCK TABLES `playlist_registrations` WRITE;
/*!40000 ALTER TABLE `playlist_registrations` DISABLE KEYS */;
INSERT INTO `playlist_registrations` VALUES ('001','001'),('001','002'),('001','003'),('002','004'),('002','005'),('003','001'),('003','002'),('003','003'),('003','004'),('004','002'),('004','003'),('004','004'),('004','005'),('005','001'),('005','002'),('005','003'),('006','001'),('006','004'),('006','005'),('007','003'),('007','005'),('008','002'),('008','004'),('008','005');
/*!40000 ALTER TABLE `playlist_registrations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `playlists`
--

DROP TABLE IF EXISTS `playlists`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `playlists` (
  `plist_id` varchar(16) NOT NULL,
  `user_id` varchar(16) NOT NULL,
  `plist_title` varchar(16) NOT NULL,
  PRIMARY KEY (`plist_id`),
  UNIQUE KEY `playlists_Idx1` (`user_id`,`plist_title`),
  CONSTRAINT `playlist_FK_userid` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `playlists`
--

LOCK TABLES `playlists` WRITE;
/*!40000 ALTER TABLE `playlists` DISABLE KEYS */;
INSERT INTO `playlists` VALUES ('001','user1','playlist1'),('002','user1','playlist2'),('003','user1','playlist3'),('004','user2','playlist1'),('005','user2','playlist2'),('006','user3','playlist1'),('007','user3','playlist2'),('008','user3','playlist3');
/*!40000 ALTER TABLE `playlists` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `songwriters`
--

DROP TABLE IF EXISTS `songwriters`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `songwriters` (
  `swriter_id` varchar(16) NOT NULL,
  `swriter_name` varchar(16) NOT NULL,
  `agency` varchar(16) DEFAULT NULL,
  PRIMARY KEY (`swriter_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `songwriters`
--

LOCK TABLES `songwriters` WRITE;
/*!40000 ALTER TABLE `songwriters` DISABLE KEYS */;
INSERT INTO `songwriters` VALUES ('001','swriter1','Maria'),('002','swriter2','MySQL'),('003','swriter3','Maria'),('004','swriter4','Oracle'),('005','swriter5','SQLsever');
/*!40000 ALTER TABLE `songwriters` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `user_id` varchar(16) NOT NULL,
  `user_pw` varchar(16) NOT NULL,
  `user_Fname` varchar(16) NOT NULL,
  `user_Lname` varchar(16) NOT NULL,
  `jumin_no` char(13) NOT NULL,
  `bdate` date DEFAULT NULL,
  `e_mail` varchar(32) DEFAULT NULL,
  `sex` char(1) DEFAULT NULL,
  `postcode` char(5) DEFAULT NULL,
  `address` varchar(64) DEFAULT NULL,
  `job` varchar(16) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES ('user1','qwer123','Jisoo','Lee','0501081048567','2005-01-08',NULL,'F','12308','Tokyo Sinden Adachi 3-37-12-710','student'),('user10','qwer123','Donghyun','Lee','9903041098345',NULL,NULL,NULL,NULL,NULL,NULL),('user2','qwer123','Jiwoong','Lee','9510141048127','1995-10-14',NULL,'M','26202','Mangwon 1-dong, Mapo-gu, Seoul, Korea','student'),('user3','qwer123','Unjin','Kim','7304057364538','1973-04-05',NULL,'M','12194','121, Mullae-ro, Yeongdeungpo-gu, Seoul','businessman'),('user4','qwer123','Hyunu','Kim','8801011048345',NULL,NULL,NULL,NULL,NULL,NULL),('user5','qwer123','Jiunko','Lee','7710191048789','1977-10-19',NULL,'F','07293','125, Sejong-daero, Jung-gu, Seoul','office worker'),('user6','qwer123','Sihyung','Bae','9209081048908','1992-09-08',NULL,'M','07225','48-74, Namyangju-si, Gyeonggi-do','accountant'),('user7','qwer123','Sehwang','Kim','8912121048456','1989-12-12',NULL,'F','04519','6, Sopa-ro 4-gil, Jung-gu, Seoul','student'),('user8','qwer123','Songhun','Bae','8610141048123',NULL,NULL,NULL,NULL,NULL,NULL),('user9','qwer123','Yongyun','Lim','9810141038456',NULL,NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-12-01 23:44:09
