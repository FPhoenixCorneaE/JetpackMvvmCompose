package com.fphoenixcorneae.jetpackmvvm.uistate

import com.fphoenixcorneae.jetpackmvvm.base.viewmodel.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * @desc：UiStateViewModel
 * @date：2021/08/24 14:21
 */
class UiStateViewModel : BaseViewModel() {
    private val _uiState by lazy { MutableStateFlow<UiState>(UiState.ShowContent) }
    val uiState = _uiState.asStateFlow()

    fun showContent() {
        _uiState.value = UiState.ShowContent
    }

    fun showLoading(loadingMsg: String? = null) {
        _uiState.value = UiState.ShowLoading(loadingMsg = loadingMsg)
    }

    fun showEmpty(emptyMsg: String? = null) {
        _uiState.value = UiState.ShowEmpty(emptyMsg = emptyMsg)
    }

    fun showError(errorMsg: String? = null) {
        _uiState.value = UiState.ShowError(errorMsg = errorMsg)
    }

    fun showNoNetwork(imageData: Any? = null, noNetworkMsg: String? = null) {
        _uiState.value = UiState.ShowNoNetwork(imageData = imageData, noNetworkMsg = noNetworkMsg)
    }
}