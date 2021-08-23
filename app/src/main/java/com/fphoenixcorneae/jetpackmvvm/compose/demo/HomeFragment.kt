package com.fphoenixcorneae.jetpackmvvm.compose.demo

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.fphoenixcorneae.jetpackmvvm.base.fragment.BaseFragment

class HomeFragment:BaseFragment() {
    override fun initView() {
        setRealContent {
            Column {
                Text(
                    text = "弹窗",
                    style = MaterialTheme.typography.button,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .height(48.dp)
                        .border(width = 2.dp, color = Color.Red, shape = RoundedCornerShape(8.dp))
                        .background(color = Color.Gray, shape = RoundedCornerShape(8.dp))
                        .clickable {
                            SimpleDialog().show(this@HomeFragment)
                        }
                        .padding(12.dp)
                )
            }
        }
    }

    override fun initData() {
    }
}