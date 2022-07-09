#!/bin/sh

for f in /init.d/*.sh; do
 [[ -e "${f}" ]] && . "${f}"
done

java -Ddd.profiling.enabled=true -XX:+UseContainerSupport $@ -Djava.security.egd=file:/dev/./urandom -jar /app/spring-boot-application.jar

