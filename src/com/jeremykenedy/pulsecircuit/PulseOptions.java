package com.jeremykenedy.pulsecircuit;

import java.util.Random;

public final class PulseOptions {
    public final int count;
    public final float speed;
    public final int primary;
    public final int secondary;
    public final int backgroundTop;
    public final int backgroundBottom;
    public final float intensity;
    public final float pulseRate;
    public final int shapeMode;

    private PulseOptions(int count, float speed, int primary, int secondary, int backgroundTop,
            int backgroundBottom, float intensity, float pulseRate, int shapeMode) {
        this.count = count;
        this.speed = speed;
        this.primary = primary;
        this.secondary = secondary;
        this.backgroundTop = backgroundTop;
        this.backgroundBottom = backgroundBottom;
        this.intensity = intensity;
        this.pulseRate = pulseRate;
        this.shapeMode = shapeMode;
    }

    public static PulseOptions resolve(String density, String motion, String palette, String arena,
            String pulse, String brightness, String geometry, boolean randomizeAll, Random random) {
        String selectedDensity = choose(density, randomizeAll, random, "few", "handful", "many", "ton", "schools");
        String selectedMotion = choose(motion, randomizeAll, random, "slow", "steady", "fast");
        String selectedPalette = choose(palette, randomizeAll, random, "cyan", "magenta", "lime", "amber");
        String selectedArena = choose(arena, randomizeAll, random, "midnight", "violet", "ember");
        String selectedPulse = choose(pulse, randomizeAll, random, "calm", "balanced", "rapid");
        String selectedBrightness = choose(brightness, randomizeAll, random, "dim", "balanced", "bright");
        String selectedGeometry = choose(geometry, randomizeAll, random, "mixed", "triangles", "diamonds", "hexagons");
        int[] colors = paletteFor(selectedPalette);
        int[] background = arenaFor(selectedArena);
        return new PulseOptions(countFor(selectedDensity), speedFor(selectedMotion), colors[0], colors[1],
                background[0], background[1], intensityFor(selectedBrightness), pulseRateFor(selectedPulse),
                shapeModeFor(selectedGeometry));
    }

    private static String choose(String selected, boolean randomizeAll, Random random, String... values) {
        if (randomizeAll || "random".equals(selected)) return values[random.nextInt(values.length)];
        for (String value : values) if (value.equals(selected)) return selected;
        return values[0];
    }

    static int countFor(String density) {
        if ("few".equals(density)) return 8;
        if ("many".equals(density)) return 24;
        if ("ton".equals(density)) return 42;
        if ("schools".equals(density)) return 60;
        return 16;
    }

    static float speedFor(String motion) {
        if ("slow".equals(motion)) return 0.45f;
        if ("fast".equals(motion)) return 1.55f;
        return 0.85f;
    }

    static int[] paletteFor(String palette) {
        if ("magenta".equals(palette)) return new int[] {0xffff5bd6, 0xff8b6bff};
        if ("lime".equals(palette)) return new int[] {0xffb7ff63, 0xff30e8c5};
        if ("amber".equals(palette)) return new int[] {0xffffbb58, 0xffff5c79};
        return new int[] {0xff55e8ff, 0xff5988ff};
    }

    static int[] arenaFor(String arena) {
        if ("violet".equals(arena)) return new int[] {0xff100c27, 0xff080a1b};
        if ("ember".equals(arena)) return new int[] {0xff21100f, 0xff100a12};
        return new int[] {0xff07131e, 0xff050914};
    }

    static float intensityFor(String brightness) {
        if ("dim".equals(brightness)) return 0.55f;
        if ("bright".equals(brightness)) return 1f;
        return 0.78f;
    }

    static float pulseRateFor(String pulse) {
        if ("calm".equals(pulse)) return 0.60f;
        if ("rapid".equals(pulse)) return 1.65f;
        return 1f;
    }

    static int shapeModeFor(String geometry) {
        if ("triangles".equals(geometry)) return 3;
        if ("diamonds".equals(geometry)) return 4;
        if ("hexagons".equals(geometry)) return 6;
        return 0;
    }
}
