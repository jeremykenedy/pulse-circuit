package com.jeremykenedy.pulsecircuit;

public final class SettingsValues {
    private SettingsValues() {}

    public static boolean isSupported(String key, String value) {
        if (key == null || value == null) return false;
        if ("randomize_all".equals(key)) return "true".equals(value) || "false".equals(value);
        if ("density".equals(key)) return oneOf(value, "few", "handful", "many", "ton", "schools", "random");
        if ("motion".equals(key)) return oneOf(value, "slow", "steady", "fast", "random");
        if ("palette".equals(key)) return oneOf(value, "cyan", "magenta", "lime", "amber", "random");
        if ("arena".equals(key)) return oneOf(value, "midnight", "violet", "ember", "random");
        if ("pulse".equals(key)) return oneOf(value, "calm", "balanced", "rapid", "random");
        if ("brightness".equals(key)) return oneOf(value, "dim", "balanced", "bright", "random");
        if ("geometry".equals(key)) return oneOf(value, "mixed", "triangles", "diamonds", "hexagons", "random");
        return false;
    }

    private static boolean oneOf(String value, String... choices) {
        for (String choice : choices) if (choice.equals(value)) return true;
        return false;
    }
}
