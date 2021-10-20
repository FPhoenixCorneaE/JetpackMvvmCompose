package com.fphoenixcorneae.jetpackmvvm.compose.demo

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fphoenixcorneae.ext.screenHeight
import com.fphoenixcorneae.ext.screenWidth
import com.fphoenixcorneae.ext.startKtxActivity
import com.fphoenixcorneae.jetpackmvvm.compose.theme.ThemeState
import com.fphoenixcorneae.util.ScreenUtil
import com.fphoenixcorneae.util.statusbar.StatusBarUtil
import kotlinx.coroutines.launch

@SuppressLint("ResourceType")
@Composable
fun MainScreen(
    themeState: MutableState<ThemeState>,
    context: Context,
    mainViewModel: MainViewModel = viewModel()
) {
    Column(
        modifier = Modifier
            .padding(top = 64.dp)
            .fillMaxWidth()
            .height(2000.dp)
            .background(Color(integerResource(id = R.color.jm_color_bg_default)))
            .verticalScroll(state = rememberScrollState())
    ) {
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
                    context.startKtxActivity<HomeActivity>(flags = null, extra = null, value = null)
                }
                .padding(12.dp)
        )
        Text(
            text = "Hello World!",
            style = MaterialTheme.typography.button,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .height(48.dp)
                .border(width = 2.dp, color = Color.Red, shape = RoundedCornerShape(8.dp))
                .background(color = Color.Gray, shape = RoundedCornerShape(8.dp))
                .clickable {
                    mainViewModel.helloWorld()
                }
                .padding(12.dp)
        )
    }

    val coroutineScope = rememberCoroutineScope()
    // 搜索按钮
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .zIndex(1f)
            .padding(top = LocalDensity.current.run {
                StatusBarUtil
                    .getStatusBarHeight(context)
                    .toDp()
            })
    ) {
        // 揭露动画效果
        var circularReveal by remember { mutableStateOf(false) }
        val size by animateIntAsState(
            targetValue = if (circularReveal) {
                context.screenWidth.coerceAtLeast(context.screenHeight)
            } else {
                44
            },
            animationSpec = tween(durationMillis = 2_000)
        )
        val percent by animateIntAsState(
            targetValue = if (circularReveal) {
                0
            } else {
                50
            },
            animationSpec = tween(durationMillis = 1_000)
        )
        Surface(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .size(size.dp)
                .clip(RoundedCornerShape(percent))
                .background(Color.White)
        ) {
            if (circularReveal.not()) {
                IconButton(
                    onClick = {
                        coroutineScope.launch {
                            circularReveal = circularReveal.not()
                        }
                    },
                    modifier = Modifier
                        .padding(end = 8.dp),
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_search),
                        contentDescription = null,
                        tint = Color.Black,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewMainScreen() {
    MainScreen(
        themeState = remember { mutableStateOf(ThemeState()) },
        context = LocalContext.current,
    )
}