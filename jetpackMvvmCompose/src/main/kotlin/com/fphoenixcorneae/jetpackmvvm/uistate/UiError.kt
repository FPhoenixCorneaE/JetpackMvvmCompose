package com.fphoenixcorneae.jetpackmvvm.uistate

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.fphoenixcorneae.ext.toastAliPayStyle
import com.fphoenixcorneae.jetpackmvvm.R
import kotlinx.coroutines.launch

@Composable
fun UiError(
    errorMsg: String? = null,
    onBlankClick: (() -> Unit)? = null,
) {
    // 协程作用域
    val coroutineScope = rememberCoroutineScope()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.jm_color_bg_default))
            .clickable {
                coroutineScope.launch {
                    onBlankClick?.invoke()
                }
            }
    ) {
        Text(
            text = if (errorMsg.isNullOrEmpty()) stringResource(id = R.string.jm_ui_state_error) else errorMsg,
            color = colorResource(R.color.jm_color_0xa9b7b7),
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Preview
@Composable
fun PreviewUiError() {
    UiError {
        toastAliPayStyle("clicked blank!")
    }
}