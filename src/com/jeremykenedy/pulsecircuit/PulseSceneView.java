package com.jeremykenedy.pulsecircuit;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.view.View;

import java.util.Random;

final class PulseSceneView extends View {
    private static final int PARTICLE_COUNT = 60;
    private static final float TAU = 6.2831853f;
    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Path polygon = new Path();
    private final Random random = new Random(241009L);
    private final float[] phase = new float[PARTICLE_COUNT];
    private final float[] orbit = new float[PARTICLE_COUNT];
    private final float[] size = new float[PARTICLE_COUNT];
    private final int[] sides = new int[PARTICLE_COUNT];
    private PulseOptions options;
    private LinearGradient background;
    private long startedAt;
    private boolean running;

    PulseSceneView(Context context) {
        super(context);
        setLayerType(View.LAYER_TYPE_HARDWARE, null);
        for (int i = 0; i < PARTICLE_COUNT; i++) {
            phase[i] = random.nextFloat() * TAU;
            orbit[i] = 0.12f + random.nextFloat() * 0.82f;
            size[i] = 0.35f + random.nextFloat() * 0.9f;
            sides[i] = 3 + random.nextInt(4);
        }
        loadOptions();
    }

    void start() {
        if (!running) {
            running = true;
            startedAt = SystemClock.uptimeMillis();
            postInvalidateOnAnimation();
        }
    }

    void stop() {
        running = false;
        removeCallbacks(invalidator);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (getWidth() <= 0 || getHeight() <= 0) return;
        float time = (SystemClock.uptimeMillis() - startedAt) / 1000f;
        drawArena(canvas, time);
        drawShapes(canvas, time);
        drawParticles(canvas, time);
        if (running) postDelayed(invalidator, 33L);
    }

    @Override
    protected void onSizeChanged(int width, int height, int oldWidth, int oldHeight) {
        super.onSizeChanged(width, height, oldWidth, oldHeight);
        background = new LinearGradient(0, 0, 0, Math.max(height, 1), options.backgroundTop,
                options.backgroundBottom, Shader.TileMode.CLAMP);
    }

    private final Runnable invalidator = new Runnable() {
        @Override public void run() { if (running) invalidate(); }
    };

    private void loadOptions() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        options = PulseOptions.resolve(preferences.getString("density", "many"),
                preferences.getString("motion", "steady"), preferences.getString("palette", "cyan"),
                preferences.getString("arena", "midnight"), preferences.getString("pulse", "balanced"),
                preferences.getString("brightness", "bright"),
                preferences.getString("geometry", "mixed"),
                preferences.getBoolean("randomize_all", false), new Random(System.currentTimeMillis()));
    }

    private void drawArena(Canvas canvas, float time) {
        paint.setAlpha(255);
        paint.setStyle(Paint.Style.FILL);
        paint.setShader(background);
        canvas.drawRect(0, 0, getWidth(), getHeight(), paint);
        paint.setShader(null);
        float cx = getWidth() * 0.5f;
        float cy = getHeight() * 0.5f;
        float base = Math.min(getWidth(), getHeight());
        paint.setStyle(Paint.Style.STROKE);
        for (int ring = 0; ring < 6; ring++) {
            float phaseOffset = (time * options.speed * options.pulseRate * 0.13f + ring / 6f) % 1f;
            float radius = base * (0.08f + phaseOffset * 0.48f);
            int alpha = (int) ((1f - phaseOffset) * 76f * options.intensity);
            paint.setColor(withAlpha(ring % 2 == 0 ? options.primary : options.secondary, alpha));
            paint.setStrokeWidth(Math.max(1.5f, base * 0.0018f));
            canvas.drawCircle(cx, cy, radius, paint);
        }
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(withAlpha(options.primary, 30));
        canvas.drawCircle(cx, cy, base * (0.10f + 0.025f * (float) Math.sin(time * options.pulseRate)), paint);
        paint.setStyle(Paint.Style.STROKE);
    }

    private void drawShapes(Canvas canvas, float time) {
        float width = getWidth();
        float height = getHeight();
        float base = Math.min(width, height);
        float cx = width * 0.5f;
        float cy = height * 0.5f;
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        for (int i = 0; i < options.count; i++) {
            float angularSpeed = options.speed * (0.10f + (i % 7) * 0.012f);
            float angle = phase[i] + time * angularSpeed;
            float distance = orbit[i] * (0.80f + 0.08f * (float) Math.sin(time * 0.31f + phase[i]));
            float px = cx + (float) Math.cos(angle) * width * 0.43f * distance;
            float py = cy + (float) Math.sin(angle * 0.91f) * height * 0.40f * distance;
            float radius = base * 0.011f * size[i];
            int color = i % 3 == 0 ? options.secondary : options.primary;
            int alpha = (int) ((150 + 82 * (0.5f + 0.5f * Math.sin(time * options.pulseRate * 1.4f + phase[i])))
                    * options.intensity);
            paint.setStrokeWidth(radius * 0.8f);
            paint.setColor(withAlpha(color, alpha / 4));
            canvas.drawLine(px - (float) Math.cos(angle) * radius * 2.5f,
                    py - (float) Math.sin(angle) * radius * 2.5f,
                    px + (float) Math.cos(angle) * radius * 2.5f,
                    py + (float) Math.sin(angle) * radius * 2.5f, paint);
            paint.setStrokeWidth(Math.max(1.5f, radius * 0.28f));
            paint.setColor(withAlpha(color, alpha));
            drawPolygon(canvas, px, py, radius, options.shapeMode == 0 ? sides[i] : options.shapeMode,
                    angle + time * 0.22f);
            paint.setColor(withAlpha(color, alpha / 3));
            paint.setStrokeWidth(Math.max(1f, radius * 1.5f));
            canvas.drawCircle(px, py, radius * 1.25f, paint);
        }
        paint.setStyle(Paint.Style.FILL);
    }

    private void drawPolygon(Canvas canvas, float x, float y, float radius, int points, float rotation) {
        polygon.reset();
        for (int i = 0; i < points; i++) {
            float angle = rotation + TAU * i / points;
            float px = x + (float) Math.cos(angle) * radius;
            float py = y + (float) Math.sin(angle) * radius;
            if (i == 0) polygon.moveTo(px, py);
            else polygon.lineTo(px, py);
        }
        polygon.close();
        canvas.drawPath(polygon, paint);
    }

    private void drawParticles(Canvas canvas, float time) {
        float base = Math.min(getWidth(), getHeight());
        paint.setStyle(Paint.Style.FILL);
        for (int i = 0; i < PARTICLE_COUNT; i++) {
            float angle = phase[i] * 1.7f - time * options.speed * (0.04f + (i % 5) * 0.009f);
            float distance = (0.08f + ((i * 37) % 83) / 100f) * base;
            float px = getWidth() * 0.5f + (float) Math.cos(angle) * distance;
            float py = getHeight() * 0.5f + (float) Math.sin(angle * 1.13f) * distance * 0.56f;
            float pulse = 0.45f + 0.55f * (0.5f + 0.5f * (float) Math.sin(time * options.pulseRate + phase[i]));
            paint.setColor(withAlpha(i % 4 == 0 ? options.secondary : options.primary,
                    (int) (165 * pulse * options.intensity)));
            canvas.drawCircle(px, py, Math.max(1.8f, base * 0.0025f * pulse), paint);
        }
    }

    private static int withAlpha(int color, int alpha) {
        return (color & 0x00ffffff) | (Math.max(0, Math.min(255, alpha)) << 24);
    }
}
