dependencies {
    implementation(project(":common"))
}

tasks.jar { enabled = true }
tasks.bootJar { enabled = false }