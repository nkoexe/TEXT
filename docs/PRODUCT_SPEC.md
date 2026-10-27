# Product specification

## Purpose
TEXT is a minimal Android utility for composing a short message and presenting it fullscreen in landscape. It should be quick to open, type into, and show to another person.

## Platform
- Native Android app written in Kotlin.
- Jetpack Compose with Material 3 Expressive theming.
- Minimum Android version: Android 8.0 (API 26).
- Portrait home screen; fullscreen display screen forced to landscape.
- Fully offline. No accounts, backend, analytics, ads, or Internet access.
- Distribution through APK artifacts/releases; no Play Store setup.

## Home screen
- Use Material You dynamic system colors on Android 12+; fall back to coordinated Material color schemes that follow the system light/dark setting on older versions.
- Use the Outfit typeface, expressive typography hierarchy, rounded Material shapes, and tonal surfaces for interface structure.
- The message editor is a black, rounded landscape-shaped canvas with white text, mirroring fullscreen display.
- The text in the canvas is both the editor and the live preview.
- Multiline text input, natural wrapping, centered horizontally and vertically.
- Focus the input and open the keyboard on launch and when returning from display.
- Keep the Recent history section visible at all times, including while the keyboard is open. The list scrolls independently in its own constrained panel.
- Keep the editor canvas full-width within the screen margins and preserve its landscape aspect ratio when the keyboard appears; the history panel absorbs the changing vertical space.
- Keep the SHOW action visible above the keyboard. Empty/whitespace-only content cannot be displayed.
- Show an empty-history state before the first message has been displayed.

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
No manual theme picker, custom color/font controls, settings screen, sharing, synchronization, account system, telemetry, ads, permissions beyond Android defaults, or Play Store release pipeline. Dynamic color and light/dark appearance follow Android system settings automatically.

## Acceptance criteria
See the checklists in ROADMAP.md. Do not mark a feature complete until it is implemented and verified on a physical device or emulator where appropriate.
