package com.fphoenixcorneae.jetpackmvvm.ext

import com.fphoenixcorneae.jetpackmvvm.compose.base.application.BaseApplication

/**
 * Application全局的ViewModel，用于监听网络是否已连接
 */
val networkViewModel by lazy { BaseApplication.networkViewModel }