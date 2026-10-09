# Verification

## Device matrix

| Device | Platform | Resolution | Result |
|---|---|---:|---|
| Google TV emulator `screensaver-batch-androidtv` | Android 14, API 34 | 1920x1080 | PreviewActivity animation, settings display, remote palette selection, persisted provider values, manifest and package metadata verified. Captured after a 45-second settling period. Final scene pixel comparison after 3 seconds showed 130,275 changed pixels. Direct DreamService idle activation is not verified because this production-signed emulator image denies ADB root. |
| Physical Fire TV | Not tested | Unknown | We are looking for a Fire TV owner to test installation, selection, idle activation, remote settings, pause/resume, and removal, then report the model, Fire OS/API, resolution, and results in the issue tracker. |
| Physical Android TV | Not tested | Unknown | We are looking for an Android TV owner to test installation, selection, idle activation, remote settings, pause/resume, and removal, then report the model, OS/API, resolution, and results in the issue tracker. |
| Physical Google TV | Not tested | Unknown | We are looking for a Google TV owner to run the same checks and report the model, OS/API, resolution, and results in the issue tracker. |

The app targets Android API 23 and later. Emulator PreviewActivity output does not establish DreamService idle activation, vendor-specific behavior, 4K output, frame rate, or thermal performance. No physical TV has been used for testing.

## Screenshot record

`pulse-circuit-android-tv-api34-1920x1080.png` and `pulse-circuit-settings-api34-1920x1080.png` were captured with `adb -s emulator-5596 exec-out screencap -p`. The app was launched through `com.jeremykenedy.pulsecircuit/.PreviewActivity` for the scene image and `SettingsActivity` for the settings image. The scene used midnight, many shapes, mixed geometry, steady motion, cyan and blue, balanced pulses, and bright intensity. The source captures are 1920x1080 PNG files. The app preview image is a resized 384x216 JPEG derived from the scene capture.
