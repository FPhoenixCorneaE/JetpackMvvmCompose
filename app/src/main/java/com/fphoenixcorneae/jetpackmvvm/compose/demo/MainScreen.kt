package com.fphoenixcorneae.jetpackmvvm.compose.demo

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fphoenixcorneae.ext.startKtxActivity
import com.fphoenixcorneae.jetpackmvvm.theme.ThemeState

@Composable
fun MainScreen(themeState: MutableState<ThemeState>, localContext: Context) {
    Column {
        Text(
            text = "状态栏变色",
            style = MaterialTheme.typography.button,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .height(48.dp)
                .border(width = 2.dp, color = Color.Red, shape = RoundedCornerShape(8.dp))
                .background(color = Color.Gray, shape = RoundedCornerShape(8.dp))
                .clickable {
                    themeState.value = themeState.value.copy(statusBarColor = Color.Red)
                }
                .padding(12.dp)
        )
        Text(
            text = "ComposeFragment",
            style = MaterialTheme.typography.button,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .height(48.dp)
                .border(width = 2.dp, color = Color.Red, shape = RoundedCornerShape(8.dp))
                .background(color = Color.Gray, shape = RoundedCornerShape(8.dp))
                .clickable {
                    localContext.startKtxActivity<HomeActivity>(flags = null, extra = null, value = null)
                }
                .padding(12.dp)
        )
    }
}

@Preview
@Composable
fun PreviewMainScreen() {
    MainScreen(themeState = remember {
        mutableStateOf(ThemeState())
    }, localContext = LocalContext.current)
}