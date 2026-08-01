package com.yashir.vehicledashboard.ui.dashboard.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun NavPanel(
    destination: String,
    remainingTimeMin: Int,
    remainingDistanceKm: Float,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "NAVIGATION",
            color = Color(0xFF888888),
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            letterSpacing = 3.sp
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = destination,
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "$remainingTimeMin min",
            color = Color(0xFF00E5FF),
            fontSize = 42.sp,
            fontWeight = FontWeight.Light
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = "${"%.1f".format(remainingDistanceKm)} km remaining",
            color = Color(0xFFAAAAAA),
            fontSize = 16.sp
        )
    }
}