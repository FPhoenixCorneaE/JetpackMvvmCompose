package com.fphoenixcorneae.jetpackmvvm.uistate

sealed class UiState {
    object ShowContent : UiState()
    object DismissLoading : UiState()
    data class ShowLoading(val loadingMsg: CharSequence? = null) : UiState()
    data class ShowEmpty(val emptyMsg: CharSequence? = null) : UiState()
    data class ShowError(val errorMsg: CharSequence? = null) : UiState()
}
