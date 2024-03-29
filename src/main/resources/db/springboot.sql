-- MySQL Script generated by MySQL Workbench
-- 2019年08月18日 星期日 21时55分42秒
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema springboot
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `springboot` ;

-- -----------------------------------------------------
-- Schema springboot
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `springboot` DEFAULT CHARACTER SET utf8mb4 ;
USE `springboot` ;

-- -----------------------------------------------------
-- Table `springboot`.`role`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `springboot`.`role` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `role_name` VARCHAR(45) NOT NULL,
  `role_code` VARCHAR(45) NOT NULL,
  `desc` VARCHAR(800) NULL,
  PRIMARY KEY (`id`),
  INDEX `idx_roleCode` (`role_code` ASC))
ENGINE = InnoDB
COMMENT = '用户角色表';


-- -----------------------------------------------------
-- Table `springboot`.`member`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `springboot`.`member` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `role_code` VARCHAR(45) NOT NULL,
  `user_name` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `title` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_member_role_idx` (`role_code` ASC),
  CONSTRAINT `fk_member_role`
    FOREIGN KEY (`role_code`)
    REFERENCES `springboot`.`role` (`role_code`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
COMMENT = '用户表';


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
