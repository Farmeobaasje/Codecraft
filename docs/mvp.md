Filosofie van het MVP
Het doel van het MVP is niet om alles te kunnen, maar om één ding perfect te doen: je GitHub-projecten op een indrukwekkende manier presenteren. We bouwen een solide, moderne architectuur waar je later makkelijk nieuwe features aan kunt toevoegen.

De MVP Gebruikersstroom
De gebruiker opent de app.
De app vraagt om een GitHub-gebruikersnaam.
De gebruiker typt een naam (bijv. "square") en drukt op "Zoeken".
De app toont een laadindicator.
Een lijst met alle publieke repositories van die gebruiker verschijnt.
De gebruiker tikt op een repository (bijv. "okhttp").
Een detailscherm opent met meer informatie over die specifieke repository.
Dat is het. Simpel, krachtig en compleet voor een eerste versie.

MVP Features: Wat we bouwen (Must-Haves)
1. De Schermen (UI met Jetpack Compose)
   Zoekscherm:
   Een TextField om de GitHub-gebruikersnaam in te voeren.
   Een "Zoek"-knop.
   (Optioneel: een klein logo en een korte uitleg).
   Repository Lijstscherm:
   Een LazyColumn (efficiënte scrollende lijst).
   Elk item in de lijst toont:
   Repository Naam (bv. retrofit)
   Beschrijving
   Hoofdgereedschap (bv. Kotlin, Java)
   Aantal sterren (⭐ 1.234)
   Aantal forks
   Repository Detailscherm:
   Toont alle informatie uit de lijst, maar dan groter.
   De volledige beschrijving (README.md content is voor later).
   Een lijst met "Topics" (de labels die GitHub toont).
   Een knop "Open op GitHub" die de repository in de browser opent.
2. De Data (Architectuur: MVVM + Repository)
   Netwerklaag (Retrofit):
   Een API-interface die de GitHub API kan aanroepen (https://api.github.com/users/{username}/repos).
   Data classes (Repo, Owner) die de JSON-response van GitHub mappen.
   Database (Room):
   Een @Entity class voor de Repo.
   Een Dao (Data Access Object) met functies om repositories op te slaan, op te halen en te wissen.
   Repository Pattern:
   Een centrale GitHubRepository class die de logica beheert:
   Probeer data eerst uit de lokale Room database te halen.
   Haal tegelijkertijd de data van de GitHub API met Retrofit.
   Sla de nieuwe data op in de Room database.
   De UI luistert altijd naar de data in de Room database.
3. De Logica (ViewModel & State)
   ViewModel:
   Een GitHubViewModel die de GitHubRepository aanstuurt.
   Houdt de UI-state bij in een StateFlow (bv. Loading, Success(repos: List<Repo>), Error(message: String)).
   Asynchroon (Coroutines):
   Alle netwerk- en database-operaties draaien in een coroutine op een IO dispatcher, zodat de UI nooit vastloopt.
   MVP: Wat we NIET bouwen (Nice-to-Haves voor later)
   ❌ GitHub Authenticatie (OAuth): We gebruiken de publieke API, die geen inlog vereist. Dit scheelt een hoop complexiteit.
   ❌ Zelf projecten toevoegen/bewerken: We tonen alleen wat op GitHub staat.
   ❌ Commit-statistieken en taal-grafieken: Dit is een geweldige "versie 2.0" feature.
   ❌ "Showcase Mode": Een simpele toevoeging later.
   ❌ Demo-video's opslaan: Ook een perfecte feature voor een latere versie.
   ❌ Caching van afbeeldingen (avatars): We kunnen de avatar-URLs tonen, maar het slim cachen ervan is een volgende stap.
   Stappenplan om het MVP te bouwen
   Week 1: Fundament.
   Maak een nieuw Android-project met Jetpack Compose.
   Voeg alle dependencies toe: Compose, Navigation, ViewModel, Retrofit, Room, Hilt, Coroutines.
   Zet de projectstructuur op volgens Clean Architecture (layers: presentation, domain, data).
   Definieer alle data classes en de Room database.
   Week 2: Data & Logica.
   Bouw de Retrofit API-service.
   Implementeer de GitHubRepository met de logica: API -> Room.
   Schrijf de GitHubViewModel die de repository aanroept en de StateFlow beheert.
   Week 3: UI & Integratie.
   Bouw de drie Compose-schermen met "dummy data".
   Koppel de schermen aan de ViewModel zodat ze live data tonen.
   Implementeer de navigatie tussen de schermen.
   Week 4: Polijpen & Bugfixen.
   Implementeer de loading- en error-states.
   Voeg mooie animaties en een strak Material Design 3 thema toe.
   Test de app grondig: wat gebeurt er als je geen internet hebt? Wat als je een ongeldige gebruiker opgeeft?