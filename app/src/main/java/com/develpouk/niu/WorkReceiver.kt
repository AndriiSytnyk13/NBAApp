package com.develpouk.niu

import android.app.Dialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

class WorkReceiver : BroadcastReceiver() {

    private var vegasDialog: Dialog? = null

    override fun onReceive(context: Context, intent: Intent) {
        try {
            if (!context.vegasInternet()) {
                vegasDialog = Dialog(context, android.R.style.Theme_NoTitleBar)
                vegasDialog?.apply {
                    setCancelable(false)
                    setContentView(R.layout.dialog_missing_internet)
                    show()
                }
            } else if (context.vegasInternet()) {
                vegasDialog?.dismiss()
                vegasDialog = null
            }
        } catch (_: NullPointerException) {

        }
    }
}

fun Context.vegasInternet(): Boolean {
    val vegasConnManager =
        getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val vegasCapabilities =
        vegasConnManager.getNetworkCapabilities(vegasConnManager.activeNetwork)
    if (vegasCapabilities != null) {
        if (vegasCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
            return true
        } else if (vegasCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
            return true
        } else if (vegasCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
            return true
        }
    }
    return false
}