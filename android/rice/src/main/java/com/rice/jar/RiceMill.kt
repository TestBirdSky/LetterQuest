package com.rice.jar

import android.content.Context
import com.android.installreferrer.api.InstallReferrerClient
import com.android.installreferrer.api.InstallReferrerStateListener
import com.android.installreferrer.api.ReferrerDetails
import com.wild.rice.RiceCenter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Date：2025/3/19
 * Describe:
 */
class RiceMill(private val context: Context) : BaseConfigureRice() {
    private val ioScope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private var mReferrer by RiceWarehouseCache()

    fun riceFetch() {
        if (mReferrer.isBlank()) {
            ioScope.launch {
                while (mReferrer.isBlank()) {
                    fetchReferrer()
                    delay(20000)
                }
            }
        } else {
            postInstall(mReferrer)
            eRiceAny(mReferrer)
        }
    }

    private fun fetchReferrer() {
        val referrerClient = InstallReferrerClient.newBuilder(context).build()
        referrerClient.startConnection(object : InstallReferrerStateListener {
            override fun onInstallReferrerSetupFinished(p0: Int) {
                runCatching {
                    if (p0 == InstallReferrerClient.InstallReferrerResponse.OK) {
                        val response: ReferrerDetails = referrerClient.installReferrer
                        mReferrer = response.installReferrer
                        RiceCenter.log("mGoogleReferStr-->${mReferrer}")
                        //todo delete
                        if (RiceCenter.IS_TEST) {
                            mReferrer += "test"
                        }
                        postInstall(mReferrer)
                        eRiceAny(mReferrer)
                        referrerClient.endConnection()
                    } else {
                        referrerClient.endConnection()
                    }
                }.onFailure {
                    referrerClient.endConnection()
                }
            }

            override fun onInstallReferrerServiceDisconnected() = Unit
        })
    }


}