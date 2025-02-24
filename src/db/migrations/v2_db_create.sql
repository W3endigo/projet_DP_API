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
                     email      Varchar (255) NOT NULL ,
                     first_name Varchar (255) NOT NULL ,
                     last_name  Varchar (255) NOT NULL ,
                     password   Varchar (255) NOT NULL ,
                     name       Varchar (50)
    ,CONSTRAINT user_PK PRIMARY KEY (email)

    ,CONSTRAINT user_company_FK FOREIGN KEY (name) REFERENCES company(name)
)ENGINE=InnoDB;


#------------------------------------------------------------
# Table: project
#------------------------------------------------------------

CREATE TABLE project(
                        id          Int  Auto_increment  NOT NULL ,
                        description Text NOT NULL ,
                        title       Varchar (100) NOT NULL ,
                        status      Varchar (15) NOT NULL ,
                        start_date  Date NOT NULL ,
                        end_date    Date NOT NULL ,
                        email_chef_project       Varchar (255) NOT NULL
    ,CONSTRAINT project_PK PRIMARY KEY (id)

    ,CONSTRAINT project_user_FK FOREIGN KEY (email_chef_project) REFERENCES user(email)
)ENGINE=InnoDB;


#------------------------------------------------------------
# Table: projet_companies
#------------------------------------------------------------

CREATE TABLE project_companies(
                           name Varchar (50) NOT NULL ,
                           id   Int NOT NULL
    ,CONSTRAINT projet_companies_PK PRIMARY KEY (name,id)

    ,CONSTRAINT projet_companies_company_FK FOREIGN KEY (name) REFERENCES company(name)
    ,CONSTRAINT projet_companies_project0_FK FOREIGN KEY (id) REFERENCES project(id)
)ENGINE=InnoDB;


#------------------------------------------------------------
# Table: participant
#------------------------------------------------------------

CREATE TABLE participant(
                             id    Int NOT NULL ,
                             email Varchar (255) NOT NULL
    ,CONSTRAINT participant_PK PRIMARY KEY (id,email)

    ,CONSTRAINT participant_project_FK FOREIGN KEY (id) REFERENCES project(id)
    ,CONSTRAINT participant_user0_FK FOREIGN KEY (email) REFERENCES user(email)
)ENGINE=InnoDB;