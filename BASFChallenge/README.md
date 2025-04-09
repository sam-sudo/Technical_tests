# BASFChallenge

## Description
Android application in Kotlin that monitors in real-time:
- **Network status**: Wi-Fi, mobile data, signal strength, and SSID.
- **Battery status**: Percentage, temperature, and charging state.

## Dependencies
  - Kotlin
  - Jetpack Compose
  - Hilt for dependency injection
  - Material Icons Extended (for Wi-Fi and battery icons)

## Execution
1. Connect a device or launch an emulator (API >= 21).
2. Run the project from Android Studio using the **Run** button.

## Notes
- To observe battery changes, plug or unplug the charger or force a percentage change.
- For network status, enable/disable Wi-Fi or mobile data; the UI will update in real time.
