package com.mpv.player.component;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.Surface;
import java.util.ArrayList;
import java.util.List;

public class MPVLib {
    private static final List<C3250b> log_observers = new ArrayList();
    private static final List<C3249a> observers = new ArrayList();

    /* renamed from: com.mpv.player.component.MPVLib$a */
    public interface C3249a {
        /* renamed from: a */
        void mo11725a(int i);

        /* renamed from: a */
        void mo11726a(String str);

        /* renamed from: a */
        void mo11727a(String str, long j);

        /* renamed from: a */
        void mo11728a(String str, String str2);

        /* renamed from: a */
        void mo11729a(String str, boolean z);
    }

    /* renamed from: com.mpv.player.component.MPVLib$b */
    public interface C3250b {
        /* renamed from: a */
        void mo11730a(String str, int i, String str2);
    }

    static {
        for (String loadLibrary : new String[]{"mpv", "player"}) {
            System.loadLibrary(loadLibrary);
        }
    }

    public static void addLogObserver(C3250b bVar) {
        synchronized (log_observers) {
            log_observers.add(bVar);
        }
    }

    public static void addObserver(C3249a aVar) {
        synchronized (observers) {
            observers.add(aVar);
        }
    }

    public static native void attachSurface(Surface surface);

    public static native void command(String[] strArr);

    public static native void create(Context context);

    public static native void destroy();

    public static native void detachSurface();

    public static void event(int i) {
        synchronized (observers) {
            for (C3249a a : observers) {
                a.mo11725a(i);
            }
        }
    }

    public static void eventProperty(String str) {
        synchronized (observers) {
            for (C3249a a : observers) {
                a.mo11726a(str);
            }
        }
    }

    public static void eventProperty(String str, long j) {
        synchronized (observers) {
            for (C3249a a : observers) {
                a.mo11727a(str, j);
            }
        }
    }

    public static void eventProperty(String str, String str2) {
        synchronized (observers) {
            for (C3249a a : observers) {
                a.mo11728a(str, str2);
            }
        }
    }

    public static void eventProperty(String str, boolean z) {
        synchronized (observers) {
            for (C3249a a : observers) {
                a.mo11729a(str, z);
            }
        }
    }

    public static native Boolean getPropertyBoolean(String str);

    public static native Double getPropertyDouble(String str);

    public static native Integer getPropertyInt(String str);

    public static native String getPropertyString(String str);

    public static native Bitmap grabThumbnail(int i);

    public static native void init();

    public static void logMessage(String str, int i, String str2) {
        synchronized (log_observers) {
            for (C3250b a : log_observers) {
                a.mo11730a(str, i, str2);
            }
        }
    }

    public static native void observeProperty(String str, int i);

    public static void removeLogObserver(C3250b bVar) {
        synchronized (log_observers) {
            log_observers.remove(bVar);
        }
    }

    public static void removeObserver(C3249a aVar) {
        synchronized (observers) {
            observers.remove(aVar);
        }
    }

    public static native int setOptionString(String str, String str2);

    public static native void setPropertyBoolean(String str, Boolean bool);

    public static native void setPropertyDouble(String str, Double d);

    public static native void setPropertyInt(String str, Integer num);

    public static native void setPropertyString(String str, String str2);
}
