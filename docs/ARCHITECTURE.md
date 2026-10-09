# Architecture

Pulse Circuit is a dependency-free Android app. `PulseDreamService` owns the Android DreamService lifecycle and hosts `PulseSceneView`. The view draws with Android Canvas using a fixed set of normalized shape parameters and reusable drawing objects.

The scene combines animated orbit paths, outlined polygons, short light trails, a sparse particle field, and expanding rings. Geometry is derived from elapsed monotonic time rather than accumulated per-frame state, so pausing and resuming does not leave the renderer with an unbounded animation loop. The view schedules a frame about every 33 milliseconds while the dream is active and removes its callback when stopped or detached. It does not request a 3840 by 2160 surface; Android provides the display surface and the renderer adapts to its dimensions.

`PulseOptions` resolves settings into bounded counts, speeds, colors, and pulse rates. The settings activity stores choices in app-local preferences. `SettingsProvider` exposes a versioned read schema and validates every write against `SettingsValues`. No network client or content service is used.

The standalone `install.py` script is separate from app runtime. It downloads only the latest GitHub release assets from this repository, checks the APK's published SHA-256, then uses ADB to install or remove the package after confirmation.
