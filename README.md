# TestServeRestVerifyMy

## Objective
This project has been developed to test Serverest API using get, post, delete and put.
It has 11 testes.

## How To Execute

It is possible execute in development environment or locally 

### Development env
To execute this in dev: ``mvn clean test -Denv=dev``

### Locally
To execute this locally you should be set Docker in your environment.
While Docker is running you execute in project's root this: ``docker compose up -d``
It will up the api locally using the file docker-compose.yml
After this, you execute this command: ``mvn clean test -Denv=local``

