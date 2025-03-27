package com.rice.jar

import com.wild.rice.RiceCenter
import com.wild.rice.RiceShrimp
import kotlin.reflect.KProperty

/**
 * Date：2025/3/19
 * Describe:
 */
class RiceWarehouseCache(private val def: String = "") {

    private fun getRiceName(string: String): String {
        return RiceCenter.md5ThenBase64(string)
    }

    operator fun getValue(me: Any?, p: KProperty<*>): String {
        return RiceShrimp.mmkv.decodeString(getRiceName(p.name), def) ?: ""
    }

    operator fun setValue(me: Any?, p: KProperty<*>, value: String) {
        RiceShrimp.mmkv.encode(getRiceName(p.name), value)
    }

}