package com.fphoenixcorneae.jetpackmvvm.ext

import com.fphoenixcorneae.jetpackmvvm.compose.base.application.BaseApplication
import com.fphoenixcorneae.jetpackmvvm.compose.base.viewmodel.NetworkViewModel

/**
 * Application全局的ViewModel，用于监听网络是否已连接
 */
val networkViewModel by lazy {
    BaseApplication.getInstance().getAndroidViewModel(NetworkViewModel::class.java)
}