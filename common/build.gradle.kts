dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")

    // sentry
    implementation("io.sentry:sentry-logback:_")
}

tasks.jar { enabled = true }
tasks.bootJar { enabled = false }