package com.yashir.vehicledashboard.network

data class DashboardRequest(
    val is_playing: Boolean,
    val track_name: String,
    val track_artist: String,
    val outside_temp_celsius: Int,
    val driving_status: String,
    val nav_destination: String,
    val nav_remaining_time_min: Int,
    val nav_remaining_distance_km: Float
)

data class DashboardResponse(
    val status: String
)