dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")

    // sentry
    implementation("io.sentry:sentry-logback:_")

    // aop
    implementation("org.springframework.boot:spring-boot-starter-aop")
}

tasks.jar { enabled = true }
tasks.bootJar { enabled = false }