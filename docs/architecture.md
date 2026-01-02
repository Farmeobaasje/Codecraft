De Fundamenten: Architectuur Principes
Ons hele ontwerp is gebaseerd op een paar kernprincipes die de code robuust, schaalbaar en testbaar maken.

Separation of Concerns (Scheiding van Belangen): Elke laag en elke klasse heeft één, duidelijk gedefinieerde verantwoordelijkheid. De UI weet niets van de database, en de database weet niets van de UI.
Dependency Inversion (Afhankelijkheidsinversie): De hogere lagen (bv. UI) zijn niet afhankelijk van lagere lagen (bv. Database). Beiden zijn afhankelijk van abstracties (interfaces). Dit is de kern van Clean Architecture.
Unidirectional Data Flow (UDF): Data stroomt altijd in één richting: UI Event -> ViewModel -> Use Case -> Repository -> Data Source. De UI reageert op veranderingen in de data, niet andersom. Dit voorkomt inconsistenties.
Single Source of Truth (SSOT): Voor elke piece of data is er één, autoritatieve bron. In onze app is dat de Room database. De UI toont altijd wat er in de database staat, die op zijn beurt wordt bijgewerkt door het netwerk.
De Lagen van Clean Architecture in "CodeCraft"
We bouwen de app in drie strikt gescheiden lagen, elk in zijn eigen module.

Laag 1: Presentation Layer (:app module)
Verantwoordelijkheid: Alles wat de gebruiker ziet en waarmee hij interacteert.

UI (Jetpack Compose): De schermen (SearchScreen, RepoListScreen, etc.) die zijn opgebouwd uit stateless, herbruikbare Composable-functies.
ViewModels: De brug tussen de UI en de business logic. Een ViewModel:
Houdt de UI-state bij in een StateFlow (bv. Loading, Success, Error).
Ontvangt UI-events (bv. een klik op een knop).
Roept de juiste Use Cases aan uit de Domain layer.
Is niet verantwoordelijk voor hoe de data wordt verkregen, alleen wat er moet gebeuren.
Navigation: De Navigation Compose component die de schermen beheert en de data doorgeeft tussen schermen.
Laag 2: Domain Layer (:domain module)
Verantwoordelijkheid: De kernbusinesslogica van de app. Deze laag is puur Kotlin en bevat geen enkele Android-afhankelijkheid. Dit maakt het 100% testbaar en herbruikbaar.

Use Cases (Interactors): Elke specifieke actie die de app kan uitvoeren, is een Use Case.
GetUserReposUseCase(username: String): Flow<List<Repo>>
RefreshUserReposUseCase(username: String)
GetUserInsightsUseCase(repos: List<Repo>): Insights
Een Use Case pakt data van een Repository, past er eventueel transformaties op toe en geeft het resultaat terug.
Repository Interfaces: Abstracties die definiëren welke data beschikbaar is, niet hoe die wordt verkregen.
kotlin

interface GitHubRepository {
fun getUserRepos(username: String): Flow<List<Repo>>
suspend fun refreshUserRepos(username: String)
}
Domain Models: De pure data-klassen die de kern van onze applicatie vormen (bv. Repo, User, Insights). Deze bevatten geen logica die gerelateerd is aan data-transport (zoals @Entity of @Json annotaties).
Laag 3: Data Layer (:data module)
Verantwoordelijkheid: Het verkrijgen en beheren van data. Dit is de enige laag die weet waar de data vandaan komt (netwerk, database, bestandssysteem).

Repository Implementatie: De concrete klasse die de interface uit de Domain layer implementeert.
kotlin

class GitHubRepositoryImpl @Inject constructor(
private val remoteDataSource: RemoteDataSource,
private val localDataSource: LocalDataSource
) : GitHubRepository {
override fun getUserRepos(username: String): Flow<List<Repo>> {
// Logic: return localDataSource.getRepos()
// Also trigger remoteDataSource.fetchRepos() in the background
}
// ...
}
Data Sources: Klassen die de communicatie met de concrete bronnen afhandelen.
Remote DataSource: Beheert de Retrofit API calls naar de GitHub API.
Local DataSource: Beheert de Room database operaties via de DAO's.
Data Transfer Objects (DTOs) & Mappers:
DTOs: Data-klassen die specifiek zijn voor een bron (bv. RepoDto met @Json annotaties voor de netwerk-response, of RepoEntity met @Entity voor de Room-tabel).
Mappers: Functies die een DTO omzetten naar een Domain Model en vice versa. Dit isoleert de domeinlogica van de data-transport-logica.
De Complete Data Flow: Een Voorbeeld
Laten we de stroom volgen wanneer een gebruiker "google" zoekt en op de knop drukt.

UI Event: SearchScreen -> SearchViewModel.onSearchClicked("google").
ViewModel -> Use Case: SearchViewModel roept getUserReposUseCase("google") aan.
Use Case -> Repository: De GetUserReposUseCase roept gitHubRepository.getUserRepos("google") aan. Dit retourneert een Flow<List<Repo>>.
Repository -> Data Sources:
De GitHubRepositoryImpl start een Flow vanuit de LocalDataSource (Room).
Tegelijkertijd roept het RemoteDataSource (Retrofit) aan om de data van de GitHub API te halen.
Als de netwerk-call slaagt, wordt de response (RepoDto) gemapt naar een Repo domain model en doorgegeven aan de LocalDataSource om op te slaan in de database.
Database -> UI Update: Omdat de Room database is bijgewerkt, emit de Flow uit stap 4 automatisch de nieuwe lijst van Repo objecten.
Use Case -> ViewModel: De Flow stroomt terug door de Use Case naar de SearchViewModel.
ViewModel -> UI: De ViewModel update zijn StateFlow met Success(repos). De SearchScreen, die deze StateFlow observeert, recomposeert zichzelf en toont de RepositoryListScreen met de nieuwe data.
De Dependency Injection (DI) Graph met Hilt
Hilt is de lijm die alles bij elkaar houdt. Het zorgt ervoor dat de ViewModel zijn UseCase krijgt, de UseCase zijn Repository, en de Repository zijn Data Sources.


@HiltAndroidApp
Application
└── Provides
└── GitHubApi (Retrofit)
└── AppDatabase (Room)

@ActivityRetainedScoped
SearchViewModel
└── Constructor Injects
└── GetUserReposUseCase

@Singleton
GetUserReposUseCase
└── Constructor Injects
└── GitHubRepository (interface)

@Singleton
GitHubRepositoryImpl
└── Constructor Injects
└── RemoteDataSource
└── LocalDataSource

@Singleton
RemoteDataSource
└── Constructor Injects
└── GitHubApi

@Singleton
LocalDataSource
└── Constructor Injects
└── RepoDao
└── ... (andere DAO's)
Visueel Architectuur Diagram

+---------------------------------------------------+
|                 PRESENTATION LAYER               |
|  +-----------------+    +-----------------------+ |
|  |   UI (Compose)  |<-->|      ViewModel        | |
|  +-----------------+    +-----------------------+ |
|         ^                         |                |
|         | UI State (StateFlow)     | Calls          |
|         |                         v                |
|  +-----------------+    +-----------------------+ |
|  |   Navigation    |    |      Use Cases       | |
|  +-----------------+    +-----------------------+ |
+---------------------------------------------------+
| Depends on (Interface)
v
+---------------------------------------------------+
|                   DOMAIN LAYER                    |
|  +-----------------------------------------------+ |
|  |         GitHubRepository (Interface)          | |
|  +-----------------------------------------------+ |
|  |  Domain Models (Repo, User, Insights)         | |
|  +-----------------------------------------------+ |
+---------------------------------------------------+
^ Implements
|
+---------------------------------------------------+
|                    DATA LAYER                      |
|  +-----------------------------------------------+ |
|  |       GitHubRepositoryImpl                    | |
|  +-----------------------------------------------+ |
|           ^                         ^            |
|  Provides |                         | Provides   |
|           v                         v            |
|  +-----------------+    +-----------------------+ |
|  | Remote DataSource|    |   Local DataSource    | |
|  | (Retrofit/OkHttp)|    |      (Room)           | |
|  +-----------------+    +-----------------------+ |
+---------------------------------------------------+
Dit is de complete, high-class architectuur. Het is een robuust systeem dat niet alleen voor "CodeCraft" werkt, maar ook als blauwdruk kan dienen voor vrijwel elke moderne data-gedreven Android-app. Het is de ultieme demonstratie van je vaardigheden als software-architect.