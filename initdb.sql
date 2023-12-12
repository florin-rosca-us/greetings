-- Initialize database
-- The database and the user get created the first time the container is started, see db: environment: in docker-compose.yml

-- TODO: Anything else that we want to initialize here? This will be executed only one time when the docker image is started for the first time

-- CREATE USER greeting CREATEDB PASSWORD 'secret';
-- CREATE DATABASE example OWNER greeting;