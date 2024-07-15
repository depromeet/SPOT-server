dependencies {
    implementation(project(":common"))
    implementation(project(":domain"))
    implementation(project(":usecase"))
    implementation(project(":infrastructure:jpa"))

    // spring
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework:spring-aspects")

    // security
    implementation("org.springframework.boot:spring-boot-starter-security")

    // swagger
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:_")

    // jwt
//    implementation("io.jsonwebtoken:jjwt:_")
    implementation("io.jsonwebtoken:jjwt-api:0.11.5")
    implementation("io.jsonwebtoken:jjwt-impl:0.11.5")
    implementation("io.jsonwebtoken:jjwt-jackson:0.11.5")

}

// spring boot main application이므로 실행 가능한 jar를 생성한다.
tasks.bootJar { enabled = true }
tasks.jar { enabled = true }