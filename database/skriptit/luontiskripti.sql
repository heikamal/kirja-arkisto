-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema kirjakanta
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `kirjakanta` ;

-- -----------------------------------------------------
-- Schema kirjakanta
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `kirjakanta` DEFAULT CHARACTER SET utf8 ;
USE `kirjakanta` ;

-- -----------------------------------------------------
-- Table `kirjakanta`.`kayttaja`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `kirjakanta`.`kayttaja` ;

CREATE TABLE IF NOT EXISTS `kirjakanta`.`kayttaja` (
  `idkayttaja` INT NOT NULL AUTO_INCREMENT,
  `kayttajanimi` VARCHAR(45) NULL,
  `salasana` VARCHAR(120) NULL,
  `salt` VARCHAR(255) NULL,
  PRIMARY KEY (`idkayttaja`))
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `kirjakanta`.`rooli`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `kirjakanta`.`rooli` ;

CREATE TABLE IF NOT EXISTS `kirjakanta`.`rooli` (
  `idrooli` INT NOT NULL AUTO_INCREMENT,
  `nimi` VARCHAR(45) NULL,
  PRIMARY KEY (`idrooli`))
ENGINE = InnoDB;

INSERT INTO rooli (nimi) VALUES ('ROLE_USER');
INSERT INTO rooli (nimi) VALUES ('ROLE_ADMIN');


-- -----------------------------------------------------
-- Table `kirjakanta`.`kayttajan_rooli`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `kirjakanta`.`kayttajan_rooli` ;

CREATE TABLE IF NOT EXISTS `kirjakanta`.`kayttajan_rooli` (
  `idkayttaja` INT NOT NULL,
  `idrooli` INT NOT NULL,
  PRIMARY KEY (`idkayttaja`, `idrooli`),
  INDEX `fk_kayttaja_has_rooli_rooli1_idx` (`idrooli` ASC) VISIBLE,
  INDEX `fk_kayttaja_has_rooli_kayttaja1_idx` (`idkayttaja` ASC) VISIBLE,
  CONSTRAINT `fk_kayttaja_has_rooli_kayttaja1`
    FOREIGN KEY (`idkayttaja`)
    REFERENCES `kirjakanta`.`kayttaja` (`idkayttaja`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_kayttaja_has_rooli_rooli1`
    FOREIGN KEY (`idrooli`)
    REFERENCES `kirjakanta`.`rooli` (`idrooli`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `kirjakanta`.`kirjasarja`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `kirjakanta`.`kirjasarja` ;

CREATE TABLE IF NOT EXISTS `kirjakanta`.`kirjasarja` (
  `idkirjasarja` INT NOT NULL AUTO_INCREMENT,
  `kirjasarja` VARCHAR(45) NULL,
  `kustantaja` VARCHAR(45) NULL,
  `kuvaus` VARCHAR(255) NULL,
  `luokittelu` VARCHAR(45) NULL,
  PRIMARY KEY (`idkirjasarja`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `kirjakanta`.`kirja`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `kirjakanta`.`kirja` ;

CREATE TABLE IF NOT EXISTS `kirjakanta`.`kirja` (
  `idkirja` INT NOT NULL AUTO_INCREMENT,
  `kirjanimi` VARCHAR(45) NULL,
  `kirjailija` VARCHAR(45) NULL,
  `julkaisuvuosi` YEAR(4) NULL,
  `idkirjasarja` INT NOT NULL,
  `jarjestysnro` INT NULL,
  `kuvaus` VARCHAR(255) NULL,
  PRIMARY KEY (`idkirja`),
  INDEX `fk_kirja_kirjasarja_idx` (`idkirjasarja` ASC) VISIBLE,
  CONSTRAINT `fk_kirja_kirjasarja`
    FOREIGN KEY (`idkirjasarja`)
    REFERENCES `kirjakanta`.`kirjasarja` (`idkirjasarja`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `kirjakanta`.`kuva`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `kirjakanta`.`kuva` ;

CREATE TABLE IF NOT EXISTS `kirjakanta`.`kuva` (
  `idkuva` INT NOT NULL AUTO_INCREMENT,
  `kuvanimi` VARCHAR(45) NULL,
  `julkaisuvuosi` YEAR(4) NULL,
  `taiteilija` VARCHAR(45) NULL,
  `tyyli` VARCHAR(45) NULL,
  `kuvaus` VARCHAR(255) NULL,
  `tiedostonimi` VARCHAR(45) NULL,
  PRIMARY KEY (`idkuva`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `kirjakanta`.`kuvitus`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `kirjakanta`.`kuvitus` ;

CREATE TABLE IF NOT EXISTS `kirjakanta`.`kuvitus` (
  `idkuva` INT NOT NULL,
  `idkirja` INT NOT NULL,
  `sivunro` INT NULL,
  PRIMARY KEY (`idkuva`, `idkirja`),
  INDEX `fk_kuva_has_kirja_kirja1_idx` (`idkirja` ASC) VISIBLE,
  INDEX `fk_kuva_has_kirja_kuva1_idx` (`idkuva` ASC) VISIBLE,
  CONSTRAINT `fk_kuva_has_kirja_kuva1`
    FOREIGN KEY (`idkuva`)
    REFERENCES `kirjakanta`.`kuva` (`idkuva`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_kuva_has_kirja_kirja1`
    FOREIGN KEY (`idkirja`)
    REFERENCES `kirjakanta`.`kirja` (`idkirja`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `kirjakanta`.`kirjahylly`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `kirjakanta`.`kirjahylly` ;

CREATE TABLE IF NOT EXISTS `kirjakanta`.`kirjahylly` (
  `idkirjahylly` INT NOT NULL AUTO_INCREMENT,
  `idkayttaja` INT NOT NULL,
  PRIMARY KEY (`idkirjahylly`),
  CONSTRAINT `fk_kirjahylly_kayttaja`
    FOREIGN KEY (`idkayttaja`)
    REFERENCES `kirjakanta`.`kayttaja` (`idkayttaja`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `kirjakanta`.`omatsarjat`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `kirjakanta`.`omatsarjat` ;

CREATE TABLE IF NOT EXISTS `kirjakanta`.`omatsarjat` (
  `idkirjahylly` INT NOT NULL,
  `idkirjasarja` INT NOT NULL,
  PRIMARY KEY (`idkirjahylly`, `idkirjasarja`),
  INDEX `fk_kirjahylly_has_kirjasarja_kirjasarja1_idx` (`idkirjasarja` ASC) VISIBLE,
  INDEX `fk_kirjahylly_has_kirjasarja_kirjahylly1_idx` (`idkirjahylly` ASC) VISIBLE,
  CONSTRAINT `fk_kirjahylly_has_kirjasarja_kirjahylly1`
    FOREIGN KEY (`idkirjahylly`)
    REFERENCES `kirjakanta`.`kirjahylly` (`idkirjahylly`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_kirjahylly_has_kirjasarja_kirjasarja1`
    FOREIGN KEY (`idkirjasarja`)
    REFERENCES `kirjakanta`.`kirjasarja` (`idkirjasarja`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `kirjakanta`.`kirjakopio`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `kirjakanta`.`kirjakopio` ;

CREATE TABLE IF NOT EXISTS `kirjakanta`.`kirjakopio` (
  `idkirjakopio` INT NOT NULL AUTO_INCREMENT,
  `kirjanimi` VARCHAR(45) NULL,
  `painos` INT NULL,
  `painosvuosi` YEAR(4) NULL,
  `idkirja` INT NOT NULL,
  `hankintahinta` DECIMAL(6,2) NULL,
  `hankintapvm` DATE NULL,
  `kunto` INT NULL,
  `kuvaus` VARCHAR(45) NULL,
  `myyntipvm` DATE NULL,
  `myyntihinta` DECIMAL(6,2) NULL,
  `idkirjahylly` INT NOT NULL,
  `idkirjasarja` INT NOT NULL,
  PRIMARY KEY (`idkirjakopio`),
  INDEX `fk_kirjakopio_kirja1_idx` (`idkirja` ASC) VISIBLE,
  INDEX `fk_kirjakopio_kirjahylly_has_kirjasarja1_idx` (`idkirjahylly` ASC, `idkirjasarja` ASC) VISIBLE,
  CONSTRAINT `fk_kirjakopio_kirja1`
    FOREIGN KEY (`idkirja`)
    REFERENCES `kirjakanta`.`kirja` (`idkirja`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_kirjakopio_kirjahylly_has_kirjasarja1`
    FOREIGN KEY (`idkirjahylly` , `idkirjasarja`)
    REFERENCES `kirjakanta`.`omatsarjat` (`idkirjahylly` , `idkirjasarja`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `kirjakanta`.`valokuva`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `kirjakanta`.`valokuva` ;

CREATE TABLE IF NOT EXISTS `kirjakanta`.`valokuva` (
  `idkuva` INT NOT NULL AUTO_INCREMENT,
  `kuvanimi` VARCHAR(45) NULL,
  `idkirjakopio` INT NOT NULL,
  `sivunnro` INT NULL,
  PRIMARY KEY (`idkuva`),
  INDEX `fk_valokuva_kirjakopio1_idx` (`idkirjakopio` ASC) VISIBLE,
  CONSTRAINT `fk_valokuva_kirjakopio1`
    FOREIGN KEY (`idkirjakopio`)
    REFERENCES `kirjakanta`.`kirjakopio` (`idkirjakopio`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

USE `kirjakanta` ;

-- -----------------------------------------------------
-- Placeholder table for view `kirjakanta`.`omat_kirjasarjat`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `kirjakanta`.`omat_kirjasarjat` (`idkirjahylly` INT, `omistaja` INT, `idkirjasarja` INT);

-- -----------------------------------------------------
-- Placeholder table for view `kirjakanta`.`oma_kokoelma`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `kirjakanta`.`oma_kokoelma` (`idkirjahylly` INT, `idkirjasarja` INT, `kirjasarja` INT, `kustantaja` INT, `kuvaus` INT, `luokittelu` INT);

-- -----------------------------------------------------
-- View `kirjakanta`.`omat_kirjasarjat`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `kirjakanta`.`omat_kirjasarjat`;
DROP VIEW IF EXISTS `kirjakanta`.`omat_kirjasarjat` ;
USE `kirjakanta`;
CREATE  OR REPLACE VIEW `omat_kirjasarjat` AS
SELECT
	idkirjahylly,
	idkayttaja,
    idkirjasarja
FROM
	kirjahylly
INNER JOIN
	omatsarjat USING (idkirjahylly);

-- -----------------------------------------------------
-- View `kirjakanta`.`oma_kokoelma`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `kirjakanta`.`oma_kokoelma`;
DROP VIEW IF EXISTS `kirjakanta`.`oma_kokoelma` ;
USE `kirjakanta`;
CREATE  OR REPLACE VIEW `oma_kokoelma` AS
SELECT
	idkirjahylly,
    idkirjasarja,
    kirjasarja,
    kustantaja,
    kuvaus,
    luokittelu
FROM
	omatsarjat
INNER JOIN
	kirjasarja USING (idkirjasarja);

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
