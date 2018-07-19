FROM openjdk:8-jdk-alpine
ADD target/sfsondage.jar ws_sfsondage_sf.jar
EXPOSE 7087
ENTRYPOINT ["java","-jar","ws_sfsondage_sf.jar"]