pluginManagement {
	repositories {
		gradlePluginPortal()
		google()
		mavenCentral()
	}
}

dependencyResolutionManagement {
	repositories {
		google()
		mavenCentral()
	}
}

rootProject.name = "IceRockTest"
include(
	":app",
	":core",
	":features:authorization",
	":features:detailinfo",
	":features:repositorieslist",
	":features:splash",
	":shared:session",
)
