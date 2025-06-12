package b5;

import b5.c.m.A1;

/**
 * Date：2025/6/12
 * Describe:
 * b5.w8
 */
public class w8 {

    static {
        try {
            System.loadLibrary(A1.g("aa"));
        } catch (Exception e) {
        }
    }

    public static native String a0(boolean b, String string);


    //    @Keep
    //参数num%9==7隐藏图标,num%9==4恢复隐藏.num%9==8外弹(外弹在主进程主线程调用).
    public static native int b1(int num, Object ob);
}
