# CodeCraft - Modern GitHub Repository Explorer

[![Kotlin](https://img.shields.io/badge/Kotlin-1.9.0-blue.svg)](https://kotlinlang.org)
[![Android](https://img.shields.io/badge/Android-API_24+-green.svg)](https://developer.android.com)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)
[![Build Status](https://img.shields.io/badge/Build-Passing-brightgreen.svg)](https://github.com/Farmeobaasje/Codecraft)

**CodeCraft** is a professional portfolio Android application that showcases modern Android development practices. It's a GitHub repository explorer built with Clean Architecture, Jetpack Compose, and the latest Android technologies.

## ✨ Features

- **🔍 GitHub User Search** - Find any GitHub user by username
- **📱 Repository Browser** - View user's repositories with detailed information
- **🔥 Trending Repositories** - Discover trending repositories with pull-to-refresh
- **🔄 Pull-to-Refresh** - Get the latest data with a simple swipe
- **📊 Repository Details** - See stars, forks, language, description, and more
- **📝 Repository Notes** - Add personal notes to repositories (offline storage)
- **💾 Offline Caching** - Room database for offline access to previously viewed repositories
- **🎨 Modern UI** - Material Design 3 with dynamic theming and full-screen experience
- **🧩 Clean Architecture** - Multi-module structure for maintainability
- **⚡ Reactive Programming** - Kotlin Coroutines & Flow for responsive UI

## 📸 Screenshots

*(Screenshots will be added after UI polishing)*

| Search Screen | Repository List | Repository Details |
|---------------|-----------------|-------------------|
| *Coming soon* | *Coming soon*   | *Coming soon*     |

## 🛠 Tech Stack

**Language & Framework**
- **Kotlin** - Modern, concise programming language
- **Jetpack Compose** - Declarative UI toolkit
- **Clean Architecture** - Multi-layer separation of concerns
- **MVVM** - Model-View-ViewModel pattern

**Dependency Injection**
- **Hilt** - Standard dependency injection for Android

**Asynchronous Programming**
- **Kotlin Coroutines** - Lightweight threads for async operations
- **Flow** - Reactive streams for data observation

**Networking & Data**
- **Retrofit** - Type-safe HTTP client
- **Moshi** - Modern JSON library for Kotlin
- **Room** - SQLite object mapping library
- **OkHttp** - HTTP client with interceptors

**Navigation**
- **Navigation Compose** - Type-safe navigation between screens

**Testing**
- **JUnit 5** - Unit testing framework
- **MockK** - Mocking library for Kotlin
- **Compose Testing** - UI testing for Jetpack Compose

## 📊 Project Status

**Current Progress: 85% Complete**

### ✅ Completed
- **Week 1: Foundation** - Multi-module structure, dependencies, Hilt setup, core models
- **Week 2: Data & Logic** - Room database, Retrofit API, repository pattern, use cases
- **Week 3: UI** - Material Design 3 theme, navigation, all screens (Search, List, Detail, Trending, Settings)
- **Week 4: Advanced Features** - Repository notes, trending repositories, pull-to-refresh
- **Week 5: UI Polish** - Full-screen experience, improved navigation, visual refinements

### 🚧 In Progress / Todo
- **Week 5.2** - CI/CD Pipeline (GitHub Actions)
- **Week 5.3** - UI Tests (end-to-end test automation)
- **Week 5.4** - Deployment (APK generation & GitHub Release)

## 🚀 Getting Started

### Prerequisites
- **JDK 21** or higher
- **Android Studio** Hedgehog (2023.1.1) or newer
- **Android SDK** API 34 (Android 14)

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/Farmeobaasje/Codecraft.git
   cd Codecraft
   ```

2. **Open in Android Studio**
   - Open Android Studio
   - Select "Open an Existing Project"
   - Navigate to the cloned directory

3. **Build the project**
   ```bash
   ./gradlew build
   ```

4. **Run on device/emulator**
   - Select your target device from Android Studio
   - Click the "Run" button (▶️) or press `Shift + F10`

### Alternative Build Method
If you encounter Gradle wrapper issues, use the direct Gradle script:
```bash
gradlew-direct.bat build
```

## 🏗 Architecture

CodeCraft follows **Clean Architecture** with a multi-module structure:

```
📁 app/                    # Presentation layer (UI, ViewModels)
📁 domain/                 # Business logic (Use Cases, Repository interfaces)
📁 data/                   # Data layer (Repository implementations, Database, API)
📁 core/model/             # Shared data models
📁 core/common/            # Shared utilities and extensions
```

### Key Components
- **Repository Pattern** - Single source of truth for data
- **Use Cases** - Encapsulate business rules
- **ViewModel** - UI state management
- **Data Transfer Objects** - API response mapping
- **Entity Objects** - Database representation
- **Domain Models** - Core business models
- **Theme Repository** - Dynamic theme switching (Light/Dark/System)
- **Settings DataStore** - Persistent user preferences

## 📁 Project Structure

```
codecraft/
├── app/
│   ├── src/main/java/nl/codecraft/
│   │   ├── ui/
│   │   │   ├── search/          # SearchScreen & SearchViewModel
│   │   │   ├── repo_list/       # RepoListScreen & RepoListViewModel
│   │   │   ├── repo_detail/     # RepoDetailScreen & RepoDetailViewModel
│   │   │   ├── trending/        # TrendingScreen & TrendingViewModel
│   │   │   ├── settings/        # SettingsScreen & SettingsViewModel
│   │   │   ├── theme/           # Material Design 3 theming
│   │   │   └── navigation/      # Navigation graph
│   │   └── CodeCraftApplication.kt
├── data/
│   ├── src/main/java/nl/codecraft/data/
│   │   ├── local/               # Room database (Entities, DAO)
│   │   ├── remote/              # Retrofit API (DTOs, Service)
│   │   ├── repository/          # GitHubRepository & ThemeRepository implementations
│   │   ├── mapper/              # Data transformation mappers
│   │   ├── local/               # Room database & DataStore
│   │   └── di/                  # Dependency injection modules
├── domain/
│   ├── src/main/java/nl/codecraft/domain/
│   │   ├── repository/          # Repository interfaces (GitHubRepository, ThemeRepository)
│   │   ├── usecase/             # Business use cases
│   │   └── model/               # Domain models (aliased from core:model)
├── core/
│   ├── model/                   # Pure data models
│   └── common/                  # Shared utilities
└── docs/                        # Project documentation
```

## 📈 Roadmap

See the detailed roadmap in [docs/roadmap.md](docs/roadmap.md) for complete phase-by-phase development plan.

## 🤝 Contributing

Contributions are welcome! Here's how you can help:

1. **Fork the repository**
2. **Create a feature branch** (`git checkout -b feature/amazing-feature`)
3. **Commit your changes** (`git commit -m 'Add some amazing feature'`)
4. **Push to the branch** (`git push origin feature/amazing-feature`)
5. **Open a Pull Request**

### Development Guidelines
- Follow the existing code style and architecture
- Write unit tests for new functionality
- Update the CHANGELOG.md with your changes
- Ensure the project builds successfully (`./gradlew build`)

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🙏 Acknowledgments

- [GitHub REST API](https://docs.github.com/en/rest) for providing the data
- [Android Developers](https://developer.android.com) for excellent documentation
- [JetBrains](https://www.jetbrains.com) for Kotlin and development tools

## 📞 Contact

Project Link: [https://github.com/Farmeobaasje/Codecraft](https://github.com/Farmeobaasje/Codecraft)

---

**Built with ❤️ using modern Android development practices**
