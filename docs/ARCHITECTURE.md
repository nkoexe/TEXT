# Architecture

Status: intended architecture; only the activity, Compose screens/theme, and a testable history policy helper exist in the scaffold today.

## Principles

- Keep a single Activity and a small number of Compose screens.
- Keep UI state coordination separate from persistence.
- Avoid adding a formal domain/use-case layer until a concrete need appears.
- Keep all data local. No network stack or Internet permission.
- Favor deterministic pure helpers for business rules.

## Planned responsibilities

- `MainActivity`: activity entry point and host window/orientation lifecycle.
- `ui/HomeScreen.kt`: focused multiline editor, scaled preview surface, SHOW action, history UI once implemented.
- `ui/DisplayScreen.kt`: immersive landscape rendering and text fitting.
- `ui/theme/Theme.kt`: monochrome Material 3 dark color scheme.
- `data/HistoryPolicy.kt`: pure history rules, independent of the storage engine.
- `viewmodel/HomeViewModel.kt` (to add): current editor state, observable history, selection, display/save action, and deletion.
- `data/`: Room entity, DAO, database, and repository (to add for persistent history).

## History storage contract

Use Room for durable history. Record a display operation transactionally:

1. Reject blank/whitespace-only text.
2. Find an exact text match.
3. If found, update its last-displayed timestamp; otherwise insert it.
4. Sort newest first and delete entries beyond 10.
5. Emit the updated list to the UI.

The operation must not trim text, and concurrent/rapid display actions must not create duplicate records.

## Preview/display sizing

A matching aspect ratio alone is insufficient to guarantee identical line breaks. The implementation must share fitting parameters and layout rules. Test identical text on the preview and fullscreen display across different densities, font scales, and aspect ratios. Document any unavoidable platform differences rather than claiming perfect parity without proof.

## Android window lifecycle

- Home remains portrait.
- Display requests landscape and immersive system-bar behavior.
- Keep-screen-on is enabled only during display.
- Back/navigation restores orientation, bars, and timeout behavior.
- Preserve editor text through configuration changes and process recreation where feasible.
