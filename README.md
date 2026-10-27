# TEXT

A minimal offline Android app for typing text, previewing it in a landscape-shaped surface, and showing it fullscreen in landscape.

## Features
- Native Kotlin + Jetpack Compose + Material 3 Expressive; Android 8.0 (API 26)+.
- Material You dynamic colors from the system wallpaper on Android 12+, with system-aware light/dark fallbacks on older versions.
- Outfit typography, expressive type hierarchy, rounded shapes, and tonal Material surfaces.
- Multiline editor with a live landscape-proportioned preview.
- Recent history always remains visible while typing; scroll within its panel, tap an entry to reuse it, or swipe to delete. History is newest-first, promotes exact duplicates, and is capped at 10 entries.
- Measured font fitting down to a minimum of 18sp; the editor warns when text still cannot fit.
- One-tap immersive landscape display; Back returns to portrait editing.
- No accounts, network access, analytics, or external storage at runtime.

## Build and verify

Requirements: JDK 17, Android SDK Platform 36, Build Tools 36.0.0, and Gradle 8.13.

```bash
gradle assembleDebug
gradle testDebugUnitTest
gradle lintDebug
```

The debug APK is generated at `app/build/outputs/apk/debug/app-debug.apk`. GitHub Actions builds and uploads the APK, runs JVM unit tests, and runs Android lint without starting an emulator. The artifact is retained for 14 days.

The Outfit font is retrieved from a pinned Google Fonts commit on a clean build and verified against its Git blob SHA-1 before it is packaged as an Android resource. This build-time download requires network access on a clean checkout; the app itself makes no network requests. The font's SIL Open Font License is included at `licenses/Outfit-OFL.txt`.

## Documentation
- Agent instructions: `AGENTS.md`
- Product specification: `docs/PRODUCT_SPEC.md`
- Architecture: `docs/ARCHITECTURE.md`
- Development guide: `docs/DEVELOPMENT.md`
- Roadmap: `docs/ROADMAP.md`

## License
No license has been selected for the application source. The Outfit font retains its own license, included in `licenses/Outfit-OFL.txt`.
