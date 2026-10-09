# Installation

## Install or update the latest release

Enable ADB debugging on the TV, connect it to the same network, and run:

```bash
adb connect TV_IP:5555
python3 install.py --serial TV_IP:5555
```

The installer shows a confirmation prompt, downloads `pulse-circuit.apk` and its `.sha256` file from the latest GitHub release, verifies the digest, and installs the APK. GitHub is contacted only after the installer is run. Installation does not change the selected system screensaver, sleep timers, or OS update settings.

## Install a local APK

```bash
bash build.sh
python3 install.py --serial TV_IP:5555 --apk build/pulse-circuit.apk
```

## Select the screensaver

Open Pulse Circuit from the TV launcher to preview its animation and edit settings. Then choose Pulse Circuit in the device's screensaver or ambient-mode settings. Menu names and availability vary by vendor and OS version. An emulator does not establish vendor-specific idle activation.

## Remove

```bash
python3 install.py --serial TV_IP:5555 --uninstall
```

Removal asks for confirmation and deletes Pulse Circuit and its local settings. For a non-interactive script, use `--uninstall --yes --force`. The installer does not alter unrelated apps or device preferences.

## ADB safety

Always pass `--serial` when more than one Android device is connected. The installer selects only authorized ADB devices, rejects unexpected release URLs and redirects, limits download sizes, and verifies the published checksum before installing. `--yes` confirms install/update only; removal needs its own explicit confirmation or the combined non-interactive flags above.
