package com.jeremykenedy.pulsecircuit;

import android.service.dreams.DreamService;

public final class PulseDreamService extends DreamService {
    private PulseSceneView scene;

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        setInteractive(false);
        setFullscreen(true);
        setScreenBright(false);
        scene = new PulseSceneView(this);
        setContentView(scene);
    }

    @Override
    public void onDreamingStarted() {
        super.onDreamingStarted();
        if (scene != null) scene.start();
    }

    @Override
    public void onDreamingStopped() {
        if (scene != null) scene.stop();
        super.onDreamingStopped();
    }

    @Override
    public void onDetachedFromWindow() {
        if (scene != null) scene.stop();
        scene = null;
        super.onDetachedFromWindow();
    }
}
