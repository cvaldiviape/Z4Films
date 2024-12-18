# imagen base
FROM amazoncorretto:17.0.9
# destino
WORKDIR /app
# origen
COPY ./target/gateway-0.0.1-SNAPSHOT.jar /app

# Indica que al iniciar el contenedor se debe ejecutar el comando "java -jar /eureka-0.0.1-SNAPSHOT.jar"
ENTRYPOINT ["java", "-jar", "gateway-0.0.1-SNAPSHOT.jar"]