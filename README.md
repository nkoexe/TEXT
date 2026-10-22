# TEXT

A minimal Android app for typing a message, previewing it in a landscape-shaped surface, and showing it fullscreen in landscape.

> **Bootstrap status:** the first Compose skeleton is in place. Persistent history, verified preview/display parity, and release packaging are not implemented yet. Track progress in [`docs/ROADMAP.md`](docs/ROADMAP.md).

## Product constraints

- Kotlin + Jetpack Compose + Material 3
- Android 8.0 (API 26) and newer
- Portrait editor with a landscape-shaped live preview
- One-tap immersive landscape text display
- Local history, limited to the 10 most recently displayed texts (planned; policy tests exist)
- No accounts, backend, Internet access, analytics, customization, or Play Store distribution
- GitHub Actions for tests, lint, and debug APK artifacts

## Build

Requirements: JDK 17, Android SDK Platform 36, and Gradle 8.13.

```bash
gradle testDebugUnitTest
gradle lintDebug
gradle assembleDebug
```

The debug APK is generated at `app/build/outputs/apk/debug/app-debug.apk`.

The initial workflow installs Gradle directly. Adding the official Gradle Wrapper is tracked in the roadmap so contributors can use `./gradlew` consistently.

## Documentation

- [`AGENTS.md`](AGENTS.md) — coding-agent instructions
- [Product specification](docs/PRODUCT_SPEC.md)
- [Architecture](docs/ARCHITECTURE.md)
- [Development guide](docs/DEVELOPMENT.md)
- [Roadmap](docs/ROADMAP.md)

## License

No license has been selected. Do not assume the project is open-source licensed until a license is added.
