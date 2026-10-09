#!/usr/bin/env bash
set -euo pipefail
ROOT="$(cd "$(dirname "$0")" && pwd)"
OUT="$ROOT/build/tests"
mkdir -p "$OUT"
javac -source 8 -target 8 -d "$OUT" \
  "$ROOT/src/com/jeremykenedy/pulsecircuit/PulseOptions.java" \
  "$ROOT/src/com/jeremykenedy/pulsecircuit/SettingsValues.java" \
  "$ROOT/tests/PulseCircuitOptionsTest.java"
java -ea -cp "$OUT" com.jeremykenedy.pulsecircuit.PulseCircuitOptionsTest
python3 -m unittest -v tests.test_installer
