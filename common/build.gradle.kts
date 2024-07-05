dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
}

tasks.jar { enabled = true }
tasks.bootJar { enabled = false }