/*
 Navicat Premium Data Transfer

 Source Server         : Datamart datamart-cmc.mysql.database.azure.com
 Source Server Type    : MySQL
 Source Server Version : 80032
 Source Host           : datamart-cmc.mysql.database.azure.com:3306
 Source Schema         : injury_case

 Target Server Type    : MySQL
 Target Server Version : 80032
 File Encoding         : 65001

 Date: 01/07/2023 23:01:31
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for history_search_term
-- ----------------------------
DROP TABLE IF EXISTS `history_search_term`;
CREATE TABLE `history_search_term`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `search_term` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `date` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of history_search_term
-- ----------------------------
INSERT INTO `history_search_term` VALUES (1, 'Tang Tak Wah', '2023-06-28 16:28:15');
INSERT INTO `history_search_term` VALUES (2, 'Tang Tak Wah kjsdhfkjdshfkhsd', '2023-06-28 16:43:49');
INSERT INTO `history_search_term` VALUES (3, 'a', '2023-06-28 16:49:30');
INSERT INTO `history_search_term` VALUES (4, 'Tao', '2023-06-28 17:07:48');
INSERT INTO `history_search_term` VALUES (5, 'To', '2023-06-28 17:31:10');
INSERT INTO `history_search_term` VALUES (6, 'Donna', '2023-06-29 14:29:42');
INSERT INTO `history_search_term` VALUES (7, 'Jeffrey', '2023-06-29 14:30:18');
INSERT INTO `history_search_term` VALUES (8, 'jeffray', '2023-06-30 09:29:57');
INSERT INTO `history_search_term` VALUES (9, 'string', '2023-06-30 15:52:35');
INSERT INTO `history_search_term` VALUES (10, 'Tan', '2023-06-30 16:40:09');
INSERT INTO `history_search_term` VALUES (11, 'Custom', '2023-06-30 16:40:23');
INSERT INTO `history_search_term` VALUES (12, 'Cole', '2023-06-30 16:40:32');

-- ----------------------------
-- Table structure for law_attributes
-- ----------------------------
DROP TABLE IF EXISTS `law_attributes`;
CREATE TABLE `law_attributes`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `level` enum('Level 1','Level 2','Level 3') CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `entity` enum('injury_case') CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `value` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `parent` int NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `created_by` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT 'for audit',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = 'Hold attribute name for filtering' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of law_attributes
-- ----------------------------
INSERT INTO `law_attributes` VALUES (1, 'Body part', 'Level 1', 'injury_case', 'Head', 0, NULL, NULL);
INSERT INTO `law_attributes` VALUES (2, 'Body part', 'Level 2', 'injury_case', 'Headaches', 1, NULL, NULL);
INSERT INTO `law_attributes` VALUES (3, 'Body part', 'Level 2', 'injury_case', 'Griddiness', 1, NULL, NULL);
INSERT INTO `law_attributes` VALUES (4, 'Body part', 'Level 1', 'injury_case', 'Lower limb', 0, NULL, NULL);
INSERT INTO `law_attributes` VALUES (5, 'Body part', 'Level 2', 'injury_case', 'Ankle', 4, NULL, NULL);
INSERT INTO `law_attributes` VALUES (6, 'Body part', 'Level 2', 'injury_case', 'Knee', 4, NULL, NULL);
INSERT INTO `law_attributes` VALUES (7, 'Body part', 'Level 2', 'injury_case', 'Foot, Heel and Toes', 4, NULL, NULL);
INSERT INTO `law_attributes` VALUES (8, 'Body part', 'Level 3', 'injury_case', 'Sprain', 5, NULL, NULL);
INSERT INTO `law_attributes` VALUES (9, 'Body part', 'Level 3', 'injury_case', 'Strain', 5, NULL, NULL);
INSERT INTO `law_attributes` VALUES (10, 'Body part', 'Level 3', 'injury_case', 'Fracture', 5, NULL, NULL);

-- ----------------------------
-- Table structure for law_case_attribute_assoc
-- ----------------------------
DROP TABLE IF EXISTS `law_case_attribute_assoc`;
CREATE TABLE `law_case_attribute_assoc`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `case_id` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `attribute_id` int NULL DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `created_by` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT 'for audit',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = 'Case to attribute' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of law_case_attribute_assoc
-- ----------------------------
INSERT INTO `law_case_attribute_assoc` VALUES (1, '1', 10, '2023-06-06 09:18:06', 'hoangpt');
INSERT INTO `law_case_attribute_assoc` VALUES (2, '2', 3, '2023-06-06 09:18:11', 'hoangpt');
INSERT INTO `law_case_attribute_assoc` VALUES (3, '3', 4, '2023-06-06 09:18:14', 'hoangpt');

-- ----------------------------
-- Table structure for law_injurry_case
-- ----------------------------
DROP TABLE IF EXISTS `law_injurry_case`;
CREATE TABLE `law_injurry_case`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'Ref in mysql\n',
  `uuid` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT 'Ref for hardening, avoid brutal force',
  `case_nr` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT 'Case name',
  `party_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT 'Party name',
  `liability` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT 'Account for',
  `accident_time` timestamp NULL DEFAULT NULL COMMENT 'Time of accident',
  `asessed_time` timestamp NULL DEFAULT NULL COMMENT 'Accessment',
  `plaintiff_sex` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT 'Sex',
  `plaintiff_age` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT 'Age',
  `plaintiff_job` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT 'Job',
  `injury` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT 'Injury',
  `cause` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT 'Reason',
  `treatment` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT 'Treatment',
  `disabilities` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT 'Disablities',
  `awarded` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT 'Awarded',
  `doc_json` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT 'Aggregration JSON',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `is_deleted` int NOT NULL DEFAULT 0,
  `is_confidental` int NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `CASENR`(`case_nr` ASC) USING BTREE COMMENT 'Logic, not duplicated',
  UNIQUE INDEX `UUID`(`uuid` ASC) USING BTREE COMMENT 'Logic, not duplicated',
  INDEX `YEARMONTH`(`accident_time` ASC) USING BTREE COMMENT 'For searching',
  FULLTEXT INDEX `SEARCH`(`doc_json`) COMMENT 'Fulltext Mysql'
) ENGINE = InnoDB AUTO_INCREMENT = 1010 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = 'Hold information of injury case' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of law_injurry_case
-- ----------------------------
INSERT INTO `law_injurry_case` VALUES (1, '86efc140-caf2-47fc-81bd-a9501dc65ca6', '2:2023cv01050', 'Donna Kondziela', 'Costco Wholesale Corporation and Verona Bridges', 'Occupier', '2023-06-02 02:39:57', '2023-06-05 14:45:18', 'Male', '28', 'Self-employed at time of accident and assessement', 'Multiple scar, loss of amenties', '28 U.S.C. § 1446 Petition for Removal- Personal Injury', 'Removal of implants teeth', '1446 Petition for Removal- Personal Injury', 'Pain and suffering for lower limb injuries including predisposition to osteroarthritis', NULL, '2023-06-05 02:04:25', '2023-06-23 04:07:12', 0, 0);
INSERT INTO `law_injurry_case` VALUES (2, 'a8fe75e4-3e40-4b46-942e-a16238a79a68', '2:2023cv00868', 'Elizabeth Cochran', 'Walmart, Inc. doing business as Walmart #3473', 'US District Court for the District of Nevada', '2023-06-03 02:49:22', '2023-06-03 14:49:30', 'Female', '22', 'Partime', 'Several brain injury', '28 U.S.C. § 1332 Diversity-Petition for Removal', 'Operation', 'Brain', '30000', NULL, '2023-06-05 02:04:25', '2023-06-06 02:05:34', 0, 0);
INSERT INTO `law_injurry_case` VALUES (3, '5cf72316-5513-46d5-968b-94949fbb54f0', '2:2023cv00872', 'Drewlynne Summers-Pernell', 'P.F. Changs China Bistro, Inc.', 'US District Court for the District of Nevada', '2023-06-04 14:59:03', '2023-06-05 14:59:19', 'Female', '50', 'Fulltime, 5000$ month', 'Arm bone', '28 U.S.C. § 1441 Petition for Removal- Personal Injury', 'Light treatiment', '', NULL, NULL, '2023-06-05 02:04:25', '2023-06-06 02:05:36', 0, 0);

SET FOREIGN_KEY_CHECKS = 1;
