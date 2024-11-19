dependencies {
    implementation(project(":common"))
    implementation(project(":domain"))

    // spring
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:_") {
        because("@Transactional을 위해 추가")
    }

    // spring retry
    implementation("org.springframework.retry:spring-retry")
    implementation("org.springframework:spring-aspects")


    // Mixpanel
    implementation("com.mixpanel:mixpanel-java:_")

}

tasks.bootJar { enabled = false }
tasks.jar { enabled = true }