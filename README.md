# Flimix TV

Android TV (OTT) app for browsing and streaming movies. Built with Jetpack Compose for TV, Material3, and Media3 ExoPlayer.

## Features

- **Movie list** – Grid of movies with thumbnails and titles (D-pad focused)
- **Movie detail** – Cover, title, year, genre, description, and Play Now (D-pad focused)
- **Video player** – Full-screen playback with on-screen controls (play/pause, ±10s seek). D-pad Down shows/hides controls; Back closes controls first, then exits to detail
- **TV-friendly UX** – Clear focus borders (blue) on all interactive elements; Flimix branding with blue app card in launcher

## Tech stack

- Kotlin, Jetpack Compose (TV), Material3 for TV  
- Navigation Compose, ViewModel, StateFlow  
- Retrofit + Gson, Coil  
- Media3 ExoPlayer (HLS)

## Build and run

1. Open the project in Android Studio.
2. Sync Gradle and connect an Android TV device or emulator (API 21+, leanback).
3. Run the app (e.g. Run → Run 'app').

```bash
./gradlew assembleDebug
```

APK output: `app/build/outputs/apk/debug/app-debug.apk`

## Project structure

- `app/src/main/java/.../` – UI (splash, home, detail, player), navigation, ViewModel, data layer, config  
- `app/src/main/res/` – themes, strings, drawables (e.g. TV banner)

## License

Private / project use.
