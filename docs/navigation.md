De Navigatie-architectuur: Het Landkaart
We gebruiken een Bottom Navigation Bar als de ruggengraat van de app. Dit is een standaard, intuïtief patroon dat de gebruiker snel toegang geeft tot de belangrijkste secties.

De drie hoofdtabbladen zijn:

Portfolio: Het hart van de app, hier bekijk je projecten.
Insights: Hier visualiseren we data en statistieken.
Profile: Voor instellingen en accountbeheer.
Laten we nu elke tak van deze kaart verkennen.

Schematisch Overzicht

[CodeCraft App]
├── OnboardingFlow
│   └── SplashScreen
│
└── MainApp (Scaffold met BottomNav)
│
├── Tab 1: Portfolio
│   ├── SearchScreen (initieel scherm)
│   ├── RepositoryListScreen
│   └── RepositoryDetailScreen
│
├── Tab 2: Insights
│   ├── InsightsOverviewScreen
│   └── (Future) LanguageDetailScreen
│
└── Tab 3: Profile
├── ProfileScreen
├── SettingsScreen
└── AboutScreen
Detailanalyse van Elk Scherm & Zijn Wiring
1. Onboarding Flow: SplashScreen
   Doel: Een professionele eerste indruk maken en de app in de achtergrond voorbereiden.
   UI Componenten:
   Groot "CodeCraft" logo in het midden.
   Subtiele animatie (bijv. een pulserend effect of een fade-in).
   De "Wiring":
   Data: Geen data nodig.
   Logica: Een LaunchedEffect in Compose start een coroutine. Na 2 seconden wordt er genavigeerd naar de MainApp (met de Portfolio-tab als startpunt).
   Architectuur: Dit is een pure UI-screen; het heeft geen ViewModel nodig.
2. Tab 1: Portfolio
   A. SearchScreen (het startpunt van de Portfolio-tab)
   Doel: De gebruiker toestaan om een GitHub-gebruiker te zoeken.
   UI Componenten:
   Een TopAppBar met de titel "Portfolio".
   Een TextField met een placeholder "Voer een GitHub-gebruikersnaam in...".
   Een "Zoek" Button.
   Onderaan een tekstuele tip: "Bijv: google, square, of je eigen gebruikersnaam".
   De "Wiring":
   Data: Gebruikersinput (String).
   Logica (SearchViewModel):
   Houdt de tekst van de TextField bij in een StateFlow.
   De "Zoek"-knop is disabled zolang de tekst leeg is.
   Bij een klik wordt een GetUserReposUseCase aangeroepen.
   Navigatie: Als de Use Case succesvol data ophaalt, navigeren we naar de RepositoryListScreen en geven we de gebruikersnaam mee als argument.
   B. RepositoryListScreen
   Doel: Een overzichtelijke, scrollbare lijst van alle repositories van een gebruiker tonen.
   UI Componenten:
   TopAppBar met de gebruikersnaam en een avatar.
   Een SwipeRefresh (pull-to-refresh) component.
   Daarin een LazyColumn voor een efficiënte lijst.
   Elk RepoListItem toont: Naam, Beschrijving, Taal (met gekleurde dot), Sterren, Forks.
   De "Wiring":
   Data: List<Repo> object.
   Logica (RepoListViewModel):
   Ontvangt de gebruikersnaam via het SavedStateHandle.
   Heeft een StateFlow<UiState> waar UiState Loading, Success, Error kan zijn.
   De UI observeert deze StateFlow.
   De Repository wordt aangeroepen om data te krijgen (eerst uit Room, dan van het netwerk).
   De SwipeRefresh triggert een "force refresh" in de repository.
   Navigatie: Een klik op een list-item navigeert naar de RepositoryDetailScreen en geeft het volledige Repo object mee.
   C. RepositoryDetailScreen
   Doel: Alle details van één specifiek project tonen.
   UI Componenten:
   TopAppBar met de repository-naam.
   Een Column met de details:
   Volledige beschrijving.
   Statistieken (Stars, Watchers, Forks) in Cards.
   Een LazyRow met "Topics" (als FilterChips).
   Een FloatingActionButton of een grote Button onderaan: "Open op GitHub".
   De "Wiring":
   Data: Het Repo object, meegegeven via navigatie.
   Logica (RepoDetailViewModel):
   Heeft weinig logica. Het houdt het Repo object vast in een State.
   De "Open op GitHub" knop gebruikt een Intent om de URL in de browser te openen.
   Navigatie: Geen verdere navigatie vanaf hier.
3. Tab 2: Insights
   A. InsightsOverviewScreen
   Doel: De gebruiker inzicht geven in de programmeergewoonten van de geselecteerde gebruiker.
   UI Componenten:
   TopAppBar met "Insights".
   Een LazyColumn met "insight cards":
   Card 1: Taalverdeling. Een cirkeldiagram (donut chart) dat de verhouding van programmeertalen toont.
   Card 2: Meest Gelikete Repos. Een horizontale lijst van de top 3 repositories met de meeste sterren.
   Card 3: Projectgrootte. Een lijst met de 5 grootste repositories (aantal bytes).
   De "Wiring":
   Data: De List<Repo> die al is opgehaald in de Portfolio-tab.
   Logica (InsightsViewModel):
   Deze ViewModel heeft zijn eigen StateFlow die de berekende insights bevat.
   Het luistert naar de repository-list-flow uit de GitHubRepository.
   Zodra er nieuwe repo-data binnenkomt, worden de insights (taalpercentages, etc.) herberekend op een IO dispatcher.
   Gebruikt een library als Vico om de grafieken in Compose te tekenen.
   Navigatie: (Toekomst) Een tik op een taal in het diagram kan naar een LanguageDetailScreen navigeren die alle repos in die taal toont.
4. Tab 3: Profile
   A. ProfileScreen
   Doel: Een centrale plek voor app-instellingen en gebruikersinformatie.
   UI Componenten:
   TopAppBar met "Profile".
   Een Card aan de top met de huidige GitHub-gebruiker (naam, avatar, "Switch User" knop).
   Een LazyColumn met instellingen:
   ListPreferenceItem voor "Thema" (Licht, Donker, Systeemstandaard).
   ListPreferenceItem voor "Dynamische Kleuren" (Aan/Uit).
   ClickableText voor "Over CodeCraft".
   De "Wiring":
   Data: Gebruikerspreferences (thema, etc.).
   Logica (ProfileViewModel):
   Gebruikt DataStore om preferences op te slaan en te lezen.
   Houdt de huidige staat van de preferences bij in StateFlows.
   De "Switch User" knop navigeert terug naar de SearchScreen en wist de huidige gebruikersdata.
   Navigatie: "Over CodeCraft" navigeert naar een AboutScreen.
   B. AboutScreen
   Doel: Informatie over de app en de ontwikkelaar.
   UI Componenten:
   App-versie (bv. "v1.0.0").
   Links naar jouw GitHub, LinkedIn, en de repository van de app zelf.
   Een "Licenties" sectie die de open-source libraries toont.
   De "Wiring":
   Data: Statische strings en versie-info uit BuildConfig.
   Logica: Minimaal. Voornamelijk UI.
   Navigatie: Alleen terug.
