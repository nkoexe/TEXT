# Agent instructions

These instructions apply to the entire repository. Read this file before making changes.

## Product boundaries

- TEXT is a small, offline-first Android utility: type text, preview it in a landscape-shaped surface, and show it fullscreen in landscape.
- Keep the implementation deliberately small. Do not add accounts, runtime networking, analytics, ads, settings, theme customization, or Play Store publishing without an explicit product decision.
- Minimum supported Android version is Android 8.0 (API 26).
- Use Kotlin, Jetpack Compose, and Material 3. Follow `docs/PRODUCT_SPEC.md` as the source of truth for behavior.
- The editor uses Outfit typography across the Compose UI. Android 12+ uses wallpaper-derived Material You colors; older versions use system light/dark fallback palettes.
- The three formatting toolbar buttons (Text color, Background, Font) are deliberately inert UI placeholders. Do not imply that custom colors or fonts work until implemented.
- Keep the editor canvas full available width and derive its height from the device's landscape aspect ratio, independent of keyboard visibility. History must remain visible and scroll independently while typing.
- The editor canvas is intentionally blank when no text has been entered; do not re-add a placeholder prompt without a product request.

## Engineering rules

- Prefer straightforward code and clear names over abstractions, frameworks, and speculative extensibility.
- Keep UI, state coordination, and persistence responsibilities separate. Avoid a formal domain/use-case layer without a concrete need.
- The preview and display must share their text sizing/layout logic. Do not independently tune two separate renderers and call the result an exact preview.
- Never silently truncate or discard user-entered text.
- History is local, newest first, and capped at 10 entries. Compare text exactly; do not trim or normalize its content before storing it.
- Empty or whitespace-only text must not be displayed or added to history.
- History rows support horizontal swipe-to-delete. The threshold is deliberately firm; a single gesture-threshold haptic is requested when the dismissal target is reached, with restrained spring-driven color feedback.
- Keep the screen awake only while the fullscreen display is active. Restore portrait orientation and normal system UI on exit.
- Do not add Android permissions unless a documented feature requires them. The app should not need Internet permission. The build currently downloads a pinned Outfit font only when preparing generated resources; that is build-time access, not app runtime networking.
- Keep dependencies minimal and versions centralized in `gradle/libs.versions.toml`.

## Build and verification

The normal CI path intentionally does not start an emulator. Keep it limited to:

```bash
gradle assembleDebug
gradle testDebugUnitTest
gradle lintDebug
```

CI uploads the debug APK before running unit tests and lint, with a 14-day artifact retention period. Instrumentation test source files remain under `app/src/androidTest`, but the default workflow does not compile or execute them. Do not add an emulator workflow or run connected tests unless explicitly requested.

## Before finishing a change

1. Read relevant documentation and existing code before editing.
2. Run focused checks, then `gradle assembleDebug testDebugUnitTest lintDebug` when the environment permits.
3. Add or update tests for behavior changes; prioritize history policy, font fitting, and lifecycle/orientation behavior. Keep runtime/device validation separate from JVM test results.
4. Review the diff for accidental scope expansion, unused dependencies, credentials, generated files, and undocumented behavior.
5. Update docs only when behavior or build instructions actually change; do not create duplicate documentation.
6. Report what changed, checks actually run, anything not verified, and the best next step. Never claim a build or test passed without running it.

## Scope discipline

- Do not add features just because a common app has them.
- Do not add a license without an explicit project decision.
- Do not commit signing keys, local SDK paths, machine-specific settings, or generated build output.
- Follow the repository's existing conventions when they differ from examples elsewhere.
