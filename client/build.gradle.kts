plugins {
    id("kmp-configuration")
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.publish)
}

description = "Kotlin multiplatform client for the SubSonic API"

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                api(projects.api)

                implementation(libs.ktor.client.core)
                implementation(libs.ktor.client.contentNegotiation)
                implementation(libs.ktor.serialization)
                implementation(libs.hash.md5)
            }
        }

        commonTest {
            dependencies {
                implementation(libs.kotlin.test)
                implementation(libs.coroutines.test)
                implementation(libs.ktor.test)
            }
        }

        jvmTest {
            dependencies {
                implementation(libs.ktor.client.okhttp)
            }
        }

        nativeTest {
            dependencies {
                implementation(libs.ktor.client.curl)
            }
        }

        jsTest {
            dependencies {
                implementation(libs.ktor.client.js)
            }
        }
    }
}

mavenPublishing {
    coordinates("dev.zt64.subsonic", "subsonic-client", version.toString())
    publishToMavenCentral()

    // Only sign if signing keys are configured
    val hasSigningKey = providers
        .environmentVariable("ORG_GRADLE_PROJECT_signingInMemoryKey")
        .orElse(providers.gradleProperty("signing.keyId"))
        .orNull != null

    if (hasSigningKey) {
        signAllPublications()
    }

    val path = "zt64/subsonic-kotlin"

    pom {
        name = "subsonic-kotlin"
        description = "Kotlin Multiplatform library for the Subsonic API"
        inceptionYear = "2026"
        url = "https://github.com/$path"

        licenses {
            license {
                name = "MIT License"
                url = "https://opensource.org/licenses/MIT"
            }
        }

        developers {
            developer {
                id = "zt64"
                name = "zt64"
                url = "https://zt64.dev"
            }
        }

        scm {
            url = "https://github.com/$path"
            connection = "scm:git:github.com/$path.git"
            developerConnection = "scm:git:ssh://github.com/$path.git"
        }
    }
}