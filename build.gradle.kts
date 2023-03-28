import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
    id("app.cash.molecule")
}

group = "com.aosp"
version = "1.0"

repositories {
    google()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

kotlin {
    jvm {
        jvmToolchain(17)
        withJava()
    }
    sourceSets {
        val jvmMain by getting {
            dependencies {
                implementation(compose.desktop.currentOs)
                implementation(compose.foundation)
                implementation(compose.animation)
                val preComposeVersion = "1.3.15"
                implementation("moe.tlaster:precompose:$preComposeVersion")
                implementation("moe.tlaster:precompose-molecule:$preComposeVersion")

                implementation("cn.hutool:hutool-cron:5.8.15")
                implementation("com.alibaba.fastjson2:fastjson2-kotlin:2.0.26")
            }
        }
        val jvmTest by getting
    }
}

compose.desktop {
    application {
        mainClass = "MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "AospGUI"
            packageVersion = "1.0.0"
        }
    }
}
