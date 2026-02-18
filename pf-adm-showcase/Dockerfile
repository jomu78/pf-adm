FROM bellsoft/liberica-openjdk-alpine:21 AS builder
ARG JAR_FILE
RUN addgroup -S javaapp && adduser -S javaapp -G javaapp -h /app
USER javaapp
WORKDIR app
COPY ${JAR_FILE} app.jar
#RUN java -Djarmode=tools -jar app.jar extract --layers --destination extracted

FROM bellsoft/liberica-openjdk-alpine:21
RUN addgroup -S javaapp && adduser -S javaapp -G javaapp -h /home/javaapp \
    && apk --no-cache add curl bind-tools
USER javaapp
RUN mkdir -p /home/javaapp
WORKDIR /home/javaapp
COPY --from=builder /app/app.jar ./
ENTRYPOINT ["java", "-XX:+UseContainerSupport","-Dj ava.security.egd=file:/dev/./urandom", "-jar", "app.jar"]
