dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")

    // sentry
    implementation("io.sentry:sentry-logback:_")
    implementation("io.sentry:sentry-spring-boot-starter-jakarta:_")
}

tasks.jar { enabled = true }
tasks.bootJar { enabled = false }