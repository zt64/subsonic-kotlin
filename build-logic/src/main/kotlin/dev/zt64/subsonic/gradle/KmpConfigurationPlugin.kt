package dev.zt64.subsonic.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.provider.Property
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import org.jetbrains.kotlin.gradle.targets.js.dsl.KotlinJsSubTargetDsl
import org.jlleitschuh.gradle.ktlint.KtlintExtension

@Suppress("unused")
private fun <T : Any> Property<T>.assign(value: T) = set(value)

private val Project.libs
    get(): VersionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")

class KmpConfigurationPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        configureKmp(target)
        configureMaintenance(target)
    }

    @OptIn(ExperimentalWasmDsl::class)
    private fun configureKmp(target: Project) {
        target.apply(plugin = "org.jetbrains.kotlin.multiplatform")
        target.extensions.configure<KotlinMultiplatformExtension> {
            explicitApi()
            jvmToolchain(17)

            jvm()

            listOf(js(), wasmJs()).forEach {
                it.apply {
                    fun KotlinJsSubTargetDsl.extendTimeout() {
                        testTask {
                            useMocha {
                                timeout = "10s"
                            }
                        }
                    }

                    nodejs {
                        extendTimeout()
                    }

                    browser {
                        extendTimeout()
                    }
                }
            }

            linuxArm64()
            linuxX64()
            mingwX64()
            apple()

            compilerOptions {
                freeCompilerArgs.add("-Xconsistent-data-class-copy-visibility")
            }
        }
    }

    private fun KotlinMultiplatformExtension.apple(configure: KotlinNativeTarget.() -> Unit = {}) {
        val isMacOs = System.getProperty("os.name").lowercase().contains("mac")

        if (!isMacOs) return

        listOf(
            iosX64(),
            iosArm64(),
            iosSimulatorArm64(),
            macosX64(),
            macosArm64(),
            tvosX64(),
            tvosArm64(),
            tvosSimulatorArm64(),
            watchosArm32(),
            watchosArm64(),
            watchosSimulatorArm64()
        ).forEach(configure)
    }

    private fun configureMaintenance(target: Project) {
        target.apply {
            plugin("org.jlleitschuh.gradle.ktlint")
            plugin("org.jetbrains.kotlinx.binary-compatibility-validator")
        }

        target.configure<KtlintExtension> {
            version = target.libs.findVersion("ktlint").get().toString()
        }
    }
}