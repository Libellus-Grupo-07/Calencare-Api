#Usando uma imagem com Maven pré-instalado
FROM maven:3.8.5-openjdk-17 AS build

#Define o diretório de trabalho
WORKDIR /target

#Copia o arquivo pom.xml e o diretório src
COPY pom.xml .
COPY src ./src

#Executa o Maven para compilar o projeto e criar o JAR
RUN mvn clean install

#Cria a imagem final com o JAR
FROM openjdk:17
WORKDIR /target
COPY --from=build /target/*.jar /target/app.jar

#Comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "/target/app.jar"]