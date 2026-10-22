# TEXT

A minimal offline Android app for typing text, previewing it in a landscape-shaped surface, and showing it fullscreen in landscape.

## Features

- Native Kotlin + Jetpack Compose + Material 3; Android 8.0 (API 26)+.
- Multiline editor with a live landscape-proportioned preview.
- Measured font fitting down to a minimum of 18sp; the editor warns when text still cannot fit.
- One-tap immersive landscape display; Back returns to portrait editing.
- Local on-device history: newest first, exact duplicates move to the top, capped at 10 entries. Tap to reuse; swipe horizontally to delete.
- No accounts, network access, analytics, or external storage.

## Build and verify

Requirements: JDK 17, Android SDK Platform 36, Build Tools 36.0.0, and Gradle 8.13.

```bash
gradle assembleDebug
gradle testDebugUnitTest
gradle lintDebug
gradle connectedDebugAndroidTest # requires a running emulator/device
```

The debug APK is generated at app/build/outputs/apk/debug/app-debug.apk. GitHub Actions uploads it as the TEXT-debug-apk artifact before running unit, lint, and emulator instrumentation tests. Artifacts are retained for 14 days, including when later test assertions fail. A compilation failure cannot produce an APK.

The repository currently uses installed Gradle in local/CI instructions rather than the Gradle Wrapper. Generate and check in the official wrapper from a trusted Gradle installation before expecting ./gradlew to work.

## Documentation

- Agent instructions: AGENTS.md
- Product specification: docs/PRODUCT_SPEC.md
- Architecture: docs/ARCHITECTURE.md
- Development guide: docs/DEVELOPMENT.md
- Roadmap: docs/ROADMAP.md

## License

No license has been selected. Do not assume the project is open-source licensed until one is added.
