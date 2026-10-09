# Building

## Requirements

- Android SDK platform 36 and build-tools 36.0.0
- A Java compiler, `keytool`, OpenSSL, `zip`, and `shasum`
- Python 3 for host tests and installer use

Set `ANDROID_HOME` if the SDK is not under `$HOME/Library/Android/sdk`.

## Build a signed APK

```bash
bash build.sh
```

The first build creates a unique signing key at `$HOME/.android/pulse-circuit.jks` and a private password file beside it. Both files are excluded from Git. Back up these files securely: future APK upgrades must use the same signing key. Set `PULSE_CIRCUIT_KEYSTORE` and `PULSE_CIRCUIT_KEYPASS` to use another protected location. Never commit the key or password.

The output is `build/pulse-circuit.apk` with a matching SHA-256 file. `VERSION_NAME` and `VERSION_CODE` can be set when building a release candidate. The minimum Android API is 23.

## Install a local build

```bash
python3 install.py --serial TV_IP:5555 --apk build/pulse-circuit.apk
```

See [installation](INSTALLATION.md) and [release process](RELEASING.md) before distributing a build.
