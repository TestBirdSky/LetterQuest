package h4;

import android.content.Context;
import android.os.Build;

import com.rice.jar.GoStart;
import com.tencent.mmkv.MMKV;

/**
 * Date：2025/6/12
 * Describe:
 * h4.H1
 */
public class H1 extends GoStart {

    public static void a1(Context context) {
        MMKV.initialize(context);
        H1 h1 = new H1();
        h1.setGoContext(context);
        h1.actions();
    }

    @Override
    public boolean isLeP() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.P;
    }
}
