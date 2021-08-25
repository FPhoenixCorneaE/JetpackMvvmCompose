package com.fphoenixcorneae.jetpackmvvm.compose.demo

import com.fphoenixcorneae.ext.toastQQStyle
import com.fphoenixcorneae.jetpackmvvm.compose.base.viewmodel.BaseViewModel

class MainViewModel : BaseViewModel() {

    fun helloWorld() {
        toastQQStyle("Hello World!")
    }
}