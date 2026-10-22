# Product specification

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
- Main input is a rounded rectangle whose aspect ratio matches the device's landscape display.
- The text in the rectangle is both the editor and the live preview.
- Multiline text input, natural wrapping, centered horizontally and vertically.
- Focus the input and open the keyboard on launch and when returning from display.
- History is shown below the preview only while the keyboard is hidden.
- A prominent SHOW button starts display mode. Empty/whitespace-only content cannot be displayed.

## Preview and display parity

- Share a measured font-fitting implementation rather than maintaining unrelated renderers.
- Measure text within actual width and height constraints and use binary search to choose the largest fitting font.
- Minimum font size is 18sp. If content still overflows, show a warning in the editor and retain the minimum size rather than making text unreadably small.
- The preview is a scaled-down representation of the landscape display layout; line breaks and proportions still need device verification.
- Never modify, normalize, or discard the source text.

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
- Swipe horizontally to delete.
- Exact duplicates move to the top rather than being added twice.
- Store text exactly as entered; do not trim or normalize it.
- Update duplicate's last-displayed time.

## Out of scope

No settings, customization, themes, font controls, sharing, synchronization, account system, telemetry, ads, permissions beyond Android defaults, or Play Store release pipeline.

## Acceptance criteria

See the checklists in ROADMAP.md. Do not mark a feature complete until it is implemented and verified on an emulator/device as appropriate.
