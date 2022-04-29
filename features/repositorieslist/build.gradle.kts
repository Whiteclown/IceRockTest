plugins {
    id(Dependencies.Plugins.ANDROID_LIBRARY)
    id(Dependencies.Plugins.KOTLIN_ANDROID)
    kotlin(Dependencies.Plugins.KAPT)
    id(Dependencies.Plugins.HILT)
    `android-kotlin-convention`
}

dependencies {
    kapt(Dependencies.Hilt.COMPILER)
    implementation(Dependencies.Hilt.ANDROID)

    implementation(Dependencies.Core.CORE)
    implementation(Dependencies.Core.APPCOMPAT)
    implementation(Dependencies.Core.MATERIAL)

    implementation(Dependencies.Navigation.FRAGMENT)

    implementation(Dependencies.Layout.CONSTRAINT)

    implementation(project(Modules.Core.CORE))
    implementation(project(Modules.Shared.SESSION))
}