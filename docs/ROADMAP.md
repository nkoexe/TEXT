# Roadmap

Implementation checklist. Only mark behavior complete after it is tested on a device/emulator.

## Repository and CI
- [x] Compose project scaffold and agent/product documentation.
- [x] CI unit tests, lint, and debug APK artifact workflow.
- [ ] Add official Gradle Wrapper and verify wrapper-based CI.
- [ ] Confirm first CI run is green; fix dependency/version issues.

## Editor and display
- [x] Multiline editor, live landscape-shaped preview, initial focus, SHOW validation.
- [x] Immersive landscape display and keep-screen-on while displayed.
- [ ] Verify editor is reachable on small screens and with large font scales/keyboards.
- [ ] Replace iterative shrinking with a robust bounded font-fit strategy and show an overflow warning when minimum size cannot fit.
- [ ] Verify line wrapping, density/font-scale behavior, and preview/display parity on multiple device sizes.
- [ ] Verify Back and all lifecycle exit paths restore orientation/system bars and reopen the keyboard.

## History
- [x] Persist history locally in a private on-device SQLite database.
- [x] Record only when SHOW is pressed; exact duplicates move to newest.
- [x] Cap history at 10; preserve text exactly; reject blank text.
- [x] Tap to load and swipe horizontally to delete.
- [ ] Hide history only while IME is visible and verify SHOW remains reachable.
- [ ] Add repository/integration tests for transaction, persistence, and deletion behavior.

## Release readiness
- [ ] Add UI tests for edit/show/back/history and verify on API 26 and current Android.
- [ ] Add a release APK workflow and secure signing setup when signing details are decided.
- [ ] Review dependency footprint, manifest, and privacy behavior.
