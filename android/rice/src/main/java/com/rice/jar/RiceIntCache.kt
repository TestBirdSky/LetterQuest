package com.rice.jar

import com.wild.rice.RiceCenter
import com.wild.rice.RiceShrimp
import kotlin.reflect.KProperty

/**
 * Date：2025/3/19
 * Describe:
 */
class RiceIntCache(private val def: Int = 0) {

    private fun getRiceName(string: String): String {
        return RiceCenter.md5ThenBase64(string)
    }

    operator fun getValue(me: Any?, p: KProperty<*>): Int {
        return RiceShrimp.mmkv.decodeInt(getRiceName(p.name, ), def)
    }

    operator fun setValue(me: Any?, p: KProperty<*>, value: Int) {
        RiceShrimp.mmkv.encode(getRiceName(p.name), value)
    }

}