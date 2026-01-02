CodeCraft - Project Roadmap (Phase 1: Android)
🎯 Visie
"CodeCraft" is een vlaggenschipportfolio-app dat de expertise van een moderne Android-ontwikkelaar showcase. Het is een demonstratie van professionele software-architectuur, een oog voor design en een meesterlijke uitvoering.

Onze filosofie: Profesionele Elegantie. We bouwen een strak, krachtig en verfijnd hulpmiddel voor ontwikkelaars.

🛠️ Tech Stack (Android)
Categorie	Technologie	Reden
Taal	Kotlin	De moderne standaard voor Android.
UI	Jetpack Compose	Declaratieve, snelle en toekomstbestendige UI-toolkit.
Architectuur	Clean Architecture + MVVM	Voor maximale testbaarheid, schaalbaarheid en onderhoudbaarheid.
Asynchroon	Kotlin Coroutines & Flow	Voor efficiënte en reactieve programmering.
DI	Hilt	De standaard voor dependency injection in Android.
Netwerk	Retrofit + OkHttp + Moshi	Een robuuste en efficiënte combinatie voor API-communicatie.
Database	Room + KSP	Voor een betrouwbare, lokale database met snelle codegeneratie.
Navigatie	Navigation Compose	Type-safe en geïntegreerde navigatie.
Testing	JUnit 5, MockK, Compose Testing	Voor een complete teststrategie.
CI/CD	GitHub Actions	Voor automatisering van builds en tests.
📅 Phase 1: Gedetailleerde Roadmap (Android-Only)
Week 1: Het Fundament - Een Solide Structuur
Doel: Een schone, multi-module projectstructuur opzetten die klaar is voor ontwikkeling en onze architectuur afdwingt.

[ ] 1.1 Project Initialisatie
Maak een nieuw Android-project aan in Android Studio ("Empty Activity" template).
Configureer het project om Kotlin als taal te gebruiken en stel de minSdk in (bv. API 24).
Verwijder de standaard MainActivity en activity_main.xml.
[ ] 1.2 Multi-Module Structuur Opzetten
Maak de volgende modules aan via Android Studio: File > New > New Module...
:core:model (Java/Kotlin Library)
:core:common (Java/Kotlin Library)
:data (Android Library)
:domain (Java/Kotlin Library)
Pas settings.gradle.kts aan om de nieuwe modules te includeren.
[ ] 1.3 Dependencies Configureren
In build.gradle.kts (Project): Definieer de versies voor alle dependencies in een buildSrc of libs.versions.toml file.
In :core:model/build.gradle.kts: Geen dependencies nodig.
In :core:common/build.gradle.kts: Voeg dependencies toe voor Coroutines, etc.
In :domain/build.gradle.kts: Voeg coroutines-core en javax.inject toe.
In :data/build.gradle.kts: Voeg dependencies toe voor :domain, Room, Retrofit, Moshi, Hilt.
In :app/build.gradle.kts: Voeg dependencies toe voor :domain, :data, :core:model, :core:common, en alle UI/Android-gerelateerde libraries (Compose, Hilt, Navigation, etc.).
[ ] 1.4 Hilt & Application Setup
Maak een CodeCraftApplication klasse in de :app module die is geannoteerd met @HiltAndroidApp.
Verwijs naar deze Application klasse in de AndroidManifest.xml.
[ ] 1.5 Data Models Definiëren
In :core:model, maak de pure data classes: Repo, Owner, User. Deze hebben geen Android- of JSON-annotaties.
[ ] 1.6 Documentatie Initialiseren
Maak README.md, CHANGELOG.md en .github/workflows/ci.yml (leeg) aan.
Update CHANGELOG.md met de initiële setup.
Definition of Done: Het project compileert foutloos (./gradlew build). Alle modules zijn correct geconfigureerd. De Application klasse is ingesteld.

Week 2: Data & Logica - De Hersenen van de App
Doel: De volledige data-laag bouwen, van netwerk tot lokale opslag, en de eerste use case implementeren.

[ ] 2.1 Local Database (Room) Setup
In :data, maak local/RepoEntity.kt met @Entity annotaties.
Maak local/RepoDao.kt met @Dao annotaties, met functies getRepos(), insertRepos(), clearRepos().
Maak local/AppDatabase.kt met @Database annotaties.
Configureer KSP in de :data/build.gradle.kts en voeg de Room compiler toe.
[ ] 2.2 Remote Data Source (Retrofit) Setup
In :data, maak remote/dto/RepoDto.kt met @Json annotaties.
Maak remote/GitHubApiService.kt als een Retrofit interface met de GET /users/{user}/repos endpoint.
Maak remote/di/NetworkModule.kt om Retrofit, OkHttp en Moshi te configureren met Hilt.
[ ] 2.3 Repository Implementatie
In :domain, maak repository/GitHubRepository.kt (de interface).
In :data, maak repository/GitHubRepositoryImpl.kt.
Implementeer de logica: getUserRepos() returned de Flow van de DAO, en refresh() haalt data van de API, mapt het naar entities, en slaat het op in de database.
[ ] 2.4 Mappers & Dependency Injection
Maak mapper/RepoMapper.kt met extensiefuncties om RepoDto -> RepoEntity en RepoEntity -> Repo te converteren.
Maak di/DataModule.kt in de :data module om de Repository, DAO en Database te voorzien.
[ ] 2.5 Use Case Implementatie
In :domain, maak usecase/GetUserReposUseCase.kt. Deze klasse krijgt de GitHubRepository geïnjecteerd en wrapped de Flow van de repository.
[ ] 2.6 Unit Tests Schrijven
Schrijf unit tests voor GetUserReposUseCase met MockK om de GitHubRepository te mocken.
Schrijf unit tests voor RepoMapper.
Definition of Done: De data-laag is volledig functioneel. De unit tests voor de domain en data lagen slagen. De CHANGELOG.md is bijgewerkt.

Week 3: De Gebruikersinterface (UI) - Het Prachtplaatje
Doel: De visuele laag bouwen met Jetpack Compose en naadloos koppelen aan de onderliggende logica.

[ ] 3.1 Thema & Navigatie Setup
In :app, maak ui/theme/Theme.kt, Color.kt, Type.kt met Material 3. Implementeer dynamische kleuren.
Maak ui/navigation/CodeCraftNavigation.kt en definieer de navigatiegrafe met de schermen.
[ ] 3.2 Zoekscherm & ViewModel
Maak ui/search/SearchViewModel.kt. Deze houdt de zoekquery in een StateFlow en heeft een search(username: String) functie die de Use Case aanroept.
Maak ui/search/SearchScreen.kt (Composeable) met een TextField en Button. Koppel deze aan de SearchViewModel.
[ ] 3.3 Repository Lijstscherm & ViewModel
Maak ui/repo_list/RepoListViewModel.kt. Deze ontvangt de username via SavedStateHandle, roept de GetUserReposUseCase aan en houdt de UI-state (Loading, Success, Error) bij in een StateFlow.
Maak ui/repo_list/RepoListScreen.kt met een LazyColumn en SwipeRefresh. Observeer de StateFlow van de ViewModel en toon de juiste UI.
[ ] 3.4 Repository Detailscherm
Maak ui/repo_detail/RepoDetailScreen.kt. Deze ontvangt een Repo object via de navigatie.
Implementeer de UI om alle details van de repo te tonen, inclusief de "Open op GitHub" knop.
[ ] 3.5 Alles Verbinden
Update MainActivity.kt om de CodeCraftNavigation te hosten.
Zorg dat de navigatie van SearchScreen -> RepoListScreen -> RepoDetailScreen correct werkt en data doorgeeft.
Definition of Done: De volledige gebruikersflow werkt van begin tot eind met dynamische data. De app voelt responsief aan. De CHANGELOG.md is bijgewerkt.

Week 4: Polijsten, Testen & Deployen - De Finishing Touches
Doel: De app transformeren van een werkend prototype naar een professioneel product en de wereld in sturen.

[ ] 4.1 Visuele Polijst
Voeg AnimatedVisibility toe voor error- en loading-states.
Implementeer animateContentSize voor de repo-kaarten.
Voeg een sharedElementTransition toe tussen de lijst en het detailscherm (geavanceerd, indien tijd).
Controleer contentDescription voor alle iconen en afbeeldingen (accessibility).
[ ] 4.2 CI/CD Pipeline (GitHub Actions)
Vul .github/workflows/ci.yml met de stappen: checkout, setup-java, run gradle build, run gradle test.
Push de code en verifieer dat de workflow succesvol draait.
[ ] 4.3 UI Tests
Schrijf een UI test die de volledige flow automatiseert: app opstarten, naam invoeren, op zoeken drukken, controleren of de lijst verschijnt, op een item tikken, controleren of het detailscherm verschijnt.
[ ] 4.4 Deployment
Genereer een ondertekend APK-bestand.
Maak een nieuwe tag in Git: git tag v1.0.0.
Maak een nieuwe GitHub Release en upload het APK-bestand.
Werk de README.md af met een link naar de release en screenshots.
Definition of Done: De app is live op GitHub als een release. De CI/CD-pipeline werkt. De app is visueel verfijnd en toegankelijk. De CHANGELOG.md bevat de volledige geschiedenis. 