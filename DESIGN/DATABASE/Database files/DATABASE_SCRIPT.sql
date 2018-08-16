CREATE TABLE IF NOT EXISTS `Category` (
  `idCategory` INT NOT NULL DEFAULT 0,
  `name` VARCHAR(45) NULL,
  PRIMARY KEY (`idCategory`));

CREATE TABLE IF NOT EXISTS `Entry` (
  `idEntry` INT NOT NULL DEFAULT 0,
  `icon` VARCHAR(180) NULL,
  `cidCategory` INT NOT NULL,
  PRIMARY KEY (`idEntry`, `cidCategory`),
  INDEX `fk_Entry` (`cidCategory` ASC),
  CONSTRAINT `fk_Entry_Category1`
    FOREIGN KEY (`cidCategory`)
    REFERENCES `Category` (`idCategory`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE);

CREATE TABLE IF NOT EXISTS `QRCode` (
  `idQRCode` INT NOT NULL DEFAULT 0,
  `name` VARCHAR(45) NOT NULL,
  `description` VARCHAR(180) NULL,
  `data` LONGTEXT NOT NULL,
  `fidEntry` INT NOT NULL,
  PRIMARY KEY (`idQRCode`, `fidEntry`),
  INDEX `fk_QRCode` (`fidEntry` ASC),
  CONSTRAINT `fk_QRCode_Entry1`
    FOREIGN KEY (`fidEntry`)
    REFERENCES `Entry` (`idEntry`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION);

CREATE TABLE IF NOT EXISTS `SecurityCodes` (
  `idSecurityCodes` INT NOT NULL DEFAULT 0,
  `accountName` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idSecurityCodes`));

CREATE TABLE IF NOT EXISTS `Fields` (
  `idField` INT NOT NULL DEFAULT 0,
  `code` VARCHAR(180) NOT NULL,
  `used` TINYINT NOT NULL DEFAULT 0,
  `fidSecurityCodes` INT NOT NULL,
  PRIMARY KEY (`idField`, `fidSecurityCodes`),
  INDEX `fk_Fields` (`fidSecurityCodes` ASC),
  CONSTRAINT `fk_Fields_SecurityCodes1`
    FOREIGN KEY (`fidSecurityCodes`)
    REFERENCES `SecurityCodes` (`idSecurityCodes`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION);

CREATE TABLE IF NOT EXISTS `Password` (
  `idPassword` INT NOT NULL,
  `password` VARCHAR(256) NOT NULL,
  `field_desc` VARCHAR(45) NOT NULL,
  `idEntry` INT NOT NULL,
  `cidCategory` INT NOT NULL,
  PRIMARY KEY (`idPassword`, `idEntry`, `cidCategory`),
  INDEX `fk_Password` (`idEntry` ASC, `cidCategory` ASC),
  CONSTRAINT `fk_Password_Entry1`
    FOREIGN KEY (`idEntry` , `cidCategory`)
    REFERENCES `Entry` (`idEntry` , `cidCategory`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION);

CREATE TABLE IF NOT EXISTS `SmallText` (
  `idSmallText` INT NOT NULL,
  `text` VARCHAR(180) NOT NULL,
  `field_desc` VARCHAR(45) NOT NULL,
  `idEntry` INT NOT NULL,
  `cidCategory` INT NOT NULL,
  PRIMARY KEY (`idSmallText`, `idEntry`, `cidCategory`),
  INDEX `fk_SmallText` (`idEntry` ASC, `cidCategory` ASC),
  CONSTRAINT `fk_SmallText_Entry1`
    FOREIGN KEY (`idEntry` , `cidCategory`)
    REFERENCES `Entry` (`idEntry` , `cidCategory`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION);

CREATE TABLE IF NOT EXISTS `Image` (
  `idImage` INT NOT NULL,
  `source` LONGBLOB NOT NULL,
  `field_desc` VARCHAR(45) NOT NULL,
  `idEntry` INT NOT NULL,
  `cidCategory` INT NOT NULL,
  PRIMARY KEY (`idImage`, `idEntry`, `cidCategory`),
  INDEX `fk_Image` (`idEntry` ASC, `cidCategory` ASC),
  CONSTRAINT `fk_Image_Entry1`
    FOREIGN KEY (`idEntry` , `cidCategory`)
    REFERENCES `Entry` (`idEntry` , `cidCategory`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION);

CREATE TABLE IF NOT EXISTS `LongText` (
  `idLongText` INT NOT NULL,
  `text` LONGBLOB NOT NULL,
  `field_desc` VARCHAR(45) NOT NULL,
  `idEntry` INT NOT NULL,
  `cidCategory` INT NOT NULL,
  PRIMARY KEY (`idLongText`, `idEntry`, `cidCategory`),
  INDEX `fk_LongText` (`idEntry` ASC, `cidCategory` ASC),
  CONSTRAINT `fk_LongText_Entry1`
    FOREIGN KEY (`idEntry` , `cidCategory`)
    REFERENCES `Entry` (`idEntry` , `cidCategory`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION);