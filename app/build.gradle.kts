import java.net.URI
import java.security.MessageDigest

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

val outfitResourceDirectory = layout.buildDirectory.dir("generated/res/outfit")
val outfitFontFile = outfitResourceDirectory.map { it.file("font/outfit_variable.ttf") }
val prepareOutfitFont = tasks.register("prepareOutfitFont") {
    outputs.file(outfitFontFile)
    doLast {
        val destination = outfitFontFile.get().asFile
        if (!destination.exists()) {
            destination.parentFile.mkdirs()
            val temporary = destination.resolveSibling("${destination.name}.download")
            val fontUrl = URI(
                "https://raw.githubusercontent.com/google/fonts/45e3d5269e4a17d823f6d84470bc084dc5ae46f6/ofl/outfit/Outfit%5Bwght%5D.ttf"
            ).toURL()
            val connection = fontUrl.openConnection().apply {
                connectTimeout = 15_000
                readTimeout = 30_000
            }
            connection.getInputStream().use { source ->
                temporary.outputStream().use { target -> source.copyTo(target) }
            }

            val bytes = temporary.readBytes()
            val digest = MessageDigest.getInstance("SHA-1")
            digest.update("blob ${bytes.size}\u0000".toByteArray(Charsets.UTF_8))
            val actualBlobSha = digest.digest(bytes).joinToString("") {
                "%02x".format(it.toInt() and 0xff)
            }
            check(actualBlobSha == "466d6245f9582df39bf73da91a8b3c938fd061cd") {
                "Downloaded Outfit font failed its pinned Git blob SHA-1 check."
            }

            if (!temporary.renameTo(destination)) {
                temporary.copyTo(destination, overwrite = true)
                temporary.delete()
            }
        }
    }
}

android {
    namespace = "com.nkoexe.text"
    compileSdk = 36
    buildToolsVersion = "36.0.0"

    defaultConfig {
        applicationId = "com.nkoexe.text"
        minSdk = 26
        targetSdk = 36
        versionCode = 1
        versionName = "0.1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    sourceSets.getByName("main").res.srcDir(outfitResourceDirectory)

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    buildFeatures { compose = true }
}

tasks.named("preBuild").configure {
    dependsOn(prepareOutfitFont)
}

kotlin {
    compilerOptions { jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17) }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    implementation(platform(libs.compose.bom))
    androidTestImplementation(platform(libs.compose.bom))
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.graphics)
    implementation(libs.compose.foundation)
    implementation(libs.compose.material3)
    implementation(libs.compose.material.icons.core)
    implementation(libs.compose.ui.tooling.preview)
    debugImplementation(libs.compose.ui.tooling)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.junit)
    androidTestImplementation(libs.androidx.test.runner)
    androidTestImplementation(libs.compose.ui.test.junit4)
    debugImplementation(libs.compose.ui.test.manifest)
}
