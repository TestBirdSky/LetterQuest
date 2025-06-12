package com.mill.tips

import org.json.JSONObject

/**
 * Date：2025/6/12
 * Describe:
 */
interface EventImpl {
    fun postEventOpen(string: String, value: String?)

    fun postJsonOpen(jsonObject: JSONObject)
}