package com.fphoenixcorneae.jetpackmvvm.compose.demo

import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fphoenixcorneae.jetpackmvvm.base.activity.BaseActivity
import kotlinx.coroutines.delay

class MainActivity : BaseActivity() {

    override fun initView() {
        setRealContent { themeState ->
            MainScreen(themeState, getLocalContext(), viewModel<MainViewModel>())
        }

        onToolbarUpdate = {
            // 设置标题栏属性
            centerText = context.getString(R.string.app_name)
        }
    }

    override fun initListener() {

    }

    override fun initData() {
        uiStateViewModel.showLoading("正在拼命加载中...")
        lifecycleScope.launchWhenResumed {
            delay(2000)
            uiStateViewModel.showContent()
//            uiStateViewModel.showEmpty()
//            uiStateViewModel.showError()
//            uiStateViewModel.showNoNetwork()
        }
    }
}