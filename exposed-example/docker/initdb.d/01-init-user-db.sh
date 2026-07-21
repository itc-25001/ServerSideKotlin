#!/usr/bin/env bash
set -e

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
  CREATE USER exposed ENCRYPTED PASSWORD 'kotlin+exposed';
  CREATE DATABASE exposed_example OWNER exposed;
  GRANT ALL PRIVILEGES ON DATABASE exposed_example TO exposed;
EOSQL