import com.varabyte.kobweb.gradle.application.util.configAsKobwebApplication

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kobweb.application)
}

group = "xyz.malefic.hell"
version = "1.0-SNAPSHOT"

kobweb {
    app {
        index {
            description.set("A story-based cooking game")
        }
    }
}

kotlin {
    configAsKobwebApplication("hells-kitchen")

    sourceSets {
        jsMain.dependencies {
            implementation(libs.bundles.silk.icons)
            implementation(libs.kotlinx.datetime)
            implementation(libs.bundles.compose)
            implementation(libs.bundles.kobweb)
        }
    }
}
