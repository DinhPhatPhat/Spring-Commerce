# Using image JDK 17
FROM openjdk:17
WORKDIR /springCommerce
COPY target/spring_commerce-0.0.1-SNAPSHOT.jar spring_commerce.jar
RUN mkdir -p /spring_commerce/uploads  # Tạo thư mục uploads trong container
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "spring_commerce.jar"]
