package com.yashir.vehicledashboard.ui.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.yashir.vehicledashboard.ui.dashboard.components.*


@Preview(
    showBackground = true,
    widthDp = 1280,
    heightDp = 800,
    name = "Tablet Dashboard"
)
@Composable
fun DashboardScreen(viewModel: DashboardViewModel = viewModel()) {
    val state by viewModel.uiState.collectAsState()
    val bgColor = Color(0xFF0D0D0D)
    val cardColor = Color(0xFF1A1A2E)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(bgColor)
    ) {
        // Top status bar
        StatusBar(
            drivingStatus = state.drivingStatus,
            outsideTempCelsius = state.outsideTempCelsius
        )

        HorizontalDivider(color = Color(0xFF2A2A2A), thickness = 1.dp)

        // Main content area
        Row(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            // Left: Navigation
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .background(cardColor)
            ) {
                NavPanel(
                    destination = state.navDestination,
                    remainingTimeMin = state.navRemainingTimeMin,
                    remainingDistanceKm = state.navRemainingDistanceKm
                )
            }

            HorizontalDivider(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(1.dp),
                color = Color(0xFF2A2A2A)
            )

            // Center: Speed
            Box(
                modifier = Modifier
                    .weight(2f)
                    .fillMaxHeight()
                    .background(bgColor)
            ) {
                SpeedPanel(speedKmh = state.speedKmh)
            }

            HorizontalDivider(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(1.dp),
                color = Color(0xFF2A2A2A)
            )

            // Right: Battery
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .background(cardColor)
            ) {
                BatteryPanel(batteryPercent = state.batteryPercent)
            }
        }

        HorizontalDivider(color = Color(0xFF2A2A2A), thickness = 1.dp)

        // Bottom: Media
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(cardColor)
        ) {
            MediaPanel(
                isPlaying = state.isPlaying,
                trackName = state.trackName,
                trackArtist = state.trackArtist,
                progressPercent = state.mediaProgressPercent,
                onPlayPause = { viewModel.togglePlayPause() },
                onNext = { viewModel.nextTrack() },
                onPrev = { viewModel.prevTrack() }
            )
        }
    }
}