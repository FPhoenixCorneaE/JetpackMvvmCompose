package com.fphoenixcorneae.jetpackmvvm.compose.demo

import com.fphoenixcorneae.jetpackmvvm.base.activity.BaseActivity

class MainActivity : BaseActivity() {

    override fun initView() {
        setRealContent { themeState ->
            MainScreen(themeState, getLocalContext())
        }
    }

    override fun initData() {
    }

}