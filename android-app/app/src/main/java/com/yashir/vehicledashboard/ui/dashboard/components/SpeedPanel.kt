package com.yashir.vehicledashboard.ui.dashboard.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun SpeedPanel(
    speedKmh: Int,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "$speedKmh",
            color = Color.White,
            fontSize = 120.sp,
            fontWeight = FontWeight.Thin,
            lineHeight = 120.sp
        )
        Text(
            text = "km/h",
            color = Color(0xFF00E5FF),
            fontSize = 22.sp,
            fontWeight = FontWeight.Medium,
            letterSpacing = 4.sp
        )
    }
}