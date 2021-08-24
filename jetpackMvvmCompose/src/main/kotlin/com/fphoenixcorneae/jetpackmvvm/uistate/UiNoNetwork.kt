package com.fphoenixcorneae.jetpackmvvm.uistate

import android.annotation.SuppressLint
import android.content.res.Resources
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.drawable.toDrawable
import coil.compose.rememberImagePainter
import com.fphoenixcorneae.ext.toastAliPayStyle
import com.fphoenixcorneae.jetpackmvvm.R
import kotlinx.coroutines.launch

@SuppressLint("ResourceType")
@Composable
fun UiNoNetwork(
    imageData: Any? = null,
    noNetworkMsg: String? = null,
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
        Column(
            modifier = Modifier.align(Alignment.Center)
        ) {
            val painter = rememberImagePainter(
                data = imageData ?: ImageBitmap.imageResource(id = R.mipmap.jm_ic_no_network)
                    .asAndroidBitmap()
                    .toDrawable(Resources.getSystem()),
                builder = {
                    crossfade(true)
                }
            )
            Image(
                contentScale = ContentScale.Crop,
                painter = painter,
                contentDescription = "NoNetwork",
                modifier = Modifier
                    .size(180.dp)
            )
            Text(
                text = if (noNetworkMsg.isNullOrEmpty()) stringResource(id = R.string.jm_ui_state_no_network) else noNetworkMsg,
                color = colorResource(R.color.jm_color_0xa9b7b7),
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(top = 12.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }
    }
}

@SuppressLint("ResourceType")
@Preview
@Composable
fun PreviewUiNoNetwork() {
    UiNoNetwork {
        toastAliPayStyle("clicked blank!")
    }
}