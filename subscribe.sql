DROP DATABASE IF EXISTS subscribe;
CREATE DATABASE subscribe;

USE subscribe;

CREATE TABLE `users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `description` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `enabled` bit(1) DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `locked` bit(1) DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `reset_password_token` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
);

INSERT INTO subscribe.users
(description, email, enabled, first_name, last_name, locked, password, reset_password_token, `role`)
VALUES('test', 'dirisa@csir.co.za', 1, 'admin', 'admin', 0, '$2a$12$79iWh78mAyQoghAk5VgQX.QDL6oa3JTZLty61jOA/0oNhdMMAfcuW', '', 'ADMIN');

CREATE TABLE `services` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `url` varchar(255) DEFAULT NULL,
  `description` varchar(3000) DEFAULT NULL,
  `icon` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `services` (`id`, `url`,`abbreviation`, `description`, `icon`, `name`) VALUES
(1, "https://localhost:8081","DDT","Providing a storage for research data to all publicly funded R&D institutions in South Africa. For effective utilization of the storage, 
 DIRISA is implementing a Data Deposit Tool (DDT) that will be made available to all South African research communities.","bi bi-clipboard-data","Data Deposit Tool(DDT)") ;
(2, "https://localhost:8082","SST","Describes the data you expect to acquire or generate during the course of a research project, how you will manage, describe, analyze,
 and store those data, and what mechanisms you will use at the end of your project to share and preserve your data.","bi bi-kanban","Data Management Plan(DMP)") ;

CREATE TABLE `institution` (
  `id` bigint NOT NULL,
  `abbreviation` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `user_institution` (
  `institution_id` bigint DEFAULT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`user_id`),
  KEY `FKicotgxhfllxbnemb4kia4vo5g` (`institution_id`),
  CONSTRAINT `FKicotgxhfllxbnemb4kia4vo5g` FOREIGN KEY (`institution_id`) REFERENCES `institution` (`id`),
  CONSTRAINT `FKmmctuofo90kmtut5b4vbm5aa5` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
);

INSERT INTO `institution` (`id`, `name`, `abbreviation`) VALUES
(1, 'University of Cape Town', 'UCT'),
(2, 'University of Pretoria\r\n', 'UP'),
(3, 'University of South Africa\r\n', 'UNISA'),
(4, 'University of the Witwatersrand\r\n', 'WITS'),
(5, 'University of KwaZulu-Natal\r\n', 'UKZN'),
(6, 'Universiteit Stellenbosch\r\n', 'SUN'),
(7, 'University of Johannesburg\r\n', 'UJ'),
(8, 'North-West University\r\n', 'NWU'),
(9, 'University of the Western Cape\r\n', 'UWC'),
(10, 'Rhodes University\r\n', 'RU'),
(11, 'Nelson Mandela Metropolitan University\r\n', 'NMMU'),
(12, 'Universiteit van die Vrystaat\r\n','UFS'),
(13, 'Cape Peninsula University of Technology\r\n', 'CPUT'),
(14, 'Tshwane University of Technology\r\n', 'TUT'),
(15, 'Durban University of Technology\r\n', 'DUT'),
(16, 'University of Fort Hare Alice\r\n', 'UFH'),
(17, 'Central University of Technology\r\n', 'CUT'),
(18, 'Vaal University of Technology\r\n', 'VUT'),
(19, 'University of Zululand\r\n', 'UZULU'),
(20, 'University of Venda\r\n', 'UNIVEN'),
(21, 'University of Limpopo\r\n', 'UL'),
(22, 'Walter Sisulu University\r\n', 'WSU'),
(23, 'Mangosuthu University of Technology\r\n','MUT'),
(24, 'Sefako Makgatho Health Sciences University\r\n', 'SMU'),
(25, 'University of Mpumalanga\r\n', 'UMP'),
(26, 'Sol Plaatje University', 'SPU'),
(27, 'Council for Scientific and Industrial Research', 'CSIR'),
(28, 'National Research Foundation', 'NRF'),
(29, 'Other', '');

CREATE TABLE `confirmation_tokens` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `confirmed_at` datetime DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `expires_at` datetime NOT NULL,
  `token` varchar(255) NOT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKhpuw37a1pqxfb6ya1nv5lm4ga` (`user_id`),
  CONSTRAINT `FKhpuw37a1pqxfb6ya1nv5lm4ga` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
);

COMMIT;