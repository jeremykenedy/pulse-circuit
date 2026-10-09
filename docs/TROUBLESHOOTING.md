# Troubleshooting

## Pulse Circuit is not listed

Confirm the package is installed:

```bash
adb -s TV_IP:5555 shell pm list packages | grep pulsecircuit
```

Some TV vendors hide Android DreamService selection or use a separate ambient-mode menu. Reopen the device's screensaver settings after installation and consult the [verification matrix](VERIFICATION.md) for devices actually tested.

## Preview is blank

Launch Pulse Circuit from the TV app list and choose **Preview animation**. If this is also blank, capture `adb logcat` while reopening the preview and include the relevant renderer or lifecycle error in an issue report.

## Installer reports a checksum error

Do not install that download. Confirm that the release provides both `pulse-circuit.apk` and `pulse-circuit.apk.sha256` and that the checksum names the APK. Report the release tag and error in an issue.

## ADB cannot find the TV

Confirm ADB debugging is enabled, approve the host key on the TV, and run `adb devices`. If several devices are connected, pass the exact TV serial with `--serial`.
