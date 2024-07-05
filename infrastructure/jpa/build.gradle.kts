dependencies {
    implementation(project(":domain"))
    implementation(project(":usecase"))

    // spring
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:_")

    runtimeOnly("com.mysql:mysql-connector-j")
}

tasks.bootJar { enabled = false }
tasks.jar { enabled = true }