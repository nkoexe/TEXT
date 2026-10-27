# Development guide

## Requirements

- JDK 17.
- Android SDK Platform 36 and Build Tools 36.0.0.
- Gradle 8.13 installed separately; the official Gradle Wrapper is not checked in.
- Network access for Gradle dependency resolution and fetching the pinned Outfit font on a clean build.

## Standard build and checks

Run the same non-emulator checks used by the default GitHub Actions workflow:

```bash
gradle assembleDebug
gradle testDebugUnitTest
gradle lintDebug
```

The debug APK is written to `app/build/outputs/apk/debug/app-debug.apk`. CI uploads it as `TEXT-debug-apk` before running JVM unit tests and lint. Artifacts are retained for 14 days. No emulator is started by the standard workflow.

The repository contains instrumentation test sources under `app/src/androidTest` for basic Compose home behavior and SQLite persistence. They are deliberately not run by default CI; their execution requires a connected physical device or a working emulator. The recent CI setup avoids emulator startup because it was unreliable in the runner environment. Do not interpret a green standard CI run as device-level verification.

## Font resource

The Outfit font is downloaded from a pinned Google Fonts commit during `preBuild`, checked against the expected Git blob SHA-1, and placed in generated Android resources. The font is packaged into the APK; the app makes no font request at runtime. The SIL Open Font License is at `licenses/Outfit-OFL.txt`.

The generated variable font is referenced by `app/src/main/res/font/outfit.xml`. Do not hand-edit generated output under `app/build`; the build task recreates it when necessary.

## Source and tests

- `app/src/main/java`: app code.
- `app/src/test`: JVM tests for history policy/property invariants and font-fit policy.
- `app/src/androidTest`: instrumentation test source, not part of default CI.
- `gradle/libs.versions.toml`: centralized dependency/plugin versions.
- `.github/workflows/android.yml`: current CI definition.

Open the repository root in Android Studio and allow Gradle sync to finish. Do not commit `local.properties`, generated build output, SDK paths, or signing credentials.

## Dependencies

Versions are centralized in `gradle/libs.versions.toml`. Update Kotlin, Compose, AGP, and Gradle together, consult their compatibility documentation, and verify unit tests plus a full APK build before merging.

## Release

Distribution is intended to be through APK files, not Play Store. A release signing setup is not configured yet. Do not commit keystores or passwords; use GitHub Secrets only after a signing key and backup/recovery process have been deliberately chosen.
