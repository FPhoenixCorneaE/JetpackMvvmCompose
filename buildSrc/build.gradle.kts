plugins {
    `kotlin-dsl`
    `kotlin-dsl-precompiled-script-plugins`
}

buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.21")
    }
}

repositories {
    google()
    mavenCentral()
}

dependencies {
    // in order to be able to use "kotlin-android" in the common script
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.21")
    // in order to recognize the "plugins" block in the common script
    implementation("com.android.tools.build:gradle:7.0.1")
    // in order to recognize the "android" block in the common script
    implementation("com.android.tools.build:gradle-api:7.0.1")
}