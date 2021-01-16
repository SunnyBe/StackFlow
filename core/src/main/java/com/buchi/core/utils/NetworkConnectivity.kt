package com.buchi.core.utils

import android.content.Context
import android.net.ConnectivityManager

/**
 * Network connectivity
 */
object NetworkConnectivity {

    private fun isNetworkConnected(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return run {
            val activeNetwork = connectivityManager.activeNetworkInfo
            activeNetwork != null && activeNetwork.isConnectedOrConnecting
        }
    }
}