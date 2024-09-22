FROM eclipse-temurin:21 AS build
ARG BUILD_DIR=/home/build
WORKDIR $BUILD_DIR

COPY .mvn .mvn
COPY mvnw .
COPY pom.xml .
COPY entrypoint.sh .
RUN ./mvnw -B dependency:go-offline

COPY src src
RUN ./mvnw -B clean package spring-boot:repackage

FROM eclipse-temurin:21
ARG BUILD_DIR=/home/build
ARG HOME_DIR=/home/tracing
RUN useradd -d $HOME_DIR tracing
WORKDIR $HOME_DIR

COPY --from=build $BUILD_DIR/target/tracing.jar .
COPY --from=build $BUILD_DIR/entrypoint.sh .

RUN chmod +x entrypoint.sh

EXPOSE 8080
USER tracing
ENTRYPOINT ["./entrypoint.sh"]
