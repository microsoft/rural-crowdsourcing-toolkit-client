// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    repositories {
        google()
        mavenCentral()
        jcenter()

        // TODO: Remove JCenter
        @Suppress("JcenterRepositoryObsolete")
        jcenter {
            content {
                //  org.jetbrains.trove4j is only available in JCenter
                includeGroup("org.jetbrains.trove4j")
            }
        }
    }

    // TODO: Move to plugins block (recommended from AGP 7.3)
    dependencies {
        classpath(Plugins.agp)
        classpath(Plugins.kotlin)
        classpath(Plugins.gms)
        classpath(Plugins.crashlytics)
        classpath(Plugins.safeArgs)
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.21")
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle.kts files
    }
}

plugins {
    id(Plugin.KtLint.id) version Plugin.KtLint.version
    id(Plugin.Hilt.id) version Plugin.Hilt.version apply false
}

apply {
    from(rootProject.file("install-git-hooks.gradle"))
}

allprojects {

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

    repositories {
        google()
        mavenCentral()
        jcenter()
        maven(url = "https://jitpack.io")

        // TODO: Remove JCenter
        @Suppress("JcenterRepositoryObsolete")
        jcenter {
            content {
                includeGroup("com.amitshekhar.android")
                includeGroup("com.kofigyan.stateprogressbar")
                includeGroup("org.jetbrains.trove4j")
            }
        }
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
