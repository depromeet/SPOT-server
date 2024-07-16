rootProject.name = "SPOT-server"

plugins {
    // See https://jmfayard.github.io/refreshVersions
    id("de.fayard.refreshVersions") version "0.60.5"
}

include("domain")
include("application")
include("infrastructure")
include("infrastructure:jpa")
findProject(":infrastructure:jpa")?.name = "jpa"
include("usecase")
include("common")
include("infrastructure:ncp")
findProject(":infrastructure:ncp")?.name = "ncp"
