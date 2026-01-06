plugins {
    kotlin("jvm") version "2.3.0"
}

sourceSets {
    main {
        kotlin.srcDir("src")
        dependencies {
            implementation("androidx.compose.ui:ui-unit:1.10.0")
            implementation("androidx.compose.ui:ui-geometry:1.10.0")
        }
    }
}

tasks {
    wrapper {
        gradleVersion = "8.11.1"
    }
}
