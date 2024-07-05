dependencies {
    implementation(project(":common"))
    implementation(project(":domain"))

    // spring
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web")
}

tasks.bootJar { enabled = false }
tasks.jar { enabled = true }