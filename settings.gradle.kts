pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()

        maven("https://dl.google.com/dl/android/maven2/")
        maven("https://mirrors.huaweicloud.com/repository/maven/")
        // Добавляем репозитории Huawei
        maven("https://developer.huawei.com/repo/") // Основной репозиторий с библиотеками
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()

        gradlePluginPortal()
        // Здесь также можно указать репозитории для зависимостей проекта,
        // но обычно достаточно тех, что указаны выше
        maven("https://mirrors.huaweicloud.com/repository/maven/")
        maven("https://developer.huawei.com/repo/")

    }
}

rootProject.name = "WeatherApp"
include(":app")
