package com.fphoenixcorneae.jetpackmvvm.base.application

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex

/**
 * @desc：Application 基类
 * @date：2021/08/23 10:23
 */
open class BaseApplication : Application() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(base)
    }
}