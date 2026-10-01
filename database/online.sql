
/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `online` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `online`;
DROP TABLE IF EXISTS `cart`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cart` (
  `cartId` varchar(50) NOT NULL,
  `userId` int DEFAULT NULL,
  `buyDate` datetime DEFAULT NULL,
  `status` int DEFAULT NULL,
  `recipientName` varchar(100) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `address` varchar(500) DEFAULT NULL,
  `note` varchar(500) DEFAULT NULL,
  `paymentMethod` varchar(20) NOT NULL DEFAULT 'COD',
  `totalAmount` decimal(18,2) DEFAULT NULL,
  PRIMARY KEY (`cartId`),
  KEY `FK_Cart_User` (`userId`),
  CONSTRAINT `FK_Cart_User` FOREIGN KEY (`userId`) REFERENCES `users` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `cart` WRITE;
/*!40000 ALTER TABLE `cart` DISABLE KEYS */;
/*!40000 ALTER TABLE `cart` ENABLE KEYS */;
UNLOCK TABLES;
DROP TABLE IF EXISTS `cartitem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cartitem` (
  `cartItemId` varchar(50) NOT NULL,
  `quantity` int DEFAULT NULL,
  `unitPrice` double DEFAULT NULL,
  `productId` int DEFAULT NULL,
  `cartId` varchar(50) DEFAULT NULL,
  `productName` varchar(200) DEFAULT NULL,
  `images` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`cartItemId`),
  KEY `FK_CartItem_Product` (`productId`),
  KEY `FK_CartItem_Cart` (`cartId`),
  CONSTRAINT `FK_CartItem_Cart` FOREIGN KEY (`cartId`) REFERENCES `cart` (`cartId`),
  CONSTRAINT `FK_CartItem_Product` FOREIGN KEY (`productId`) REFERENCES `product` (`productId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `cartitem` WRITE;
/*!40000 ALTER TABLE `cartitem` DISABLE KEYS */;
/*!40000 ALTER TABLE `cartitem` ENABLE KEYS */;
UNLOCK TABLES;
DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category` (
  `categoryId` int NOT NULL AUTO_INCREMENT,
  `categoryName` varchar(200) DEFAULT NULL,
  `images` varchar(500) DEFAULT NULL,
  `status` int DEFAULT NULL,
  PRIMARY KEY (`categoryId`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (1,'Điện thoại','https://picsum.photos/id/10/100',1),(2,'Laptop','https://picsum.photos/id/11/100',1),(3,'Phụ kiện','https://picsum.photos/id/12/100',1),(5,'test1','',1),(6,'test2','',1),(7,'test3','',1),(8,'test4','',0),(13,'test 5','',1);
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;
DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product` (
  `productId` int NOT NULL AUTO_INCREMENT,
  `productName` varchar(200) DEFAULT NULL,
  `productCode` bigint DEFAULT NULL,
  `categoryId` int DEFAULT NULL,
  `description` varchar(500) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `amount` int DEFAULT NULL,
  `stock` int DEFAULT NULL,
  `images` varchar(500) DEFAULT NULL,
  `wishlist` int DEFAULT NULL,
  `status` int DEFAULT NULL,
  `createDate` date DEFAULT NULL,
  `sellerId` int DEFAULT NULL,
  PRIMARY KEY (`productId`),
  KEY `FK_Product_Category` (`categoryId`),
  KEY `FK_Product_Seller` (`sellerId`),
  CONSTRAINT `FK_Product_Category` FOREIGN KEY (`categoryId`) REFERENCES `category` (`categoryId`),
  CONSTRAINT `FK_Product_Seller` FOREIGN KEY (`sellerId`) REFERENCES `seller` (`sellerId`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (1,'iPhone 15',100001,1,'Điện thoại Apple',20000000,10,10,'https://picsum.photos/id/20/200',0,1,'2026-09-24',1),(2,'Samsung S24',100002,1,'Điện thoại Samsung',18000000,8,8,'https://picsum.photos/id/21/200',0,1,'2026-09-24',1),(3,'Macbook Air M2',100003,2,'Laptop Apple',28000000,5,5,'https://picsum.photos/id/22/200',0,1,'2026-09-24',1),(4,'Dell XPS 13',100004,2,'Laptop Dell',26000000,6,6,'https://picsum.photos/id/23/200',0,1,'2026-09-24',2),(5,'Tai nghe AirPods',100005,3,'Tai nghe không dây',4000000,20,20,'https://picsum.photos/id/24/200',0,1,'2026-09-24',2),(6,'Sạc nhanh 65W',100006,3,'Củ sạc nhanh',500000,50,50,'https://picsum.photos/id/25/200',0,1,'2026-09-24',2);
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;
DROP TABLE IF EXISTS `seller`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `seller` (
  `sellerId` int NOT NULL AUTO_INCREMENT,
  `sellername` varchar(50) DEFAULT NULL,
  `images` varchar(500) DEFAULT NULL,
  `status` int DEFAULT NULL,
  PRIMARY KEY (`sellerId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `seller` WRITE;
/*!40000 ALTER TABLE `seller` DISABLE KEYS */;
INSERT INTO `seller` VALUES (1,'Cửa hàng A','https://picsum.photos/id/1/100',1),(2,'Cửa hàng B','https://picsum.photos/id/2/100',1);
/*!40000 ALTER TABLE `seller` ENABLE KEYS */;
UNLOCK TABLES;
DROP TABLE IF EXISTS `userroles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `userroles` (
  `roleId` int NOT NULL AUTO_INCREMENT,
  `roleName` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`roleId`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `userroles` WRITE;
/*!40000 ALTER TABLE `userroles` DISABLE KEYS */;
INSERT INTO `userroles` VALUES (1,'Admin'),(2,'User'),(3,'Seller');
/*!40000 ALTER TABLE `userroles` ENABLE KEYS */;
UNLOCK TABLES;
DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `userId` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `fullname` varchar(50) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `images` varchar(500) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `status` int DEFAULT NULL,
  `code` varchar(50) DEFAULT NULL,
  `roleId` int DEFAULT NULL,
  `sellerid` int DEFAULT NULL,
  PRIMARY KEY (`userId`),
  KEY `FK_Users_Role` (`roleId`),
  KEY `FK_Users_Seller` (`sellerid`),
  CONSTRAINT `FK_Users_Role` FOREIGN KEY (`roleId`) REFERENCES `userroles` (`roleId`),
  CONSTRAINT `FK_Users_Seller` FOREIGN KEY (`sellerid`) REFERENCES `seller` (`sellerId`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'admin','admin@gmail.com','Quản trị viên','123456','','0900000001',1,NULL,1,NULL),(2,'user1','user1@gmail.com','Nguyễn Văn A','123456','','0900000002',1,NULL,2,NULL),(3,'seller1','seller1@gmail.com','Trần Thị B','123456','','0900000003',1,NULL,3,1),(4,'lvdue','lyvudue@gmail.com','Vũ Duệ','123456','','0356163117',1,NULL,2,NULL),(6,'test2','thangle@gmail.com','Thắng','123456','','',1,NULL,2,NULL),(7,'test 3','TTT@gmail.com','Tạ Thượng Thanh','123456','','',1,NULL,2,NULL);
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

