package com.yashir.vehicledashboard.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yashir.vehicledashboard.model.DashboardUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

class DashboardViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()

    private val tracks = listOf(
        Pair("Stay", "Justin Bieber"),
        Pair("Aadat", "Atif Aslam"),
        Pair("Blinding Lights", "The Weeknd"),
        Pair("O re Piya", "Rahat"),
        Pair("Levitating", "Dua Lipa")
    )

    private val drivingStatuses = listOf("Parked", "Driving", "Charging")

    // Class-level variables — not inside the loop
    private var currentTrackIndex = 0
    private var speed = 0
    private var battery = 85
    private var temp = Random.nextInt(15, 28)
    private var mediaProgress = 0f
    private var statusIndex = 1
    private var statusTickCounter = 0

    init {
        startSimulation()
    }

    private fun startSimulation() {
        viewModelScope.launch {
            while (true) {
                delay(1000L)

                val currentStatus = drivingStatuses[statusIndex]

                // Speed
                speed = when (currentStatus) {
                    "Driving" -> (speed + Random.nextInt(-10, 15)).coerceIn(0, 250)
                    else -> 0
                }

                // Battery
                battery = when (currentStatus) {
                    "Driving" -> (battery - 1).coerceAtLeast(0)
                    "Charging" -> (battery + 2).coerceAtMost(100)
                    else -> battery
                }

                // Temperature
                temp = (temp + listOf(-1, 0, 0, 1).random()).coerceIn(-20, 50)

                // Media progress — reads isPlaying from state
                if (_uiState.value.isPlaying) {
                    mediaProgress += 0.005f
                    if (mediaProgress >= 1f) {
                        mediaProgress = 0f
                        currentTrackIndex = (currentTrackIndex + 1) % tracks.size
                    }
                } else {
                    // Sync mediaProgress from state in case user skipped track
                    mediaProgress = _uiState.value.mediaProgressPercent
                }

                // Status cycle every 30 seconds
                statusTickCounter++
                if (statusTickCounter >= 30) {
                    statusTickCounter = 0
                    statusIndex = (statusIndex + 1) % drivingStatuses.size
                    speed = 0
                }

                // Nav countdown
                val remainingTime = (30 - (mediaProgress * 30).toInt()).coerceAtLeast(0)
                val remainingDist = (15f - mediaProgress * 15f).coerceAtLeast(0f)

                _uiState.value = _uiState.value.copy(
                    speedKmh = speed,
                    batteryPercent = battery,
                    outsideTempCelsius = temp,
                    drivingStatus = currentStatus,
                    trackName = tracks[currentTrackIndex].first,
                    trackArtist = tracks[currentTrackIndex].second,
                    mediaProgressPercent = mediaProgress,
                    navDestination = "Stuttgart Hauptbahnhof",
                    navRemainingTimeMin = remainingTime,
                    navRemainingDistanceKm = remainingDist
                )
            }
        }
    }

    fun togglePlayPause() {
        _uiState.value = _uiState.value.copy(
            isPlaying = !_uiState.value.isPlaying
        )
    }

    fun nextTrack() {
        currentTrackIndex = (currentTrackIndex + 1) % tracks.size
        mediaProgress = 0f
        _uiState.value = _uiState.value.copy(
            trackName = tracks[currentTrackIndex].first,
            trackArtist = tracks[currentTrackIndex].second,
            mediaProgressPercent = 0f
        )
    }

    fun prevTrack() {
        currentTrackIndex = if (currentTrackIndex <= 0) tracks.size - 1 else currentTrackIndex - 1
        mediaProgress = 0f
        _uiState.value = _uiState.value.copy(
            trackName = tracks[currentTrackIndex].first,
            trackArtist = tracks[currentTrackIndex].second,
            mediaProgressPercent = 0f
        )
    }
}