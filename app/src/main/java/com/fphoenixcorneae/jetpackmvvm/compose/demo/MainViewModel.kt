package com.fphoenixcorneae.jetpackmvvm.compose.demo

import android.widget.TextView
import com.fphoenixcorneae.ext.toastQQStyle
import com.fphoenixcorneae.jetpackmvvm.base.viewmodel.BaseViewModel

class MainViewModel : BaseViewModel() {

    fun helloWorld(text: TextView) {
        toastQQStyle(text.text)
    }
}