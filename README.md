# KMP Mobile Request Tracker

A Kotlin Multiplatform (KMP) project demonstrating a request tracking system with shared business logic and native UI.

## Architecture

This project follows **Clean Architecture** principles and a **Layered Architecture** to maximize code sharing while maintaining native UI flexibility.

### Modules
- **`:sharedLogic`**: Contains the core business logic.
    - **Domain Layer**: Models, Repository Interfaces, and Use Cases.
    - **Data Layer**: Repository implementations and Mock Data Source.
    - **Presentation Layer**: Shared ViewModels using `androidx.lifecycle.ViewModel` (KMP) and UI State/Event models.
- **`:androidApp`**: Native Android application using Jetpack Compose. Consumes shared ViewModels and state.
- **`:iosApp`**: Native iOS application using SwiftUI. Consumes shared logic (to be fully implemented).

### Key Features
- **Shared ViewModels**: Business logic for UI state management is written once in `commonMain`.
- **RTL Support**: Native Android resources used for full Arabic support and layout mirroring.
- **State Management**: Handling of Loading, Success, Error (with Retry), and Empty states.
- **Unit Testing**: Tests for shared Use Cases and failure paths.

## Tech Stack
- **Kotlin Multiplatform**
- **Jetpack Compose** (Android)
- **SwiftUI** (iOS)
- **Coroutines & Flow** for asynchronous data handling.
- **Kotlinx Datetime** for multiplatform date handling.
- **AndroidX Lifecycle ViewModel** (KMP version).

## Run Instructions

### Android
1. Open the project in Android Studio.
2. Select `androidApp` run configuration.
3. Run on an emulator or physical device.

### iOS
1. Navigate to the `iosApp` folder.
2. Open `iosApp.xcodeproj` in Xcode.
3. Build and run.

## Assumptions & Tradeoffs
- **Mock Data**: A `MockRequestRepository` is used to simulate network calls with artificial delays and mock objects.
- **Navigation**: Simple state-based navigation is used in the Android app to keep the scope focused on KMP logic sharing.
- **Resources**: Standard native resources are used for localization to ensure full platform-specific feature support (like RTL mirroring).

## Screenshots

![App Screenshot](.screenshot/Screenshot_20260903_235903.png)
![App Screenshot](.screenshot/Screenshot_20260903_235954.png)
![App Screenshot](.screenshot/Screenshot_20260904_000027.png)
![App Screenshot](.screenshot/Screenshot_20260904_000042.png)

