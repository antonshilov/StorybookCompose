pluginManagement {
    repositories {
        google()
        jcenter()
        gradlePluginPortal()
        mavenCentral()
        maven { url = uri("https://maven.pkg.jetbrains.space/public/p/compose/dev") }
    }
    
}
rootProject.name = "StorybookCompose"

include(":storybook")
include(":utils:SplitPane")
include(":examples:MaterialDemo")
