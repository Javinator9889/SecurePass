CREATE TABLE IF NOT EXISTS Fields (
    idField INTEGER NOT NULL DEFAULT 0,
    code VARCHAR(180) NOT NULL,
    used TINYINT NOT NULL DEFAULT 0,
    fidSecurityCodes INTEGER NOT NULL,
    PRIMARY KEY (idField),
    CONSTRAINT fk_Fields_SecurityCodes
        FOREIGN KEY (fidSecurityCodes)
        REFERENCES SecurityCodes (idSecurityCodes)
        ON DELETE CASCADE
        ON UPDATE NO ACTION);