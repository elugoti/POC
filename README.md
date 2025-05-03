# Android POC

A modern Android application demonstrating best practices in Android development using Jetpack Compose, Hilt, MVVM architecture, and Clean Architecture principles.

## Features

- **Authentication Flow**: Login and Signup screens with form validation
- **Country List**: Display a list of countries fetched from a remote API
- **Country Details**: View detailed information about a selected country
- **Modern UI**: Built entirely with Jetpack Compose

## Architecture

This project follows Clean Architecture principles with a clear separation of concerns:

### Layers

- **Presentation Layer**: Contains UI components (Compose), ViewModels, and UI state management
- **Domain Layer**: Contains business logic, use cases, and domain models
- **Data Layer**: Handles data operations, repository implementations, and remote data sources

### Key Components

- **MVVM Pattern**: ViewModels manage UI state and business logic
- **Dependency Injection**: Hilt for dependency injection
- **Navigation**: Jetpack Navigation Compose for screen navigation
- **Network**: Retrofit and OkHttp for API communication
- **Concurrency**: Kotlin Coroutines for asynchronous operations

## Tech Stack

- **UI**: Jetpack Compose with Material 3
- **Architecture**: MVVM + Clean Architecture
- **Dependency Injection**: Hilt
- **Networking**: Retrofit, OkHttp
- **Concurrency**: Kotlin Coroutines
- **Image Loading**: Coil
- **Testing**: JUnit, Mockito

## Project Structure

```
app/src/main/java/com/capgemini/androidpoc/
├── di/                  # Dependency Injection modules
├── data/                # Data layer
│   ├── remote/          # Remote data sources and API interfaces
│   └── repository/      # Repository implementations
├── domain/              # Domain layer
│   ├── model/           # Domain models
│   ├── repository/      # Repository interfaces
│   ├── usecase/         # Use cases
│   └── exception/       # Domain exceptions
├── presentation/        # Presentation layer
│   ├── navigation/      # Navigation components
│   ├── screen/          # Compose UI screens
│   ├── state/           # UI state definitions
│   └── viewmodel/       # ViewModels
├── ui/                  # UI components and theme
└── utils/               # Utility classes and extensions
```

## Getting Started

### Prerequisites

- Android Studio Meerkat | 2024.3.1 or newer
- JDK 11 or higher
- Android SDK 35

### Setup

1. Clone the repository
2. Open the project in Android Studio
3. Sync Gradle files
4. Run the app on an emulator or physical device

## Development

### Build Variants

- **Debug**: Development build with logging enabled
- **Release**: Production-ready build with optimizations

### Testing

The project includes both unit tests and instrumentation tests:

- Unit tests: `./gradlew test`
- Instrumentation tests: `./gradlew connectedAndroidTest`

## License

[Add your license information here]

## Acknowledgements

- [List any libraries, resources, or inspirations]