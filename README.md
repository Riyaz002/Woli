# 🖼️ Woli

## 📝 Overview

**Purpose**:  
Woli is a simple and elegant wallpaper app that allows users to explore, preview, and apply wallpapers on their Android devices.

**Key Features**:
- Browse high-quality wallpapers
- Set wallpapers directly from the app
- Favorites and save for later
- Lightweight and fast

---

## 🧱 Architecture

Woli follows the **MVI (Model–View–Intent/Action)** architecture to manage UI state and user interactions in a predictable, testable, and scalable way.

### Layers:

- **Model**: Represents the app state and data models.
- **View**: Jetpack Compose-based UI components that render based on the current state.
- **Action**: User interactions and events that update the model via reducers and use-cases.

---

## 🛠️ Tech Stack

| Area              | Technology              |
|-------------------|--------------------------|
| Language          | Kotlin                   |
| UI                | Jetpack Compose          |
| Architecture      | MVI                      |
| Networking        | Retrofit, OkHttp         |
| Image Loading     | Coil                     |
| Dependency Injection | Koin                |
| State Management  | Kotlin Flows / StateFlow |
| Async             | Kotlin Coroutines        |

---

## 📁 Project Structure
<pre> woli/
├── data/
│   ├── local/                  ← Database & SharedPreference (Room, SharedPreference)
│   │    ├── db/                ← Database (Room)
│   │    ├── sharedpreference/  ← SharedPreference (Encrypted SharedPreference)
│   ├── remote/                 ← remote apis httpclient config for authentication and media (Pexels and Firebase)
│   │    ├── core/              ← Core class (Interceptors and HttpClient provider)
│   │    ├── firebase/          ← Firebase firestore connectivity classes
│   │    ├── media/             ← Pexels Api for media
│   ├── repository/             ← repository class(Domain) implementation (AccountRepository, etc)
│   └── model/                  ← Data and domain models
├── domain/
│   ├── usecase/                ← Business logic and use-case classes
│   ├── event/                  ← Event bridge (ui ──> data)
│   ├── repository/             ← Repository interfaces
│   ├── model/                  ← Domain-level models (shared between layers)
│   └── util/                   ← Domain-level utility classes
├── ui/
│   ├── event/                  ← UI level action/event handler (ActionHandler, Action)
│   ├── screens/                ← UI screens (Compose-based, MVI state, intent)
│   ├── notification/           ← Notification services and helper classes  (FirebaseNotificationService, NotificationHandler)
│   ├── navigation/             ← Navigation related components (Root & BottomNavigation composable, Screens)
│   └── shared/                 ← Shared/Reusable UI level components & data model (Button, Dialog, Progressbar, etc)
├── di/                         ← Koin modules and dependency providers
├── utils/                      ← Utility functions and helpers
└── MainActivity.kt             ← Entry point for the app </pre>

---

## 🚀 Build & Run

### Requirements:
- Android Studio Giraffe or later
- JDK 17+
- Kotlin 2.0+
- Min SDK: **24**
- Target SDK: **35**

### Steps:
1. Clone the repo
2. Open in Android Studio
3. Run `./gradlew build` or use the “Run” button
4. App should launch on emulator or device

---

## 🔁 Feature Flows

### 1. Browse Wallpapers Flow

- **View**: HOME
- **Action**: `ActionHandler.handle(Navigate(Screen.Home/Screen.Collections))`
- **UseCase**: `GetMediaUseCase` calls API via repository

### 2. Set Wallpaper Flow

- **View**: DETAIL
- **Action**: `OnClickSetWallpaper`
- **UseCase**: `SetWallpaperUseCase` calls API via repository

---

## 🔐 Environment Configuration

If required:
- Store API secrets in a `secrets.properties` (ignored in git)
- Example:
  WALLPAPER_API_KEY=your_key_here

---

## 🧪 Testing

- **Unit Tests**: ViewModel reducers and use-cases
- **UI Tests**: Compose UI tests using `androidx.compose.ui.test`
- **Mocking**: Use `MockK` for testing repositories or use-cases

---

## 🔍 Known Issues / TODO

- [ ] Add dark mode support

---

## 🤝 Contributing / Handoff Notes

- All state management is in `ui/screen/common/PageViewModel<T>`
- Use Koin for all DI
- Use `sealed class` for `Action` and `State` for Screens
- Navigation is done via `NavHost` in Compose
- All new screens should follow the MVI pattern