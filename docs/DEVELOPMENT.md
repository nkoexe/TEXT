# Development guide

## Requirements

- Android Studio with Android SDK Platform 36 installed, or equivalent command-line SDK tools.
- JDK 17.
- Gradle 8.13 for local command-line builds. CI installs this Gradle version directly because the Gradle Wrapper binary is not part of this initial scaffold; adding and verifying the official wrapper is an early setup task.
- Internet access for the initial dependency resolution.

## Build and test

From the repository root:

```bash
gradle testDebugUnitTest
gradle lintDebug
gradle assembleDebug
```

The debug APK is written to `app/build/outputs/apk/debug/app-debug.apk`.

The GitHub Actions workflow runs unit tests, lint, Android UI tests on an API 35 emulator, and the debug build for pushes and pull requests targeting `main`, then uploads the APK as a 14-day artifact. A release workflow can be added when tagged APK releases are ready; it is intentionally not configured yet.

## Android Studio

Open the repository root as an Android project. Allow Gradle sync and dependency resolution to finish. Do not commit `local.properties`, generated build outputs, local SDK paths, or signing credentials.

## Dependency updates

Versions are centralized in `gradle/libs.versions.toml`. Update related Kotlin/Compose/AGP/Gradle versions together, consult their official compatibility documentation, and verify a full build plus tests before merging.

## Release APK

Release distribution will use a signed APK attached to a GitHub Release. Do not commit signing material. Configure a persistent release keystore via GitHub Secrets only when a release signing decision has been made; document secure backup and recovery then. No Play Store bundle or upload is required.
