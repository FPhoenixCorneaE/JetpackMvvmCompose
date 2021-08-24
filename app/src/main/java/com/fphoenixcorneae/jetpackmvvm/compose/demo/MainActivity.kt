package com.fphoenixcorneae.jetpackmvvm.compose.demo

import androidx.compose.foundation.layout.Box
import com.fphoenixcorneae.jetpackmvvm.base.activity.BaseActivity
import com.fphoenixcorneae.jetpackmvvm.widget.Toolbar
import com.fphoenixcorneae.toolbar.CommonToolbar

class MainActivity : BaseActivity() {

    override fun initView() {
        setRealContent { themeState ->
            Box {
                MainScreen(themeState, getLocalContext())
                // 标题栏
                Toolbar(
                    onToolbarClick = { v, action, extra ->
                        if (action == CommonToolbar.TYPE_LEFT_IMAGE_BUTTON) {
                            onBackPressed()
                        }
                    }
                ) {
                    // 设置标题栏属性
                    centerText = context.getString(R.string.app_name)
                }
            }
        }
    }

    override fun initData() {
    }

}