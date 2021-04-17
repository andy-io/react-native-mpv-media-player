package com.mpv.player.component;

import android.content.Context;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableMapKeySetIterator;
import com.facebook.react.bridge.ReadableType;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.RCTEventEmitter;
import com.mpv.player.component.MPVLib;

/* renamed from: com.mpv.player.component.a */
public class C3252a extends SurfaceView implements SurfaceHolder.Callback, MPVLib.C3249a, MPVLib.C3250b {

    /* renamed from: b */
    private boolean f9028b = false;

    /* renamed from: c */
    private boolean f9029c = false;

    /* renamed from: d */
    private String f9030d;

    /* renamed from: e */
    private boolean f9031e = false;

    /* renamed from: f */
    private ReadableMap f9032f;

    /* renamed from: g */
    private boolean f9033g = false;

    /* renamed from: com.mpv.player.component.a$a */
    static /* synthetic */ class C3253a {

        /* renamed from: a */
        static final /* synthetic */ int[] f9034a = new int[ReadableType.values().length];

        static {
            try {
                f9034a[ReadableType.String.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
        }
    }

    public C3252a(Context context) {
        super(context);
    }

    /* renamed from: e */
    private void m13404e() {
        ReadableMap readableMap;
        if (this.f9028b && (readableMap = this.f9032f) != null) {
            ReadableMapKeySetIterator keySetIterator = readableMap.keySetIterator();
            while (keySetIterator.hasNextKey()) {
                String nextKey = keySetIterator.nextKey();
                if (C3253a.f9034a[this.f9032f.getType(nextKey).ordinal()] != 1) {
                    Log.w(MPVPlayerViewManager.REACT_CLASS, "Option '" + nextKey + "' has an invalid value type. Only strings are supported");
                } else {
                    MPVLib.command(new String[]{"set", nextKey, this.f9032f.getString(nextKey)});
                }
            }
        }
    }

    /* renamed from: f */
    private void m13405f() {
        String str = this.f9030d;
        if (str != null && this.f9028b && this.f9029c) {
            if (!this.f9031e) {
                MPVLib.command(new String[]{"loadfile", str});
                Log.d(MPVPlayerViewManager.REACT_CLASS, "load stream:" + this.f9030d);
                this.f9031e = true;
            }
            MPVLib.setPropertyInt("vid", 1);
        }
    }

    /* renamed from: g */
    private void m13406g() {
        if (!this.f9028b) {
            MPVPlayerModule.initializeEngine(getContext(), this.f9032f);
            MPVLib.addObserver(this);
            MPVLib.addLogObserver(this);
            getHolder().addCallback(this);
            m13407h();
            this.f9028b = true;
            m13404e();
        }
    }

    /* renamed from: h */
    private void m13407h() {
        MPVLib.observeProperty("time-pos", 4);
        MPVLib.observeProperty("duration", 4);
        MPVLib.observeProperty("buffer", 4);
        MPVLib.observeProperty("pause", 3);
        MPVLib.observeProperty("mute", 3);
    }

    /* renamed from: a */
    public void mo11740a() {
        if (this.f9028b) {
            if (this.f9029c) {
                MPVLib.detachSurface();
                this.f9029c = false;
            }
            getHolder().removeCallback(this);
            MPVLib.removeObserver(this);
            MPVLib.removeLogObserver(this);
            this.f9028b = false;
            this.f9031e = false;
            if (this.f9033g) {
                MPVPlayerModule.destroyEngine();
                return;
            }
            MPVLib.command(new String[]{"stop"});
            MPVLib.setPropertyBoolean("pause", false);
            MPVLib.setPropertyBoolean("mute", false);
        }
    }

    /* renamed from: a */
    public void mo11725a(int i) {
        Log.d(MPVPlayerViewManager.REACT_CLASS, "event " + i);
        mo11744b(i);
    }

    /* renamed from: a */
    public void mo11741a(int i, int i2) {
        WritableMap createMap = Arguments.createMap();
        createMap.putInt("duration", i);
        createMap.putInt("currentTime", i2);
        ((RCTEventEmitter) ((ReactContext) getContext()).getJSModule(RCTEventEmitter.class)).receiveEvent(getId(), "onProgress", createMap);
    }

    /* renamed from: a */
    public void mo11726a(String str) {
    }

    /* renamed from: a */
    public void mo11730a(String str, int i, String str2) {
        if (str2.indexOf("Opening done") == 0 || str2.indexOf("Failed to open") == 0 || str2.indexOf("Enter buffering") == 0 || str2.indexOf("End buffering") == 0 || str2.indexOf("finished playback") == 0) {
            mo11745b(str, i, str2);
        }
    }

    /* renamed from: a */
    public void mo11727a(String str, long j) {
        if (str.equalsIgnoreCase("time-pos")) {
            int duration = getDuration();
            if (duration < 0) {
                duration = -1;
            }
            mo11741a(duration, (int) j);
        }
    }

    /* renamed from: a */
    public void mo11728a(String str, String str2) {
    }

    /* renamed from: a */
    public void mo11729a(String str, boolean z) {
    }

    /* renamed from: a */
    public void mo11742a(boolean z) {
        if (this.f9028b) {
            MPVLib.setPropertyBoolean("mute", Boolean.valueOf(z));
        }
    }

    /* renamed from: b */
    public void mo11743b() {
        if (this.f9028b) {
            MPVLib.setPropertyBoolean("pause", true);
        }
    }

    /* renamed from: b */
    public void mo11744b(int i) {
        WritableMap createMap = Arguments.createMap();
        createMap.putInt("eventId", i);
        ((RCTEventEmitter) ((ReactContext) getContext()).getJSModule(RCTEventEmitter.class)).receiveEvent(getId(), "onEvent", createMap);
    }

    /* renamed from: b */
    public void mo11745b(String str, int i, String str2) {
        WritableMap createMap = Arguments.createMap();
        createMap.putString("prefix", str);
        createMap.putInt("level", i);
        createMap.putString("text", str2);
        ((RCTEventEmitter) ((ReactContext) getContext()).getJSModule(RCTEventEmitter.class)).receiveEvent(getId(), "onMessage", createMap);
    }

    /* renamed from: c */
    public void mo11746c() {
        if (this.f9028b) {
            MPVLib.setPropertyBoolean("pause", false);
        }
    }

    /* renamed from: c */
    public void mo11747c(int i) {
        if (this.f9028b) {
            MPVLib.setPropertyInt("time-pos", Integer.valueOf(i));
        }
    }

    /* renamed from: d */
    public void mo11748d() {
        mo11747c(0);
        mo11743b();
    }

    public int getDuration() {
        if (!this.f9028b) {
            return -1;
        }
        try {
            return MPVLib.getPropertyInt("duration").intValue();
        } catch (NullPointerException unused) {
            return -1;
        }
    }

    public int getPosition() {
        if (!this.f9028b) {
            return 0;
        }
        return MPVLib.getPropertyInt("time-pos").intValue();
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        Log.d(MPVPlayerViewManager.REACT_CLASS, "activity: onAttachedToWindow");
        super.onAttachedToWindow();
        m13406g();
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        Log.d(MPVPlayerViewManager.REACT_CLASS, "activity: onDetachedFromWindow");
        super.onDetachedFromWindow();
        mo11740a();
    }

    public void onWindowVisibilityChanged(int i) {
        Log.d(MPVPlayerViewManager.REACT_CLASS, "activity: onWindowVisibilityChanged " + i);
        super.onWindowVisibilityChanged(i);
    }

    public void setDestroyMPV(boolean z) {
        this.f9033g = z;
    }

    public void setOptions(ReadableMap readableMap) {
        this.f9032f = readableMap;
        if (this.f9028b) {
            m13404e();
        }
    }

    public void setUrl(String str) {
        if (this.f9031e && this.f9030d != str) {
            this.f9031e = false;
        }
        this.f9030d = str;
        m13405f();
    }

    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
        if (this.f9029c) {
            MPVLib.setPropertyString("android-surface-size", i2 + "x" + i3);
        }
    }

    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        Log.d(MPVPlayerViewManager.REACT_CLASS, "activity: surfaceCreated");
        MPVLib.attachSurface(getHolder().getSurface());
        this.f9029c = true;
        m13405f();
    }

    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        if (this.f9028b) {
            Log.d(MPVPlayerViewManager.REACT_CLASS, "activity: surfaceDestroyed");
            MPVLib.setPropertyString("vid", "no");
            MPVLib.detachSurface();
            this.f9029c = false;
        }
    }
}
