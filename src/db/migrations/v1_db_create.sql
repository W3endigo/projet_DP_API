#------------------------------------------------------------
#        Script MySQL.
#------------------------------------------------------------

#------------------------------------------------------------
# User: projet_dp_user
#------------------------------------------------------------

CREATE USER IF NOT EXISTS 'projet_dp_user'@'%' IDENTIFIED BY 'projet_dp_pwd';
GRANT ALL PRIVILEGES ON bdd_projet_dp.* TO 'projet_dp_user'@'%';
FLUSH PRIVILEGES;

#------------------------------------------------------------
# Database: bdd_projet_dp
#------------------------------------------------------------

CREATE DATABASE IF NOT EXISTS bdd_projet_dp;
USE bdd_projet_dp;

#------------------------------------------------------------
# Table: company
#------------------------------------------------------------

CREATE TABLE company(
        name Varchar (50) NOT NULL
	,CONSTRAINT company_PK PRIMARY KEY (name)
)ENGINE=InnoDB;


#------------------------------------------------------------
# Table: user
#------------------------------------------------------------

CREATE TABLE user(
        mail       Varchar (150) NOT NULL ,
        first_name Varchar (30) NOT NULL ,
        last_name  Varchar (30) NOT NULL ,
        password   Varchar (255) NOT NULL ,
        name       Varchar (50)
	,CONSTRAINT user_PK PRIMARY KEY (mail)

	,CONSTRAINT user_company_FK FOREIGN KEY (name) REFERENCES company(name)
)ENGINE=InnoDB;

