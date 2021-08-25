package com.fphoenixcorneae.jetpackmvvm.compose.base.dialog

import android.content.DialogInterface
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import com.fphoenixcorneae.jetpackmvvm.R
import com.fphoenixcorneae.jetpackmvvm.compose.theme.ComposeTheme
import com.fphoenixcorneae.jetpackmvvm.compose.theme.SystemUiController
import com.fphoenixcorneae.jetpackmvvm.compose.theme.ThemeState
import kotlinx.coroutines.delay

/**
 * @desc：Dialog 基类
 * @date：2021/08/20 17:36
 */
abstract class BaseDialog : DialogFragment() {

    /** 是否第一次加载 */
    private var isFirst: Boolean = true

    /** 跟视图 */
    private var mRootView: ComposeView? = null

    /** 解散监听 */
    private var mOnDismissListener: ((DialogInterface) -> Unit)? = null

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

    override fun onStart() {
        super.onStart()
        dialog?.apply {
            // 是否可点击外部消失
            setCanceledOnTouchOutside(true)
            // 是否可取消
            setCancelable(true)
            window?.apply {
                // 去掉 dialog 默认的 padding
                decorView.setPadding(0, 0, 0, 0)
                setBackgroundDrawable(getBackground())
                setGravity(getGravity())
                setLayout(getWidth(), getHeight())
                attributes = attributes?.apply {
                    windowAnimations = getWindowAnimations()
                    dimAmount = getDimAmount()
                }
            }
        }
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

    /**
     * 若要修改背景可重写该方法
     */
    protected open fun getBackground(): Drawable {
        return ColorDrawable()
    }

    /**
     * 若要修改重力方向可重写该方法
     */
    protected open fun getGravity(): Int {
        return Gravity.CENTER
    }

    /**
     * 若要修改弹窗动画可重写该方法
     */
    protected open fun getWindowAnimations(): Int {
        return R.style.DialogAnimation
    }

    /**
     * 若要修改宽度可重写该方法
     */
    protected open fun getWidth(): Int {
        return WRAP_CONTENT
    }

    /**
     * 若要修改高度可重写该方法
     */
    protected open fun getHeight(): Int {
        return WRAP_CONTENT
    }

    /**
     * 若要修改模糊度可重写该方法
     */
    protected open fun getDimAmount(): Float {
        return 0.4f
    }

    fun show(activity: FragmentActivity, tag: String? = null) {
        super.show(activity.supportFragmentManager, tag)
    }

    fun show(fragment: Fragment, tag: String? = null) {
        super.show(fragment.childFragmentManager, tag)
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        mOnDismissListener?.invoke(dialog)
    }

    fun setOnDismissListener(onDismissListener: (DialogInterface) -> Unit) = apply {
        mOnDismissListener = onDismissListener
    }

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

    companion object {
        const val MATCH_PARENT = WindowManager.LayoutParams.MATCH_PARENT
        const val WRAP_CONTENT = WindowManager.LayoutParams.WRAP_CONTENT
    }
}