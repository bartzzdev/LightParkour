FROM maven:3.8.3-openjdk-17 AS build
WORKDIR /app
COPY . /app
RUN mvn clean package

FROM marctv/minecraft-papermc-server:1.18
RUN mkdir -p /data/plugins
COPY --from=build /app/lightparkour-paper/target/LightParkour.jar /data/plugins/