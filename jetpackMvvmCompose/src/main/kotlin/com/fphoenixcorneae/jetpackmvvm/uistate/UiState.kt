package com.fphoenixcorneae.jetpackmvvm.uistate

/**
 * @desc：UiState
 * @date：2021/08/24 15:21
 */
sealed class UiState {
    object ShowContent : UiState()
    data class ShowLoading(val loadingMsg: String? = null) : UiState()
    data class ShowEmpty(val emptyMsg: String? = null) : UiState()
    data class ShowError(val errorMsg: String? = null) : UiState()
    data class ShowNoNetwork(val imageData: Any? = null, val noNetworkMsg: String? = null) : UiState()
}
