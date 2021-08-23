package com.fphoenixcorneae.jetpackmvvm.compose.demo

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.fphoenixcorneae.ext.dp2Px
import com.fphoenixcorneae.ext.dpToPx
import com.fphoenixcorneae.ext.toast
import com.fphoenixcorneae.jetpackmvvm.base.dialog.BaseDialog

class SimpleDialog() : BaseDialog() {

    override fun initView() {
        setRealContent { themeState ->
            Box(
                modifier = Modifier
                    .padding(32.dp)
                    .fillMaxSize()
                    .background(Color.White, shape = RoundedCornerShape(8.dp))
            ) {
                Text(
                    text = "提交",
                    style = MaterialTheme.typography.button,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .height(48.dp)
                        .border(width = 3.dp, color = Color.Red, shape = RoundedCornerShape(8.dp))
                        .background(color = Color.Gray, shape = RoundedCornerShape(8.dp))
                        .align(Alignment.BottomCenter)
                        .clickable {
                            toast("提交")
                        }
                        .padding(12.dp)
                )
            }
        }
    }

    override fun initData() {
    }

    override fun getWidth(): Int {
        return MATCH_PARENT
    }

    override fun getHeight(): Int {
        return 280.dp2Px()
    }
}