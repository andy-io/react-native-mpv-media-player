package com.mpv.player.component;

import android.content.Context;
import android.media.AudioTrack;
import android.os.Build;
import android.util.Log;
import android.view.WindowManager;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableMapKeySetIterator;
import com.facebook.react.bridge.ReadableType;

public class MPVPlayerModule extends ReactContextBaseJavaModule {
    private static final String TAG = "MPVPlayerModule";
    private static boolean mIsInitialized = false;
    private final ReactApplicationContext reactContext;

    /* renamed from: com.mpv.player.component.MPVPlayerModule$a */
    static /* synthetic */ class C3251a {

        /* renamed from: a */
        static final /* synthetic */ int[] f9027a = new int[ReadableType.values().length];

        static {
            try {
                f9027a[ReadableType.String.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
        }
    }

    public MPVPlayerModule(ReactApplicationContext reactApplicationContext) {
        super(reactApplicationContext);
        this.reactContext = reactApplicationContext;
    }

    public static boolean destroyEngine() {
        if (!mIsInitialized) {
            return false;
        }
        MPVLib.destroy();
        mIsInitialized = false;
        return true;
    }

    public static boolean initializeEngine(Context context, ReadableMap readableMap) {
        if (mIsInitialized) {
            return false;
        }
        MPVLib.create(context);
        MPVLib.setOptionString("config", "false");
        if (Build.VERSION.SDK_INT >= 23) {
            Log.v(TAG, "Display ${disp.displayId} reports FPS of $refreshRate");
            MPVLib.setOptionString("override-display-fps", String.valueOf(((WindowManager) context.getSystemService("window")).getDefaultDisplay().getMode().getRefreshRate()));
        } else {
            Log.v(TAG, "Android version too old, disabling refresh rate functionality (${Build.VERSION.SDK_INT} < ${Build.VERSION_CODES.M})");
        }
        MPVLib.setOptionString("audio-samplerate", String.valueOf(AudioTrack.getNativeOutputSampleRate(3)));
        if (readableMap != null) {
            ReadableMapKeySetIterator keySetIterator = readableMap.keySetIterator();
            while (keySetIterator.hasNextKey()) {
                String nextKey = keySetIterator.nextKey();
                if (C3251a.f9027a[readableMap.getType(nextKey).ordinal()] != 1) {
                    Log.w(TAG, "Option '" + nextKey + "' has an invalid value type. Only strings are supported");
                } else {
                    MPVLib.setOptionString(nextKey, readableMap.getString(nextKey));
                }
            }
        }
        MPVLib.init();
        mIsInitialized = true;
        return true;
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public boolean destroy() {
        return destroyEngine();
    }

    public String getName() {
        return TAG;
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public boolean initialize(ReadableMap readableMap) {
        return initializeEngine(this.reactContext, readableMap);
    }
}
