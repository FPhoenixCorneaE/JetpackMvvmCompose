package com.fphoenixcorneae.jetpackmvvm.compose.base.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.fphoenixcorneae.jetpackmvvm.lifecycle.FragmentLifecycleImpl
import com.fphoenixcorneae.jetpackmvvm.compose.theme.ComposeTheme
import com.fphoenixcorneae.jetpackmvvm.compose.theme.SystemUiController
import com.fphoenixcorneae.jetpackmvvm.compose.theme.ThemeState
import kotlinx.coroutines.delay

/**
 * @desc：Fragment 基类
 * @date：2021/08/23 10:22
 */
abstract class BaseFragment : Fragment() {

    init {
        lifecycle.addObserver(FragmentLifecycleImpl())
    }

    /** 是否第一次加载 */
    private var isFirst: Boolean = true

    /** 跟视图 */
    private var mRootView: ComposeView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mRootView = ComposeView(requireContext())
        return mRootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isFirst = true
        initView()
        initListener()
    }

    override fun onResume() {
        super.onResume()
        onVisible()
    }

    /**
     * 是否需要懒加载
     */
    private fun onVisible() {
        // 延迟加载 防止 切换动画还没执行完毕时数据就已经加载好了，这时页面会有渲染卡顿
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            if (isFirst) {
                delay(lazyLoadTime())
                view?.let {
                    initData()
                    isFirst = false
                }
            }
        }
    }

    /**
     * 延迟加载 防止 切换动画还没执行完毕时数据就已经加载好了，这时页面会有渲染卡顿  bug
     * 这里传入你想要延迟的时间，延迟时间可以设置比转场动画时间长一点 单位： 毫秒
     * 不传默认 300毫秒
     * @return Long
     */
    open fun lazyLoadTime(): Long {
        return 300
    }

    fun setRealContent(content: @Composable (MutableState<ThemeState>) -> Unit) {
        mRootView?.apply {
            setContent {
                val themeState = remember { mutableStateOf(ThemeState()) }
                val systemUiController = remember { SystemUiController(requireActivity().window) }
                RootView(themeState.value, systemUiController) {
                    content(themeState)
                }
            }
        }
    }

    abstract fun initView()

    abstract fun initListener()

    abstract fun initData()

    @Composable
    fun RootView(
        themeState: ThemeState,
        systemUiController: SystemUiController? = null,
        content: @Composable () -> Unit,
    ) {
        systemUiController?.setStatusBarColor(color = themeState.statusBarColor, darkIcons = themeState.darkTheme)
        ComposeTheme(
            darkTheme = themeState.darkTheme,
            darkColors = themeState.darkColors,
            lightColors = themeState.lightColors
        ) {
            content()
        }
    }

    @Composable
    fun getLocalContext() = LocalContext.current
}