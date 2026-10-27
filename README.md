# TEXT

A small offline Android app for composing a message, previewing it in a landscape-shaped canvas, and showing it fullscreen in landscape.

## Features

- Native Kotlin + Jetpack Compose + Material 3 Expressive; Android 8.0 (API 26)+.
- Outfit variable-font typography with explicitly mapped weights from Thin through Black, so requested text styles use real font weights.
- Material You wallpaper-derived colors on Android 12+, with system-aware light/dark fallbacks on older Android versions.
- A blank, multiline editor canvas with centered white text on black. Its width and landscape aspect ratio stay constant when the keyboard opens.
- Three compact, icon-only Material formatting placeholders for text color, background fill, and font, with accessible descriptions; customization is not functional yet.
- Recent appears only when history contains entries. Entries render as a standalone list without an enclosing border, scroll independently, and hold at most 10 items; tap to reuse and swipe to delete.
- Swipe deletion has a firmer threshold, a single threshold haptic request, and restrained color-transition feedback.
- Measured font fitting down to 18sp; the editor warns if text still overflows at the minimum size.
- One-tap immersive landscape display; Back returns to portrait editing.
- No runtime network requests, accounts, analytics, ads, or external storage.

## Build and verify

Requirements: JDK 17, Android SDK Platform 36, Build Tools 36.0.0, and Gradle 8.13 installed separately. The official Gradle Wrapper is not checked in yet.

```bash
gradle assembleDebug
gradle testDebugUnitTest
gradle lintDebug
```

The debug APK is generated at `app/build/outputs/apk/debug/app-debug.apk`. GitHub Actions runs these non-emulator checks and uploads the APK as the `TEXT-debug-apk` artifact before unit tests and lint. Artifacts are retained for 14 days. The workflow does not start an emulator.

Instrumentation test sources remain under `app/src/androidTest`, but are not run by default CI. Device-level behavior such as keyboard layout, orientation transitions, swipe haptics, and preview/display parity has not been verified by the normal CI checks.

The Outfit font is retrieved from a pinned Google Fonts commit on a clean build and verified against its Git blob SHA-1 before being packaged as an Android resource. This build-time download requires network access on a clean checkout; the app itself makes no network requests. The font's SIL Open Font License is included at `licenses/Outfit-OFL.txt`.

## Documentation

- Agent instructions: `AGENTS.md`
- Product specification: `docs/PRODUCT_SPEC.md`
- Architecture: `docs/ARCHITECTURE.md`
- Development guide: `docs/DEVELOPMENT.md`
- Roadmap and validation status: `docs/ROADMAP.md`

## License

No license has been selected for the application source. The Outfit font retains its own license, included in `licenses/Outfit-OFL.txt`.
