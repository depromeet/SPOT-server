dependencies {
    implementation(project(":domain"))
    implementation(project(":application"))

    // spring
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:_")

    // h2 - DB (또는 도커) 세팅 후 사라질 예정,,
    runtimeOnly("com.h2database:h2")

}

tasks.bootJar { enabled = false }
tasks.jar { enabled = true }