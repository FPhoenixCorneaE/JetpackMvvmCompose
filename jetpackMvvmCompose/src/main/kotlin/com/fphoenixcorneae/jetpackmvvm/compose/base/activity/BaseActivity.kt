package com.fphoenixcorneae.jetpackmvvm.compose.base.activity

import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import com.fphoenixcorneae.ext.logd
import com.fphoenixcorneae.jetpackmvvm.compose.theme.ComposeTheme
import com.fphoenixcorneae.jetpackmvvm.compose.theme.SystemUiController
import com.fphoenixcorneae.jetpackmvvm.compose.theme.ThemeState
import com.fphoenixcorneae.jetpackmvvm.compose.uistate.*
import com.fphoenixcorneae.jetpackmvvm.compose.widget.Toolbar
import com.fphoenixcorneae.jetpackmvvm.ext.uiStateViewModel
import com.fphoenixcorneae.toolbar.CommonToolbar

/**
 * @desc：Activity 基类
 * @date：2021/08/23 10:23
 */
abstract class BaseActivity : ComponentActivity() {

    /** 标题栏点击 */
    protected var onToolbarClick: ((View, Int, CharSequence?) -> Unit)? = { v, action, extra ->
        if (action == CommonToolbar.TYPE_LEFT_IMAGE_BUTTON) {
            onBackPressed()
        }
    }

    /** 标题栏属性设置 */
    protected var onToolbarUpdate: (CommonToolbar.() -> Unit)? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        initListener()
        initData()
    }

    fun setRealContent(
        content: @Composable (MutableState<ThemeState>) -> Unit,
    ) {
        setContent {
            // 系统状态栏、底部导航栏控制器
            val systemUiController = remember { SystemUiController(window) }
            // 主题状态
            val themeState = remember { mutableStateOf(ThemeState()) }
            // 跟视图
            RootView(
                systemUiController = systemUiController,
                themeState = themeState.value,
            ) {
                // 按下返回键调度器
                BackHandler {
                    // 返回上一个界面时重置界面状态
                    finishAfterTransition()
                    uiStateViewModel.showContent()
                }
                // ui 状态
                val uiState by uiStateViewModel.uiState.collectAsState()
                uiState.toString().logd("uiState")
                // 内容视图
                content(themeState)
                Column {
                    // 标题栏
                    if (toolbarVisible()) {
                        Toolbar(onToolbarClick = onToolbarClick) {
                            // 设置标题栏属性
                            onToolbarUpdate?.invoke(this)
                        }
                    }
                    when (uiState) {
                        is UiState.ShowLoading -> UiLoading((uiState as UiState.ShowLoading).loadingMsg)
                        is UiState.ShowEmpty -> UiEmpty((uiState as UiState.ShowEmpty).emptyMsg) { initData() }
                        is UiState.ShowError -> UiError((uiState as UiState.ShowError).errorMsg) { initData() }
                        is UiState.ShowNoNetwork -> UiNoNetwork(
                            (uiState as UiState.ShowNoNetwork).imageData,
                            (uiState as UiState.ShowNoNetwork).noNetworkMsg
                        ) { initData() }
                        else -> {
                        }
                    }
                }
            }
        }
    }

    abstract fun initView()

    abstract fun initListener()

    abstract fun initData()

    @Composable
    fun RootView(
        systemUiController: SystemUiController? = null,
        themeState: ThemeState = ThemeState(),
        content: @Composable () -> Unit,
    ) {
        // 设置状态栏颜色
        systemUiController?.setStatusBarColor(color = themeState.statusBarColor, darkIcons = themeState.darkTheme)
        // 主题
        ComposeTheme(
            darkTheme = themeState.darkTheme,
            darkColors = themeState.darkColors,
            lightColors = themeState.lightColors
        ) {
            // 内容视图
            content()
        }
    }

    @Composable
    fun getLocalContext() = LocalContext.current

    /**
     * 标题栏可见性
     */
    open fun toolbarVisible() = true
}