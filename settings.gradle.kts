rootProject.name = "SPOT-server"

plugins {
    // See https://jmfayard.github.io/refreshVersions
    id("de.fayard.refreshVersions") version "0.60.5"
}

include("domain")
include("application")
