package com.fphoenixcorneae.jetpackmvvm.compose.demo

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.fphoenixcorneae.jetpackmvvm.base.activity.BaseActivity

class MainActivity : BaseActivity() {

    override fun initView() {
        setRealContent { themeState ->
            Text(
                text = "提交",
                style = MaterialTheme.typography.button,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(16.dp)
                    .padding(8.dp)
                    .background(color = Color.Gray)
                    .fillMaxWidth()
                    .height(48.dp)
                    .border(width = 3.dp, color = Color.Red, shape = RoundedCornerShape(8.dp))
                    .clickable {
                        themeState.value = themeState.value.copy(statusBarColor = Color.Red)
                    }
            )
        }
    }

    override fun initData() {
    }

}