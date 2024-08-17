dependencies {
    implementation(project(":common"))
    implementation(project(":domain"))
    implementation(project(":usecase"))

    // spring
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-cache")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:_")

    // mysql
    runtimeOnly("com.mysql:mysql-connector-j")

    // queryDSL
    implementation("com.querydsl:querydsl-jpa:_:jakarta")
    annotationProcessor("com.querydsl:querydsl-apt:_:jakarta")
    annotationProcessor("jakarta.annotation:jakarta.annotation-api")
    annotationProcessor("jakarta.persistence:jakarta.persistence-api")

    // p6spy
    implementation("com.github.gavlyukovskiy:p6spy-spring-boot-starter:_")

    // aws
    implementation("org.springframework.cloud:spring-cloud-starter-aws:_")
    implementation("org.springframework.boot:spring-boot-starter-data-redis:_")

    // webflux (HTTP 요청에 사용)
    implementation("org.springframework.boot:spring-boot-starter-webflux")

    // caffeine cache
    implementation("com.github.ben-manes.caffeine:caffeine:_")
}

tasks.bootJar { enabled = false }
tasks.jar { enabled = true }