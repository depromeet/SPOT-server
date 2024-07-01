dependencies {
    implementation(project(":domain"))

    // spring
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:_")
}

tasks.bootJar { enabled = false }
tasks.jar { enabled = true }