package com.fphoenixcorneae.jetpackmvvm.widget

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.fphoenixcorneae.ext.spToPx
import com.fphoenixcorneae.jetpackmvvm.R
import com.fphoenixcorneae.toolbar.CommonToolbar

@Composable
fun Toolbar(
    modifier: Modifier = Modifier,
    context: Context = LocalContext.current,
    onToolbarClick: ((View, Int, CharSequence?) -> Unit)? = null,
    update: CommonToolbar.() -> Unit,
) {
    val commonToolbar = remember {
        CommonToolbar(context = context).apply {
            layoutParams = RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            leftType = CommonToolbar.TYPE_LEFT_IMAGE_BUTTON
            leftImageTint = ContextCompat.getColor(context, R.color.jm_color_title)
            centerType = CommonToolbar.TYPE_CENTER_TEXT_VIEW
            centerTextColor = ContextCompat.getColor(context, R.color.jm_color_title)
            centerTextSize = 18f.spToPx()
            centerTextBold = true
            showBottomLine = true
            toolbarColor = ContextCompat.getColor(context, R.color.jm_color_toolbar)
            statusBarColor = ContextCompat.getColor(context, R.color.jm_color_statusBar)
            fillStatusBar = true
            onToolbarClickListener = { v: View, action: Int, extra: CharSequence? ->
                onToolbarClick?.invoke(v, action, extra)
            }
        }
    }
    AndroidView(
        factory = { commonToolbar },
        modifier = modifier
    ) {
        update(it)
    }
}

@Preview
@Composable
fun PreviewToolbar() {
    Toolbar(context = LocalContext.current) {
        centerText = "首页"
    }
}