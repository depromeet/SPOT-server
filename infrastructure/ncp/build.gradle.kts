dependencies {
    implementation(project(":domain"))
    implementation(project(":common"))
    implementation(project(":usecase"))

    // spring
    implementation("org.springframework.boot:spring-boot-starter")

    // ncp
    implementation("org.springframework.cloud:spring-cloud-starter-aws:_") {
        because("ncp의 object storage를 사용하기 위해 필요한 의존성이에요.")
    }
}

tasks.bootJar { enabled = false }
tasks.jar { enabled = true }