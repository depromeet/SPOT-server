dependencies {
    implementation(project(":domain"))
    implementation(project(":application"))

    // spring
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:_")
}

tasks.bootJar { enabled = false }
tasks.jar { enabled = true }