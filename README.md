This project is a **single screen, single activity** input form built using **Jetpack Compose**. It follows the **MVVM architecture** pattern and saves user details (Name, Age, DOB, and Address) in a local database using **Room**. The project demonstrates the complete flow from the **ViewModel** to the **Repository** for database operations.

## Features
- **Modern UI**: Built entirely with Jetpack Compose.
- **Room Database**: Stores user information locally.
- **MVVM Architecture**: Clean separation of concerns.
- **Dagger Hilt**: Used for dependency injection.
- **State Management**: Uses `StateFlow` for observing data changes.
- **Date Picker**: Integrated date picker for selecting DOB.

## Tech Stack
- **Kotlin**
- **Jetpack Compose** 
- **Room Database**
- **Dagger Hilt** for dependency injection
- **StateFlow** for reactive UI updates

## Project Structure

- core              # Constants and utilities
- data              # Room database entities and DAOs
- domain            # Model classes
- presentation      # UI layer (Jetpack Compose)
- di                # Dependency Injection setup using Hilt
- ViewModel         # ViewModel layer

Prerequisites
Android Studio Giraffe (or later)
Kotlin 1.9+
Gradle 8+
Installation
Clone the repository:
git clone: https://github.com/vansh-tandon/EkaCareAndroidAssignment.git
Open the project in Android Studio.
Sync the project with Gradle files.
Build and run on an emulator or physical device.
Dependencies
The project leverages modern libraries for a robust implementation:

- Jetpack Compose (Material3, UI, and tooling)
- Room Database
- Dagger Hilt
- Lifecycle Components (ViewModel and Runtime)
- Key Dependencies in build.gradle.kts

Usage
- Launch the app.
- Fill out the form with Name, Age, DOB, and Address.
- Submit the form to save the details in the local database.
- A snackbar message confirms the saved information.
  
Architecture
MVVM Pattern: Ensures a clear separation of UI, business logic, and data handling layers.
Dagger Hilt: Manages dependency injection, promoting clean code and testability.
APK Download
Download the APK

License
This project is licensed under the MIT License. See the LICENSE file for details.

