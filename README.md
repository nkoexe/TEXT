# TEXT

A minimal offline Android app for typing text, previewing it in a landscape-shaped surface, and showing it fullscreen in landscape.

## Features

- Native Kotlin + Jetpack Compose + Material 3; Android 8.0 (API 26)+.
- Multiline editor with a live landscape-proportioned preview.
- One-tap immersive landscape display; Back returns to portrait editing.
- Local on-device history: newest first, exact duplicates move to the top, capped at 10 entries. Tap to reuse; swipe horizontally to delete.
- No accounts, network access, analytics, or external storage.

## Build

Requirements: JDK 17, Android SDK Platform 36, Build Tools 36.0.0, and Gradle 8.13.

```bash
gradle testDebugUnitTest
gradle lintDebug
gradle assembleDebug
```

The debug APK is generated at `app/build/outputs/apk/debug/app-debug.apk`. GitHub Actions runs tests, lint, and a debug build for pushes and pull requests to `main`, then uploads the APK as an artifact.

The repository currently uses installed Gradle in local/CI instructions rather than the Gradle Wrapper. A wrapper should be generated and checked in from a trusted Gradle installation before expecting `./gradlew` to work.

## Documentation

- [Agent instructions](AGENTS.md)
- [Product specification](docs/PRODUCT_SPEC.md)
- [Architecture](docs/ARCHITECTURE.md)
- [Development guide](docs/DEVELOPMENT.md)
- [Roadmap](docs/ROADMAP.md)

## License

No license has been selected. Do not assume the project is open-source licensed until one is added.
