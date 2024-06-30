plugins {
    id("java")
    id("org.springframework.boot") version "3.0.1"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
}

allprojects {
    group = "org.depromeet.spot"
    version = "1.0-SNAPSHOT"

    repositories {
        mavenCentral()
    }
}

subprojects {
    apply(plugin = "idea")
    apply(plugin = "java")
    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")

    java {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    dependencies {
        // subproject 모두에 필요한 의존성을 관리합니다.
    }

    tasks.test {
        useJUnitPlatform()
    }
}

// root 모듈은 실행 파일이 없으므로 bootJar를 생성하지 않는다.
tasks.bootJar { enabled = false }
tasks.jar { enabled = true }