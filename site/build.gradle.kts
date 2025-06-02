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

    js {
        browser {
            commonWebpackConfig {
                cssSupport {
                    enabled.set(true)
                }
            }
        }
        binaries.executable()
    }

    sourceSets {
        jsMain.dependencies {
            implementation(npm("html2canvas", "1.4.1"))
            implementation(libs.bundles.compose)
            implementation(libs.bundles.kobweb)
            implementation(libs.bundles.silk.icons)
        }
    }
}
