CREATE TABLE IF NOT EXISTS Entry (
  idEntry INTEGER NOT NULL DEFAULT 0,
  account VARCHAR(100) NOT NULL,
  password VARCHAR(180) NOT NULL,
  icon VARCHAR(180) NULL,
  description LONGBLOB NULL,
  cidCategory INT NOT NULL,
  PRIMARY KEY (idEntry),
  CONSTRAINT fk_Entry_Category
    FOREIGN KEY (cidCategory)
    REFERENCES Category (idCategory)
    ON DELETE NO ACTION
    ON UPDATE CASCADE);