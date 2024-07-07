dependencies {
    implementation(project(":domain"))
    implementation(project(":usecase"))
}

tasks.bootJar { enabled = false }
tasks.jar { enabled = true }