# Docker-Compose

This docker-compose setup is provided for devs to use when developing. This is not needed nor used by any other technologies in this repo. 

Docker compose needs to be installed prior to using this resource (or testcontainers, for that matter).
To use this resource, simply call it in a detached state
```shell
docker-compose up -d
```


## What's included
docker compose provides alpine containers running postgres and pgadmin installs with a bridge network. 

The ports and authentication used in this are not reflected in the testcontainers implementation.

### Connect to the database


| Host      | Port    | Database  | Username   | Password   | Authentication  |
|-----------|---------|-----------|------------|------------|-----------------|
| localhost | `55432` | northwind | `postgres` | `postgres` | Database Native |

<br>

------------

#### Credit
I took most of this from this [implementation of the northwind db](https://github.com/pthom/northwind_psql) and modified it to mirror the testcontainer implementation
of postgresql.