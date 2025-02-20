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
CREATE TABLE user
(
    email      VARCHAR(255) NOT NULL,
    password   VARCHAR(255) NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    last_name  VARCHAR(255) NOT NULL,
    name       VARCHAR(50),
    CONSTRAINT pk_user PRIMARY KEY (email),
    CONSTRAINT user_company_FK FOREIGN KEY (name) REFERENCES company(name)
);

#------------------------------------------------------------
# Table: project
#------------------------------------------------------------
CREATE TABLE project (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        email_chef_project VARCHAR(255) NOT NULL,
                        participants TEXT NOT NULL,
                        description TEXT NOT NULL,
                        companies TEXT NOT NULL,
                        title VARCHAR(100) NOT NULL,
                        status VARCHAR(15) NOT NULL,
                        start_date DATE NOT NULL,
                        end_date DATE NOT NULL,
                        CONSTRAINT project_chef_FK FOREIGN KEY (email_chef_project) REFERENCES user(email)
) ENGINE=InnoDB;

#------------------------------------------------------------
# Table: step
#------------------------------------------------------------
CREATE TABLE step (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       project_id BIGINT NOT NULL,
                       name VARCHAR(100) NOT NULL,
                       date DATE NOT NULL,
                       CONSTRAINT step_project_FK FOREIGN KEY (project_id) REFERENCES project(id) ON DELETE CASCADE
) ENGINE=InnoDB;

