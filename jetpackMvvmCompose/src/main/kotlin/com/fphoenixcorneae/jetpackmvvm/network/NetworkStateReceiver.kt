package com.fphoenixcorneae.jetpackmvvm.network

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import com.fphoenixcorneae.ext.toast
import com.fphoenixcorneae.jetpackmvvm.ext.networkViewModel
import com.fphoenixcorneae.util.NetworkUtil

/**
 * @desc：网络变化接收器
 * @date：2021/4/5 19:19
 */
class NetworkStateReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == ConnectivityManager.CONNECTIVITY_ACTION) {
            if (NetworkUtil.isConnected.not()) {
                // 收到没有网络时判断之前的值是不是有网络，如果有网络才提示通知，防止重复通知
                if (networkViewModel.isConnected()) {
                    toast("网络不给力啊！")
                    networkViewModel.setNetworkConnected(false)
                }
            } else {
                // 收到有网络时判断之前的值是不是没有网络，如果没有网络才提示通知，防止重复通知
                if (networkViewModel.isConnected().not()) {
                    networkViewModel.setNetworkConnected(true)
                }
            }
        }
    }
}