dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")

    // sentry
    implementation("io.sentry:sentry-logback:_")
    implementation("io.sentry:sentry-spring-boot-starter-jakarta:_")
}

sentry {
    includeSourceContext.set(true)
    org.set("dpm15th-6team")
    projectName.set("spot-server")
}

tasks.jar { enabled = true }
tasks.bootJar { enabled = false }