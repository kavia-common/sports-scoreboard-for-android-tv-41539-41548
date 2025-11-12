# Sports Scoreboard TV - Android (Declarative Gradle)

This module contains an Android TV app that displays live sports scores backed by Firebase Realtime Database. It uses:
- Navigation, RecyclerView, Lifecycle ViewModel
- Media3 ExoPlayer for highlight playback
- Coil for image loading
- Ocean Professional theme

Google Services setup:
- Place your Firebase `google-services.json` at:
  `sports-scoreboard-for-android-tv-41539-41548/android_tv_frontend/app/google-services.json`
- Ensure your Firebase project has Realtime Database and a `/matches` node.

Build:
- ./gradlew build

Install and run on device/emulator:
- ./gradlew :app:installDebug
- Launch "Sports Scoreboard TV"

Preview (web, for visual validation):
- Serve `sports-scoreboard-for-android-tv-41539-41548/preview` on port 3000 using any static server.

Build notes (Gradle wrapper):
- Wrapper is pinned to Gradle 9.0.2 from services.gradle.org to avoid mirror redirects and checksum proxy mismatches.
- If your CI cache requires a different 9.x artifact, try updating distributionUrl to:
  - https://services.gradle.org/distributions/gradle-9.1-all.zip
  - https://services.gradle.org/distributions/gradle-9.2-all.zip
- Keep using distributions from services.gradle.org and avoid Github mirrors.
- Do not add distributionSha256Sum when behind proxies that may rewrite or cache artifacts (can cause false mismatches).