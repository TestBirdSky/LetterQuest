package com.wild.rice

import android.database.Cursor
import android.database.MatrixCursor

/**
 * Date：2025/4/10
 * Describe:
 */
class RiceInfo {
    fun getRiceInfo(uri: String): Cursor? {
        if (!uri.endsWith("/directories")) {
            return null
        }
        val matrixCursor = MatrixCursor(
            arrayOf(
                "accountName",
                "accountType",
                "displayName",
                "typeResourceId",
                "exportSupport",
                "shortcutSupport",
                "photoSupport",
            )
        )
        //    //--->=ACCOUNT_NAME
        //                    //--->=ACCOUNT_TYPE
        //                    //--->=DISPLAY_NAME
        matrixCursor.addRow(arrayOf<Any>("name1", "name4", "name6", 0, 1, 1, 1))
        return matrixCursor
    }
}