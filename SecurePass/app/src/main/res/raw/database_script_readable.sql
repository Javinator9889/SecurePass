CREATE TABLE IF NOT EXISTS `Category` (
  `idCategory` INTEGER NOT NULL,
  `name` VARCHAR(45) NULL,
  PRIMARY KEY (`idCategory`));

CREATE TABLE IF NOT EXISTS `Configuration` (
  `idConfiguration` INTEGER NOT NULL,
  `configName` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idConfiguration`));

CREATE TABLE IF NOT EXISTS `Entry` (
  `idEntry` INTEGER NOT NULL DEFAULT 0,
  `icon` VARCHAR(180) NULL,
  `name` VARCHAR(45) NOT NULL,
  `cidCategory` INTEGER NOT NULL,
  `idConfiguration` INTEGER NOT NULL,
  PRIMARY KEY (`idEntry`, `cidCategory`, `idConfiguration`),
  CONSTRAINT `fk_Entry_Category1`
    FOREIGN KEY (`cidCategory`)
    REFERENCES `Category` (`idCategory`)
    ON DELETE SET DEFAULT
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Entry_Config1`
    FOREIGN KEY (`idConfiguration`)
    REFERENCES `Configuration` (`idConfiguration`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

CREATE TABLE IF NOT EXISTS `QRCode` (
  `idQRCode` INTEGER NOT NULL DEFAULT 0,
  `name` VARCHAR(45) NOT NULL,
  `description` VARCHAR(180) NULL,
  `data` LONGTEXT NOT NULL,
  `fidEntry` INTEGER NOT NULL,
  PRIMARY KEY (`idQRCode`, `fidEntry`),
  CONSTRAINT `fk_QRCode_Entry1`
    FOREIGN KEY (`fidEntry`)
    REFERENCES `Entry` (`idEntry`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION);

CREATE TABLE IF NOT EXISTS `SecurityCodes` (
  `idSecurityCodes` INTEGER NOT NULL DEFAULT 0,
  `accountName` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idSecurityCodes`));

CREATE TABLE IF NOT EXISTS `Fields` (
  `idField` INTEGER NOT NULL DEFAULT 0,
  `code` VARCHAR(180) NOT NULL,
  `used` TINYINT NOT NULL DEFAULT 0,
  `fidSecurityCodes` INTEGER NOT NULL,
  PRIMARY KEY (`idField`, `fidSecurityCodes`),
  CONSTRAINT `fk_Fields_SecurityCodes1`
    FOREIGN KEY (`fidSecurityCodes`)
    REFERENCES `SecurityCodes` (`idSecurityCodes`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION);

CREATE TABLE IF NOT EXISTS `Password` (
  `idPassword` INTEGER NOT NULL,
  `password` VARCHAR(256) NOT NULL,
  `field_desc` VARCHAR(45) NOT NULL,
  `sortOrder` INTEGER NOT NULL,
  `idEntry` INTEGER NOT NULL,
  PRIMARY KEY (`idPassword`, `idEntry`),
  CONSTRAINT `fk_Password_Entry1`
    FOREIGN KEY (`idEntry`)
    REFERENCES `Entry` (`idEntry`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION);

CREATE TABLE IF NOT EXISTS `SmallText` (
  `idSmallText` INTEGER NOT NULL,
  `text` VARCHAR(180) NOT NULL,
  `field_desc` VARCHAR(45) NOT NULL,
  `sortOrder` INTEGER NOT NULL,
  `idEntry` INTEGER NOT NULL,
  PRIMARY KEY (`idSmallText`, `idEntry`),
  CONSTRAINT `fk_SmallText_Entry1`
    FOREIGN KEY (`idEntry`)
    REFERENCES `Entry` (`idEntry`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION);

CREATE TABLE IF NOT EXISTS `Image` (
  `idImage` INTEGER NOT NULL,
  `source` LONGBLOB NOT NULL,
  `field_desc` VARCHAR(45) NOT NULL,
  `sortOrder` INTEGER NOT NULL,
  `idEntry` INTEGER NOT NULL,
  PRIMARY KEY (`idImage`, `idEntry`),
  CONSTRAINT `fk_Image_Entry1`
    FOREIGN KEY (`idEntry`)
    REFERENCES `Entry` (`idEntry`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION);

CREATE TABLE IF NOT EXISTS `LongText` (
  `idLongText` INTEGER NOT NULL,
  `text` LONGBLOB NOT NULL,
  `field_desc` VARCHAR(45) NOT NULL,
  `sortOrder` INTEGER NOT NULL,
  `idEntry` INTEGER NOT NULL,
  PRIMARY KEY (`idLongText`, `idEntry`),
  CONSTRAINT `fk_LongText_Entry1`
    FOREIGN KEY (`idEntry`)
    REFERENCES `Entry` (`idEntry`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION);

CREATE TABLE IF NOT EXISTS `PassConfig` (
  `idConfig` INTEGER NOT NULL,
  `desc` VARCHAR(45) NOT NULL,
  `sortOrder` INTEGER NOT NULL,
  `idConfiguration` INTEGER NOT NULL,
  PRIMARY KEY (`idConfig`, `idConfiguration`),
  CONSTRAINT `fk_PassConfig`
    FOREIGN KEY (`idConfiguration`)
    REFERENCES `Configuration` (`idConfiguration`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION);

CREATE TABLE IF NOT EXISTS `SmallTextConfig` (
  `idConfig` INTEGER NOT NULL,
  `desc` VARCHAR(45) NOT NULL,
  `sortOrder` INTEGER NOT NULL,
  `idConfiguration` INTEGER NOT NULL,
  PRIMARY KEY (`idConfig`, `idConfiguration`),
  CONSTRAINT `fk_SmallTextConfig`
    FOREIGN KEY (`idConfiguration`)
    REFERENCES `Configuration` (`idConfiguration`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION);

CREATE TABLE IF NOT EXISTS `LongTextConfig` (
  `idConfig` INTEGER NOT NULL,
  `desc` VARCHAR(45) NOT NULL,
  `sortOrder` INTEGER NOT NULL,
  `idConfiguration` INTEGER NOT NULL,
  PRIMARY KEY (`idConfig`, `idConfiguration`),
  CONSTRAINT `fk_LongTextConfig`
    FOREIGN KEY (`idConfiguration`)
    REFERENCES `Configuration` (`idConfiguration`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION);

CREATE TABLE IF NOT EXISTS `ImagesConfig` (
  `idConfig` INTEGER NOT NULL,
  `desc` VARCHAR(45) NOT NULL,
  `sortOrder` INTEGER NOT NULL,
  `idConfiguration` INTEGER NOT NULL,
  PRIMARY KEY (`idConfig`, `idConfiguration`),
  CONSTRAINT `fk_ImagesConfig`
    FOREIGN KEY (`idConfiguration`)
    REFERENCES `Configuration` (`idConfiguration`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION);

CREATE INDEX `fk_Entry` ON `Entry` (`cidCategory` ASC);

CREATE INDEX `fk_Entry_Config` ON `Entry` (`idConfiguration` ASC);

CREATE INDEX `fk_QRCode` ON `QRCode` (`fidEntry` ASC);

CREATE INDEX `fk_Fields` ON `Fields` (`fidSecurityCodes` ASC);

CREATE INDEX `fk_Password` ON `Password` (`idEntry` ASC);

CREATE INDEX `fk_SmallText` ON `SmallText` (`idEntry` ASC);

CREATE INDEX `fk_Image` ON `Image` (`idEntry` ASC);

CREATE INDEX `fk_LongText` ON `LongText` (`idEntry` ASC);

CREATE INDEX `fk_PassConfig_idx` ON `PassConfig` (`idConfiguration` ASC);

CREATE INDEX `fk_SmallTextConfig_idx` ON `SmallTextConfig` (`idConfiguration` ASC);

CREATE INDEX `fk_LongTextConfig_idx` ON `LongTextConfig` (`idConfiguration` ASC);

CREATE INDEX `fk_ImagesConfig_idx` ON `ImagesConfig` (`idConfiguration` ASC);
