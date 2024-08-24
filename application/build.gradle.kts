dependencies {
    implementation(project(":common"))
    implementation(project(":domain"))
    implementation(project(":usecase"))
    implementation(project(":infrastructure"))

    // spring
    implementation("org.springframework:spring-aspects")
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa") // pageable
    implementation("org.springframework.boot:spring-boot-starter-actuator") {
        because("spring application의 metric 수집 및 모니터링을 위해 추가합니다.")
    }
    implementation("org.springframework.boot:spring-boot-starter-validation")

    // security
    implementation("org.springframework.boot:spring-boot-starter-security")

    // prometheus
    implementation("com.github.loki4j:loki-logback-appender:_")
    runtimeOnly("io.micrometer:micrometer-registry-prometheus:_")

    // swagger
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:_")

    // jwt
    implementation("io.jsonwebtoken:jjwt-api:_")
    implementation("io.jsonwebtoken:jjwt-impl:_")
    implementation("io.jsonwebtoken:jjwt-jackson:_")

    // aop
    implementation("org.springframework.boot:spring-boot-starter-aop")

    // Mixpanel
    implementation("com.mixpanel:mixpanel-java:_")

}

// spring boot main application이므로 실행 가능한 jar를 생성한다.
tasks.bootJar { enabled = true }
tasks.jar { enabled = true }