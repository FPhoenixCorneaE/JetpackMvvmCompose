package com.fphoenixcorneae.jetpackmvvm.base.dialog

import android.annotation.SuppressLint
import android.content.Context
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
import com.fphoenixcorneae.jetpackmvvm.R
import com.fphoenixcorneae.jetpackmvvm.lifecycle.DialogLifecycleImpl
import com.fphoenixcorneae.jetpackmvvm.lifecycle.LifecycleHandler
import com.fphoenixcorneae.jetpackmvvm.theme.ComposeTheme
import com.fphoenixcorneae.jetpackmvvm.theme.SystemUiController
import com.fphoenixcorneae.jetpackmvvm.theme.ThemeState

/**
 * @desc: Dialog 基类
 * @since：2021-04-09 14:12
 */
abstract class BaseDialog : DialogFragment() {

    init {
        lifecycle.addObserver(DialogLifecycleImpl())
    }

    /** 绑定生命周期的 Handler */
    private val mLifecycleHandler by lazy { LifecycleHandler(viewLifecycleOwner) }

    /** 当前界面 Context 对象*/
    protected lateinit var mContext: FragmentActivity

    /** 视图是否加载完毕 */
    private var isViewPrepared = false

    /** 数据是否加载过了 */
    private var hasLoadedData = false

    private var mOnDismissListener: ((DialogInterface) -> Unit)? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = requireActivity()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return getContentView()
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            lazyLoadDataIfPrepared()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isViewPrepared = true
        lazyLoadDataIfPrepared()
    }

    @SuppressLint("ObsoleteSdkInt")
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

    fun setRealContent(content: @Composable (MutableState<ThemeState>) -> Unit) {
        ComposeView(requireContext()).apply {
            setContent {
                val themeState = remember { mutableStateOf(ThemeState()) }
                val systemUiController = remember { SystemUiController(requireActivity().window) }
                RootView(themeState.value, systemUiController) {
                    content(themeState)
                }
            }
        }
    }

    abstract fun getContentView(): View?

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
        return WindowManager.LayoutParams.WRAP_CONTENT
    }

    /**
     * 若要修改高度可重写该方法
     */
    protected open fun getHeight(): Int {
        return WindowManager.LayoutParams.WRAP_CONTENT
    }

    /**
     * 若要修改模糊度可重写该方法
     */
    protected open fun getDimAmount(): Float {
        return 0.4f
    }

    private fun lazyLoadDataIfPrepared() {
        if (userVisibleHint && isViewPrepared && !hasLoadedData) {
            // 延迟加载 防止 切换动画还没执行完毕时数据就已经加载好了，这时页面会有渲染卡顿
            mLifecycleHandler.postDelayed({
                view?.let {
                    initData()
                }
            }, lazyLoadTime())
            hasLoadedData = true
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
}