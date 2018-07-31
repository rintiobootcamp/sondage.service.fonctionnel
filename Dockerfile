FROM openjdk:8-jdk-alpine
ADD target/sfsondage.jar ws_sfsondage_sf.jar
EXPOSE 7087
ENV JAVA_OPTS="-XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap  -XX:MaxRAMFraction=1 -XshowSettings:vm "
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar ws_sfsondage_sf.jar" ]