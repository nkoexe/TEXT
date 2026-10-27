# Roadmap and validation status

Last reviewed: 2026-10-27.

This checklist distinguishes implementation from device validation. A checked implementation item means the code or test source exists; it does not claim that runtime behavior has been verified on a physical device.

## Implemented

### Repository and CI

- [x] Native Kotlin/Compose project for Android API 26+.
- [x] Centralized plugin and dependency versions in `gradle/libs.versions.toml`.
- [x] Default CI builds the debug APK, uploads it as `TEXT-debug-apk`, runs JVM unit tests, and runs Android lint.
- [x] CI intentionally avoids emulator startup. Artifacts are retained for 14 days.
- [ ] Add the official Gradle Wrapper and verify wrapper-based CI.
- [ ] Add a release APK/signing workflow after signing and key-recovery decisions are made.

### History

- [x] Persist up to ten unique messages in an app-private SQLite database.
- [x] Keep exact-match duplicate handling, promotion, ordering, blank rejection, and the history cap in the pure `HistoryPolicy`.
- [x] JVM unit tests cover exact text matching, whitespace/newlines, promotion, timestamps, eviction, duplicate cleanup, cap, and invariant-style generated inputs.
- [x] UI supports tap-to-load and horizontal swipe-to-delete.
- [x] History stays visible while typing; the list scrolls independently and absorbs available vertical space.
- [x] Swipe dismissal uses a 36% positional threshold and requests a gesture-threshold haptic once per threshold-crossing attempt.
- [ ] Verify actual haptic strength/availability and delete-gesture feel on a physical device.
- [ ] Verify history persistence and interactions on Android 8 and a current Android release.

### Editor, appearance, and display

- [x] Blank multiline editor and landscape-shaped preview; no placeholder text or character counter.
- [x] Preview width and landscape aspect ratio are independent of keyboard visibility.
- [x] Outfit is applied to all Compose Material typography roles and used by both editor and fullscreen text.
- [x] Dynamic Material You color on Android 12+ and system-aware light/dark fallback palettes on older versions.
- [x] Icon-only Material formatting placeholders for text color, background fill, and font; these controls are intentionally inert.
- [x] Fullscreen black/white message display, landscape orientation request, immersive system bars, and keep-screen-on behavior.
- [x] Pure font-fit policy tests cover boundary cases and invalid input.
- [ ] Verify keyboard opening/closing, fixed canvas geometry, and SHOW reachability with different keyboard heights, screen sizes, and font scales.
- [ ] Verify line wrapping and preview/display parity at multiple display densities/aspect ratios.
- [ ] Verify Back, orientation restoration, keyboard reopening, and system-bar lifecycle behavior on a device.

## Test status and limits

- Standard CI builds and uploads the debug APK, runs JVM unit tests, and runs Android lint. Check GitHub Actions for the result on the current commit.
- Instrumentation test source remains under `app/src/androidTest` for home-screen smoke/validation and SQLite repository behavior. It is not run by the default workflow and there is no active emulator workflow.
- No device/emulator validation is implied by a passing standard CI run.

## Privacy and distribution

- [x] Manifest declares no runtime permissions; app runtime does not perform network requests or use external storage.
- [x] Outfit's SIL Open Font License is included at `licenses/Outfit-OFL.txt`.
- [ ] Decide whether to select a license for the app source.
- [ ] Decide release signing and APK distribution process.
