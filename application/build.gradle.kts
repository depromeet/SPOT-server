dependencies {
    implementation(project(":domain"))

    // spring
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web")
}

// spring boot main application이므로 실행 가능한 jar를 생성한다.
tasks.bootJar { enabled = true }
tasks.jar { enabled = true }