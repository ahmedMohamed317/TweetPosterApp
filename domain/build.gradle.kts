plugins {
    id(Plugins.JAVA_LIBRARY)
    id(Plugins.KOTLIN_JVM)
    kotlin(Plugins.KOTLIN_KAPT)
}
java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    implementation (Dependencies.daggerAndroid)
    implementation(Dependencies.coroutinesDependency)
    Dependencies.junitDependency.forEach { testImplementation(it) }
}
