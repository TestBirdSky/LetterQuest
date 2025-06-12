package com.rice.jar

import com.wild.rice.RiceCenter
import com.wild.rice.RiceShrimp
import kotlin.reflect.KProperty

/**
 * Date：2025/3/19
 * Describe:
 */
class RiceBoolCache(private val def: Boolean = false, val key: String = "") {
    operator fun getValue(me: Any?, p: KProperty<*>): Boolean {
        return RiceShrimp.mmkv.decodeBool(key.ifBlank { p.name }, def)
    }

    operator fun setValue(me: Any?, p: KProperty<*>, value: Boolean) {
        RiceShrimp.mmkv.encode(key.ifBlank { p.name }, value)
    }

}