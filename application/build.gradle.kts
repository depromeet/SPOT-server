dependencies {
    implementation(project(":domain"))
    implementation(project(":usecase"))

    // spring
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework:spring-aspects")

    // swagger
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:_")
}

// spring boot main application이므로 실행 가능한 jar를 생성한다.
tasks.bootJar { enabled = true }
tasks.jar { enabled = true }