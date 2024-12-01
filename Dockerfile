# 使用官方的 OpenJDK 映像作為基礎映像
FROM openjdk:17-jdk-alpine

ENV APP_DIR=/app

COPY ./target/springboot-practiceMall-0.0.1-SNAPSHOT.jar ${APP_DIR}/
COPY ./env-config/prod ${APP_DIR}/config

# 設定工作目錄
WORKDIR ${APP_DIR}

# 將應用程式設定為可執行狀態
ENTRYPOINT ["java", "-Dspring.config.location=${APP_DIR}/config/", "-Dspring.profiles.active=prod", "-jar", "springboot-practiceMall-0.0.1-SNAPSHOT.jar"]
# 路徑掛載到 volume
VOLUME ${APP_DIR}/log