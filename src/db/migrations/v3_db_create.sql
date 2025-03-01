#------------------------------------------------------------
#        Script MySQL avec ON DELETE CASCADE
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
                        name VARCHAR(50) NOT NULL,
                        CONSTRAINT company_PK PRIMARY KEY (name)
) ENGINE=InnoDB;

#------------------------------------------------------------
# Table: user
#------------------------------------------------------------

CREATE TABLE user(
                     email      VARCHAR(255) NOT NULL,
                     first_name VARCHAR(255) NOT NULL,
                     last_name  VARCHAR(255) NOT NULL,
                     password   VARCHAR(255) NOT NULL,
                     name       VARCHAR(50),
                     CONSTRAINT user_PK PRIMARY KEY (email),
                     CONSTRAINT user_company_FK FOREIGN KEY (name) REFERENCES company(name) ON DELETE SET NULL
) ENGINE=InnoDB;

#------------------------------------------------------------
# Table: project
#------------------------------------------------------------

CREATE TABLE project(
                        id                  INT AUTO_INCREMENT NOT NULL,
                        description         TEXT NOT NULL,
                        title              VARCHAR(100) NOT NULL,
                        status             VARCHAR(15) NOT NULL,
                        start_date         DATE NOT NULL,
                        end_date           DATE NOT NULL,
                        email_chef_project VARCHAR(255) NOT NULL,
                        CONSTRAINT project_PK PRIMARY KEY (id),
                        CONSTRAINT project_user_FK FOREIGN KEY (email_chef_project) REFERENCES user(email) ON DELETE CASCADE
) ENGINE=InnoDB;

#------------------------------------------------------------
# Table: project_companies
#------------------------------------------------------------

CREATE TABLE project_companies(
                                  name VARCHAR(50) NOT NULL,
                                  id   INT NOT NULL,
                                  CONSTRAINT projet_companies_PK PRIMARY KEY (name, id),
                                  CONSTRAINT projet_companies_company_FK FOREIGN KEY (name) REFERENCES company(name) ON DELETE CASCADE,
                                  CONSTRAINT projet_companies_project0_FK FOREIGN KEY (id) REFERENCES project(id) ON DELETE CASCADE
) ENGINE=InnoDB;

#------------------------------------------------------------
# Table: participant
#------------------------------------------------------------

CREATE TABLE participant(
                            id    INT NOT NULL,
                            email VARCHAR(255) NOT NULL,
                            CONSTRAINT participant_PK PRIMARY KEY (id, email),
                            CONSTRAINT participant_project_FK FOREIGN KEY (id) REFERENCES project(id) ON DELETE CASCADE,
                            CONSTRAINT participant_user0_FK FOREIGN KEY (email) REFERENCES user(email) ON DELETE CASCADE
) ENGINE=InnoDB;
