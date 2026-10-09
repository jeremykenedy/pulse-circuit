# Releasing

1. Update `VERSION_NAME`, `VERSION_CODE`, changelog, release notes, and documentation together.
2. Run all local checks in [testing](TESTING.md), then build the final signed APK with the preserved release key.
3. Verify the APK signature, package ID, version, DreamService declaration, requested permissions, and SHA-256. Confirm that the manifest contains no Internet permission.
4. Create a SemVer tag and GitHub release with `pulse-circuit.apk`, `pulse-circuit.apk.sha256`, and the matching release notes.
5. Download the published assets and verify the checksum again. Update integration catalogs only from the published release metadata.

Never replace an asset under an existing tag. Publish any correction under a new patch release. Keep the signing keystore and password file private and backed up.
