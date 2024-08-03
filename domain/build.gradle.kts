dependencies {
    implementation(project(":common"))

    // ImmutableMap을 위한 구아바 사용
    implementation("com.google.guava:guava:31.0.1-jre")
}

tasks.jar { enabled = true }
tasks.bootJar { enabled = false }