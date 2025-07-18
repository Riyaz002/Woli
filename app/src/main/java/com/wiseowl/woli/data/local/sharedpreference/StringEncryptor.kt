package com.wiseowl.woli.data.local.sharedpreference

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import com.wiseowl.woli.BuildConfig
import java.security.Key
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.spec.GCMParameterSpec

class StringEncryptor: Encryptor<String> {
    private val provider = "AndroidKeyStore"
    private val algorithm = KeyProperties.KEY_ALGORITHM_AES
    private val blockMode = KeyProperties.BLOCK_MODE_GCM
    private val padding = KeyProperties.ENCRYPTION_PADDING_NONE
    private val transformation = "$algorithm/$blockMode/$padding"
    private val iv: ByteArray = byteArrayOf(1,2,3,4,5,6,7,8,9,10,11,12)
    private val ivParams = GCMParameterSpec(128, iv)
    private val secretKey = generateSymmetricKey(
        keystoreName = provider,
        alias = BuildConfig.KEY_STORE_ALIAS,
        algorithm = algorithm,
        blockMode = blockMode,
        padding = padding,
        purposes = KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
    )

    private fun getInstance(): Cipher = Cipher.getInstance(transformation)

    override fun encrypt(value: String): String {
        val cipher = getInstance()
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParams)
        val byteArray = value.encodeToByteArray()
        val encryptedByteArray = cipher.doFinal(byteArray)
        return Base64.encodeToString(encryptedByteArray, Base64.DEFAULT)
    }

    override fun decrypt(value: String): String {
        val encryptedByteArray = Base64.decode(value, Base64.DEFAULT)
        val cipher = getInstance()
        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParams)
        return cipher.doFinal(encryptedByteArray).decodeToString()
    }

    private fun generateSymmetricKey(keystoreName: String, alias: String, algorithm: String, padding: String, blockMode: String, purposes: Int): Key {
        val keystore = KeyStore.getInstance(keystoreName)
        keystore.load(null)

        if (!keystore.containsAlias(alias)) {
            val keyGenerator = KeyGenerator.getInstance(algorithm, keystoreName)

            val keyGenParameterSpec = KeyGenParameterSpec.Builder(alias, purposes)
                .setBlockModes(blockMode)
                .setEncryptionPaddings(padding)
                .setUserAuthenticationRequired(false)
                .setRandomizedEncryptionRequired(false)
                .build()

            keyGenerator.init(keyGenParameterSpec)

            return keyGenerator.generateKey()
        } else {
            return keystore.getKey(alias, null) as Key
        }
    }
}



