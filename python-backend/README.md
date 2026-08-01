# Vehicle Dashboard — Python Backend

This is the backend part of the task. It is a small FastAPI service that receives the simulated dashboard data sent from the Android app, logs it to a file with a timestamp, and can return the most recent state it received. It does not do any real vehicle processing; its job is only to receive, log, and report back.

## What it does

- Exposes an endpoint that receives the dashboard data from the Android app
- Validates the incoming data automatically before accepting it
- Logs every received update to a file with a timestamp
- Keeps the latest received state in memory and exposes it through a separate endpoint for inspection

## Endpoints

| Method | Path | Description |
|---|---|---|
| GET | `/` | Simple health check to confirm the server is running |
| POST | `/dashboard/update` | Receives dashboard data from the app, logs it, and returns a confirmation |
| GET | `/dashboard/state` | Returns the most recent data the server received |

## Project files

    python-backend/
    ├── main.py            # FastAPI app and the endpoints
    ├── models.py          # Pydantic models for request and response
    ├── logger.py          # Logging setup and log formatting
    ├── requirements.txt   # Dependencies
    └── dashboard_log.txt  # Created automatically when data is received

## Why I made these choices

**FastAPI.** I chose FastAPI because it is lightweight and fits this task well. It validates incoming data automatically through Pydantic models, so if the app ever sends a wrong data type the request is rejected with a clear error instead of failing silently. It also generates an interactive documentation page at `/docs`, which means the API can be tested directly in the browser without any extra tool like Postman. This made it easy to verify the endpoint before connecting the Android app.

**Python's built-in logging module.** For the logging requirement I used the standard `logging` module instead of writing to the file manually. It handles the timestamp, the file writing, and the formatting on its own, so there is no need for an extra dependency. I also added a small guard so that the log handler is not added twice when the module reloads, which would otherwise cause duplicate log lines.

**Keeping the state in memory.** The latest received state is simply stored in a variable. For a demo this is enough and keeps the service simple. It does mean the state is lost if the server restarts, which I note under the limitations.

## Running the backend

    python -m venv venv
    venv\Scripts\activate        # Windows
    pip install -r requirements.txt
    uvicorn main:app --reload --host 0.0.0.0 --port 8000

Once it is running, the interactive API documentation is available at `http://localhost:8000/docs`.

The server is started with `--host 0.0.0.0` so that it is reachable from the Android emulator, which reaches the host machine through the address `10.0.2.2`.

## Log format

Every update received from the app is written to `dashboard_log.txt` with a timestamp:

    2026-08-01 17:30:00 | status=Driving | track=Bohemian Rhapsody by Queen | playing=True | temp=22°C | nav=Stuttgart Hauptbahnhof | time=27min | dist=13.3km

## Known limitations

- The latest state is only kept in memory, so it is lost when the server restarts.
- There is no authentication; the API is open and meant only for local use.
- The app sends data by posting every 5 seconds. A WebSocket connection would be more efficient for continuous data, but for this task the simple polling approach is enough.
- The log is written to a single file with no rotation. For long-running use, a rotating file handler would be better.