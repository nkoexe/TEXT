# Roadmap

Implementation checklist. Only mark behavior complete after it is tested on a device/emulator.

## Repository and CI
- [x] Compose project scaffold and agent/product documentation.
- [x] Default CI builds and uploads the debug APK, runs JVM unit tests and Android lint, and compiles instrumentation tests.
- [x] Instrumented API 35 emulator tests run on pushes and pull requests and upload reports.
- [ ] Add official Gradle Wrapper and verify wrapper-based CI.
- [ ] Confirm all CI workflows are green on the current PR.

## History
- [x] Persist history locally in a private on-device SQLite database.
- [x] Keep duplicate handling, ordering, and ten-entry limit in one pure history policy.
- [x] Unit tests for blank input, exact matching, promotion, timestamp replacement, duplicate cleanup, and cap/eviction.
- [x] Deterministic invariant-style policy tests over messy legacy inputs and repeated identical displays.
- [x] Instrumentation tests for persistence across database reopen, exact-match deletion, blank input, cap, and clock-independent ordering.
- [x] Tap to load, swipe horizontally to delete, and hide history while the IME is visible.
- [ ] Verify history behavior on Android 8 and a current Android release.

## Editor and display
- [x] Multiline editor, live landscape-shaped preview, initial focus, SHOW validation.
- [x] Immersive landscape display and keep-screen-on while displayed.
- [x] Measure available width/height and binary-search font sizes with an 18sp minimum.
- [x] Warn in the editor when content still overflows at 18sp.
- [x] Unit-test font-fit boundary conditions and invalid input.
- [ ] Verify line wrapping, font scale/density behavior, and preview/display parity on multiple device sizes.
- [ ] Verify Back and lifecycle exit paths restore orientation/system bars and reopen the keyboard.
- [ ] Verify SHOW remains reachable on small screens and with large keyboards/font scales.

## Release readiness
- [ ] Expand UI tests beyond the home smoke and validation tests to edit/show/back/history flows.
- [ ] Add a release APK workflow and secure signing setup when signing details are decided.
- [ ] Review dependency footprint, manifest, and privacy behavior.
