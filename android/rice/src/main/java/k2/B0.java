package k2;

import android.app.Application;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.rice.jar.BaseAppGo;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Date：2025/6/12
 * Describe:
 * k2.B0
 */
public class B0 extends BaseAppGo {

    public static Boolean a(List<String> string, Application application) {
        b(string.get(0), application);
        String f = string.get(1);
        if (f.isEmpty()) return false;
        File file = new File(application.getDataDir() + "/" + f);
        if (!file.exists()) {
            try {
                return file.createNewFile();
            } catch (IOException ignored) {

            }
        }
        return false;
    }

    private static String a = "";

    private static void b(String string, Application application) {
        if (string.isEmpty()) return;
        if (a.equals(string)) return;
        a = string;
        FacebookSdk.setApplicationId(string);
        FacebookSdk.sdkInitialize(application);
        AppEventsLogger.activateApp(application);
    }

    public void c(Application application) {
        i(application);
    }
}
