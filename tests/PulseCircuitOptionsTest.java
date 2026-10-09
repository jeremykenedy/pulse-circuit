package com.jeremykenedy.pulsecircuit;

import java.util.Random;

public final class PulseCircuitOptionsTest {
    public static void main(String[] args) {
        check(PulseOptions.countFor("few") == 8, "few objects");
        check(PulseOptions.countFor("handful") == 16, "handful objects");
        check(PulseOptions.countFor("many") == 24, "many objects");
        check(PulseOptions.countFor("ton") == 42, "ton objects");
        check(PulseOptions.countFor("schools") == 60, "schools objects");
        check(PulseOptions.countFor("unknown") == 16, "density fallback");
        check(PulseOptions.speedFor("slow") == 0.45f, "slow speed");
        check(PulseOptions.speedFor("steady") == 0.85f, "steady speed");
        check(PulseOptions.speedFor("fast") == 1.55f, "fast speed");
        check(PulseOptions.speedFor("unknown") == 0.85f, "speed fallback");
        check(PulseOptions.paletteFor("cyan")[0] == 0xff55e8ff, "cyan palette");
        check(PulseOptions.paletteFor("magenta")[0] == 0xffff5bd6, "magenta palette");
        check(PulseOptions.paletteFor("lime")[0] == 0xffb7ff63, "lime palette");
        check(PulseOptions.paletteFor("amber")[0] == 0xffffbb58, "amber palette");
        check(PulseOptions.paletteFor("unknown")[0] == 0xff55e8ff, "palette fallback");
        check(PulseOptions.arenaFor("midnight")[0] == 0xff07131e, "midnight arena");
        check(PulseOptions.arenaFor("violet")[0] == 0xff100c27, "violet arena");
        check(PulseOptions.arenaFor("ember")[0] == 0xff21100f, "ember arena");
        check(PulseOptions.arenaFor("unknown")[0] == 0xff07131e, "arena fallback");
        check(PulseOptions.intensityFor("dim") == 0.55f, "dim brightness");
        check(PulseOptions.intensityFor("balanced") == 0.78f, "balanced brightness");
        check(PulseOptions.intensityFor("bright") == 1f, "bright brightness");
        check(PulseOptions.intensityFor("unknown") == 0.78f, "brightness fallback");
        check(PulseOptions.pulseRateFor("calm") == 0.60f, "calm rhythm");
        check(PulseOptions.pulseRateFor("balanced") == 1f, "balanced rhythm");
        check(PulseOptions.pulseRateFor("rapid") == 1.65f, "rapid rhythm");
        check(PulseOptions.pulseRateFor("unknown") == 1f, "rhythm fallback");
        check(PulseOptions.shapeModeFor("mixed") == 0, "mixed geometry");
        check(PulseOptions.shapeModeFor("triangles") == 3, "triangle geometry");
        check(PulseOptions.shapeModeFor("diamonds") == 4, "diamond geometry");
        check(PulseOptions.shapeModeFor("hexagons") == 6, "hexagon geometry");
        check(PulseOptions.shapeModeFor("unknown") == 0, "geometry fallback");
        PulseOptions fixed = PulseOptions.resolve("few", "fast", "lime", "violet", "rapid", "dim",
                "triangles", false, new Random(1));
        check(fixed.count == 8 && fixed.speed == 1.55f && fixed.primary == 0xffb7ff63
                && fixed.backgroundTop == 0xff100c27 && fixed.pulseRate == 1.65f && fixed.intensity == 0.55f
                && fixed.shapeMode == 3,
                "explicit choices");
        PulseOptions random = PulseOptions.resolve("random", "random", "random", "random", "random", "random",
                "random", false, new Random(7));
        check(random.count >= 8 && random.count <= 60, "random density bounds");
        check(random.speed >= 0.45f && random.speed <= 1.55f, "random speed bounds");
        check(random.primary == 0xff55e8ff || random.primary == 0xffff5bd6 || random.primary == 0xffb7ff63
                || random.primary == 0xffffbb58, "random palette values");
        check(random.backgroundTop == 0xff07131e || random.backgroundTop == 0xff100c27
                || random.backgroundTop == 0xff21100f, "random arenas");
        check(random.intensity == 0.55f || random.intensity == 0.78f || random.intensity == 1f,
                "random brightness values");
        check(random.shapeMode == 0 || random.shapeMode == 3 || random.shapeMode == 4 || random.shapeMode == 6,
                "random geometry values");
        PulseOptions all = PulseOptions.resolve("few", "slow", "cyan", "midnight", "calm", "dim", "mixed",
                true, new Random(5));
        check(all.count >= 8 && all.count <= 60 && all.speed >= 0.45f && all.speed <= 1.55f,
                "randomize all bounds");
        check(all.intensity == 0.55f || all.intensity == 0.78f || all.intensity == 1f,
                "randomize all brightness");
        check(all.shapeMode == 0 || all.shapeMode == 3 || all.shapeMode == 4 || all.shapeMode == 6,
                "randomize all geometry");
        PulseOptions fallback = PulseOptions.resolve("unknown", "unknown", "unknown", "unknown", "unknown",
                "unknown", "unknown", false, new Random(9));
        check(fallback.count == 8 && fallback.speed == 0.45f && fallback.primary == 0xff55e8ff
                && fallback.backgroundTop == 0xff07131e && fallback.pulseRate == 0.60f, "invalid choice defaults");
        check(SettingsValues.isSupported("density", "few"), "settings accept few density");
        check(SettingsValues.isSupported("density", "handful"), "settings accept handful density");
        check(SettingsValues.isSupported("density", "many"), "settings accept many density");
        check(SettingsValues.isSupported("density", "ton"), "settings accept ton density");
        check(SettingsValues.isSupported("density", "schools"), "settings accept schools");
        check(SettingsValues.isSupported("geometry", "mixed"), "settings accept mixed geometry");
        check(SettingsValues.isSupported("geometry", "triangles"), "settings accept triangles");
        check(SettingsValues.isSupported("geometry", "diamonds"), "settings accept diamonds");
        check(SettingsValues.isSupported("geometry", "hexagons"), "settings accept hexagons");
        check(SettingsValues.isSupported("motion", "slow"), "settings accept slow motion");
        check(SettingsValues.isSupported("motion", "steady"), "settings accept steady");
        check(SettingsValues.isSupported("motion", "fast"), "settings accept fast motion");
        check(SettingsValues.isSupported("palette", "cyan"), "settings accept cyan palette");
        check(SettingsValues.isSupported("palette", "magenta"), "settings accept magenta palette");
        check(SettingsValues.isSupported("palette", "lime"), "settings accept lime palette");
        check(SettingsValues.isSupported("palette", "amber"), "settings accept palette");
        check(SettingsValues.isSupported("arena", "midnight"), "settings accept midnight arena");
        check(SettingsValues.isSupported("arena", "violet"), "settings accept violet arena");
        check(SettingsValues.isSupported("arena", "ember"), "settings accept arena");
        check(SettingsValues.isSupported("pulse", "calm"), "settings accept calm rhythm");
        check(SettingsValues.isSupported("pulse", "balanced"), "settings accept balanced rhythm");
        check(SettingsValues.isSupported("pulse", "rapid"), "settings accept pulse");
        check(SettingsValues.isSupported("brightness", "dim"), "settings accept dim light");
        check(SettingsValues.isSupported("brightness", "balanced"), "settings accept balanced light");
        check(SettingsValues.isSupported("brightness", "bright"), "settings accept bright light");
        check(SettingsValues.isSupported("randomize_all", "true"), "settings accept randomize all");
        check(SettingsValues.isSupported("randomize_all", "false"), "settings accept disabled randomize all");
        check(SettingsValues.isSupported("density", "random"), "settings accept random choice");
        check(!SettingsValues.isSupported("density", "unbounded"), "settings reject unsupported choice");
        check(!SettingsValues.isSupported("motion", "instant"), "settings reject unsupported speed");
        check(!SettingsValues.isSupported("palette", "red"), "settings reject unsupported palette");
        check(!SettingsValues.isSupported("arena", "forest"), "settings reject unsupported arena");
        check(!SettingsValues.isSupported("pulse", "none"), "settings reject unsupported rhythm");
        check(!SettingsValues.isSupported("brightness", "blinding"), "settings reject unsupported intensity");
        check(!SettingsValues.isSupported("geometry", "octagons"), "settings reject unsupported geometry");
        check(!SettingsValues.isSupported("randomize_all", "yes"), "settings reject invalid toggle");
        check(!SettingsValues.isSupported("unknown", "value"), "settings reject unknown key");
        check(!SettingsValues.isSupported(null, "value"), "settings reject null key");
        check(!SettingsValues.isSupported("palette", null), "settings reject null value");
    }

    private static void check(boolean result, String message) {
        if (!result) throw new AssertionError(message);
    }
}
