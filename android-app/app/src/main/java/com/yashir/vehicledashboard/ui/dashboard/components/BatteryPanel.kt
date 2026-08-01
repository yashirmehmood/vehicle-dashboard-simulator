package com.yashir.vehicledashboard.ui.dashboard.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BatteryPanel(
    batteryPercent: Int,
    modifier: Modifier = Modifier
) {
    val batteryColor = when {
        batteryPercent > 50 -> Color(0xFF00E676)
        batteryPercent > 20 -> Color(0xFFFFB300)
        else -> Color(0xFFFF1744)
    }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "BATTERY",
            color = Color(0xFF888888),
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            letterSpacing = 3.sp
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "$batteryPercent%",
            color = batteryColor,
            fontSize = 52.sp,
            fontWeight = FontWeight.Light
        )
        Spacer(modifier = Modifier.height(16.dp))
        LinearProgressIndicator(
            progress = { batteryPercent / 100f },
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .height(10.dp)
                .clip(RoundedCornerShape(5.dp)),
            color = batteryColor,
            trackColor = Color(0xFF2A2A2A)
        )
    }
}