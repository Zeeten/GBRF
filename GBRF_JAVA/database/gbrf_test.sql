/*
SQLyog Ultimate v9.02 
MySQL - 5.0.27-community-nt : Database - gbrf_test
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`gbrf_test` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `gbrf_test`;

/*Table structure for table `books` */

DROP TABLE IF EXISTS `books`;

CREATE TABLE `books` (
  `ID` int(15) NOT NULL,
  `BOOK_NAME` varchar(55) default NULL,
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `books` */

insert  into `books`(`ID`,`BOOK_NAME`) values (1,'CREATIVE BOOKS (English)'),(2,'CREATIVE BOOKS (Hindi)'),(3,'CREATIVE BOOKS (Gujrati)'),(4,'CREATIVE BOOKS - (Bengali)'),(5,'SELF LEARN JAVA'),(6,'SELF LEARN ADVANCE JAVA'),(7,'SELF LEARN HIBERNATE'),(8,'JAVA PROGRAMMING & TECHNOLOGIES'),(9,'A SECURITY BOOK - (English)'),(10,'A SECURITY BOOK - (Hindi)'),(11,'A SECURITY BOOK - (Bengali)'),(12,'A SECURITY BOOK - (Punjabi)'),(13,'A SECURITY BOOK - (Gujrati)'),(14,'A SECURITY BOOK - (Marathi)'),(15,'A SECURITY BOOK - (Tamil)'),(16,'A SECURITY BOOK - (Telugu)'),(17,'YOU CAN BECOME A HONHAR GUARD - BASIC (English)'),(18,'YOU CAN BECOME A HONHAR GUARD - BASIC (Hindi)'),(19,'YOU CAN BECOME A HONHAR GUARD - ADVANCE (English)'),(20,'YOU CAN BECOME A HONHAR GUARD - ADVANCE (Hindi)'),(21,'BAAL-E-HUMA'),(22,'BAAL-E-UNQA'),(23,'NAWA-E-SAAZ-E-JAAN'),(24,'SHAH-PAR-E-TAAOOS');

/*Table structure for table `invite` */

DROP TABLE IF EXISTS `invite`;

CREATE TABLE `invite` (
  `ID` int(15) NOT NULL,
  `NAME` varchar(55) default NULL,
  `EMAIL` varchar(55) default NULL,
  `DATE` timestamp NULL default NULL,
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `invite` */

insert  into `invite`(`ID`,`NAME`,`EMAIL`,`DATE`) values (1,NULL,'rahul.sahu@nenosystems.com','2015-02-26 15:42:44');

/*Table structure for table `likes` */

DROP TABLE IF EXISTS `likes`;

CREATE TABLE `likes` (
  `ID` bigint(15) NOT NULL,
  `EMAIL` varchar(55) default NULL,
  `BOOK_NAME` varchar(55) default NULL,
  `BOOK_NO` varchar(55) default NULL,
  `LIKE1` varchar(55) default NULL,
  `LIKE2` varchar(55) default NULL,
  `LIKE3` varchar(55) default NULL,
  `DATE` timestamp NULL default NULL,
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `likes` */

insert  into `likes`(`ID`,`EMAIL`,`BOOK_NAME`,`BOOK_NO`,`LIKE1`,`LIKE2`,`LIKE3`,`DATE`) values (1,'rahul.sahu@nenosystems.com','CREATIVE BOOKS (Gujrati)','8888','1','3','9','2015-02-26 13:30:43'),(2,'rahul.sahu@nenosystems.com','CREATIVE BOOKS (English)','888','2','7','9','2015-02-26 13:32:36'),(3,'rahul.sahu@nenosystems.com','CREATIVE BOOKS (Hindi)','44','8','6','3','2015-02-26 15:44:33'),(4,'rahul.sahu@nenosystems.com','YOU CAN BECOME A HONHAR GUARD - BASIC (English)','986532147','8','1','4','2015-02-26 20:22:03'),(5,'rahul.sahu@nenosystems.com','YOU CAN BECOME A HONHAR GUARD - BASIC (English)','986532147','1','5','9','2015-02-26 20:23:10'),(6,'pankaj.sahu@nenosystems.com','CREATIVE BOOKS (English)','98745621','1','5','8','2015-02-27 15:45:11'),(7,'A',NULL,NULL,'4','5','7','2015-02-27 15:45:11'),(8,'B',NULL,NULL,'7','6','9','2015-02-27 15:45:11'),(9,'C',NULL,NULL,'9','8','6','2015-02-27 15:45:11'),(10,'D',NULL,NULL,'6','4','3','2015-02-27 15:45:11'),(11,'E',NULL,NULL,'4','2','8','2015-02-27 15:45:11'),(12,'F',NULL,NULL,'8','3','5','2015-02-27 15:45:11'),(13,'G',NULL,NULL,'5','9','2','2015-02-27 15:45:11'),(14,'H',NULL,NULL,'2','1','7','2015-02-27 15:45:11'),(15,'I',NULL,NULL,'1','4','8','2015-02-27 15:45:11'),(16,'J',NULL,NULL,'3','7','1','2015-02-27 15:45:11'),(17,'K',NULL,NULL,'7','5','2','2015-02-27 15:45:11'),(18,'L',NULL,NULL,'8','7','5','2015-02-27 15:45:11'),(19,'M',NULL,NULL,'9','2','8','2015-02-27 15:45:11'),(20,'N',NULL,NULL,'6','8','9','2015-02-27 15:45:11'),(21,'O',NULL,NULL,'5','9','8','2015-02-27 15:45:11'),(22,'P',NULL,NULL,'4','7','5','2015-02-27 15:45:11'),(23,'Q',NULL,NULL,'1','2','6','2015-02-27 15:45:11'),(24,'R',NULL,NULL,'4','1','7','2015-02-27 15:45:11'),(25,'S',NULL,NULL,'2','4','9','2015-02-27 15:45:11'),(26,'T',NULL,NULL,'3','1','7','2015-02-27 15:45:11'),(27,'U',NULL,NULL,'8','6','5','2015-02-27 15:45:11'),(28,'V',NULL,NULL,'4','1','3','2015-02-27 15:45:11'),(29,'W',NULL,NULL,'7','6','2','2015-02-27 15:45:11'),(30,'X',NULL,NULL,'5','2','8','2015-02-27 15:45:11'),(31,'Y',NULL,NULL,'2','8','9','2015-02-27 15:45:11'),(32,'Z',NULL,NULL,'1','9','5','2015-02-27 15:45:11');

/*Table structure for table `login` */

DROP TABLE IF EXISTS `login`;

CREATE TABLE `login` (
  `ID` bigint(15) NOT NULL,
  `EMAIL_ID` varchar(55) default NULL,
  `PASSWORD` varchar(55) default NULL,
  `DATE` varchar(55) default NULL,
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `login` */

insert  into `login`(`ID`,`EMAIL_ID`,`PASSWORD`,`DATE`) values (1,'pankaj.sahu@nenosystems.com','123456','2015-02-17'),(2,'sahu.pankaj07@gmail.com','123456','2015-02-17');

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `ID` bigint(15) NOT NULL,
  `NAME` varchar(55) default NULL,
  `SURNAME` varchar(55) default NULL,
  `EMAIL` varchar(55) default NULL,
  `PASSWORD` varchar(55) default NULL,
  `DATE` datetime default NULL,
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `user` */

insert  into `user`(`ID`,`NAME`,`SURNAME`,`EMAIL`,`PASSWORD`,`DATE`) values (1,'Rahul','Sahu','rahul.sahu@nenosystems.com','pass1234','2015-02-21 18:29:57'),(2,'Pankaj ','Sahu','pankaj.sahu@nenosystems.com','123456','2015-02-27 15:40:17');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
