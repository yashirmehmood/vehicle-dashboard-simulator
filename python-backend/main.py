from fastapi import FastAPI
from models import DashboardData, DashboardResponse
from logger import log_dashboard_data

app = FastAPI(
    title="Vehicle Dashboard API",
    description="Receives and logs simulated vehicle dashboard data from the Android app.",
    version="1.0.0"
)

# In-memory store of latest dashboard state
latest_data: DashboardData | None = None

@app.get("/")
def root():
    return {"message": "Vehicle Dashboard API is running."}

@app.post("/dashboard/update", response_model=DashboardResponse)
def update_dashboard(data: DashboardData):
    global latest_data
    latest_data = data
    log_dashboard_data(data)
    return DashboardResponse(status="ok", received=data)

@app.get("/dashboard/state")
def get_state():
    if latest_data is None:
        return {"status": "no data received yet"}
    return {"status": "ok", "data": latest_data}