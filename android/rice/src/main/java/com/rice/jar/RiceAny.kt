package com.rice.jar


/**
 * Date：2025/3/19
 * Describe:
 */
interface RiceAny {

    fun eRiceAny(any: Any)

    fun postInstall(str: String)

    fun postEvent(name: String, value: String?)

}