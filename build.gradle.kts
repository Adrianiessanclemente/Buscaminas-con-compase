import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("jvm") version "2.0.0"
    id("org.jetbrains.compose") version "1.7.3"
    id("org.jetbrains.kotlin.plugin.compose")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    google()
}

dependencies {
    // Dependencias de Compose para desktop
    implementation(compose.desktop.currentOs) // Para la plataforma en la que estás trabajando
    // Agrega otros si es necesario para plataformas específicas:
    // implementation(compose.desktop.linuxX64)
    // implementation(compose.desktop.macOS)
}

kotlin {
    jvmToolchain(22) // Asegúrate de que estás usando JDK 23 correctamente
}

compose.desktop {
    application {
        mainClass = "MainKt" // Tu clase principal para la app

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "Buscaminas_Compose"
            packageVersion = "1.0.0"
        }
    }
}
