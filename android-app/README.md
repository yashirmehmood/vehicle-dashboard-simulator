# Vehicle Dashboard — Android App

This is the Android part of the task. It is a tablet application that simulates a vehicle dashboard and displays driving data in a clear, driver-friendly way. All data is simulated inside the app, so no real vehicle connection is required.

## What it shows

The dashboard is split into a few clearly separated areas:

- **Speed** in the center, drawn as a circular arc gauge (0–250 km/h)
- **Battery level** on the right as a percentage with a progress bar (0–100%)
- **Outside temperature** in the top status bar (–20°C to +50°C)
- **Driving status** in the top status bar (Parked, Driving, or Charging)
- **Navigation** on the left with destination, remaining time, and remaining distance
- **Media controller** at the bottom with track info, a progress bar, and play/pause/next/previous controls

## Architecture

I used the MVVM (Model-View-ViewModel) pattern, which is the standard approach for modern Android apps and keeps the UI cleanly separated from the logic.

    MainActivity
        └── DashboardScreen (Composable)
                ├── StatusBar     — driving status + temperature
                ├── NavPanel      — destination, time, distance
                ├── SpeedPanel    — circular arc speedometer (Canvas)
                ├── BatteryPanel  — battery % + progress bar
                └── MediaPanel    — track info + play controls
                        │
                DashboardViewModel
                        ├── DashboardUiState (single source of truth, exposed as StateFlow)
                        ├── Simulation loop (coroutine, updates data every second)
                        └── DashboardApi (Retrofit, posts data to the Python backend)

The whole UI reads from one `DashboardUiState` object. The ViewModel owns this state and is the only place where it is changed. The composables are just render functions, they display whatever the state currently holds and never manage data themselves. This makes the data flow easy to follow and avoids bugs where the UI and the logic get out of sync.

## Why I made these choices

**Jetpack Compose instead of XML layouts.** Compose is Google's recommended toolkit for new Android projects and its declarative style fits a reactive dashboard well. It also let me draw the circular speedometer directly with the `Canvas` API instead of pulling in a third-party charting library, and the built-in preview meant I could iterate on the UI without launching the emulator every time.

**MVVM with StateFlow.** This keeps the logic testable and survives screen rotation, so the simulation does not restart unexpectedly. StateFlow is observed natively by Compose, so there is no manual lifecycle handling.

**Retrofit for networking.** It is a type-safe HTTP client with very little boilerplate and works cleanly with Kotlin coroutines. The network call is wrapped in a try/catch, so if the backend is not running the app simply keeps working offline instead of crashing.

**Landscape and dark theme.** Real automotive displays are landscape and are never rotated, so the app is locked to landscape at the manifest level. The dark background with high-contrast white text and large font sizes follows the idea that the driver needs to read the important values at a single glance.

## How the data is simulated

All values are generated inside `DashboardViewModel` in a coroutine loop that runs once per second:

- Speed only changes while the status is Driving; it stays at zero when Parked or Charging
- Battery drains while Driving and increases while Charging
- Temperature drifts slowly within the –20°C to +50°C range
- Media progress advances while playing and moves to the next track when it reaches the end
- Driving status cycles through Parked → Driving → Charging roughly every 30 seconds

Every 5 seconds the current state is also sent to the Python backend.

## A note on the emulator networking

The backend runs on `localhost` of the development machine, but inside the Android emulator `localhost` refers to the emulator itself. Android provides the special alias `10.0.2.2` for this exact case, which maps to the host machine's localhost, so that is the address the app posts to.

## Known limitations

- There is no real vehicle signal integration (no CAN bus or Android Automotive Vehicle HAL); everything is simulated.
- The connection uses plain HTTP, which is fine for a local demo but would need HTTPS in production.
- The speed values are random and do not model realistic acceleration.
- The media track list is hardcoded; there is no real media library.
- The app was only tested on the tablet emulator, not on physical hardware.
- I deliberately did not use Android Automotive OS (AAOS) to keep the scope realistic for the time budget. For a production version, AAOS would be the natural next step.

## Libraries used

| Library | Purpose |
|---|---|
| Jetpack Compose | Declarative UI |
| Material 3 | Design components |
| Lifecycle ViewModel Compose | ViewModel integration with Compose |
| Material Icons Extended | Media control icons |
| Retrofit 2 + Gson | HTTP client and JSON serialization |