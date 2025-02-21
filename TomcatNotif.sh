#!/bin/bash

# Establece la ruta al archivo .jar
JAR_PATH="/webapps8/TomcatNotifications/TomcatNotifications-1.0-SNAPSHOT.jar"

# Verifica si el archivo .jar existe
if [ -f "$JAR_PATH" ]; then
    echo "Iniciando la aplicación Java..."
    java -jar "$JAR_PATH"
else
    echo "Error: El archivo .jar no se encuentra en la ruta especificada."
    exit 1
fi
