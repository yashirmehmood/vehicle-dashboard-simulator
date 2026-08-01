from pydantic import BaseModel

class DashboardData(BaseModel):
    is_playing: bool
    track_name: str
    track_artist: str
    outside_temp_celsius: int
    driving_status: str
    nav_destination: str
    nav_remaining_time_min: int
    nav_remaining_distance_km: float

class DashboardResponse(BaseModel):
    status: str
    received: DashboardData