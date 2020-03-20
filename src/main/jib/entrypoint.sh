#!/bin/sh

echo "The application will start in ${INIT_DELAY}s..." && sleep ${INIT_DELAY}
exec java ${JAVA_OPTS} -Djava.security.egd=file:/dev/./urandom -cp /app/resources/:/app/classes/:/app/libs/* "ru.olegraskin.gpzuul.GpZuulApplication"  "$@"
