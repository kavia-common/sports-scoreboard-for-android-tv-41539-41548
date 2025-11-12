androidApplication {
    namespace = "org.example.app"

    dependencies {
        // Core AndroidX + TV
        implementation("androidx.core:core-ktx:1.13.1")
        implementation("androidx.appcompat:appcompat:1.7.0")
        implementation("androidx.recyclerview:recyclerview:1.3.2")
        implementation("androidx.leanback:leanback:1.2.0")
        implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.6")
        implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.6")
        implementation("androidx.navigation:navigation-fragment-ktx:2.8.3")
        implementation("androidx.navigation:navigation-ui-ktx:2.8.3")

        // Media3 ExoPlayer + UI
        implementation("androidx.media3:media3-exoplayer:1.4.1")
        implementation("androidx.media3:media3-ui:1.4.1")

        // Image loading (Coil)
        implementation("io.coil-kt:coil:2.6.0")

        // Firebase (explicit versions to work with DCL)
        implementation(platform("com.google.firebase:firebase-bom:33.4.0"))
        implementation("com.google.firebase:firebase-database-ktx:21.0.0")
        implementation("com.google.firebase:firebase-analytics-ktx:22.1.2")

        // Material
        implementation("com.google.android.material:material:1.12.0")
    }
}
