# Architecture

## Principles

- One Activity with a small number of Compose screens.
- Keep UI state coordination separate from persistence.
- Keep all user data local. There is no app runtime network stack or Internet permission.
- Use deterministic pure policies for behavior that can be tested on the JVM.
- Avoid a formal domain/use-case layer without a concrete need.

## Responsibilities

- `MainActivity.kt`: entry point, editor/display state, portrait/landscape transitions, system-bar styling, and keep-screen-on lifecycle.
- `HomeViewModel.kt`: serializes history operations, performs database work off the main thread, and exposes observable history state.
- `ui/HomeScreen.kt`: blank multiline editor, icon-only formatting placeholders, landscape preview, compact independently scrollable history panel, and SHOW action. Owns swipe-to-delete threshold haptics and transition styling.
- `ui/DisplayScreen.kt`: immersive landscape rendering with centered white text on black.
- `ui/TextFitting.kt`: shared measured font-fit implementation used by editor and display.
- `ui/FontFitPolicy.kt`: pure binary-search policy, including minimum-size overflow status.
- `ui/theme/Theme.kt`: Outfit typography, expressive shape scale, Android 12+ dynamic Material You colors, and system-aware fallback palettes.
- `data/HistoryPolicy.kt`: pure rules for exact matching, duplicate promotion, ordering, and the ten-entry limit.
- `data/HistoryRepository.kt`: app-private SQLite database and transactional persistence.

## UI behavior

- Compose typography uses Outfit. The editor and fullscreen message canvas remain black with white text; the surrounding interface uses wallpaper-derived dynamic colors on Android 12+ and system light/dark fallback palettes on older versions.
- The icon-only Text color, Background fill, and Font controls use locally bundled Material vectors and accessible descriptions. They remain presentation-only placeholders with empty click handlers.
- The editor canvas is blank before text is entered. It fills the available content width; its height is derived from that width and the device's landscape display aspect ratio, not keyboard visibility.
- History remains visible while the keyboard is open and scrolls independently inside a compact, responsive panel with a bounded height; it does not consume all remaining vertical space.
- A history swipe uses a 36% positional threshold. When the dismissal target is reached, the UI requests one gesture-threshold haptic per attempt; a restrained spring animates the delete-reveal color. This is a platform haptic request and device behavior still needs physical verification.
- SHOW rejects blank/whitespace-only text, records valid displayed text, then enters display mode.

## History storage contract

- Reject blank/whitespace-only text.
- Keep exact text (no trimming, normalization, or case folding).
- Promote exact duplicates and refresh their timestamp.
- Keep newest-first ordering and at most 10 unique non-blank entries.
- Apply the pure policy to the existing list and replace at most 10 rows in one transaction.
- Use row insertion order for ordering instead of wall-clock time, so system clock changes do not reorder history.
- Support exact text deletion and survive app restarts.

The small fixed history limit makes replacing ten rows transactionally a straightforward choice. A failed transaction rolls back as a unit.

## Preview/display sizing

Both screens use TextMeasurer against actual width and height constraints, then binary-search for the largest font that fits down to 18sp. The maximum candidate is based on available canvas height. When even 18sp overflows, the editor shows a warning and the display keeps the minimum rather than silently shrinking the type to an unreadable size.

The implementation shares the measurement policy and typeface, but line wrapping and preview/display parity still require on-device checks across font scales, densities, keyboard configurations, and aspect ratios.

## Android window lifecycle

- Home is portrait; display requests landscape and hides the system bars with transient system-gesture reveal enabled.
- Keep-screen-on is enabled only during display.
- Back returns to the editor and restores portrait orientation/system bars.
- Editor text and display state use `rememberSaveable`; history is persisted separately in SQLite.
- The Activity declares orientation/screen/keyboard configuration changes in the manifest. Orientation, Back, keyboard reopening, and system-bar restoration still need device-level regression checks.
