# Docker-Compose

This docker-compose setup is provided for devs to use when developing. This is not needed nor used by any other technologies in this repo. 

## What's included
docker compose provides alpine containers running postgres and pgadmin installs with a bridge network. 

The ports and authentication used in this are not reflected in the testcontainers implementation.

### Authentication
auth for both psql and pgadmin are provided via environment vars. If you're wanting to look at this data with a third party tool, the environment variables are:
```yaml
POSTGRES_DB: northwind
POSTGRES_USER: postgres
POSTGRES_PASSWORD: postgres
---
PGADMIN_DEFAULT_EMAIL: pgadmin4@pgadmin.org
PGADMIN_DEFAULT_PASSWORD: postgres
PGADMIN_LISTEN_PORT: 5050
PGADMIN_CONFIG_SERVER_MODE: 'False'
```
To use this resource, simply call it in a detached state
```shell
docker-compose up -d
```



#### credit
I took most of this from this [implementation of the northwind db](https://github.com/pthom/northwind_psql) and modified it to mirror the testcontainer implementation
of postgresql.