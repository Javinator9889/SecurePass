CREATE TABLE IF NOT EXISTS QRCode (
    idQRCode INTEGER NOT NULL DEFAULT 0,
    name VARCHAR(45) NOT NULL,
    description VARCHAR(180) NULL,
    data LONGTEXT NOT NULL,
    fidEntry INTEGER NOT NULL,
    PRIMARY KEY (idQRCode),
    CONSTRAINT fk_QRCode_Entry
        FOREIGN KEY (fidEntry)
        REFERENCES Entry (idEntry)
        ON DELETE CASCADE
        ON UPDATE NO ACTION);