# Architecture

## Principles

- One Activity with a small number of Compose screens.
- Keep UI state coordination separate from persistence.
- Keep all data local. No network stack or Internet permission.
- Use deterministic pure policies for behavior that can be tested on the JVM.
- Avoid a formal domain/use-case layer without a concrete need.

## Responsibilities

- MainActivity.kt: entry point, editor/display state, orientation and system-bar transitions.
- ui/HomeScreen.kt: focused multiline editor, landscape-shaped preview, SHOW action, and history UI.
- ui/DisplayScreen.kt: immersive landscape rendering.
- ui/TextFitting.kt: shared measured font-fit implementation used by editor and display.
- ui/FontFitPolicy.kt: pure binary-search policy, including minimum-size overflow status.
- ui/theme/Theme.kt: monochrome Material 3 dark color scheme.
- data/HistoryPolicy.kt: pure rules for exact matching, duplicate promotion, ordering, and the ten-entry limit.
- data/HistoryRepository.kt: app-private SQLite database and transactional persistence.
- HomeViewModel.kt: serializes history operations, performs database work off the main thread, and exposes observable history state.

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

Both screens use TextMeasurer against actual width and height constraints, then binary-search for the largest font that fits down to 18sp. When even 18sp overflows, the editor shows a warning and the display keeps the minimum rather than silently shrinking the type to an unreadable size. Line wrapping and preview/display parity still require device verification across font scales and aspect ratios.

## Android window lifecycle

- Home remains portrait.
- Display requests landscape and immersive system-bar behavior.
- Keep-screen-on is enabled only during display.
- Back returns to the editor and restores orientation/system bars.
- Preserve editor content through configuration changes and process recreation where feasible.
