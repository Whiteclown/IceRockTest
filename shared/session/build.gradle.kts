plugins {
	id(Dependencies.Plugins.ANDROID_LIBRARY)
	id(Dependencies.Plugins.KOTLIN_ANDROID)
	kotlin(Dependencies.Plugins.KAPT)
	kotlin(Dependencies.Plugins.SERIALIZATION) version Dependencies.Versions.SERIALIZATION
	id(Dependencies.Plugins.HILT)
	`android-kotlin-convention`
}

dependencies {
	kapt(Dependencies.Hilt.COMPILER)
	implementation(Dependencies.Hilt.ANDROID)

	implementation(Dependencies.Core.CORE)
	implementation(Dependencies.Core.APPCOMPAT)
	implementation(Dependencies.Core.MATERIAL)

	implementation(Dependencies.Network.OKHTTP)
	implementation(Dependencies.Network.RETROFIT)
	implementation(Dependencies.Network.KOTLINX_SERIALIZATION)

	implementation(project(Modules.Core.CORE))
}