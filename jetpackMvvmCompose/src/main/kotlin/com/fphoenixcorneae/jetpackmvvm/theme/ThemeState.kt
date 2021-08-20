package com.fphoenixcorneae.jetpackmvvm.theme

import androidx.compose.material.Colors
import androidx.compose.ui.graphics.Color

/**
 * @desc：应用主题状态
 * @date：2021/08/19 17:54
 */
data class ThemeState(
    var darkTheme: Boolean = false,
    var darkColors: Colors = darkBlueColors,
    var lightColors: Colors = lightBlueColors,
    var statusBarColor: Color = blue700,
) {
    companion object {
        @Volatile
        private var sInstance: ThemeState? = null

        @Synchronized
        fun getInstance(): ThemeState {
            return sInstance ?: synchronized(this) {
                sInstance ?: ThemeState().also { sInstance = it }
            }
        }
    }
}