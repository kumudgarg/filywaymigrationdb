FROM openjdk:8
ADD ./target/flywayDbMigration-0.0.1-SNAPSHOT.jar  flywayDbMigration-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "flywayDbMigration-0.0.1-SNAPSHOT.jar"]