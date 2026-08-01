import logging
from datetime import datetime

def setup_logger():
    logger = logging.getLogger("dashboard")
    logger.setLevel(logging.INFO)

    file_handler = logging.FileHandler("dashboard_log.txt", encoding="utf-8")
    file_handler.setLevel(logging.INFO)

    formatter = logging.Formatter("%(asctime)s | %(message)s", datefmt="%Y-%m-%d %H:%M:%S")
    file_handler.setFormatter(formatter)

    if not logger.handlers:
        logger.addHandler(file_handler)

    return logger

dashboard_logger = setup_logger()

def log_dashboard_data(data):
    dashboard_logger.info(
        f"status={data.driving_status} | "
        f"track={data.track_name} by {data.track_artist} | "
        f"playing={data.is_playing} | "
        f"temp={data.outside_temp_celsius}°C | "
        f"nav={data.nav_destination} | "
        f"time={data.nav_remaining_time_min}min | "
        f"dist={data.nav_remaining_distance_km:.1f}km"
    )