dependencies {
    implementation(project(":domain"))
    implementation(project(":usecase"))

    // spring
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:_")

    // p6spy
    implementation("com.github.gavlyukovskiy:p6spy-spring-boot-starter:_")

    // h2 - DB (또는 도커) 세팅 후 사라질 예정,,
    runtimeOnly("com.h2database:h2")

}

tasks.bootJar { enabled = false }
tasks.jar { enabled = true }