package com.fphoenixcorneae.jetpackmvvm.compose.demo

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.lifecycleScope
import com.fphoenixcorneae.ext.logd
import com.fphoenixcorneae.jetpackmvvm.compose.base.activity.BaseActivity
import com.fphoenixcorneae.jetpackmvvm.ext.networkViewModel
import com.fphoenixcorneae.jetpackmvvm.ext.uiStateViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : BaseActivity() {

    override fun initView() {
        setRealContent { themeState ->
            MainScreen(themeState = themeState, localContext = getLocalContext())
        }

        onToolbarUpdate = {
            // 设置标题栏属性
            centerText = context.getString(R.string.app_name)
            toolbarColor = Color.Cyan.toArgb()
        }
    }

    override fun initListener() {
        lifecycleScope.launch {
            networkViewModel.networkState.observe(this@MainActivity) {
                "NetworkState: $it".logd()
            }
        }
    }

    override fun initData() {
        uiStateViewModel.showLoading("正在拼命加载中...")
        lifecycleScope.launchWhenResumed {
            delay(2000)
//            uiStateViewModel.showContent()
            //            uiStateViewModel.showEmpty()
            //            uiStateViewModel.showError()
            //            uiStateViewModel.showNoNetwork()
        }
    }

    override fun toolbarVisible() = true
}