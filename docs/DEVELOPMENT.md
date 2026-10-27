# Development guide

## Requirements
- JDK 17.
- Android SDK Platform 36 and Build Tools 36.0.0.
- Gradle 8.13 (installed separately; the official Gradle Wrapper is not checked in yet).
- Network access for Gradle dependency resolution and for fetching the pinned Outfit font on a clean build.

## Build and test

```bash
gradle assembleDebug
gradle testDebugUnitTest
gradle lintDebug
```

The debug APK is written to `app/build/outputs/apk/debug/app-debug.apk`. The normal GitHub Actions workflow performs these same checks without starting an emulator and uploads the APK artifact before unit tests and lint. The artifact is retained for 14 days.

The Outfit font is downloaded from a pinned Google Fonts commit during `preBuild`, checked against the expected Git blob SHA-1, and placed in generated Android resources. It is packaged into the APK; no font request is made at runtime. Its SIL Open Font License is at `licenses/Outfit-OFL.txt`.

Open the repository root in Android Studio and allow Gradle sync to finish. Do not commit `local.properties`, generated build output, SDK paths, or signing credentials.

## Dependencies
Versions are centralized in `gradle/libs.versions.toml`. Update Kotlin, Compose, AGP, and Gradle together, consult their compatibility documentation, and verify unit tests plus a full APK build before merging.

## Release
Distribution is intended to be through APK files, not Play Store. A release signing setup is not configured yet. Do not commit keystores or passwords; use GitHub Secrets only after a signing key and backup/recovery process have been deliberately chosen.
