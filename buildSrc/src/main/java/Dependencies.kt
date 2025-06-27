object Dependencies {
    // App Ui Dependencies
    val uiDependencies by lazy {
        listOf(
            "androidx.core:core-ktx:${DependencyVersions.CORE_KTX}",
            "androidx.appcompat:appcompat:${DependencyVersions.APP_COMPAT}",
            "com.google.android.material:material:${DependencyVersions.MATERIAL}",
            "androidx.constraintlayout:constraintlayout:${DependencyVersions.CONSTRAINT_LAYOUT}",
            "com.airbnb.android:lottie:${DependencyVersions.LOTTIE}",
            "androidx.slidingpanelayout:slidingpanelayout:${DependencyVersions.SLIDE_PANE}"
        )
    }

    // Test Dependencies
    val junitDependency by lazy {
        listOf(
            "junit:junit:${DependencyVersions.JUNIT}",
            "org.jetbrains.kotlin:kotlin-test-junit:1.8.10",
            "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.1",
            "org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.1",
            "io.mockk:mockk:1.13.2",
            "app.cash.turbine:turbine:0.7.0"
        )

    }
    val androidTestDependencies by lazy {
        listOf(
            "androidx.test.espresso:espresso-core:${DependencyVersions.ESPRESSO}",
            "androidx.test.ext:junit:${DependencyVersions.ANDROID_JUNIT}",
            "androidx.compose.ui:ui-test-junit4"
        )
    }
    val debugmplementation by lazy {
        listOf(
            "androidx.compose.ui:ui-tooling",
            "androidx.compose.ui:ui-test-manifest"
        )
    }

    // navigation
    val navigationDependencies by lazy {
        listOf(
            "androidx.hilt:hilt-navigation-compose:1.0.0",
            "androidx.navigation:navigation-compose:2.6.0"
        )
    }

    // Networking Dependencies
    val retrofitDependencies by lazy {
        listOf(
            "com.squareup.retrofit2:retrofit:${DependencyVersions.RETROFIT}",
            "com.squareup.retrofit2:converter-gson:${DependencyVersions.RETROFIT}",
            "com.squareup.okhttp3:logging-interceptor:${DependencyVersions.OKHTTP_INTERCEPTOR}",
            "com.google.code.gson:gson:${DependencyVersions.GSON}"
        )
    }

    // Coroutines Dependencies
    val coroutinesDependency by lazy {
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${DependencyVersions.COROUTINES}"
    }

    // Hilt
    val hiltDependency by lazy {
        "com.google.dagger:hilt-android:${DependencyVersions.HILT}"
    }
    val hiltCompiler by lazy {
        "com.google.dagger:hilt-android-compiler:${DependencyVersions.HILT}"
    }
    val daggerAndroid by lazy {
        "com.google.dagger:dagger-android:${DependencyVersions.DAGGER_ANDROID}"
    }
    val roomCompiler by lazy {
        "androidx.room:room-compiler:${DependencyVersions.ROOM}"
    }

    // Room
    val roomDependencies by lazy {
        listOf(
            "androidx.room:room-runtime:${DependencyVersions.ROOM}",
            "androidx.room:room-ktx:${DependencyVersions.ROOM}",
            "androidx.room:room-common:${DependencyVersions.ROOM}",
        )
    }

    val composeDependency by lazy {
        listOf(
            "androidx.compose.material3:material3:${DependencyVersions.MATERIAL3_COMPOSE}",
            "androidx.activity:activity-compose:${DependencyVersions.ACTIVITY_COMPOSE}",
            "androidx.compose.ui:ui",
            "androidx.compose.ui:ui-graphics",
            "androidx.compose.ui:ui-tooling-preview",
            "androidx.compose.foundation:foundation:${DependencyVersions.FOUNDATION_COMPOSE}",
            "androidx.compose.ui:ui-util:${DependencyVersions.UI_UTIL_COMPOSE}",
            "androidx.constraintlayout:constraintlayout-compose:${DependencyVersions.CONSTRAINT_LAYOUT_COMPOSE}",
            "com.google.accompanist:accompanist-systemuicontroller:${DependencyVersions.SYSTEM_UI_CONTROLLER}",
            "io.coil-kt:coil-compose:${DependencyVersions.COIL_COMPOSE}",
            "androidx.appcompat:appcompat:1.3.0-beta01",
            "androidx.hilt:hilt-navigation-compose:${DependencyVersions.HILT_COMPOSE}",
            "com.airbnb.android:lottie-compose:${DependencyVersions.LOTTIE_COMPOSE}",
            "androidx.compose.material:material:${DependencyVersions.MATERIAL_COMPOSE}",
            "androidx.lifecycle:lifecycle-runtime-ktx:${DependencyVersions.RUN_TIME_LIFECYCLE_COMPOSE}"
        )
    }
    val composeUiDependency by lazy {
        "androidx.compose.ui:ui-tooling:${DependencyVersions.MATERIAL_COMPOSE}"
    }
    val composePlatformBom by lazy {
        "androidx.compose:compose-bom:${DependencyVersions.PLATFORM_BOM_COMPOSE}"
    }
    val composePlatformBomAndroidTest by lazy {
        "androidx.compose:compose-bom:${DependencyVersions.PLATFORM_BOM_COMPOSE}"
    }

    object Classpath {

        val toolsBuildClasspath by lazy {
            "com.android.tools.build:gradle:${DependencyVersions.ClasspathVersions.TOOLS_BUILD_CLASSPATH}"
        }
        val kotlinSerializationClasspath by lazy {
            "org.jetbrains.kotlin:kotlin-serialization:${DependencyVersions.ClasspathVersions.KOTLIN_SERIALZATION_CLASSPATH}"
        }
    }

}