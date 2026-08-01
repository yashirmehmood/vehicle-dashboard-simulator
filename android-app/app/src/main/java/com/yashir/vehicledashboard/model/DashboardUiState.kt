package com.yashir.vehicledashboard.model

data class DashboardUiState(
    val speedKmh: Int = 0,
    val batteryPercent: Int = 100,
    val outsideTempCelsius: Int = 30,
    val drivingStatus: String = "Parked",

    // Media
    val isPlaying: Boolean = false,
    val trackName: String = "No Track",
    val trackArtist: String = "",
    val mediaProgressPercent: Float = 0f,

    // Navigation
    val navDestination: String = "Home",
    val navRemainingTimeMin: Int = 0,
    val navRemainingDistanceKm: Float = 0f
)