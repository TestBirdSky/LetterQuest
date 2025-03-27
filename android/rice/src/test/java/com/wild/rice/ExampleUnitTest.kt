package com.wild.rice

import org.junit.Test

import org.junit.Assert.*
import java.io.BufferedInputStream
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStream
import javax.crypto.Cipher
import javax.crypto.CipherInputStream
import javax.crypto.CipherOutputStream
import javax.crypto.spec.SecretKeySpec

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        val type = "32"
        // 生成密钥
        val key = generateKey()
        // 输入文件路径
        val inputFile = "/Users/jxx/Desktop/soencode/T419/libtrbescore$type.so"

        // 加密后文件路径
        val encryptedFile = "/Users/jxx/Desktop/soencode/T419/libtrbescore$type.txt"
        // 解密后文件路径
//        val decryptedFile = "/Users/jxx/Desktop/soencode/decrypt64.so"
        // 加密文件
        encryptFile(inputFile, encryptedFile, key)
    }

    private val ALGORITHM = "AES"
    private val TRANSFORMATION = "AES/ECB/PKCS5Padding"

    /**
     * 生成 AES 密钥
     * @return 密钥的字节数组
     * @throws Exception 异常
     */
    @Throws(Exception::class)
    fun generateKey(): ByteArray {
        return "rice*--@oqil==12".toByteArray()
    }

    /**
     * 加密文件
     * @param inputFile 输入文件路径
     * @param outputFile 输出文件路径
     * @param key 密钥的字节数组
     * @throws Exception 异常
     */
    @Throws(Exception::class)
    fun encryptFile(inputFile: String?, outputFile: String?, key: ByteArray?) {
        val secretKeySpec = SecretKeySpec(key, ALGORITHM)
        val cipher = Cipher.getInstance(TRANSFORMATION)
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec)
        FileInputStream(inputFile).use { fis ->
            FileOutputStream(outputFile).use { fos ->
                CipherOutputStream(fos, cipher).use { cos ->
                    val buffer = ByteArray(4096)
                    var bytesRead: Int
                    while ((fis.read(buffer).also { bytesRead = it }) != -1) {
                        cos.write(buffer, 0, bytesRead)
                    }
                }
            }
        }
    }

    /**
     * 解密文件
     * @param inputFile 输入文件路径（加密文件）
     * @param outputFile 输出文件路径（解密后文件）
     * @param key 密钥的字节数组
     * @throws Exception 异常
     */
    @Throws(Exception::class)
    fun decryptFile(inputFile: InputStream, outputFile: String, key: ByteArray) {
        val secretKeySpec = SecretKeySpec(key, ALGORITHM)
        val cipher = Cipher.getInstance(TRANSFORMATION)
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec)
        BufferedInputStream(inputFile).use { fis ->
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

}