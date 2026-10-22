# Architecture

## Principles

- One Activity with a small number of Compose screens.
- Keep UI state coordination separate from persistence.
- Keep all data local. No network stack or Internet permission.
- Favor deterministic pure helpers for business rules.
- Avoid a formal domain/use-case layer until a concrete need appears.

## Responsibilities

- `MainActivity.kt`: entry point, editor/display state, orientation and system-bar transitions.
- `ui/HomeScreen.kt`: focused multiline editor, landscape-shaped preview, SHOW action, and history UI.
- `ui/DisplayScreen.kt`: immersive landscape rendering and text fitting.
- `ui/theme/Theme.kt`: monochrome Material 3 dark color scheme.
- `data/HistoryPolicy.kt`: pure history rules, independent of persistence.
- `data/HistoryRepository.kt`: private SQLite database and transactional history updates.
- `HomeViewModel.kt`: asynchronous repository access and observable history state.

## History storage contract

Record a display operation transactionally:

1. Reject blank/whitespace-only text.
2. Delete an exact text match before reinserting it as the newest item.
3. Sort newest first and delete entries beyond 10.
4. Emit the updated list to the UI.

Text is never trimmed or normalized. The database's unique text constraint and transaction prevent duplicate rows. The database is app-private and contains no user data beyond the text and timestamp.

## Preview/display sizing

A matching aspect ratio alone is insufficient to guarantee identical line breaks. The current code shares the text-fitting function but still needs device verification. Test identical text across densities, font scales, and aspect ratios. Document any unavoidable platform differences rather than claiming perfect parity without proof.

## Android window lifecycle

- Home remains portrait.
- Display requests landscape and immersive system-bar behavior.
- Keep-screen-on is enabled only during display.
- Back returns to the editor and restores system bars and normal timeout behavior.
- Preserve editor content through configuration changes and process recreation where feasible.
