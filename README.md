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
gradle compileDebugAndroidTestKotlin
gradle connectedDebugAndroidTest # requires a running emulator/device
```

The debug APK is generated at `app/build/outputs/apk/debug/app-debug.apk`. GitHub Actions uploads it as the `TEXT-debug-apk` artifact before running JVM unit tests, lint, and instrumentation-test compilation. The artifact is retained for 14 days, including when a later test assertion fails. A build/compilation failure cannot produce an APK.

The separate Android Instrumented Tests workflow now runs automatically on pushes and pull requests targeting `main`, and can also be triggered manually. It boots an API 35 emulator and runs repository persistence tests and Compose UI smoke tests. The repository still uses installed Gradle rather than the Gradle Wrapper; `./gradlew` is not available until the official wrapper is checked in.

## Documentation

- Agent instructions: `AGENTS.md`
- Product specification: `docs/PRODUCT_SPEC.md`
- Architecture: `docs/ARCHITECTURE.md`
- Development guide: `docs/DEVELOPMENT.md`
- Roadmap: `docs/ROADMAP.md`

## License

No license has been selected. Do not assume the project is open-source licensed until one is added.
