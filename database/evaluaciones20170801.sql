CREATE DATABASE  IF NOT EXISTS `evaluaciones` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `evaluaciones`;
-- MySQL dump 10.13  Distrib 5.7.12, for Win64 (x86_64)
--
-- Host: localhost    Database: evaluaciones
-- ------------------------------------------------------
-- Server version	5.7.17-log

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
-- Table structure for table `ev_answers`
--

DROP TABLE IF EXISTS `ev_answers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ev_answers` (
  `id_answer` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `id_question` int(10) unsigned NOT NULL,
  `text` varchar(100) NOT NULL,
  `correct` bit(1) NOT NULL DEFAULT b'0',
  PRIMARY KEY (`id_answer`),
  KEY `question_fk2_idx` (`id_question`),
  CONSTRAINT `question_fk3` FOREIGN KEY (`id_question`) REFERENCES `ev_questions` (`id_question`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ev_answers`
--

LOCK TABLES `ev_answers` WRITE;
/*!40000 ALTER TABLE `ev_answers` DISABLE KEYS */;
INSERT INTO `ev_answers` VALUES (1,1,'Estructurado','\0'),(2,1,'Orientado a Objetos',''),(3,1,'Predictivo','\0'),(4,1,'Semantico','\0'),(5,2,'Abstraccion',''),(6,2,'Polimorfismo',''),(7,2,'Encapsulamiento',''),(8,2,'Herencia',''),(9,3,'Java Runtime Exception','\0'),(10,3,'Java Reference Environment','\0'),(11,3,'Java Runtime Environment',''),(12,3,'Java Rean Environment','\0'),(13,6,'respuesta de la pregunta falsa',''),(14,6,'respuesta de la pregunta verdadera',''),(17,4,'resp 1',''),(18,4,'resp 3',''),(19,4,'respo 2','\0'),(20,5,'respuesta incorrecta','\0'),(21,5,'respuesta valida',''),(22,7,'Un lenguaje de programación',''),(23,7,'Un sistema operativo','\0'),(24,7,'Un framework','\0'),(25,7,'Un IDE','\0'),(29,8,'Es una librería de pruebas de seguridad','\0'),(30,8,'Es un framework de pruebas unitarias',''),(31,8,'Es un API de desarrollo','\0'),(34,9,'dos','\0'),(35,9,'uno','');
/*!40000 ALTER TABLE `ev_answers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ev_authorities`
--

DROP TABLE IF EXISTS `ev_authorities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ev_authorities` (
  `id_authority` int(11) NOT NULL AUTO_INCREMENT,
  `id_user` int(11) NOT NULL,
  `authority` varchar(45) COLLATE latin1_spanish_ci NOT NULL,
  PRIMARY KEY (`id_authority`),
  UNIQUE KEY `authority__un` (`id_user`,`authority`),
  KEY `fk_user_idx` (`id_user`),
  CONSTRAINT `sg_users_fk_1` FOREIGN KEY (`id_user`) REFERENCES `ev_users` (`id_user`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=latin1 COLLATE=latin1_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ev_authorities`
--

LOCK TABLES `ev_authorities` WRITE;
/*!40000 ALTER TABLE `ev_authorities` DISABLE KEYS */;
INSERT INTO `ev_authorities` VALUES (2,1,'ROLE_ADMIN'),(3,2,'ROLE_MANAGER'),(15,3,'ROLE_USER'),(5,6,'ROLE_USER'),(27,9,'ROLE_USER'),(28,10,'ROLE_USER'),(31,12,'ROLE_MANAGER'),(40,13,'ROLE_USER');
/*!40000 ALTER TABLE `ev_authorities` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ev_categories`
--

DROP TABLE IF EXISTS `ev_categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ev_categories` (
  `id_category` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `description` varchar(100) NOT NULL,
  PRIMARY KEY (`id_category`),
  UNIQUE KEY `description_UNIQUE` (`description`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ev_categories`
--

LOCK TABLES `ev_categories` WRITE;
/*!40000 ALTER TABLE `ev_categories` DISABLE KEYS */;
INSERT INTO `ev_categories` VALUES (5,'.NET avanzado'),(4,'.NET basico'),(6,'.NET intermedio'),(7,'android'),(24,'demo'),(8,'iOS'),(19,'Java'),(17,'Java 8'),(3,'java avanzado'),(1,'java basico'),(2,'java intermedio'),(23,'Jenkins'),(12,'MySQL'),(10,'Oracle'),(18,'PHP'),(22,'Pruebas Unitarias'),(20,'Servlets y JSP\'s'),(21,'Testing');
/*!40000 ALTER TABLE `ev_categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ev_configurations`
--

DROP TABLE IF EXISTS `ev_configurations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ev_configurations` (
  `id_configuration` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `config_key` varchar(45) NOT NULL,
  `config_value` varchar(100) NOT NULL,
  PRIMARY KEY (`id_configuration`),
  UNIQUE KEY `config_key_UNIQUE` (`config_key`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ev_configurations`
--

LOCK TABLES `ev_configurations` WRITE;
/*!40000 ALTER TABLE `ev_configurations` DISABLE KEYS */;
INSERT INTO `ev_configurations` VALUES (1,'EXPIRATION_DAY','5'),(2,'URL_SITE','http://192.168.0.92:8080/evaluaciones'),(3,'EXAM_MINUTES','120'),(4,'QUESTIONS_X_PAGE','10'),(5,'ROWS_X_PAGE','20'),(6,'MAX_TAG_SIZE','5');
/*!40000 ALTER TABLE `ev_configurations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ev_evaluations`
--

DROP TABLE IF EXISTS `ev_evaluations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ev_evaluations` (
  `id_evaluation` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `id_exam` int(10) unsigned DEFAULT NULL,
  `id_generator` int(11) NOT NULL,
  `id_applicant` int(11) NOT NULL,
  `questions` int(11) NOT NULL,
  `pass_percent` float NOT NULL,
  `creation_date` datetime NOT NULL,
  `expiration_date` date NOT NULL,
  `status` enum('PENDIENTE','EJECUCION','APLICADA','AGOTADA') NOT NULL,
  `name` varchar(100) NOT NULL,
  `eval_minutes` int(10) unsigned NOT NULL,
  `questions_x_page` int(11) NOT NULL,
  `current_page` int(11) DEFAULT NULL,
  `start_time` datetime DEFAULT NULL,
  `end_time` datetime DEFAULT NULL,
  `result` float DEFAULT NULL,
  PRIMARY KEY (`id_evaluation`),
  KEY `exam_fk1_idx` (`id_exam`),
  KEY `users_fk3_idx` (`id_generator`),
  KEY `users_fk4_idx` (`id_applicant`),
  CONSTRAINT `exam_fk1` FOREIGN KEY (`id_exam`) REFERENCES `ev_exams` (`id_exam`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `users_fk3` FOREIGN KEY (`id_generator`) REFERENCES `ev_users` (`id_user`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `users_fk4` FOREIGN KEY (`id_applicant`) REFERENCES `ev_users` (`id_user`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ev_evaluations`
--

LOCK TABLES `ev_evaluations` WRITE;
/*!40000 ALTER TABLE `ev_evaluations` DISABLE KEYS */;
INSERT INTO `ev_evaluations` VALUES (2,NULL,2,6,3,0.6,'2017-06-18 03:32:29','2017-06-23','APLICADA','java basico',120,1,2,'2017-06-19 23:16:38','2017-06-20 01:15:32',1),(3,NULL,2,3,2,0.6,'2017-06-22 13:07:39','2017-06-27','APLICADA','Java demo',2,1,1,'2017-06-22 16:49:26','2017-06-22 16:51:26',0.5),(4,NULL,2,3,2,0.6,'2017-06-22 13:56:46','2017-06-27','APLICADA','demo java basico',2,1,1,'2017-06-22 14:03:45','2017-06-22 16:55:51',0.5),(5,NULL,2,3,3,0.6,'2017-06-22 18:31:43','2017-06-27','APLICADA','demo 2',3,1,2,'2017-06-27 18:13:18','2017-06-27 18:13:32',0.333333),(6,NULL,1,3,3,0.6,'2017-06-23 14:08:15','2017-06-28','APLICADA','java 1',1,1,2,'2017-06-23 14:10:11','2017-06-23 14:11:11',0.666667),(7,NULL,2,3,2,0.6,'2017-06-27 18:05:35','2017-07-02','AGOTADA','demo timeout',1,1,0,'2017-06-27 18:11:54','2017-06-27 18:13:09',0),(8,NULL,1,3,2,0.6,'2017-06-27 18:51:52','2017-07-02','APLICADA','timeout 2',1,1,1,'2017-06-29 11:31:05','2017-06-29 11:31:16',0.5),(9,1,2,3,3,0.6,'2017-06-28 09:37:11','2017-07-03','APLICADA','java basico',120,1,2,'2017-06-29 11:31:29','2017-06-29 11:31:37',0.666667),(10,NULL,2,3,1,0.6,'2017-06-29 10:28:08','2017-07-04','APLICADA','Demo java',1,1,0,'2017-06-29 10:30:12','2017-06-29 10:30:19',1),(11,1,2,9,3,0.6,'2017-06-29 11:33:02','2017-07-04','APLICADA','java basico',120,1,2,'2017-06-29 11:57:30','2017-06-29 11:58:10',1),(12,1,2,10,3,0.6,'2017-06-29 11:35:03','2017-07-04','APLICADA','java basico',120,1,2,'2017-06-29 11:37:44','2017-06-29 11:38:24',0.666667),(13,1,2,3,3,0.6,'2017-07-03 18:55:41','2017-07-08','APLICADA','java basico',120,1,2,'2017-07-03 18:57:12','2017-07-03 18:57:27',0),(14,1,2,3,3,0.6,'2017-07-03 18:58:03','2017-07-08','APLICADA','java basico',120,1,2,'2017-07-03 18:58:19','2017-07-03 18:58:45',0.333333),(15,NULL,1,3,3,0.6,'2017-07-04 20:18:02','2017-07-09','AGOTADA','java becario',1,1,2,'2017-07-04 20:19:23','2017-07-04 20:20:23',0.666667),(16,7,1,13,2,0.6,'2017-07-12 10:34:43','2017-07-17','AGOTADA','Testing con Java',2,10,0,'2017-07-12 10:39:42','2017-07-12 10:41:42',0.5);
/*!40000 ALTER TABLE `ev_evaluations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ev_evaluations_answers`
--

DROP TABLE IF EXISTS `ev_evaluations_answers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ev_evaluations_answers` (
  `id_evaluation_answer` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `id_evaluation_question` int(10) unsigned NOT NULL,
  `id_answer` int(10) unsigned NOT NULL,
  `correct` bit(1) NOT NULL DEFAULT b'0',
  PRIMARY KEY (`id_evaluation_answer`),
  UNIQUE KEY `eval_answer__un` (`id_evaluation_question`,`id_answer`),
  KEY `answer_fk1_idx` (`id_answer`),
  KEY `eval_question_idx` (`id_evaluation_question`),
  CONSTRAINT `answer_fk1` FOREIGN KEY (`id_answer`) REFERENCES `ev_answers` (`id_answer`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `eval_question_fk1` FOREIGN KEY (`id_evaluation_question`) REFERENCES `ev_evaluations_questions` (`id_evaluation_question`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=94 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ev_evaluations_answers`
--

LOCK TABLES `ev_evaluations_answers` WRITE;
/*!40000 ALTER TABLE `ev_evaluations_answers` DISABLE KEYS */;
INSERT INTO `ev_evaluations_answers` VALUES (25,4,11,''),(26,5,6,''),(27,5,7,''),(28,5,8,''),(29,5,5,''),(32,6,2,''),(37,9,7,''),(38,9,8,''),(39,9,5,''),(40,9,6,''),(41,7,8,''),(42,7,6,''),(43,7,5,''),(44,7,7,''),(45,14,2,''),(46,15,5,''),(47,15,7,''),(48,15,6,''),(49,15,8,''),(50,16,10,'\0'),(51,11,4,'\0'),(52,12,8,''),(53,13,11,''),(54,24,22,''),(55,19,6,''),(56,19,7,''),(57,19,5,''),(58,20,11,''),(59,21,8,''),(60,21,7,''),(61,21,5,''),(62,22,2,''),(63,22,4,'\0'),(64,22,1,'\0'),(65,23,11,''),(66,28,11,''),(67,29,6,''),(68,29,8,''),(69,29,5,''),(70,30,2,''),(71,25,2,''),(72,26,11,''),(73,27,8,''),(74,27,6,''),(75,27,5,''),(76,27,7,''),(77,31,3,'\0'),(78,32,8,''),(79,33,12,'\0'),(83,34,1,'\0'),(84,35,7,''),(85,36,11,''),(86,37,7,''),(87,37,8,''),(88,37,6,''),(89,37,5,''),(90,38,4,'\0'),(91,39,11,''),(92,40,31,'\0'),(93,41,30,'');
/*!40000 ALTER TABLE `ev_evaluations_answers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ev_evaluations_categories`
--

DROP TABLE IF EXISTS `ev_evaluations_categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ev_evaluations_categories` (
  `id_evaluation_category` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `id_evaluation` int(10) unsigned NOT NULL,
  `id_category` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id_evaluation_category`),
  UNIQUE KEY `eval_cat__un` (`id_evaluation`,`id_category`),
  KEY `cat_fk1_idx` (`id_category`),
  CONSTRAINT `cat_fk1` FOREIGN KEY (`id_category`) REFERENCES `ev_categories` (`id_category`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `eval_fk1` FOREIGN KEY (`id_evaluation`) REFERENCES `ev_evaluations` (`id_evaluation`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ev_evaluations_categories`
--

LOCK TABLES `ev_evaluations_categories` WRITE;
/*!40000 ALTER TABLE `ev_evaluations_categories` DISABLE KEYS */;
INSERT INTO `ev_evaluations_categories` VALUES (1,2,1),(2,3,1),(3,4,1),(4,5,1),(5,6,1),(6,7,1),(7,8,1),(8,9,1),(9,10,19),(10,11,1),(11,12,1),(12,13,1),(13,14,1),(14,15,1),(15,16,21),(16,16,22);
/*!40000 ALTER TABLE `ev_evaluations_categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ev_evaluations_questions`
--

DROP TABLE IF EXISTS `ev_evaluations_questions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ev_evaluations_questions` (
  `id_evaluation_question` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `id_evaluation` int(10) unsigned NOT NULL,
  `id_question` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id_evaluation_question`),
  KEY `evaluation_fk1_idx` (`id_evaluation`),
  KEY `question_fk2_idx` (`id_question`),
  CONSTRAINT `evaluation_fk4` FOREIGN KEY (`id_evaluation`) REFERENCES `ev_evaluations` (`id_evaluation`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `question_fk2` FOREIGN KEY (`id_question`) REFERENCES `ev_questions` (`id_question`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ev_evaluations_questions`
--

LOCK TABLES `ev_evaluations_questions` WRITE;
/*!40000 ALTER TABLE `ev_evaluations_questions` DISABLE KEYS */;
INSERT INTO `ev_evaluations_questions` VALUES (4,2,3),(5,2,2),(6,2,1),(7,3,2),(8,3,1),(9,4,2),(10,4,3),(11,5,1),(12,5,2),(13,5,3),(14,6,1),(15,6,2),(16,6,3),(17,7,2),(18,7,3),(19,8,2),(20,8,3),(21,9,2),(22,9,1),(23,9,3),(24,10,7),(25,11,1),(26,11,3),(27,11,2),(28,12,3),(29,12,2),(30,12,1),(31,13,1),(32,13,2),(33,13,3),(34,14,1),(35,14,2),(36,14,3),(37,15,2),(38,15,1),(39,15,3),(40,16,8),(41,16,8);
/*!40000 ALTER TABLE `ev_evaluations_questions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ev_exams`
--

DROP TABLE IF EXISTS `ev_exams`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ev_exams` (
  `id_exam` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `questions` int(11) NOT NULL,
  `pass_percent` float NOT NULL,
  `exam_minutes` int(11) unsigned NOT NULL,
  PRIMARY KEY (`id_exam`),
  UNIQUE KEY `exam__UN` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ev_exams`
--

LOCK TABLES `ev_exams` WRITE;
/*!40000 ALTER TABLE `ev_exams` DISABLE KEYS */;
INSERT INTO `ev_exams` VALUES (1,'java basico',10,0.6,120),(2,'java intermedio',10,0.6,120),(3,'java avanzado',10,0.6,120),(5,'.NET basico',1,0.6,1000),(6,'Java',80,0.6,120),(7,'Testing con Java',5,0.6,2);
/*!40000 ALTER TABLE `ev_exams` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ev_exams_categories`
--

DROP TABLE IF EXISTS `ev_exams_categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ev_exams_categories` (
  `id_exam_category` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `id_exam` int(10) unsigned NOT NULL,
  `id_category` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id_exam_category`),
  UNIQUE KEY `exam_cat__un` (`id_exam`,`id_category`),
  KEY `cat_fk2_idx` (`id_category`),
  CONSTRAINT `cat_fk3` FOREIGN KEY (`id_category`) REFERENCES `ev_categories` (`id_category`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `exam_fk2` FOREIGN KEY (`id_exam`) REFERENCES `ev_exams` (`id_exam`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ev_exams_categories`
--

LOCK TABLES `ev_exams_categories` WRITE;
/*!40000 ALTER TABLE `ev_exams_categories` DISABLE KEYS */;
INSERT INTO `ev_exams_categories` VALUES (1,1,1),(2,2,2),(3,3,3),(5,5,4),(7,5,6),(8,6,19),(9,7,21),(10,7,22);
/*!40000 ALTER TABLE `ev_exams_categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ev_notifications`
--

DROP TABLE IF EXISTS `ev_notifications`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ev_notifications` (
  `id_notification` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name_applicant` varchar(100) NOT NULL,
  `email_applicant` varchar(100) NOT NULL,
  `plain_password` varchar(10) NOT NULL,
  `id_manager` int(11) NOT NULL,
  `status` enum('REGISTRADA','ENVIADA') NOT NULL,
  `expiration_date` date NOT NULL,
  `type_notification` enum('EVALUACION','RESET') NOT NULL,
  PRIMARY KEY (`id_notification`),
  KEY `manager_fk1_idx` (`id_manager`),
  CONSTRAINT `manager_fk1` FOREIGN KEY (`id_manager`) REFERENCES `ev_users` (`id_user`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ev_notifications`
--

LOCK TABLES `ev_notifications` WRITE;
/*!40000 ALTER TABLE `ev_notifications` DISABLE KEYS */;
INSERT INTO `ev_notifications` VALUES (1,'cesar','czar.sevilla@gmail.com','9@cDbX',2,'ENVIADA','2017-06-23','EVALUACION'),(2,'Cesar Sevilla','czar.sevilla@gmail.com','wvYXyG',2,'ENVIADA','2017-06-27','EVALUACION'),(3,'Cesar Sevilla','czar.sevilla@gmail.com','4ss&6T',2,'ENVIADA','2017-06-27','EVALUACION'),(4,'Cesar Sevilla','czar.sevilla@gmail.com','@q4vgx',2,'ENVIADA','2017-06-27','EVALUACION'),(5,'Cesar','czar.sevilla@gmail.com','NwmY6R',1,'ENVIADA','2017-06-28','EVALUACION'),(6,'manager','cesar.sevilla@qacg.com','$T3Vf*',1,'ENVIADA','2017-06-28','RESET'),(7,'manager','cesar.sevilla@qacg.com','0KbrZ4',1,'ENVIADA','2017-06-28','RESET'),(8,'manager','cesar.sevilla@qacg.com','hw-GCs',1,'ENVIADA','2017-06-28','RESET'),(9,'Cesar Sevilla','czar.sevilla@gmail.com','oRJ!o0',2,'ENVIADA','2017-07-02','EVALUACION'),(10,'Cesar Sevilla','czar.sevilla@gmail.com','oaJ!!B',1,'ENVIADA','2017-07-02','EVALUACION'),(11,'César Sevilla','czar.sevilla@gmail.com','wQK*mB',2,'ENVIADA','2017-07-03','EVALUACION'),(12,'César Sevilla','czar.sevilla@gmail.com','',2,'ENVIADA','2017-07-04','EVALUACION'),(13,'Beto','edilberto.ventura@qacg.com','oad4%m',2,'ENVIADA','2017-07-04','EVALUACION'),(14,'Carlos Adrian Reyes','carlos.reyes@qacg.com','J2*UNq',2,'ENVIADA','2017-07-04','EVALUACION'),(15,'cesar','czar.sevilla@gmail.com','',2,'ENVIADA','2017-07-08','EVALUACION'),(16,'cesar','czar.sevilla@gmail.com','',2,'ENVIADA','2017-07-08','EVALUACION'),(17,'Cesar','czar.sevilla@gmail.com','',1,'ENVIADA','2017-07-09','EVALUACION'),(18,'Angel','angel.pimentel@qacg.com','$vD3Xm',1,'ENVIADA','2017-07-17','EVALUACION'),(19,'Isaias Cortés','isaias.cortes@qacg.com','PUR0*%',1,'ENVIADA','2017-07-18','RESET');
/*!40000 ALTER TABLE `ev_notifications` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ev_questions`
--

DROP TABLE IF EXISTS `ev_questions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ev_questions` (
  `id_question` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `text` varchar(200) NOT NULL,
  `body` varchar(2000) DEFAULT NULL,
  `id_creator` int(11) NOT NULL,
  `creation_date` datetime NOT NULL,
  `question_status` enum('REVISION','ACTIVA','OBSOLETA') NOT NULL DEFAULT 'REVISION',
  PRIMARY KEY (`id_question`),
  KEY `creator_fk1_idx` (`id_creator`),
  CONSTRAINT `creator_fk1` FOREIGN KEY (`id_creator`) REFERENCES `ev_users` (`id_user`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ev_questions`
--

LOCK TABLES `ev_questions` WRITE;
/*!40000 ALTER TABLE `ev_questions` DISABLE KEYS */;
INSERT INTO `ev_questions` VALUES (1,'Java es un lenguaje de programacion de tipo:','un cuerpo de ejemplo \n solo para validar los \n componentes de la lista',1,'2017-06-18 01:34:55','ACTIVA'),(2,'Son los pilares de la POO:',NULL,1,'2017-06-18 01:34:55','ACTIVA'),(3,'Que es JRE',NULL,1,'2017-06-18 01:34:55','ACTIVA'),(4,'Pregunta prueba','Cuerpo de la pregunta prueba',2,'2017-06-27 12:14:46','OBSOLETA'),(5,'pregunta de prueba 2','cuerpo de la pregunta de prueba 2',2,'2017-06-27 12:17:44','ACTIVA'),(6,'texto de la pregunta de prueba 3','cuerpo de la pregunta de prueba 3',2,'2017-06-27 12:32:32','ACTIVA'),(7,'¿Qué es Java?','',2,'2017-06-29 10:25:49','ACTIVA'),(8,'¿Qué es JUnit?','',1,'2017-07-12 10:24:28','ACTIVA'),(9,'otra','otra',2,'2017-08-01 11:39:08','ACTIVA');
/*!40000 ALTER TABLE `ev_questions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ev_questions_categories`
--

DROP TABLE IF EXISTS `ev_questions_categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ev_questions_categories` (
  `id_question_category` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `id_question` int(10) unsigned NOT NULL,
  `id_category` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id_question_category`),
  UNIQUE KEY `question_cat__un` (`id_question`,`id_category`),
  KEY `category_fk5_idx` (`id_category`),
  CONSTRAINT `category_fk5` FOREIGN KEY (`id_category`) REFERENCES `ev_categories` (`id_category`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `question_fk4` FOREIGN KEY (`id_question`) REFERENCES `ev_questions` (`id_question`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ev_questions_categories`
--

LOCK TABLES `ev_questions_categories` WRITE;
/*!40000 ALTER TABLE `ev_questions_categories` DISABLE KEYS */;
INSERT INTO `ev_questions_categories` VALUES (1,1,1),(2,2,1),(3,3,1),(10,4,1),(11,5,17),(7,6,1),(12,7,19),(18,8,19),(19,8,21),(16,8,22),(17,8,23),(21,9,24);
/*!40000 ALTER TABLE `ev_questions_categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ev_users`
--

DROP TABLE IF EXISTS `ev_users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ev_users` (
  `id_user` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(45) COLLATE latin1_spanish_ci NOT NULL,
  `password` varchar(100) COLLATE latin1_spanish_ci NOT NULL,
  `name` varchar(100) COLLATE latin1_spanish_ci NOT NULL,
  `email` varchar(45) COLLATE latin1_spanish_ci NOT NULL,
  `locked` tinyint(1) NOT NULL DEFAULT '0',
  `active` tinyint(1) NOT NULL DEFAULT '0',
  `expiration_date` date DEFAULT NULL,
  PRIMARY KEY (`id_user`),
  UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=latin1 COLLATE=latin1_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ev_users`
--

LOCK TABLES `ev_users` WRITE;
/*!40000 ALTER TABLE `ev_users` DISABLE KEYS */;
INSERT INTO `ev_users` VALUES (1,'admin','$2a$10$czTwlfml721txZ87/FHCTOUfCdQvnetDvejYL6rvvH9h1VsUYxmpK','Cesar Sevilla','czar.sevilla@gmail.com',0,1,NULL),(2,'manager','$2a$10$czTwlfml721txZ87/FHCTOUfCdQvnetDvejYL6rvvH9h1VsUYxmpK','manager','cesar.sevilla@qacg.com',0,1,NULL),(3,'czar.sevilla@gmail.com','$2a$10$czTwlfml721txZ87/FHCTOUfCdQvnetDvejYL6rvvH9h1VsUYxmpK','César Sevilla','czar.sevilla@gmail.com',0,1,'2018-01-01'),(6,'cesar@sevilla.com','$2a$10$aMT82FdufFfs47LktsUofuH5tzQJVyQIwYE9HhVleP24wN4xotkNu','cesar','cesar@sevilla.com',0,1,'2017-06-23'),(9,'edilberto.ventura@qacg.com','$2a$10$axb6vDOHtaz5JgTjkkHw3O8/yLF/Kecw6NUaKDtjSzixUlVfDg/fu','Beto','edilberto.ventura@qacg.com',0,1,'2017-07-04'),(10,'carlos.reyes@qacg.com','$2a$10$VhjS6ALKRHHYPOMxrdI2tOM0fWk9E5n3lzHD1eBspc4JFlZJKDUKq','Carlos Adrian Reyes','carlos.reyes@qacg.com',0,1,'2017-07-04'),(12,'isaias','$2a$10$GM1dN1zHV83wfNk8XySKBe72BVetcnpyUuuQ5NqvlQHhaxbzYbF5y','Isaias Cortés','isaias.cortes@qacg.com',0,1,NULL),(13,'angel.pimentel@qacg.com','$2a$10$aVd5RfCIOs0USu9xBxmojuFiZQCiVd.gWIn7spSDIVdHrnIeP4lya','Angel','angel.pimentel@qacg.com',1,1,'2018-07-17');
/*!40000 ALTER TABLE `ev_users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `ev_v_evaluations`
--

DROP TABLE IF EXISTS `ev_v_evaluations`;
/*!50001 DROP VIEW IF EXISTS `ev_v_evaluations`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `ev_v_evaluations` AS SELECT 
 1 AS `id_evaluation`,
 1 AS `questions`,
 1 AS `pass_percent`,
 1 AS `creation_date`,
 1 AS `expiration_date`,
 1 AS `status`,
 1 AS `name`,
 1 AS `eval_time`,
 1 AS `result`,
 1 AS `owner`,
 1 AS `applicant`,
 1 AS `approval_result`*/;
SET character_set_client = @saved_cs_client;

--
-- Dumping routines for database 'evaluaciones'
--
/*!50003 DROP FUNCTION IF EXISTS `ev_calculate_result_eval` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`evaluaciones`@`%` FUNCTION `ev_calculate_result_eval`(p_id_eval int) RETURNS float
BEGIN
	declare result float;
    declare aciertos int;
    declare preguntas int;
    
    select questions into preguntas from ev_evaluations where id_evaluation = p_id_eval;
    
    select sum(case when t1.total = t2.total then 1 else 0 end) into aciertos from (
		select a.id_question, sum(a.correct) total from ev_answers a
		where exists (select id_question from ev_evaluations_questions where
		id_evaluation = p_id_eval and id_question = a.id_question) group by id_question
	) t1 left join (
		select eq.id_question, q1.total from (
		select ea.id_evaluation_question, sum(ea.correct) total from ev_evaluations_answers ea
		where exists (select id_evaluation_question from ev_evaluations_questions where id_evaluation = p_id_eval and
		id_evaluation_question = ea.id_evaluation_question) group by ea.id_evaluation_question
		) q1 left join ev_evaluations_questions eq on q1.id_evaluation_question = eq.id_evaluation_question
	) t2 on t1.id_question = t2.id_question;
    
    select aciertos / preguntas into result;
RETURN result;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Final view structure for view `ev_v_evaluations`
--

/*!50001 DROP VIEW IF EXISTS `ev_v_evaluations`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`evaluaciones`@`%` SQL SECURITY DEFINER */
/*!50001 VIEW `ev_v_evaluations` AS select `e`.`id_evaluation` AS `id_evaluation`,`e`.`questions` AS `questions`,`e`.`pass_percent` AS `pass_percent`,`e`.`creation_date` AS `creation_date`,`e`.`expiration_date` AS `expiration_date`,`e`.`status` AS `status`,`e`.`name` AS `name`,timediff(`e`.`end_time`,`e`.`start_time`) AS `eval_time`,`e`.`result` AS `result`,`ow`.`name` AS `owner`,`ap`.`name` AS `applicant`,(case when isnull(`e`.`result`) then '' when (`e`.`pass_percent` > `e`.`result`) then 'No aprobado' when (`e`.`pass_percent` <= `e`.`result`) then 'Aprobado' end) AS `approval_result` from ((`ev_evaluations` `e` left join `ev_users` `ow` on((`e`.`id_generator` = `ow`.`id_user`))) left join `ev_users` `ap` on((`e`.`id_applicant` = `ap`.`id_user`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-08-01 18:27:20
