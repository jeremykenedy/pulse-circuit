# Testing

Run the local checks from the repository root:

```bash
bash test.sh
bash scripts/test-python-coverage.sh
bash scripts/test-coverage.sh
bash scripts/check-style.sh
python3 scripts/check-docs.py
python3 scripts/check-privacy.py
bash build.sh
```

The Java host tests verify every density, palette, arena, motion, and pulse mapping, deterministic random bounds, fallbacks, and settings validation. Installer tests cover confirmation, ADB selection, release URL and redirect checks, checksum handling, failure cleanup, install, and uninstall paths. The Python and Java coverage scripts enforce 100% line and branch coverage for those pure, project-owned logic units.

The Canvas renderer and Android framework callbacks are not included in host-side coverage. Run the app on an Android TV or Google TV emulator to check visible animation, settings, focus and remote input, pause/resume, package metadata, and screenshot framing. Use a physical device only with the owner's permission. See [device verification](VERIFICATION.md).
