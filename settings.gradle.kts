// https://developer.android.com/studio/build#settings-file
pluginManagement {

    /**
     * The pluginManagement {repositories {...}} block configures the
     * repositories Gradle uses to search or download the Gradle plugins and
     * their transitive dependencies.The code below
     * defines the Gradle Plugin Portal, Google's Maven repository, the Maven Central Repository
     * and JCenter as the repositories Gradle should use to look for its dependencies.
     */

    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
        /**
         * TODO: Remove jcenter,
         * Libraries com.kofigyan.stateprogressbar and com.amitshekhar.android:debug-db are available on jcenter() only.
         * Once project is migrated to Jetpack Compose, stateprogressbar won't be required anymore.
         */
        jcenter()
    }
}

dependencyResolutionManagement {

    /**
     * The dependencyResolutionManagement { repositories {...}}
     * block is where we configure the repositories and dependencies used by
     * all modules in your project, such as libraries that we are using to
     * create our application.
     */

    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        jcenter()
        maven(url = "https://jitpack.io")
    }
}
rootProject.name = "Karya"
include(":app")
