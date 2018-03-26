-- -----------------------------------------------------
-- Schema securepassdb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema securepassdb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `securepassdb` DEFAULT CHARACTER SET utf8 ;
USE `securepassdb` ;

-- -----------------------------------------------------
-- Table `securepassdb`.`Category`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `securepassdb`.`Category` (
  `idCategory` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL,
  PRIMARY KEY (`idCategory`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `securepassdb`.`Entry`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `securepassdb`.`Entry` (
  `idEntry` INT NOT NULL AUTO_INCREMENT,
  `account` VARCHAR(180) NOT NULL,
  `password` VARCHAR(180) NOT NULL,
  `icon` VARCHAR(180) NULL,
  `description` LONGBLOB NULL,
  PRIMARY KEY (`idEntry`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `securepassdb`.`QRCode`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `securepassdb`.`QRCode` (
  `idQRCode` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `description` VARCHAR(180) NULL,
  `data` LONGTEXT NOT NULL,
  `fidEntry` INT NOT NULL,
  PRIMARY KEY (`idQRCode`, `fidEntry`),
  INDEX `fk_QRCode_Entry1_idx` (`fidEntry` ASC),
  CONSTRAINT `fk_QRCode_Entry1`
    FOREIGN KEY (`fidEntry`)
    REFERENCES `securepassdb`.`Entry` (`idEntry`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `securepassdb`.`SecurityCodes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `securepassdb`.`SecurityCodes` (
  `idSecurityCodes` INT NOT NULL AUTO_INCREMENT,
  `accountName` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idSecurityCodes`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `securepassdb`.`Fields`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `securepassdb`.`Fields` (
  `code` VARCHAR(180) NOT NULL,
  `used` TINYINT NOT NULL DEFAULT 0,
  `fidSecurityCodes` INT NOT NULL,
  PRIMARY KEY (`code`, `fidSecurityCodes`),
  INDEX `fk_Fields_SecurityCodes1_idx` (`fidSecurityCodes` ASC),
  CONSTRAINT `fk_Fields_SecurityCodes1`
    FOREIGN KEY (`fidSecurityCodes`)
    REFERENCES `securepassdb`.`SecurityCodes` (`idSecurityCodes`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
