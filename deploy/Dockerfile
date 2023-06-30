# Copies source code and build jar file
FROM maven:3.8.5-jdk-11 as build
WORKDIR /app

COPY . ./
ARG IMAGE_TAG_ARG='m-default'
ENV SPRINGBOOT_IMAGE_TAG=${IMAGE_TAG_ARG}
RUN mvn install:install-file -Dfile=./libs/notification.jar -DgroupId=id.bukalapak -DartifactId=notification -Dversion=1.0 -Dpackaging=jar -DgeneratePom=true
RUN mvn install -DskipTests

# Serve apps
FROM openjdk:11.0.15-jre as authapi
COPY --from=build /app/apps/bib-auth/target/*.jar /app/app.jar
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app/app.jar --spring.config.location=file:///app/config/" ]

# Serve apps
FROM openjdk:11.0.15-jre as crmapi
COPY --from=build /app/apps/bib-crm/target/*.jar /app/app.jar
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app/app.jar --spring.config.location=file:///app/config/" ]

# Serve apps
FROM openjdk:11.0.15-jre as bffapi
COPY --from=build /app/apps/bib-bff/target/*.jar /app/app.jar
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app/app.jar --spring.config.location=file:///app/config/" ]