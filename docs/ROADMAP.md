# Roadmap

This is a small implementation checklist, not a promise of completion. Mark an item done only after code and verification exist.

## Stage 0 — Repository bootstrap

- [x] Create Android/Compose project skeleton and Material 3 dark theme.
- [x] Add repository-level agent instructions.
- [x] Add product, architecture, development, and roadmap docs.
- [x] Add GitHub Actions for unit tests, lint, emulator UI tests, and debug APK artifact.
- [x] Add pure history policy helper with unit tests.
- [ ] Add the official Gradle Wrapper and verify it in CI.
- [ ] Run the first GitHub Actions build and fix any integration issues.

## Stage 1 — Home/editor

- [x] Implement basic multiline editor in a landscape-shaped preview.
- [x] Focus the editor and request the keyboard on launch/return.
- [x] Add prominent SHOW button and reject blank text.
- [ ] Confirm history list is hidden only while the keyboard is visible.
- [ ] Make the SHOW button reliably reachable on short screens/large keyboards.

## Stage 2 — Display and layout parity

- [x] Add landscape display screen, immersive system bars, and keep-screen-on behavior.
- [ ] Restore bars, portrait orientation, screen timeout, focus, and keyboard on every exit path.
- [ ] Replace/review iterative text shrinking with a robust fitting algorithm.
- [ ] Verify preview/display line breaks and font proportions on several device aspect ratios and font scales.
- [ ] Handle text that still overflows at the minimum font size without truncation.

## Stage 3 — Persistent history

- [ ] Add Room entity, DAO, database, and repository.
- [ ] Add a small ViewModel and observable history state.
- [ ] Save only on SHOW; duplicates move to newest position.
- [ ] Enforce exact-text matching and a 10-entry cap transactionally.
- [ ] Add tap-to-load and swipe-to-delete interactions.
- [ ] Hide history while IME is visible and restore it afterward.

## Stage 4 — Verification and release readiness

- [ ] Unit tests for history, font-fit rules, and validation.
- [ ] UI tests for launch focus, edit, SHOW, Back, orientation, and history.
- [ ] Verify on Android 8/API 26 and a current Android release.
- [ ] Check small screen, large font scale, long text, keyboard overlap, and system gesture behavior.
- [ ] Add a reproducible release APK workflow and secure signing setup.
- [ ] Review permissions, manifest, dependency footprint, documentation accuracy, and privacy.
