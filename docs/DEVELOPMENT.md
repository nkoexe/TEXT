# Development guide

## Requirements

- JDK 17.
- Android SDK Platform 36 and Build Tools 36.0.0.
- Gradle 8.13 (installed separately for now; the official Gradle Wrapper is not checked in yet).
- Network access for initial dependency resolution.

## Build and test

```bash
gradle assembleDebug
gradle testDebugUnitTest
gradle lintDebug
# Optional local emulator/device verification:
gradle connectedDebugAndroidTest
```

The debug APK is written to `app/build/outputs/apk/debug/app-debug.apk`. GitHub Actions builds and uploads that APK before running tests, so it remains available when a later test assertion fails (a compilation/build failure cannot produce an APK). CI runs unit tests, Android lint, and instrumentation tests on an API 35 emulator. The debug APK artifact is retained for 14 days.

Open the repository root in Android Studio and allow Gradle sync to finish. Do not commit `local.properties`, generated build output, SDK paths, or signing credentials.

## Dependencies

Versions are centralized in `gradle/libs.versions.toml`. Update Kotlin, Compose, AGP, and Gradle together, consult their compatibility documentation, and verify unit tests plus a full APK build before merging.

## Release

Distribution is intended to be through APK files, not Play Store. A release signing setup is not configured yet. Do not commit keystores or passwords; use GitHub Secrets only after a signing key and backup/recovery process have been deliberately chosen.
