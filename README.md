# Vehicle Dashboard Simulator

This project is a take-home task submission consisting of two parts: an Android tablet application that simulates a vehicle dashboard, and a lightweight Python backend that receives and logs the data sent from the app.

The goal was to build something that looks and feels like a real automotive HMI — readable at a glance, logically structured, and cleanly implemented — within a 5-hour time budget.

## Repository Structure

    vehicle-dashboard-simulator/
    ├── android-app/        # Kotlin + Jetpack Compose Android project
    ├── python-backend/     # FastAPI Python backend
    ├── docs/               # Architecture diagram
    └── README.md

## How the two parts connect

The Android app simulates vehicle data internally (speed, battery, temperature, driving status, media, and navigation) and every 5 seconds sends the current state to the Python backend through an HTTP POST request. The backend validates the incoming data, logs it to a file with a timestamp, and responds with a confirmation. The app continues to work normally even if the backend is not running.

    Android App  ──── POST /dashboard/update (every 5s) ────►  Python Backend
    (Kotlin)                                                     (FastAPI)
                                                                      │
                                                                      ▼
                                                              dashboard_log.txt

## Running the project

### 1. Start the Python backend first

    cd python-backend
    python -m venv venv
    venv\Scripts\activate        # Windows
    pip install -r requirements.txt
    uvicorn main:app --reload --host 0.0.0.0 --port 8000

Once it is running, the API documentation and a manual testing interface are available at `http://localhost:8000/docs`.

### 2. Run the Android app

- Open the `android-app/` folder in Android Studio
- Create a tablet AVD in landscape orientation (API 34 or higher, 2560×1600)
- Run the app — it will start posting data to the backend automatically

## Sub-project documentation

Each part has its own README with the architecture decisions and known limitations explained in more detail:

- [Android App — README](android-app/README.md)
- [Python Backend — README](python-backend/README.md)