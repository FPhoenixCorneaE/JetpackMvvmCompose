package com.fphoenixcorneae.jetpackmvvm.base.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * @desc：视图模型工厂
 * @date：2021/08/24 11:40
 */
class ViewModelFactory(private val viewModel: BaseViewModel):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return viewModel as T
    }
}