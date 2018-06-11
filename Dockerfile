FROM ibrahim/alpine
ADD target/sfsondage.jar ws_sfsondage_sf.jar
EXPOSE 8087
ENTRYPOINT ["java","-jar","ws_sfsondage_sf.jar"]