FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

COPY build/libs/noncoroutineapp-0.0.2-SNAPSHOT.jar .

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar noncoroutineapp-0.0.2-SNAPSHOT.jar"]