# Agent instructions

These instructions apply to the entire repository. Read this file before making changes.

## Product boundaries

- TEXT is a small, offline-first Android utility: type text, preview it in a landscape-shaped surface, and show it fullscreen in landscape.
- Keep the implementation deliberately small. Do not add accounts, networking, analytics, ads, settings, theme customization, or Play Store publishing.
- Minimum supported Android version is Android 8.0 (API 26).
- Use Kotlin, Jetpack Compose, and Material 3. Follow `docs/PRODUCT_SPEC.md` as the source of truth for behavior.

## Engineering rules

- Prefer straightforward code and clear names over abstractions, frameworks, and speculative extensibility.
- Keep UI, state coordination, and persistence responsibilities separate. Do not introduce use-case layers unless a real need emerges.
- The preview and display must share their text sizing/layout logic. Do not independently tune two separate renderers and call the result an exact preview.
- Never silently truncate or discard user-entered text.
- History is local, newest first, and capped at 10 entries. Compare text exactly; do not trim or normalize its content before storing it.
- Empty or whitespace-only text must not be displayed or added to history.
- Keep the screen awake only while the fullscreen display is active. Restore portrait orientation and normal system UI on exit.
- Do not add Android permissions unless a documented feature requires them. The app should not need Internet permission.
- Keep dependencies minimal and versions centralized in `gradle/libs.versions.toml`.

## Before finishing a change

1. Read relevant documentation and existing code before editing.
2. Run focused tests, then `gradle testDebugUnitTest lintDebug assembleDebug` when the environment permits.
3. Add or update tests for behavior changes; prioritize history policy, font fitting, and lifecycle/orientation behavior.
4. Review the diff for accidental scope expansion, unused dependencies, credentials, generated files, and undocumented behavior.
5. Update docs only when behavior or build instructions actually change; do not create duplicate documentation.
6. Report what changed, checks actually run, anything not verified, and the best next step. Never claim a build or test passed without running it.

## Scope discipline

- Do not add features just because a common app has them.
- Do not add a license without an explicit project decision.
- Do not commit signing keys, local SDK paths, machine-specific settings, or generated build output.
- Follow the repository's existing conventions when they differ from examples in these instructions.
