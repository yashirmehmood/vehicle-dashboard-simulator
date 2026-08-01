package com.yashir.vehicledashboard.ui.dashboard.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun StatusBar(
    drivingStatus: String,
    outsideTempCelsius: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Color(0xFF1A1A2E))
            .padding(horizontal = 24.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = drivingStatus.uppercase(),
            color = when (drivingStatus) {
                "Driving" -> Color(0xFF00E5FF)
                "Charging" -> Color(0xFF00E676)
                else -> Color(0xFFFFB300)
            },
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 3.sp
        )
        Text(
            text = "$outsideTempCelsius °C",
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium
        )
    }
}