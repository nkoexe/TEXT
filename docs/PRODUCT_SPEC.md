# Product specification

Status: initial specification; implementation is a scaffold and is not feature-complete.

## Purpose

TEXT is a minimal Android utility for composing a short message and presenting it fullscreen in landscape. It should be quick to open, type into, and show to another person.

## Platform

- Native Android app written in Kotlin.
- Jetpack Compose and Material 3.
- Minimum Android version: Android 8.0 (API 26).
- Portrait home screen; fullscreen display screen forced to landscape.
- Fully offline. No accounts, backend, analytics, ads, or Internet access.
- Distribution through APK artifacts/releases; no Play Store setup.

## Home screen

- Black background and white text; Material 3 dark theme with a restrained monochrome primary color.
- The main input is a rounded, outlined rectangle whose aspect ratio matches the device's landscape display.
- The text in the rectangle is both the editor and the live preview.
- Multiline text input, natural wrapping, centered horizontally and vertically.
- Focus the input and open the keyboard on launch and when returning from display.
- The history list sits below the preview and is hidden while the keyboard is visible.
- A prominent SHOW button starts display mode. Empty/whitespace-only content cannot be displayed.

## Preview and display parity

- Use shared text-fitting/layout logic rather than two unrelated renderers.
- The preview is a scaled-down representation of the landscape display layout.
- Center text horizontally and vertically.
- Use the largest font that fits. The current scaffold only has an iterative overflow-shrinking baseline; exact preview/display parity and minimum-size behavior still need verification.
- Never silently truncate or discard text. If content remains too long at the minimum size, provide a clear indication in the editing UI.

## Display screen

- Force landscape while active; restore portrait after returning home.
- Hide system bars using immersive behavior, while allowing Android's transient system-gesture reveal.
- Keep the display awake only during display mode.
- Black background, white text, centered in both axes.
- Android Back button/system gesture exits. No on-screen controls.
- Preserve editor content when returning and reopen the keyboard.

## History

- Persist locally across restarts.
- Newest displayed text first, maximum 10 entries.
- Tapping an entry loads its content in the editor.
- Swipe to delete.
- Exact duplicates are moved to the top rather than added twice.
- Store text exactly as entered; do not trim or normalize it.
- Update duplicate's last-displayed time.

## Out of scope

No settings, customization, themes, font controls, sharing, synchronization, account system, telemetry, ads, permissions beyond Android defaults, or Play Store release pipeline.

## Acceptance criteria

See the checklists in [ROADMAP.md](ROADMAP.md). Do not mark a feature complete until it is implemented and verified on an emulator/device as appropriate.
