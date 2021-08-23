package com.fphoenixcorneae.jetpackmvvm.base.viewmodel

import androidx.lifecycle.ViewModel
import com.fphoenixcorneae.jetpackmvvm.livedata.Event
import com.fphoenixcorneae.jetpackmvvm.uistate.UiState
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * @desc：ViewModel 的基类
 * @date：2021/08/23 10:24
 */
open class BaseViewModel : ViewModel() {

    val uiState by lazy { MutableStateFlow(Event(UiState.ShowContent)) }
}

