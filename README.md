# GainsApp

GainsApp is a fitness application designed to help users create and follow workout plans. The app supports various training types, including strength training, calisthenics, and CrossFit. Users can view exercise details, follow workout instructions, and track their progress.


![Screenshot from 2024-09-02 16-39-37](https://github.com/user-attachments/assets/cb880eef-15e2-4583-9aee-255ad773791b)
![Screenshot from 2024-09-02 16-39-48](https://github.com/user-attachments/assets/99581cfb-db38-4ad3-8529-33c1de694200)
![Screenshot from 2024-09-02 16-40-01](https://github.com/user-attachments/assets/1686f11f-5616-4f5a-8a66-258c14db9e4c)

## Features

- **Workout Plans**: Generate random workout plans based on training type and muscle groups.
- **Exercise Details**: View exercise descriptions, GIFs, and videos.
- **Timer and Sets**: Track exercise time, sets, and rest periods.
- **Music Integration**: Control music playback during workouts.
- **Video Playback**: Watch exercise demonstration videos.

## Technologies Used

- **Kotlin**: Main programming language for the app.
- **Jetpack Compose**: UI framework for building the app's user interface.
- **Hilt**: Dependency injection library.
- **Navigation Component**: For handling navigation within the app.
- **Gradle**: Build automation tool.

## Project Structure

- `app/src/com/project/gains/data/`: Contains data classes and data-related logic.
- `app/src/com/project/gains/presentation/`: Contains UI components and view models.
- `app/src/com/project/gains/theme/`: Contains theming and styling resources.

## Getting Started

### Prerequisites

- Android Studio Iguana | 2023.2.1 Patch 1
- Android device or emulator running Android 5.0 (Lollipop) or higher

### Installation

1. Clone the repository:
    ```sh
    git clone https://github.com/GabrieleLerani/GainsApp.git
    ```
2. Open the project in Android Studio.
3. Sync the project with Gradle files.
4. Run the app on an emulator or physical device.

## Usage

1. **Generate Workout Plan**: Select a training type and muscle groups to generate a random workout plan.
2. **Follow Workout**: View exercise details, start the timer, and follow the workout instructions.
3. **Control Music**: Use the music controls to play, pause, or skip songs during your workout.
4. **Watch Videos**: Tap on the exercise image to watch a demonstration video.

## Contributing

Contributions are welcome! Please follow these steps:

1. Fork the repository.
2. Create a new branch (`git checkout -b feature/your-feature`).
3. Commit your changes (`git commit -m 'Add some feature'`).
4. Push to the branch (`git push origin feature/your-feature`).
5. Open a pull request.
