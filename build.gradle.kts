// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
	id(Dependencies.Plugins.ANDROID_APPLICATION) apply false
	id(Dependencies.Plugins.ANDROID_LIBRARY) apply false
	id(Dependencies.Plugins.KOTLIN_ANDROID) apply false
}

buildscript {
	repositories {
		google()
		mavenCentral()
	}

	dependencies {
		classpath(Dependencies.Plugins.ANDROID_GRADLE)
		classpath(Dependencies.Plugins.KOTLIN_GRADLE)
		classpath(Dependencies.Plugins.HILT_GRADLE)
	}
}

tasks.register("clean", Delete::class) {
	delete(rootProject.buildDir)
}