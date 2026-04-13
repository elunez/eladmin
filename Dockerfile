# 构建阶段
FROM maven:3.9-eclipse-temurin-17 AS builder

WORKDIR /build

# 复制 pom.xml 和源码
COPY pom.xml .
COPY eladmin-common ./eladmin-common
COPY eladmin-logging ./eladmin-logging
COPY eladmin-system ./eladmin-system
COPY eladmin-tools ./eladmin-tools
COPY eladmin-generator ./eladmin-generator

# 跳过测试构建
RUN mvn package -DskipTests

# 运行阶段
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# 从构建阶段复制 jar 包
COPY --from=builder /build/eladmin-system/target/*.jar app.jar

# 使用非 root 用户运行
RUN addgroup -S appgroup && adduser -S appuser -G appgroup
USER appuser

EXPOSE 8000

ENTRYPOINT ["java", "-jar", "app.jar"]
