FROM bitnami/postgresql:latest

ENV POSTGRES_PASSWORD=admin

RUN echo "CREATE SCHEMA IF NOT EXISTS stockai AUTHORIZATION postgres;" > /docker-entrypoint-initdb.d/create-schema.sql

#docker build -t psql .
#docker run -p 5432:5432 psql