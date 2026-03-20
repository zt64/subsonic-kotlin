plugins {
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.publish) apply false
}

allprojects {
    group = "dev.zt64.subsonic"
    version = "1.0.0-alpha06"
}