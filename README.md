# Random Things

A modern Android app built with Jetpack Compose, featuring four uniquely themed screens for discovering photos, browsing memes, saving favourites, and searching dad jokes.

## Screenshots

| Discovery | Daily Memes Stack | My Collection | Dad Joke Hub |
|:---------:|:-----------------:|:-------------:|:------------:|
| <img width="180" src="https://github.com/user-attachments/assets/f7f57d71-bee7-40c8-a54f-a420d93d59cc"> | <img width="180" src="https://github.com/user-attachments/assets/57689a5f-92b3-45e2-a91d-67eb08cb57d1"> | <img width="180" src="https://github.com/user-attachments/assets/82e8bea7-da06-437b-8ecc-bb7c781b1fa8"> | <img width="180" src="[https://github.com/user-attachments/assets/f7f57d71-bee7-40c8-a54f-a420d93d59cc](https://github.com/user-attachments/assets/cd580154-1b30-4731-a5db-5090a240aad9)"> |

> To get image URLs: drag each screenshot into any GitHub issue or PR comment box, copy the generated `https://github.com/user-attachments/...` URL, then paste it above.

## Features

- **Discovery (Home):** Endless scroll of high-quality random photos. The first item is a large featured card with author credit and a "New!" badge; subsequent items appear as discovery cards. Pull-to-refresh for fresh content.
- **Daily Memes Stack:** Dark-themed meme viewer with a 3D card stack effect. Tap the red heart FAB to favourite; tap the purple refresh FAB or pull-to-refresh for a new meme.
- **My Collection (Favourites):** Warm beige collection screen with Photos and Memes tabs. Favourited photos are displayed in a Pinterest-style staggered grid with natural aspect ratios. Unfavouriting dims the item instead of removing it, so you can re-favourite without losing your place.
- **Dad Joke Hub:** Sky blue gradient search screen. A "Joke of the Day" card is shown on load; search by keyword to get chat-bubble style results in four alternating colours; tap "Randomize Joke" to refresh the daily joke.

## Tech Stack

- **Language:** Kotlin
- **UI:** Jetpack Compose + Material3
- **Architecture:** MVVM with Clean Architecture (UI → Domain → Data)
- **Async:** Kotlin Coroutines & Flow (`StateFlow`, `MutableStateFlow`, `mutableStateListOf`)
- **DI:** Hilt
- **Networking:** Retrofit + Moshi
- **Local storage:** Room (favourites persistence)
- **Image loading:** Coil
- **Testing:** JUnit 4, MockK, Coroutines Test (`StandardTestDispatcher`, `runTest`)

## Architecture

```
presentation/        # Compose screens + ViewModels (MVVM)
domain/              # Use cases + entity models
data/
  remote/            # Retrofit APIs
  local/             # Room DAOs + entities
  repository/        # Bridges remote ↔ local ↔ domain
```

The domain layer is fully decoupled from Android — use cases operate only on plain Kotlin entities and are unit-tested without the framework.

## APIs

- [Lorem Picsum](https://picsum.photos/) — random photos
- [Meme API](https://meme-api.com/gimme) — random memes
- [icanhazdadjoke](https://icanhazdadjoke.com/api) — dad jokes

## Running Tests

```bash
./gradlew test
```

Key test files:
- `ImageContentUsecaseImplTest` — use case mapping and favourite logic
- `JokesContentUsecaseImplTest` — joke search mapping and pagination
- `RandomThingViewModelTest` — pagination guards, refresh, and reactive favourite updates
- `FavouriteViewModelTest` — soft-unfavourite in-memory state
- `JokesViewModelTest` — joke-of-the-day and search debounce
