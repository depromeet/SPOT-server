dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")

    // sentry
    implementation("io.sentry:sentry-logback:_")
    implementation("io.sentry:sentry-spring-boot-starter-jakarta:_")
}

sentry {
    includeSourceContext.set(true)
    org.set("dpm15th-6team")
    projectName.set("SPOT-server")
    authToken.set(System.getenv("SENTRY_AUTH_TOKEN"))
}

tasks.jar { enabled = true }
tasks.bootJar { enabled = false }