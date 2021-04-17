package com.mpv.player.component;

import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.annotations.ReactProp;
import java.util.Map;

public class MPVPlayerViewManager extends SimpleViewManager<C3252a> {
    private static final String PROP_DESTROY_MPV = "destroyMPV";
    private static final String PROP_MUTE = "mute";
    private static final String PROP_OPTIONS = "options";
    private static final String PROP_PLAY = "play";
    private static final String PROP_SEEK = "seek";
    private static final String PROP_STOP = "stop";
    private static final String PROP_URL = "url";
    public static final String REACT_CLASS = "MPVPlayerView";

    static final String[] Events = {
            "onMessage",
            "onEvent",
            "onProgress",
    };


    /* access modifiers changed from: protected */
    public C3252a createViewInstance(ThemedReactContext k0Var) {
        return new C3252a(k0Var);
    }

    public Map<String, Object> getExportedCustomDirectEventTypeConstants() {
       MapBuilder.Builder<String, Object> builder = MapBuilder.builder();
        for (String event : Events) {
            builder.put(event, MapBuilder.of("registrationName", event));
        }
        return builder.build();
    }

    public String getName() {
        return REACT_CLASS;
    }

    @ReactProp(defaultBoolean = false, name = "mute")
    public void mute(C3252a aVar, boolean z) {
        aVar.mo11742a(z);
    }

    @ReactProp(defaultBoolean = true, name = "play")
    public void play(C3252a aVar, boolean z) {
        if (z) {
            aVar.mo11746c();
        } else {
            aVar.mo11743b();
        }
    }

    @ReactProp(name = "seek")
    public void seek(C3252a aVar, int i) {
        aVar.mo11747c(i);
    }

    @ReactProp(name = "destroyMPV")
    public void setDestroyMPV(C3252a aVar, boolean z) {
        aVar.setDestroyMPV(z);
    }

    @ReactProp(name = "options")
    public void setOptions(C3252a aVar, ReadableMap readableMap) {
        aVar.setOptions(readableMap);
    }

    @ReactProp(name = "url")
    public void setUrl(C3252a aVar, String str) {
        aVar.setUrl(str);
    }

    @ReactProp(defaultBoolean = false, name = "stop")
    public void stop(C3252a aVar, boolean z) {
        if (z) {
            aVar.mo11748d();
        } else {
            aVar.mo11746c();
        }
    }
}
