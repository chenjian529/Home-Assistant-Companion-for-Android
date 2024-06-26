include(":common", ":app")

rootProject.name = "home-assistant-android"

pluginManagement {
    repositories {
        // maven { setUrl("https://mirrors.cloud.tencent.com/gradle/") }
        //maven { setUrl("https://mirrors.cloud.tencent.com/nexus/repository/maven-public/") }
        gradlePluginPortal()
        google()
    }
}

plugins {
    // So we can't reach the libs.plugins.* aliases from here so we need to declare them the old way...
    id("org.ajoberstar.reckon.settings").version("0.18.0")
}

extensions.configure<org.ajoberstar.reckon.gradle.ReckonExtension> {
    setDefaultInferredScope("patch")
    stages("beta", "final")
    setScopeCalc { java.util.Optional.of(org.ajoberstar.reckon.core.Scope.PATCH) }
    setStageCalc(calcStageFromProp())
    setTagWriter { it.toString() }
}

dependencyResolutionManagement {
    repositories {
        //maven { setUrl("https://mirrors.cloud.tencent.com/gradle/") }
        //maven { setUrl("https://mirrors.cloud.tencent.com/nexus/repository/maven-public/") }
        mavenCentral()
        google()
        maven("https://jitpack.io")
    }
}
