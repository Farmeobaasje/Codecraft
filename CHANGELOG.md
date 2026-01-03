All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

### Added
- Added `.clinerules` file with development session rules for Cline AI assistant.
- Added multi-module Android project structure with :app, :data, :domain, :core:model, :core:common modules.
- Configured Gradle with version catalog (libs.versions.toml) and all necessary dependencies.
- Implemented Hilt setup with CodeCraftApplication class.
- Defined core data models: Repo, Owner, User.
- Implemented Room database setup with RepoEntity, RepoDao, and AppDatabase.
- Implemented remote data source with RepoDto, OwnerDto, and GitHubApiService.
- Created NetworkModule for Retrofit and Moshi dependency injection.
- Implemented GitHubRepository interface and implementation.
- Created RepoMapper for data transformation between DTO, Entity, and Domain models.
- Implemented GetUserReposUseCase for domain logic.
- Created DataModule for Room database and repository dependency injection.
- **Added complete UI theme system** with Color.kt, Type.kt, and Theme.kt using Material Design 3.
- **Added navigation system** with CodeCraftNavigation.kt and Screen sealed class using Jetpack Navigation Compose.
- **Added SearchScreen** with SearchViewModel for GitHub username input and validation.
- **Added RepoListScreen** with RepoListViewModel to display user repositories with pull-to-refresh functionality.
- **Added RepoDetailScreen** placeholder for repository details with RepoDetailViewModel.
- **Added MainActivity** with Compose UI and Hilt integration.
- **Added repository detail functionality** with `getRepoById` method in GitHubRepository interface and implementation.
- **Implemented RepoDetailViewModel** with loading, success, and error states.
- **Updated RepoDetailScreen** to display actual repository data including name, description, stars, forks, language, and owner information.
- **Added unit tests for GetUserReposUseCase** with MockK mocking and JUnit 5.
- **Added unit tests for RepoMapper** covering all mapping scenarios including null handling.
- **Updated testing dependencies** with JUnit Jupiter, MockK, and Kotlin Coroutines Test.
- **Added DataStore dependency** to libs.versions.toml for persistent theme preferences storage.
- **Added ThemeOptions enum** in core:model module for theme selection (Light, Dark, System).
- **Added SettingsDataStore** in data module for storing and retrieving theme preferences.
- **Added ThemeRepository interface and implementation** for theme management.
- **Updated DataModule** with DataStore dependency injection.
- **Updated MainActivity** to support dynamic theme switching based on user preference.
- **Added SettingsScreen UI** with theme selection, data storage, and about sections.
- **Added SettingsViewModel** for managing theme state and user preferences.
- **Updated navigation system** to include SettingsScreen with proper routing.
- **Updated SearchScreen** with settings icon in top app bar for navigation to settings.
- **Added string resources** for SettingsScreen UI elements.
- **Added personal repository notes feature** with RepoNote data class and RepoNoteEntity for Room database.
- **Added RepoNoteDao** for CRUD operations on repository notes.
- **Updated AppDatabase** to include RepoNoteEntity table.
- **Extended GitHubRepository interface** with note functionality: `getNoteForRepo`, `saveNoteForRepo`, and `deleteNoteForRepo`.
- **Updated GitHubRepositoryImpl** to implement note functionality with proper dependency injection.
- **Updated DataModule** to provide RepoNoteDao dependency.
- **Added AddNoteUseCase** and **GetNoteUseCase** for domain layer note operations.
- **Updated RepoDetailViewModel** to support note loading, saving, and deletion.
- **Enhanced RepoDetailScreen** with note display section and edit dialog with note icon in top app bar.
- **Added GitHub Trending Repositories feature** with new API endpoint for searching trending repositories.
- **Added SearchResponseDto** for handling GitHub Search API responses.
- **Extended GitHubRepository interface** with trending functionality: `getTrendingRepos()` and `refreshTrendingRepos()`.
- **Updated GitHubRepositoryImpl** with in-memory caching for trending repositories.
- **Added GetTrendingReposUseCase** for domain layer trending repository operations.
- **Added TrendingViewModel** with loading, success, error, and empty states for trending repositories.
- **Added TrendingScreen UI** with trending repository list, refresh functionality, and navigation to repository details.
- **Updated navigation system** to include TrendingScreen with proper routing.
- **Updated SearchScreen** with trending repositories button for easy navigation to trending content.
- **Added RepoDto.toRepo() mapper function** for direct conversion from DTO to domain model, fixing compilation errors in trending repository implementation.
- **Added UI polish and animations** for Week 4.5 completion:
  - **Staggered list animations** for both RepoListScreen and TrendingScreen with fade-in and slide-in effects
  - **Pull-to-refresh indicator refinements** with Material Design 3 PullToRefreshContainer
  - **Collapsing toolbar** on RepoDetailScreen using LargeTopAppBar with exitUntilCollapsedScrollBehavior
  - **Animated content size** for repository cards with smooth size transitions
- **Added GitHub Native Theme** for Week 5.1 completion:
  - **GitHub Dark Mode Colors**: Background (#0D1117), Surface cards (#161B22, #21262D, #30363D), Border (#30363D), Primary text (#C9D1D9), Secondary text (#8B949E), Accent green (#40C463), Error red (#F85149), Warning orange (#D29922)
  - **GitHub Light Mode Colors**: Background (#FFFFFF), Surface cards (#F6F8FA, #EAECEF, #D0D7DE), Border (#EAECEF), Primary text (#24292F), Secondary text (#57606A), Accent green (#2DA44E), Error red (#CF222E), Warning orange (#9A6700)
  - **Updated Color.kt** with comprehensive GitHub color palette for both dark and light modes
  - **Updated Theme.kt** with GitHubDarkColorScheme and GitHubLightColorScheme using Material Design 3 color schemes
  - **Maintained compatibility** with existing theme system and ThemeOptions (LIGHT, DARK, SYSTEM)
- **Added CodeCraft Premium Theme** for Week 5.2 completion:
  - **CodeCraft Premium Dark Mode Colors**: Background (#0A0A0A), Surface cards (#1A1A1A, #2A2A2A, #3A3A3A), Border (#3A3A3A), Primary text (#E0E0E0), Secondary text (#A0A0A0), Accent purple (#7C3AED), Error red (#EF4444), Warning orange (#F59E0B)
  - **CodeCraft Premium Light Mode Colors**: Background (#FFFFFF), Surface cards (#F8FAFC, #F1F5F9, #E2E8F0), Border (#E2E8F0), Primary text (#1E293B), Secondary text (#64748B), Accent purple (#7C3AED), Error red (#DC2626), Warning orange (#D97706)
  - **Extended ThemeOptions with ThemeStyle**: Added ThemeStyle enum (GITHUB, CODECRAFT_PREMIUM) for theme style selection
  - **Updated Color.kt** with CodeCraft Premium color palette for both dark and light modes
  - **Updated Theme.kt** with CodeCraftDarkColorScheme and CodeCraftLightColorScheme using Material Design 3 color schemes
  - **Updated ThemeRepository** to support theme style selection with DataStore persistence
  - **Updated MainActivityViewModel** to expose both themeOption and themeStyle as StateFlow
  - **Updated MainActivity** to use both themeOption and themeStyle in CodeCraftTheme
  - **Updated SettingsViewModel** to support theme style selection and updates
  - **Enhanced SettingsScreen** with theme style selection UI (GitHub Theme vs CodeCraft Premium)
  - **Added string resources** for theme style selection UI
  - **Maintained backward compatibility** with existing theme system while adding premium theme support

### Fixed
- Diagnosed missing `gradle-wrapper.jar` issue that was preventing Gradle builds. The file was missing from the `gradle/wrapper` directory, causing "Could not find or load main class org.gradle.wrapper.GradleWrapperMain" error.
- Created `fix-gradle-wrapper.bat` script to help diagnose and fix the Gradle wrapper issue automatically.
- Created `gradlew-direct.bat` fallback script that uses a locally downloaded Gradle distribution when the wrapper is broken.
- Replaced `gradlew.bat` with the fallback script (backed up original as `gradlew-original.bat`), so `gradlew` commands now work without the wrapper JAR.
- Attempted to create a valid `gradle-wrapper.jar` by extracting from Gradle distribution, but encountered nested JAR issues.
- **Fixed JVM version incompatibility**: Updated `core/model`, `core/common`, and `domain` modules from `jvmToolchain(24)` to `jvmToolchain(21)` to match the installed Java 21 runtime, resolving the "Dependency resolution is looking for a library compatible with JVM runtime version 21, but 'project :core:model' is only compatible with JVM runtime version 24 or newer" error.
- **Added AndroidX support**: Created `gradle.properties` with `android.useAndroidX=true` and `android.enableJetifier=true` to resolve AndroidX dependency conflicts.
- **Fixed Android resource linking errors**: Created missing Android resources including `strings.xml`, `themes.xml`, `colors.xml`, `data_extraction_rules.xml`, `backup_rules.xml`, and launcher icons for all density folders, resolving AAPT errors that prevented the build.
- **Fixed GitHubRepository import**: Corrected import statement in DataModule from `nl.codecraft.data.repository.GitHubRepository` to `nl.codecraft.domain.repository.GitHubRepository`, resolving compilation error.
- **Fixed navigation argument type inference** in CodeCraftNavigation.kt by using proper lambda syntax.
- **Fixed unresolved reference errors** by updating imports from `nl.codecraft.domain.model.Repo` to `nl.codecraft.model.Repo`.
- **Fixed smart cast issue** in RepoListScreen.kt by using safe call operator for nullable description.
- **Fixed experimental API warnings** by adding proper `@OptIn` annotations.

### Changed
- **Updated RepoListViewModel** to use GitHubRepository directly instead of GetUserReposUseCase for refresh functionality.
- **Fixed SearchViewModel bug**: Changed SearchViewModel to inject GitHubRepository directly and call `refreshUserRepos(username)` instead of `getUserReposUseCase(username)` which was returning a Flow without collecting it. This fixes the "no repositories found" issue when searching for GitHub users.
- **Removed purple status bar and TopAppBars**: Updated all screens (SettingsScreen, SearchScreen, TrendingScreen, RepoListScreen, RepoDetailScreen) to use custom headers instead of Material 3 TopAppBars for a cleaner, full-screen design with better window insets handling.

**Current Status:**
- ✅ `gradlew` command now works (uses fallback to direct Gradle distribution)
- ✅ JVM version incompatibility resolved
- ✅ AndroidX configuration added
- ✅ Android resource linking errors fixed (missing resources created)
- ✅ `GitHubRepository` import fixed in DataModule (build now succeeds)
- ✅ **Search bug fixed**: Repositories are now properly fetched from GitHub API when searching
- ✅ **README.md updated**: Complete project documentation with features, tech stack, architecture, and roadmap
- ✅ **Build successful**: Project compiles without errors (`gradlew build`)
- ✅ **Tests successful**: All tests pass (`gradlew test`)
- ✅ **Theme system implemented**: Dynamic theme switching with DataStore persistence
- ✅ **SettingsScreen added**: Complete UI for theme preferences and app settings
- ✅ **Navigation updated**: SettingsScreen integrated into navigation flow

**Remaining Issues:**
1. The `gradle-wrapper.jar` file is still invalid but bypassed by the fallback script.

**Recommended Solutions:**
1. For long-term fix: Open the project in Android Studio and let it regenerate the Gradle wrapper.
2. Or install Gradle globally and run: `gradle wrapper --gradle-version 8.10` with Java 17 or 21.
3. Check Android Gradle plugin version compatibility in `gradle/libs.versions.toml` (AGP 8.10.0 might be too new).
