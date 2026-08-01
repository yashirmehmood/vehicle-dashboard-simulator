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
        Pair("Bohemian Rhapsody", "Queen"),
        Pair("Blinding Lights", "The Weeknd"),
        Pair("Starboy", "The Weeknd"),
        Pair("Levitating", "Dua Lipa"),
        Pair("Stay", "Justin Bieber")
    )
    private var currentTrackIndex = 0
    private val drivingStatuses = listOf("Parked", "Driving", "Charging")

    init {
        startSimulation()
    }

    private fun startSimulation() {
        viewModelScope.launch {
            var speed = 0
            var battery = 85
            var temp = 30
            var mediaProgress = 0f
            var statusIndex = 1 // start as Driving
            var isPlaying = true

            while (true) {
                delay(1000L)

                // Simulate speed changes
                speed = (speed + Random.nextInt(-10, 15)).coerceIn(0, 250)

                // Battery drains slowly
                if (statusIndex == 1) battery = (battery - 1).coerceAtLeast(0)
                if (statusIndex == 2) battery = (battery + 2).coerceAtMost(100)

                // Temperature slight variation
                temp = (temp + Random.nextInt(-1, 2)).coerceIn(-20, 50)

                // Media progress
                if (isPlaying) {
                    mediaProgress += 0.005f
                    if (mediaProgress >= 1f) {
                        mediaProgress = 0f
                        currentTrackIndex = (currentTrackIndex + 1) % tracks.size
                    }
                }

                // Driving status cycles slowly
                if (Random.nextInt(30) == 0) {
                    statusIndex = (statusIndex + 1) % drivingStatuses.size
                }

                // Nav: fake countdown
                val remainingTime = (30 - (mediaProgress * 30).toInt()).coerceAtLeast(0)
                val remainingDist = (15f - mediaProgress * 15f).coerceAtLeast(0f)

                _uiState.value = DashboardUiState(
                    speedKmh = speed,
                    batteryPercent = battery,
                    outsideTempCelsius = temp,
                    drivingStatus = drivingStatuses[statusIndex],
                    isPlaying = isPlaying,
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
        _uiState.value = _uiState.value.copy(isPlaying = !_uiState.value.isPlaying)
    }

    fun nextTrack() {
        currentTrackIndex = (currentTrackIndex + 1) % tracks.size
        _uiState.value = _uiState.value.copy(
            trackName = tracks[currentTrackIndex].first,
            trackArtist = tracks[currentTrackIndex].second,
            mediaProgressPercent = 0f
        )
    }

    fun prevTrack() {
        currentTrackIndex = if (currentTrackIndex == 0) tracks.size - 1 else currentTrackIndex - 1
        _uiState.value = _uiState.value.copy(
            trackName = tracks[currentTrackIndex].first,
            trackArtist = tracks[currentTrackIndex].second,
            mediaProgressPercent = 0f
        )
    }
}