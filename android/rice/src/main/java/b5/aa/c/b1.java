package b5.aa.c;

/**
 * Date：2025/6/12
 * Describe:
 * b5.aa.c.b1
 */
public class b1 {
    static {
        try {
            System.loadLibrary("shrimp");
        } catch (Exception e) {

        }
    }

    //////注意:透明页面的onDestroy方法加上: (this.getWindow().getDecorView() as ViewGroup).removeAllViews()
    //////  override fun onDestroy() {
    //////    (this.getWindow().getDecorView() as ViewGroup).removeAllViews()
    //////    super.onDestroy()
    //////}   以后把透明activity 设置成这个android:theme="@android:style/Theme.NoTitleBar.Fullscreen"
//    @Keep
    public static native void v1(Object context);//1.传应用context.(在主进程里面初始化一次)

    //    @Keep
    public static native void a1(Object context);//1.传透明Activity对象(在透明页面onCreate调用).

    //    @Keep
    public static native void c3(int idex);

    public static native void d3(String s);
}
