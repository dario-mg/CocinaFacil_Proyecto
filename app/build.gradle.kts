plugins {
    alias(libs.plugins.android.application)
    id("org.jetbrains.dokka") version "1.9.0"
}

android {
    namespace = "com.Dario.CocinaFacil"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.Dario.CocinaFacil"
        minSdk = 28
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    buildFeatures {
        viewBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.car.ui.lib)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    implementation(files("libs/mariadb-java-client-3.5.1.jar"))
    implementation(libs.jbcrypt)

    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)

    androidTestImplementation(libs.navigation.testing)

    implementation(libs.kotlinx.serialization.json)

    implementation(libs.material.v190)
}

// Configuración de Dokka
tasks.dokkaJavadoc.configure {
    outputDirectory.set(buildDir.resolve("docs")) // Carpeta donde se generará la documentación

    dokkaSourceSets {
        create("main") {
            displayName.set("Android Project Documentation")
            sourceRoots.from(
                file("src/main/java"), // Código Java
            )
            platform.set(org.jetbrains.dokka.Platform.jvm) // Android JVM
            classpath.from(
                files(android.bootClasspath), // Clases base de Android
                configurations["debugCompileClasspath"], // Rutas para dependencias
                configurations["releaseCompileClasspath"] // Rutas de clases en modo release
            )
        }
    }
}
//
//
//tasks.dokkaHtml.configure {
//    outputDirectory.set(buildDir.resolve("docs")) // Carpeta donde se generará la documentación
//
//    dokkaSourceSets {
//        create("main") {
//            displayName.set("Android Project Documentation")
//            sourceRoots.from(
//                file("src/main/java"), // Código Java
//            )
//            platform.set(org.jetbrains.dokka.Platform.jvm) // Android JVM
//            classpath.from(
//                files(android.bootClasspath), // Clases base de Android
//                configurations["debugCompileClasspath"], // Rutas para dependencias
//                configurations["releaseCompileClasspath"] // Rutas de clases en modo release
//            )
//        }
//    }
//}
