# Product specification

## Purpose

TEXT is a minimal Android utility for composing a short message and presenting it fullscreen in landscape. It should be quick to open, type into, and show to another person.

## Platform

- Native Android app written in Kotlin.
- Jetpack Compose with Material 3 Expressive theming.
- Minimum Android version: Android 8.0 (API 26).
- Portrait home screen; fullscreen display screen forced to landscape.
- Fully offline at runtime. No accounts, backend, analytics, ads, or Internet permission.
- Distribution through APK artifacts/releases; no Play Store setup.

## Home screen

- Use Material You dynamic system colors on Android 12+; fall back to coordinated Material color schemes that follow the system light/dark setting on older versions.
- Use the Outfit typeface throughout Compose, expressive typography, rounded Material shapes, and tonal surfaces.
- Show three toolbar placeholders: Text color, Background, and Font. These controls have no implemented behavior yet; the current display remains white text on black.
- Start with an empty text canvas; do not show a placeholder prompt or character counter.
- The black, rounded message canvas is both the multiline editor and a live preview of the fullscreen message, with text centered horizontally and vertically.
- Keep the editor canvas full available width and derive its height from the device landscape aspect ratio. Canvas geometry must not change when the software keyboard opens.
- Focus the editor and open the keyboard on launch and when returning from display.
- Keep the Recent history section visible at all times, including while the keyboard is open. The list scrolls independently in its own constrained panel and takes the remaining vertical space.
- Keep the SHOW action visible. Empty/whitespace-only content cannot be displayed.
- Show only the concise “Nothing here yet” empty-history message before the first item is available.

## Preview and display parity

- Both editor and display use the shared measured font-fitting implementation and Outfit typeface.
- Measure text within actual width and height constraints and use binary search to choose the largest fitting font.
- Minimum font size is 18sp. If content still overflows, show a warning in the editor and retain the minimum size rather than making text unreadably small.
- The preview uses the same landscape aspect ratio as the fullscreen display, scaled to available width. Line wrapping and visual parity still require device verification across densities, font scales, and aspect ratios.
- Never modify, normalize, or discard the source text.

## Display screen

- Force landscape while active; restore portrait after returning home.
- Hide system bars using immersive behavior, while allowing Android's transient system-gesture reveal.
- Keep the screen awake only during display mode.
- Use a black background and centered white Outfit text.
- Android Back button/system gesture exits. No on-screen controls.
- Preserve editor content when returning and reopen the keyboard.

## History

- Persist locally across restarts.
- Newest displayed text first, maximum 10 entries.
- Tapping an entry loads its content in the editor.
- Swipe horizontally to delete; the dismissal uses a 36% positional threshold, a single gesture-threshold haptic request when the target is reached, and a restrained spring-driven color reveal.
- Exact duplicates move to the top rather than being added twice.
- Store text exactly as entered; do not trim or normalize it.
- Update the duplicate's last-displayed time.

## Out of scope / current limitations

No functional color/font customization, settings screen, sharing, synchronization, account system, telemetry, ads, unnecessary permissions, or Play Store release pipeline. The three toolbar controls are placeholders only. Dynamic color and light/dark appearance follow Android system settings automatically.

## Acceptance criteria

See `docs/ROADMAP.md`. Implementation in code and green JVM/CI checks do not replace the outstanding device-level validation tasks.
