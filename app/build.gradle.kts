import com.google.firebase.appdistribution.gradle.firebaseAppDistribution
import com.google.gms.googleservices.GoogleServicesPlugin.GoogleServicesPluginConfig

plugins {
    alias(libs.plugins.homeassistant.android.application)
    alias(libs.plugins.homeassistant.android.flavor)
    alias(libs.plugins.firebase.appdistribution)
    alias(libs.plugins.google.services)
    alias(libs.plugins.homeassistant.android.dependencies)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.screenshot)
    alias(libs.plugins.hilt)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.firebase.crashlytics)
}

android {
    useLibrary("android.car")

    defaultConfig {
        manifestPlaceholders["sentryRelease"] = "$applicationId@$versionName"
        manifestPlaceholders["sentryDsn"] = System.getenv("SENTRY_DSN") ?: ""

        bundle {
            language {
                // We want to keep the translations in the final AAB for all the language
                enableSplit = false
            }
        }
    }

    lint {
        // Until we fully migrate to Material3 this lint issue is too verbose https://github.com/home-assistant/android/issues/5420
        disable += listOf("UsingMaterialAndMaterial3Libraries")
    }

    experimentalProperties["android.experimental.enableScreenshotTest"] = true

    screenshotTests {
        imageDifferenceThreshold = 0.00025f // 0.025%
    }


//    firebaseAppDistribution {
//        serviceCredentialsFile = "firebaseAppDistributionServiceCredentialsFile.json"
//        releaseNotesFile = "./app/build/outputs/changelogBeta"
//        groups = "continuous-deployment"
//    }
}

dependencies {
    // Most of the dependencies are coming from the convention plugin to avoid duplication with `:automotive` module.
    "fullImplementation"(libs.car.projected)
    //"fullImplementation"(libs.amap)

    screenshotTestImplementation(libs.compose.uiTooling)
}

// Disable to fix memory leak and be compatible with the configuration cache.
configure<GoogleServicesPluginConfig> {
    disableVersionCheck = true
}