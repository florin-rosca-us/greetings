FROM postgres:latest

# https://1kevinson.com/how-to-create-a-postgres-database-in-docker/
# You can add one or more *.sql, *.sql.gz, or *.sh after the entrypoint calls initdb to create the default Postgres user
# and database.

# This could be used to create schema. However, we are using Liquibase for creating schema (more flexible?)
ADD ../initdb.sql /docker-entrypoint-initdb.d/
