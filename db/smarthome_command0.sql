-- MySQL dump 10.13  Distrib 8.0.32, for Win64 (x86_64)
--
-- Host: localhost    Database: smarthome
-- ------------------------------------------------------
-- Server version	8.0.32

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `command`
--

DROP TABLE IF EXISTS `command`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `command` (
  `command_id` int unsigned NOT NULL AUTO_INCREMENT,
  `house_id` int unsigned NOT NULL,
  `intent_name` varchar(255) NOT NULL,
  `text` longtext NOT NULL,
  PRIMARY KEY (`command_id`),
  KEY `houseID_idx` (`house_id`),
  KEY `intentName_idx` (`intent_name`),
  CONSTRAINT `id_house` FOREIGN KEY (`house_id`) REFERENCES `house` (`house_id`),
  CONSTRAINT `intentName` FOREIGN KEY (`intent_name`) REFERENCES `intent` (`intent_name`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `command`
--

LOCK TABLES `command` WRITE;
/*!40000 ALTER TABLE `command` DISABLE KEYS */;
INSERT INTO `command` VALUES (1,1,'turn_on_light','Bật đèn lên'),(2,1,'turn_on_light','Bật hết đèn lên'),(3,1,'turn_on_light','Cho nhà sáng lên đi'),(4,1,'turn_on_light','mở đèn cho tôi'),(5,1,'turn_off_light','tắt đèn đi'),(6,1,'turn_off_light','tắt hết đèn đi'),(7,1,'turn_off_light','tắt đèn đi ngủ thôi'),(8,1,'turn_off_light','tắt đèn cho tôi'),(9,1,'turn_on_fan','bật hết quạt lên'),(10,1,'turn_on_fan','mở hết quạt lên'),(11,1,'turn_off_fan','tắt quạt đi'),(12,1,'turn_off_fan','tắt quạt cho tôi'),(13,1,'turn_on_water','mở hết vòi nước đi'),(14,1,'turn_on_water','tưới cây nào'),(15,1,'turn_on_water','tưới cây giúp tôi'),(16,1,'turn_on_water','mở vòi nước cho tôi'),(17,1,'turn_off_water','tắt hết vòi nước đi'),(18,1,'turn_off_water','tắt vòi nước'),(19,1,'turn_off_water','ngừng tưới cây'),(20,1,'turn_off_water','tắt vòi nước đi'),(21,1,'turn_on_curtain','kéo rèm cửa lên'),(22,1,'turn_on_curtain','kéo rèm lên nào'),(23,1,'turn_on_curtain','kéo rèm lên'),(24,1,'turn_off_curtain','thả rèm xuống'),(25,1,'turn_off_curtain','thả rèm xuống đi'),(26,1,'turn_off_curtain','thả rèm xuống giúp tôi với');
/*!40000 ALTER TABLE `command` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-05-06  8:53:28
