rootProject.name = "quiz"

pluginManagement {
    val detektId: String by settings
    val detektVersion: String by settings
    val dokkaId: String by settings
    val dokkaVersion: String by settings
    val kotlinId: String by settings
    val kotlinVersion: String by settings
    val springBootId: String by settings
    val springBootVersion: String by settings
    val springDependencyManagementPluginVersion: String by settings
    val springDependencyManagementPluginId: String by settings
    val testLoggerId: String by settings
    val testLoggerVersion: String by settings

    resolutionStrategy {
        eachPlugin {
            if (requested.id.namespace?.startsWith(kotlinId) == true)
                useVersion(kotlinVersion)

            when (requested.id.id) {
                testLoggerId -> useVersion(testLoggerVersion)
                detektId -> useVersion(detektVersion)
                dokkaId -> useVersion(dokkaVersion)
                kotlinId -> useVersion(kotlinVersion)
                springDependencyManagementPluginId -> useVersion(springDependencyManagementPluginVersion)
                springBootId -> useVersion(springBootVersion)
            }
        }
    }
}
