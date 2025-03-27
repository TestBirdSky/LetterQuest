package com.rice.jar

import org.json.JSONObject

/**
 * Date：2025/3/20
 * Describe:
 */
interface ConfigureListener {

    fun refresh(js: JSONObject)
}