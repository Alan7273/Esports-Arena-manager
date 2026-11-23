# ---------- 1) Construir el .jar ----------
FROM eclipse-temurin:26-jdk AS build
ARG MODULE
WORKDIR /app

COPY pom.xml ./
COPY ${MODULE} ./${MODULE}

WORKDIR /app/${MODULE}
# sed: evita el error "bad interpreter" si mvnw tiene saltos de línea de Windows
RUN sed -i 's/\r$//' mvnw && chmod +x mvnw
# cache de Maven para no descargar todo en cada build
RUN --mount=type=cache,target=/root/.m2 ./mvnw -q -DskipTests package

# ---------- 2) Imagen final, solo con el JRE ----------
FROM eclipse-temurin:26-jre
ARG MODULE
WORKDIR /app
COPY --from=build /app/${MODULE}/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]