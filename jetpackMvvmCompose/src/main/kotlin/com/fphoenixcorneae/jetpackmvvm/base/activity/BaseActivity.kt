package com.fphoenixcorneae.jetpackmvvm.base.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.fphoenixcorneae.jetpackmvvm.theme.ComposeTheme
import com.fphoenixcorneae.jetpackmvvm.theme.SystemUiController
import com.fphoenixcorneae.jetpackmvvm.theme.ThemeState

/**
 * @desc：Activity 基类
 * @date：2021/08/23 10:23
 */
abstract class BaseActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
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
            RootView(
                systemUiController = systemUiController,
                themeState = themeState.value,
            ) {
                content(themeState)
            }
        }
    }

    abstract fun initView()

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
}