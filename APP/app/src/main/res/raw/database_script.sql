-- -----------------------------------------------------
-- Schema securepassdb
-- -----------------------------------------------------
-- SCHEMAS ARE NOT ALLOWED ON SQLite
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Table `securepassdb`.`Category`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Category` (
  `idCategory` INT NOT NULL DEFAULT 0, -- NO SENSE - MUST BE AUTOINCREMENT
  `name` VARCHAR(45) NULL,
  PRIMARY KEY (`idCategory`));
-- ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `securepassdb`.`Entry`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Entry` (
  `idEntry` INT NOT NULL AUTO_INCREMENT,
  `account` VARCHAR(180) NOT NULL,
  `password` VARCHAR(180) NOT NULL,
  `icon` VARCHAR(180) NULL,
  `description` LONGBLOB NULL,
  `cidCategory` INT NOT NULL,
  PRIMARY KEY (`idEntry`, `cidCategory`),
  INDEX `fk_Entry_Category1_idx` (`cidCategory` ASC),
  CONSTRAINT `fk_Entry_Category1`
    FOREIGN KEY (`cidCategory`)
    REFERENCES `Category` (`idCategory`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE);
-- ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `securepassdb`.`QRCode`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `QRCode` (
  `idQRCode` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `description` VARCHAR(180) NULL,
  `data` LONGTEXT NOT NULL,
  `fidEntry` INT NOT NULL,
  PRIMARY KEY (`idQRCode`, `fidEntry`),
  INDEX `fk_QRCode_Entry1_idx` (`fidEntry` ASC),
  CONSTRAINT `fk_QRCode_Entry1`
    FOREIGN KEY (`fidEntry`)
    REFERENCES `Entry` (`idEntry`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION);
-- ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `securepassdb`.`SecurityCodes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `SecurityCodes` (
  `idSecurityCodes` INT NOT NULL AUTO_INCREMENT,
  `accountName` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idSecurityCodes`));
-- ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `securepassdb`.`Fields`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Fields` (
  `idField` INT NOT NULL AUTO_INCREMENT,
  `code` VARCHAR(180) NOT NULL,
  `used` TINYINT NOT NULL DEFAULT 0,
  `fidSecurityCodes` INT NOT NULL,
  PRIMARY KEY (`idField`, `fidSecurityCodes`),
  INDEX `fk_Fields_SecurityCodes1_idx` (`fidSecurityCodes` ASC),
  CONSTRAINT `fk_Fields_SecurityCodes1`
    FOREIGN KEY (`fidSecurityCodes`)
    REFERENCES `SecurityCodes` (`idSecurityCodes`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION);
-- ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
