dependencies {
    implementation(project(":domain"))
    implementation(project(":usecase"))

    // spring
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:_")

    // queryDSL
    implementation("com.querydsl:querydsl-jpa:_:jakarta")
    annotationProcessor("com.querydsl:querydsl-apt:_:jakarta")
    annotationProcessor("jakarta.annotation:jakarta.annotation-api")
    annotationProcessor("jakarta.persistence:jakarta.persistence-api")

    // p6spy
    implementation("com.github.gavlyukovskiy:p6spy-spring-boot-starter:_")

    // h2 - DB (또는 도커) 세팅 후 사라질 예정,,
    runtimeOnly("com.h2database:h2")
}

tasks.bootJar { enabled = false }
tasks.jar { enabled = true }