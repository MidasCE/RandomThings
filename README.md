# 📸 Random Things - The Random Image Gallery App

<img width="320" height="714" alt="Screenshot_20251112_144017" src="https://github.com/user-attachments/assets/b60c9c9a-89d5-4359-9273-c6f228e08899" />

<img width="320" height="714" alt="Screenshot_20251112_144035" src="https://github.com/user-attachments/assets/324d4dbd-ec3e-47ff-9d4e-e46b6492e627" />

<img width="320" height="714" alt="Screenshot_20251112_144456" src="https://github.com/user-attachments/assets/9a049d3c-6e28-4d4e-b079-d6ea8b3b2288" />



An elegant and modern Android application built with Jetpack Compose, showcasing a clean architecture, asynchronous programming with Kotlin Coroutines and Flows, robust unit testing, and efficient network communication with Retrofit. This app allows users to browse random images, discover memes, and enjoy dad jokes.

## ✨ Features

* **Home Tab:** Browse an endless stream of random, high-quality images with author details.
* **Favourites Tab:** Keep track of your most loved images by tapping the heart icon. All favourited images are persisted and accessible here.
* **Meme Tab:** Get a fresh dose of humor with random memes fetched from a public API. Swipe to refresh for new content!
* **Joke Tab:** Lighten the mood with dad jokes! Enter a keyword, and the app will fetch jokes related to your search.
* **Responsive UI:** Beautifully designed and adaptive user interface built with Jetpack Compose.
* **Offline Support (Favourites):** Favourited images are locally stored for offline viewing.

## 🚀 Technologies Used

* **Kotlin:** The primary language for Android development.
* **Jetpack Compose:** Modern toolkit for building native Android UI.
* **Kotlin Coroutines & Flow:** For asynchronous operations, background processing, and reactive streams.
* **Retrofit:** Type-safe HTTP client for making network requests to various APIs.
* **Hilt:** For dependency injection (optional, but highly recommended for this architecture).
* **Room:** For local data persistence (for favourite images).
* **Unit Testing:**
    * **JUnit 4/5:** Testing framework.
    * **mockk:** For mocking dependencies in unit tests.
    * **Coroutines Test:** For testing suspend functions and flows.
    * **Compose Test Rule:** or testing Jetpack Compose UI components and flows.
    * **Hilt Android Testing:** For managing dependencies in instrumentation tests.
* **Architecture:** Adhering to a modern MVVM (Model-View-ViewModel) pattern with a clear separation of concerns (UI, ViewModel, UseCases/Interactors, Repository, Data Sources).

## 🏗️ Architecture


The app follows a layered architectural approach to ensure scalability, maintainability, and testability:

* **UI Layer (Presentation):** Built with Jetpack Compose. Observes `StateFlow` from ViewModels and dispatches `Intents`/events.
* **ViewModel Layer:** Holds UI-related data and state, exposing them to the UI. Orchestrates data fetching and business logic via Use Cases. Integrates with Kotlin Flows for reactive data streams.
* **Domain Layer (Use Cases/Interactors):** Contains the core business logic. These are single-responsibility classes that encapsulate specific operations (e.g., `ImageContentUseCase`). They operate on data from repositories.
* **Data Layer (Repository & Data Sources):**
    * **Repository:** Abstracts data sources, providing a clean API to the domain layer. Responsible for deciding whether to fetch data from network or local storage.
    * **Data Sources:**
        * **Remote Data Source:** Handles API calls using Retrofit (e.g., , `MemeApiService`, `DadJokeApiService`).
        * **Local Data Source:** Manages local data persistence using Room (e.g., `FavouriteImageDao`).

## 🤝 Contributing

Contributions are welcome! If you have any suggestions, bug reports, or want to contribute code, please feel free to open an issue or submit a pull request.

## 🙏 Acknowledgements

* [Lorem Picsum](https://picsum.photos/) for random images.
* [Meme API](https://meme-api.com/gimme) for random memes.
* [icanhazdadjoke API](https://icanhazdadjoke.com/api) for dad jokes.
* The Android Developers community for excellent resources and documentation.
