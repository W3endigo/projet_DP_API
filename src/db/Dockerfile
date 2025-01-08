FROM mysql:latest

ENV MYSQL_ROOT_PASSWORD=root
ENV MYSQL_DATABASE=bdd_projet_dp

COPY migrations/v1_db_create.sql /docker-entrypoint-initdb.d/

EXPOSE 3306