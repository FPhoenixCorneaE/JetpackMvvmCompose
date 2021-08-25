package com.fphoenixcorneae.jetpackmvvm.network

import com.fphoenixcorneae.jetpackmvvm.compose.base.viewmodel.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * @desc：NetworkViewModel
 * @date：2021/08/25 10:39
 */
class NetworkViewModel : BaseViewModel() {

    private val _networkState = MutableStateFlow(NetworkState())
    val networkState = _networkState.asStateFlow()

    fun isConnected() = _networkState.value.isConnected

    fun setNetworkConnected(isConnected: Boolean) {
        _networkState.value = NetworkState(isConnected = isConnected)
    }
}