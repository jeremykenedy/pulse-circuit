# Configuration

Open Pulse Circuit from the TV launcher and use the remote to change settings. Choices are persisted locally and take effect when the next screensaver session begins. Each choice has a `Random` value; `Randomize all` selects every setting again each time the dream starts.

| Key | Type | Choices | Default |
|---|---|---|---|
| `arena` | choice | `midnight`, `violet`, `ember`, `random` | `midnight` |
| `density` | choice | `few`, `handful`, `many`, `ton`, `schools`, `random` | `many` |
| `geometry` | choice | `mixed`, `triangles`, `diamonds`, `hexagons`, `random` | `mixed` |
| `motion` | choice | `slow`, `steady`, `fast`, `random` | `steady` |
| `palette` | choice | `cyan`, `magenta`, `lime`, `amber`, `random` | `cyan` |
| `pulse` | choice | `calm`, `balanced`, `rapid`, `random` | `balanced` |
| `brightness` | choice | `dim`, `balanced`, `bright`, `random` | `bright` |
| `randomize_all` | boolean | `true`, `false` | `false` |

The app-owned content provider authority is `com.jeremykenedy.pulsecircuit.settings`:

- `content://com.jeremykenedy.pulsecircuit.settings/schema` returns `key`, `title`, `type`, `default`, `choices`, and `randomAllowed` columns.
- `content://com.jeremykenedy.pulsecircuit.settings/settings` returns saved `key` and `value` pairs.
- Updates use `ContentValues` fields `key` and `value` at the `settings` URI. Unsupported keys and values are rejected.

The provider currently has no read or write permission restriction. A host app must deliberately implement this contract to expose these options; declaring the provider alone does not add host-app controls. Do not treat this provider contract as permission to expose unrelated device state.
