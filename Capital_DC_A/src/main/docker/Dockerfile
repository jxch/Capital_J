FROM openjdk:11-jdk
VOLUME /tmp
ADD Capital-DC-A.jar app.jar
ADD wallet_db_capital wallet_db_capital
ENV LANG en_US.UTF-8
ENV LANGUAGE en_US:en
ENV LC_ALL en_US.UTF-8

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]