# Etapa 1: build com Maven
FROM maven:3.9.6-eclipse-temurin-21 as builder

WORKDIR /app

# Copia o projeto
COPY . .

# Executa o build com o perfil prod
RUN mvn clean install -Pprod -DskipTests

# Etapa 2: imagem final apenas com o JAR
FROM eclipse-temurin:21-jdk

WORKDIR /app

# Copia o JAR do estágio de build
COPY --from=builder /app/target/*.jar app.jar

# Expõe a porta
EXPOSE 8080

# Comando de execução
ENTRYPOINT ["java", "-jar", "app.jar"]