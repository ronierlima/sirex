FROM mcr.microsoft.com/devcontainers/java:21-bullseye 

RUN apt-get update && apt-get install -y \
    maven 



