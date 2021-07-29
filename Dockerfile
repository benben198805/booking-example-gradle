FROM registry.cn-hangzhou.aliyuncs.com/wiskind/openjdk:8-jre-alpine-apm-agent

COPY build/libs/*.jar /opt/app.jar

ENTRYPOINT ["java", "-XX:+UnlockExperimentalVMOptions", "-XX:+UseCGroupMemoryLimitForHeap", "-jar", "/opt/app.jar"]