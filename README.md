# MovieTracker 🎬

**MovieTracker** is a modern Android application built with **Kotlin**, **Jetpack Compose**, **Clean Architecture**, and an **Offline-First** reactive data flow. It consumes the [TVMaze API](https://www.tvmaze.com/api) to display popular TV shows, detailed show information, and allows users to manage their favorite shows locally with offline access.

---

## 🏗️ Architecture & Key Concepts

The project strictly follows **Clean Architecture** principles separated across a **Multi-Module** Gradle project:

```
UI (Compose) ──> ViewModel ──> UseCase ──> Repository
                                              │
                                              ├──> Retrofit (Remote Data Source)
                                              └──> Room DB (Local Data Source) ──> UI Flow
```

### 📱 Offline-First Single Source of Truth (SSOT)
1. **Room Database as SSOT**: The Compose UI **always** renders data directly observed from the local Room database via Kotlin `Flow`. The UI never displays raw network responses directly.
2. **Background Network Sync**: Remote network calls via Retrofit run asynchronously in the background and perform `@Upsert` batch operations directly into the local Room database.
3. **Reactive UI Emissions**: When Room database records are inserted or updated, Room automatically emits the new state through Kotlin `Flow` streams, updating the Jetpack Compose UI reactively.
4. **Graceful Offline Degradation**: If the network is unavailable or a fetch fails, cached database records remain fully visible while an offline banner informs the user.

---

## 🛠️ Tech Stack & Libraries

- **UI & Design**: [Jetpack Compose](https://developer.android.com/jetpack/compose), [Material 3](https://m3.material.io/), [Coil Compose](https://coil-kt.github.io/coil/compose/) for image loading.
- **Architecture**: Multi-Module Clean Architecture, MVVM, Use Cases, Unidirectional Data Flow (UDF).
- **Asynchronous Programming**: [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html) & [Kotlin Flow](https://kotlinlang.org/docs/flow.html) (`StateFlow`, `combine`, `collectAsStateWithLifecycle`).
- **Dependency Injection**: [Hilt (Dagger)](https://developer.android.com/training/dependency-injection/hilt-android).
- **Local Database**: [Room Database](https://developer.android.com/training/data-storage/room) with KSP compiler, TypeConverters, and `@Upsert` support.
- **Networking**: [Retrofit 2](https://square.github.io/retrofit/) & Gson Converter.
- **Navigation**: [Jetpack Navigation Compose](https://developer.android.com/guide/navigation/navigation-compose).
- **Build System**: Gradle with Kotlin DSL (`.gradle.kts`), Kotlin Symbol Processing (KSP).

---

## 📦 Project Modules

The application is structured into modular layers to maintain separation of concerns:

- **`:app`**: Application entry point, Hilt DI setup (`DatabaseModule`, `NetworkModule`, `RepositoryModule`), Navigation Graph, and primary screens (`ShowsScreen`, `ShowDetailsScreen`).
- **`:domain`**: Pure Kotlin module containing business models (`Show`), repository interfaces (`ShowRepository`), and Use Cases (`GetShowsUseCase`, `GetShowDetailsUseCase`, `RefreshShowsUseCase`, `GetFavoriteShowsUseCase`, `ToggleFavoriteUseCase`, `IsFavoriteUseCase`).
- **`:data`**: Implements domain repository interfaces (`ShowRepositoryImpl`), data synchronization, and DTO/Entity/Domain mappers.
- **`:database`**: Local Room database configuration (`AppDatabase`), entities (`ShowEntity`, `FavoriteShowEntity`), and DAOs (`ShowDao`, `FavoriteDao`).
- **`:network`**: Retrofit client, API service definition (`TvMazeApi`), and DTO models (`ShowDto`).
- **`:favorites`**: Dedicated feature module containing the Favorites UI (`FavoritesScreen`, `FavoritesViewModel`).
- **`:movies`**, **`:details`**, **`:common`**: Feature and core common utility libraries.

---

## ✨ Features

- **Shows Explorer**: Browse a list of TV shows with high-quality poster imagery, language badges, and genre tags.
- **Pull-to-Refresh**: Refresh data seamlessly using Material 3 `PullToRefreshBox` gestures.
- **Show Details**: Detailed show overview, language, genres, backdrop, and poster images.
- **Favorites Management**: Toggle shows as favorites directly from the list, details screen, or dedicated Favorites screen.
- **Offline Persistence**: Full offline access to cached shows and user favorites.

---

## 🚀 Getting Started

### Prerequisites
- **Android Studio**: Ladybug / 2024.2+ (or latest stable release)
- **JDK**: Java 11 or Java 17
- **Min SDK**: 24 (Android 7.0)
- **Compile / Target SDK**: 37

### Building & Running the Project

1. **Clone the repository**:
   ```bash
   git clone https://github.com/your-username/MovieTracker.git
   cd MovieTracker
   ```

2. **Open in Android Studio**:
   - Open Android Studio and select **Open**.
   - Navigate to the `MovieTracker` root directory and select it.

3. **Build the Project**:
   ```bash
   ./gradlew assembleDebug
   ```

4. **Run Unit Tests**:
   ```bash
   ./gradlew testDebugUnitTest
   ```

5. **Deploy to Device/Emulator**:
   - Select the `:app` run configuration in Android Studio and press **Run (Shift + F10)**.

---

## 🧪 Testing Strategy

Unit tests are included across modules to verify clean business logic and repository flow transformations:
- Run tests via CLI: `./gradlew test` or individually per subproject (e.g., `./gradlew :domain:testDebugUnitTest`).

---

## 📜 License

```text
Copyright 2026 MovieTracker Author

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
