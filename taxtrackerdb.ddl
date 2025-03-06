-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema taxtracker
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema taxtracker
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `taxtracker` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `taxtracker` ;

-- -----------------------------------------------------
-- Table `taxtracker`.`employment_sector`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `taxtracker`.`employment_sector` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `employment_sector_name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `employment_sector_name_UNIQUE` (`employment_sector_name` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `taxtracker`.`client`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `taxtracker`.`client` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(45) NOT NULL,
  `last_name` VARCHAR(45) NOT NULL,
  `ssn` VARCHAR(9) NOT NULL UNIQUE,
  `dob` DATE NOT NULL,
  `phone` VARCHAR(15) NOT NULL,
  `email` VARCHAR(100) NULL DEFAULT NULL,
  `address1` VARCHAR(100) NOT NULL,
  `address2` VARCHAR(100) NOT NULL,
  `city` VARCHAR(100) NOT NULL,
  `state` VARCHAR(2) NOT NULL,
  `employment_sector_id` INT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `FK_client_employment_idx` (`employment_sector_id` ASC) VISIBLE,
  CONSTRAINT `FK_client_employment`
    FOREIGN KEY (`employment_sector_id`)
    REFERENCES `taxtracker`.`employment_sector` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `taxtracker`.`cpa`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `taxtracker`.`cpa` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(45) NOT NULL,
  `last_name` VARCHAR(45) NOT NULL,
  `license` VARCHAR(45) NOT NULL,
  `phone` VARCHAR(15) NOT NULL,
  `email` VARCHAR(100) NULL DEFAULT NULL,
  `address1` VARCHAR(100) NOT NULL,
  `address2` VARCHAR(100) NOT NULL,
  `city` VARCHAR(100) NOT NULL,
  `state` VARCHAR(2) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `taxtracker`.`document_category`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `taxtracker`.`document_category` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `category_name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `category_name_UNIQUE` (`category_name` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `taxtracker`.`tax_return`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `taxtracker`.`tax_return` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `client_id` INT NULL DEFAULT NULL,
  `cpa_id` INT NULL DEFAULT NULL,
  `year` INT NOT NULL,
  `status` VARCHAR(45) NULL DEFAULT NULL,
  `amount_paid` DECIMAL(10,2) NULL DEFAULT NULL,
  `amount_owed` DECIMAL(10,2) NULL DEFAULT NULL,
`cost` DECIMAL(10,2) NULL DEFAULT 200,
  `creation_date` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  INDEX `FK_return_cpa_idx` (`cpa_id` ASC) VISIBLE,
  INDEX `FK_return_client_idx` (`client_id` ASC) VISIBLE,
  CONSTRAINT `FK_return_client`
    FOREIGN KEY (`client_id`)
    REFERENCES `taxtracker`.`client` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT `FK_return_cpa`
    FOREIGN KEY (`cpa_id`)
    REFERENCES `taxtracker`.`cpa` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `taxtracker`.`tax_document`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `taxtracker`.`tax_document` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `client_id` INT NOT NULL,
  `tax_return_id` INT NULL DEFAULT NULL,
  `document_category_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `FK_document_client_idx` (`client_id` ASC) VISIBLE,
  INDEX `FK_document_return_idx` (`tax_return_id` ASC) VISIBLE,
  INDEX `FK_document_category_idx` (`document_category_id` ASC) VISIBLE,
  CONSTRAINT `FK_document_category`
    FOREIGN KEY (`document_category_id`)
    REFERENCES `taxtracker`.`document_category` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT `FK_document_client`
    FOREIGN KEY (`client_id`)
    REFERENCES `taxtracker`.`client` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `FK_document_return`
    FOREIGN KEY (`tax_return_id`)
    REFERENCES `taxtracker`.`tax_return` (`id`)
    ON DELETE SET NULL
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
