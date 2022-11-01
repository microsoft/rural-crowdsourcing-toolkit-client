// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    dependencies {
        classpath(Plugins.gms)
        classpath(Plugins.crashlytics)
        classpath(Plugins.safeArgs)
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle.kts files
    }
}

plugins {
    /**
     * You should use `apply false` in the top-level build.gradle file
     * to add a Gradle plugin as a build dependency, but not apply it to the
     * current (root) project. You should not use `apply false` in sub-projects.
     * For more information, see https://docs.gradle.org/current/userguide/plugins.html#sec:subprojects_plugins_dsl
     */
    id(Plugin.AndroidApplication.id) version Plugin.AndroidApplication.version apply false
    id(Plugin.AndroidLibrary.id) version Plugin.AndroidLibrary.version apply false
    id(Plugin.KotlinAndroid.id) version Plugin.KotlinAndroid.version apply false
    id(Plugin.KtLint.id) version Plugin.KtLint.version
    id(Plugin.Hilt.id) version Plugin.Hilt.version apply false
}

apply {
    from(rootProject.file("install-git-hooks.gradle"))
}

allprojects {

    /**
     * Applying ktlint plugin is all modules
     */
    apply(plugin = "org.jlleitschuh.gradle.ktlint")
    ktlint {
        /**
         * By default, Ktlint doesn't allow wildcard-imports
         * and classes with name other than filename
         * Although it is a good practice, current code violates this principle
         * Once this issue has been addressed we can remove 'filename' from the list
         */
        disabledRules.set(listOf("no-wildcard-imports", "filename"))
    }
}

tasks {
    val clean by registering(Delete::class) {
        delete(rootProject.buildDir)
    }
}

/**
 * registers installGitHooks task to run before build, this
 * way whenever a new clone is made, the first build copies
 * pre-commit and commit-msg scripts to .git/hooks
 */
tasks.getByPath(":app:preBuild").dependsOn(":installPreCommit")
tasks.getByPath(":app:preBuild").dependsOn(":installCommitMessage")
