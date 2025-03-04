FROM mysql:latest

ENV MYSQL_ROOT_PASSWORD=root
ENV MYSQL_DATABASE=bdd_projet_dp

COPY src/db/migrations/v3_db_create.sql /docker-entrypoint-initdb.d/

EXPOSE 3306