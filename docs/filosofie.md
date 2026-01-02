Visie & Filosofie
Doel: Een vlaggenschipproject dat niet alleen functioneel is, maar ook een diepgaand begrip toont van moderne Android-ontwikkeling, software-architectuur en best practices.
Principes:
Proactiviteit: We anticiperen op problemen (geen netwerk, trage API's) in plaats van te reageren.
Onderhoudbaarheid: De code is zo schoon en goed gestructureerd dat een andere ontwikkelaar (of jijzelf over 6 maanden) er direct in kan werken.
Gebruikservaring (UX): De app voelt snel, responsief en intuïtief aan. Elke interactie heeft een doel en feedback.
Testbaarheid: Elke laag van de applicatie is geïsoleerd testbaar.
De State-of-the-Art Tech Stack
Dit is de kern van je technische showcase. We gebruiken de nieuwste, door Google aanbevolen technologieën.

Categorie
Technologie
Waarom dit de "high-class" keuze is
Taal	Kotlin	De standaard voor Android. Modern, concies en type-safe.
UI	Jetpack Compose	De toekomst van Android UI. Declaratief, minder code, snellere ontwikkeling. Toont dat je vooroploopt.
Architectuur	Clean Architecture + MVVM	De gouden standaard. Schept een duidelijke scheiding tussen belangen, wat de code testbaar en schaalbaar maakt.
Asynchroon	Kotlin Coroutines & Flow	Essentieel voor moderne Android-apps. Flow is perfect voor het observeren van datastromen (bv. uit een database).
Dependency Injection	Hilt	De officiële DI-library van Google. Vereenvoudigt boilerplate en integreert naadloos met Android.
Netwerk	Retrofit + OkHttp + Moshi	Retrofit voor de API-definitie, OkHttp voor de low-level HTTP-calls, en Moshi (een moderner alternatief voor Gson) voor efficiënte JSON-parsing.
Database	Room + KSP	Room voor de lokale database. We gebruiken KSP (Kotlin Symbol Processing) in plaats van KAPT. Het is sneller en de toekomst.
Navigatie	Navigation Compose	De standaard, type-safe manier om tussen Compose-schermen te navigeren.
Image Loading	Coil	Een lichte, snelle en eenvoudige image loading library, specifiek gemaakt voor Kotlin en Compose.
Theming	Material Design 3 (You)	We implementeren dynamische kleuren (Material You) als het apparaat dit ondersteunt. Dit is een enorm detail dat oog voor design toont.

Projectstructuur: De Blauwdruk (Multi-module Setup)
Een échte high-class app is niet één grote :app module. We splitsen de logica op in aparte modules.


CodeCraft/
├── app/                    // De Android App-module (alleen UI en App-klass)
│   ├── src/main/java/
│   │   └── com/zet/codecraft/
│   │       ├── MainActivity.kt
│   │       ├── di/         // Hilt modules specifiek voor de app
│   │       └── ui/         // Compose schermen en ViewModels
│   └── build.gradle.kts    // Afhankelijkheden van de app
│
├── core/                   // Gedeelde code voor alle modules
│   ├── model/              // Data classes (Repo, User, etc.)
│   ├── common/             // Utils, extensies, constants
│   └── build.gradle.kts
│
├── data/                   // De datalaag
│   ├── src/main/java/
│   │   ├── local/          // Room database, DAO's
│   │   ├── remote/         // Retrofit API interface
│   │   └── repository/     // Implementatie van de Repository
│   └── build.gradle.kts    // Afhankelijkheden: Room, Retrofit, Moshi
│
├── domain/                 // De bedrijfslogica (pure Kotlin)
│   ├── src/main/java/
│   │   ├── repository/     // *Interfaces* voor de repositories
│   │   └── usecase/        // Use cases (bv. `GetUserReposUseCase`)
│   └── build.gradle.kts    // Geen Android-afhankelijkheden!
│
└── build.gradle.kts        // Project-level build file
Waarom dit krachtig is:

Scheiding: De domain module weet niets van Android. Dit maakt de kernlogica puur en 100% testbaar.
Snelheid: Als je alleen de data-laag wijzigt, hoef je alleen de data module te herbouwen, niet de hele app.
Duidelijkheid: Het dwingt je om na te denken over de architectuur.
De Architectuur in Actie: Unidirectional Data Flow (UDF)
Dit is het hart van de app. De data stroomt altijd in één richting, wat de app voorspelbaar maakt.

UI Event: Gebruiker typt een naam en drukt op "Zoeken".
ViewModel: De GitHubViewModel ontvangt dit event.
Use Case: De ViewModel roept een GetUserReposUseCase aan met de gebruikersnaam.
Repository: De Use Case roept de GitHubRepository aan.
Data Flow in Repository:
Emit de data uit de Room database (voor directe UI-updates).
Start een network request met Retrofit naar de GitHub API.
Als de network request slaagt, sla de nieuwe data op in de Room database.
Room zal automatisch de datastroom (Flow) updaten, wat op zijn beurt de UI opnieuw zal triggeren.
UI State: De StateFlow in de ViewModel wordt bijgewerkt (bv. van Loading naar Success).
UI Update: Jetpack Compose observeert deze StateFlow en tekent de UI opnieuw met de nieuwe data.
Testing Strategie: Het Veiligheidsnet
Een professionele app is een geteste app.

Test Type
Tool
Wat we testen
Unit Tests	JUnit 5, MockK	- ViewModels: Werkt de state correct bij een event?
- Use Cases: Wordt de repository correct aangeroepen?
- Repository (isolated): Werkt de data-mapping correct?
  Integration Tests	JUnit 5, Room Testing	- DAO's: Wordt data correct opgeslagen en opgehaald uit de Room database?
- API Service: Kan de Retrofit service een succesvolle mock-response verwerken?
  UI Tests	Compose Testing, Espresso	- Gebruikersstromen: Werkt de volledige flow van zoeken tot het zien van resultaten?
- Componenten: Reageert de UI correct op klikken en input?

CI/CD: Automatisering met GitHub Actions
Dit is de ultieme "wow"-factor voor een junior. Elke keer dat je code pusht naar GitHub, gebeurt het volgende automatisch:

Build: De code wordt gecompileerd.
Test: Alle unit- en integratietests worden uitgevoerd.
Lint: De code wordt gecontroleerd op stijl- en potentiële fouten.
(Optioneel) APK Build: Als alles slaagt, wordt er een debug-APK gebouwd en als artifact beschikbaar gesteld.
Je hebt een yaml-bestand nodig in .github/workflows/ die dit proces definieert. Dit toont dat je begrijpt hoe moderne softwareontwikkeling werkt in een team.

Deployment & Distributie
Code Repository: GitHub. Zorg voor een schone README.md met screenshots, een uitleg van de tech stack en instructies om de app te bouwen.
Versiebeheer: Gebruik Git tags (bv. v1.0.0, v1.1.0) om releases te markeren.
Distributie: Maak voor elke tag een GitHub Release. Hier kun je het gesigneerde APK-bestand uploaden. Recruiters kunnen de app dan direct installeren zonder de code te hoeven compileren. Geef ze de link naar de GitHub Release-pagina.
De Volledige MVP Roadmap (4 Weken)
Week 1: Het Fundament.
Project opzetten met de multi-module structuur.
Alle dependencies toevoegen aan de build.gradle.kts files.
Hilt opzetten in de app module.
Data model (Repo, User) definiëren in de core:model module.
Room database en DAO's opzetten in de data module.
Week 2: Data & Logica.
Retrofit API-interface en Moshi-adapter bouwen in data:remote.
GitHubRepository implementeren in data:repository met de cache-eerst-logica.
GetUserReposUseCase aanmaken in domain.
GitHubViewModel bouwen in de app module, die de Use Case aanroept en een StateFlow beheert.
Week 3: De Gebruikersinterface.
Jetpack Compose thema opzetten met Material 3.
De drie schermen (Zoek, Lijst, Details) bouwen met "dummy data".
Navigation Compose opzetten om tussen de schermen te navigeren.
De UI koppelen aan de ViewModel en de StateFlow observeren.
Loading- en error-states implementeren.
Week 4: Polijsten, Testen & Deployen.
Unit- en integratietests schrijven voor de ViewModel, Use Case en Repository.
GitHub Actions workflow voor CI opzetten.
App finetunen: animaties, accessibility (contentDescription), error handling.
Eerste release (v1.0.0) taggen en een GitHub Release maken met het APK-bestand.