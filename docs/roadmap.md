# CodeCraft - Project Roadmap (Phase 1: Android)

## 🎯 Visie
"CodeCraft" is een vlaggenschipportfolio-app dat de expertise van een moderne Android-ontwikkelaar showcase. Het is een demonstratie van professionele software-architectuur, een oog voor design en een meesterlijke uitvoering.

Onze filosofie: **Profesionele Elegantie**. We bouwen een strak, krachtig en verfijnd hulpmiddel voor ontwikkelaars.

## 🛠️ Tech Stack (Android)
| Categorie | Technologie | Reden |
|-----------|-------------|-------|
| **Taal** | Kotlin | De moderne standaard voor Android. |
| **UI** | Jetpack Compose | Declaratieve, snelle en toekomstbestendige UI-toolkit. |
| **Architectuur** | Clean Architecture + MVVM | Voor maximale testbaarheid, schaalbaarheid en onderhoudbaarheid. |
| **Asynchroon** | Kotlin Coroutines & Flow | Voor efficiënte en reactieve programmering. |
| **DI** | Hilt | De standaard voor dependency injection in Android. |
| **Netwerk** | Retrofit + OkHttp + Moshi | Een robuuste en efficiënte combinatie voor API-communicatie. |
| **Database** | Room + KSP | Voor een betrouwbare, lokale database met snelle codegeneratie. |
| **Navigatie** | Navigation Compose | Type-safe en geïntegreerde navigatie. |
| **Testing** | JUnit 5, MockK, Compose Testing | Voor een complete teststrategie. |
| **CI/CD** | GitHub Actions | Voor automatisering van builds en tests. |

## 📅 Phase 1: Gedetailleerde Roadmap (Android-Only)

### **Huidige Status: Week 1-4 Compleet, Week 5 Nog niet begonnen, Week 6 In Progress (40%)**

---

### **Week 1: Het Fundament - Een Solide Structuur**
**Doel:** Een schone, multi-module projectstructuur opzetten die klaar is voor ontwikkeling en onze architectuur afdwingt.

✅ **1.1 Project Initialisatie**  
✅ **1.2 Multi-Module Structuur Opzetten**  
✅ **1.3 Dependencies Configureren**  
✅ **1.4 Hilt & Application Setup**  
✅ **1.5 Data Models Definiëren**  
✅ **1.6 Documentatie Initialiseren**

**Definition of Done:** Het project compileert foutloos (`./gradlew build`). Alle modules zijn correct geconfigureerd. De Application klasse is ingesteld.

---

### **Week 2: Data & Logica - De Hersenen van de App**
**Doel:** De volledige data-laag bouwen, van netwerk tot lokale opslag, en de eerste use case implementeren.

✅ **2.1 Local Database (Room) Setup**  
✅ **2.2 Remote Data Source (Retrofit) Setup**  
✅ **2.3 Repository Implementatie**  
✅ **2.4 Mappers & Dependency Injection**  
✅ **2.5 Use Case Implementatie**  
🔲 **2.6 Unit Tests Schrijven**  
   - Schrijf unit tests voor GetUserReposUseCase met MockK om de GitHubRepository te mocken.
   - Schrijf unit tests voor RepoMapper.

**Definition of Done:** De data-laag is volledig functioneel. De unit tests voor de domain en data lagen slagen. De CHANGELOG.md is bijgewerkt.

---

### **Week 3: De Gebruikersinterface (UI) - Het Prachtplaatje**
**Doel:** De visuele laag bouwen met Jetpack Compose en naadloos koppelen aan de onderliggende logica.

✅ **3.1 Thema & Navigatie Setup**  
✅ **3.2 Zoekscherm & ViewModel**  
✅ **3.3 Repository Lijstscherm & ViewModel**  
✅ **3.4 Repository Detailscherm**  
✅ **3.5 Alles Verbinden**

**Definition of Done:** De volledige gebruikersflow werkt van begin tot eind met dynamische data. De app voelt responsief aan. De CHANGELOG.md is bijgewerkt.

---

### **Week 4: De Premium Sprint - Van Functioneel naar Verbluffend**
**Nieuwe Visie:** "CodeCraft" transformeren van een functionele tool naar een rijke, persoonlijke en visueel verbluffende ervaring die indruk maakt.

#### **De 3 Geavanceerde Functionaliteiten**

✅ **4.1 Settings & DataStore Foundation**  
   - ✅ Maak de `ThemeOptions` enum
   - ✅ Zet DataStore op in de `:data` module
   - ✅ Maak de `ThemeRepository` die de thema-voorkeur leest/schrijft
   - ✅ Bouw de basis `SettingsScreen` UI met de secties

✅ **4.2 Advanced Theme Implementation**  
   - ✅ Implementeer de thema-logica in `MainActivity` om de juiste `MaterialTheme` toe te passen op basis van de Flow uit de `ThemeRepository`
   - ✅ Koppel de RadioButtons in de `SettingsScreen` aan de `ThemeRepository`

✅ **4.3 Feature 1: Persoonlijke Repository Notities**  
   - ✅ Voeg de `RepoNote` tabel en `RepoNoteDao` toe aan Room
   - ✅ Implementeer de `AddNoteUseCase` en `GetNoteUseCase`
   - ✅ Voeg het notitie-icoon en de AlertDialog met TextField toe aan het `RepositoryDetailScreen`

✅ **4.4 Feature 2: GitHub Trending Repositories**  
   - ✅ Voeg de nieuwe `SearchResponseDto` en de GitHub Search API-call toe
   - ✅ Implementeer de `GetTrendingReposUseCase`
   - ✅ Bouw de `TrendingScreen` UI en voeg deze toe aan de navigatie
   - ✅ Voeg trending knop toe aan SearchScreen voor eenvoudige navigatie

✅ **4.5 UI Polish & Animations**  
   - ✅ **Staggered list animations** voor zowel RepoListScreen als TrendingScreen met fade-in en slide-in effecten
   - ✅ **Pull-to-refresh indicator refinements** met Material Design 3 PullToRefreshContainer
   - ✅ **Collapsing toolbar** op RepoDetailScreen met LargeTopAppBar en exitUntilCollapsedScrollBehavior
   - ✅ **Animated content size** voor repository cards met vloeiende size transitions

#### **Dat Beetje Extra: UI/UX Verfijningen**
- **Shared Element Transition**: Kaart groeit vloeiend uit tot header van detailscherm
- **Staggered List Animation**: Items faden in met vertraging (van boven naar beneden)
- **Collapsing Toolbar**: Titel en avatar inkrimpen bij scrollen
- **Pull-to-Refresh Animatie**: Subtiele animatie van CodeCraft-logo

#### **Instellingen Menu & Gebruikersvoorkeuren**
- **Appearance**: Thema-keuzes (Licht, Donker, Systeemstandaard)
- **Data & Storage**: "Cache leegmaken" knop
- **About**: App-versie, GitHub repository link, LinkedIn link

**Definition of Done:** De app heeft drie nieuwe, indrukwekkende features. Een volwaardig instellingenmenu is aanwezig. De UI bevat meerdere vloeiende animaties en voelt premium aan. De CHANGELOG.md is bijgewerkt met alle nieuwe toevoegingen.

---

### **Week 5: De GitHub-Native UI/UX Masterclass**
**Doel:** De app transformeren naar een state-of-the-art, visueel verbluffende ervaring die de look & feel van GitHub nabootst en functionaliteiten toevoegt die je zelfs op de website niet zo vindt.

#### **Het Authentieke GitHub Thema**
**Kleurenpalet:**
- Primaire (Achtergrond): #0D1117 (GitHub Dark) en #FFFFFF (GitHub Light)
- Accent (Actie/Links): #40C463 (GitHub Green)
- Oppervlakken (Kaarten): #161B22, #21262D, #30363D
- Borders: #30363D
- Tekst (Primair): #C9D1D9
- Tekst (Secundair): #8B949E
- Fout: #F85149 (GitHub Red)
- Waarschuwing: #D29922 (GitHub Orange)

#### **De 3 "WOW" Functionaliteiten**

🔲 **5.1 Het GitHub Thema Implementeren**  
   - Analyseer het GitHub kleurenpalet en definieer alle Color objecten.
   - Bouw de GitHubTheme.kt composable die het lichte en donkere thema beheert.
   - Pas het thema toe op alle bestaande schermen en componenten.

🔲 **5.2 Feature 1: Rich Markdown Rendering**  
   - Integreer de Markdown library (bijv. compose-markdown).
   - Voeg de /readme endpoint toe aan de GitHubApiService.
   - Implementeer de logica om de README op te halen en te cachen.
   - Vervang de Text composable op het RepositoryDetailScreen door de MarkdownText composable.

🔲 **5.3 Feature 2: Interactieve Taalgrafiek**  
   - Maak de grafiek in de InsightsScreen klikbaar.
   - Implementeer de state-logica in de InsightsViewModel om de geselecteerde taal bij te houden.
   - Voeg filtering-logica toe aan de RepoListViewModel en de GitHubRepository.
   - Zorg dat navigeren van Insights naar de lijst de juiste filter meegeeft.

🔲 **5.4 Feature 3: Lokale Bladwijzers (Bookmarks)**  
   - Voeg de isBookmarked kolom toe aan de RepoEntity en RepoDao.
   - Implementeer de toggleBookmarkUseCase en getBookmarkedReposUseCase.
   - Voeg het ster-icoon en de bijbehorende logica toe aan de list items en het detailsscherm.
   - Bouw het BookmarksScreen en voeg het toe aan de navigatie (bv. in de Profile-tab).

🔲 **5.5 Finale UI-Polijst & Animaties**  
   - Implementeer AnimatedContent voor soepele paginatransities.
   - Voeg subtiele scale en alpha animaties toe aan interactieve elementen.
   - Zorg voor een consistente typography en spacing die overeenkomt met GitHub's interface.

**Definition of Done:** De app heeft een authentiek GitHub-thema en drie geavanceerde features die de gebruikerservaring transformeren. De UI is visueel verbluffend en technisch indrukwekkend. De CHANGELOG.md is bijgewerkt.

---

### **Week 6: Polijsten, CI/CD & Deployen - De Finishing Touches**
**Doel:** De app transformeren van een werkend prototype naar een professioneel product en de wereld in sturen.

✅ **6.1 Visuele Polijst**  
   - ✅ **Dependencies bijgewerkt naar state-of-the-art versies**:
     - Compose BOM: 2025.01.00
     - Compose Compiler: 1.5.14
     - Compose Material 3: 1.2.1
     - Compose Navigation: 2.8.1
     - Compose Hilt Navigation: 1.2.0
     - Compose Foundation: 1.6.7
     - Compose UI: 1.6.7
     - Compose Runtime: 1.6.7
     - Compose Material Icons Extended: 1.6.7
     - Compose Animation: 1.6.7
     - Compose UI Tooling: 1.6.7
     - Compose UI Tooling Preview: 1.6.7
     - Compose UI Test: 1.6.7
     - Compose UI Test Manifest: 1.6.7
     - Compose UI Test JUnit4: 1.6.7
     - Compose UI Test JUnit5: 1.6.7
     - Compose UI Test Espresso: 1.6.7
   - ✅ **Experimental API warnings opgelost** met `@OptIn(ExperimentalFoundationApi::class)` annotaties
   - ✅ **Pull-to-refresh functionaliteit** geïmplementeerd in zowel RepoListScreen als TrendingScreen

🔲 **6.2 CI/CD Pipeline (GitHub Actions)**  
   - Vul `.github/workflows/ci.yml` met de stappen: checkout, setup-java, run gradle build, run gradle test
   - Push de code en verifieer dat de workflow succesvol draait
   - Add release automation for APK generation

🔲 **6.3 UI Tests**  
   - Schrijf een UI test die de volledige flow automatiseert: app opstarten, naam invoeren, op zoeken drukken, controleren of de lijst verschijnt, op een item tikken, controleren of het detailscherm verschijnt

🔲 **6.4 Deployment**  
   - Genereer een ondertekend APK-bestand
   - Maak een nieuwe tag in Git: `git tag v1.0.0`
   - Maak een nieuwe GitHub Release en upload het APK-bestand
   - Werk de README.md af met een link naar de release en screenshots

**Definition of Done:** De app is live op GitHub als een release. De CI/CD-pipeline werkt. De app is visueel verfijnd en toegankelijk. De CHANGELOG.md bevat de volledige geschiedenis.

---

## 📊 Voortgangsoverzicht

| Week | Status | Compleetheid | Belangrijkste Prestaties |
|------|--------|--------------|--------------------------|
| **Week 1** | ✅ Compleet | 100% | Multi-module structuur, Hilt setup, core models |
| **Week 2** | ⚠️ Bijna Compleet | 83% | Room, Retrofit, Repository, Use Cases (tests pending) |
| **Week 3** | ✅ Compleet | 100% | Volledige UI met alle schermen en navigatie |
| **Week 4** | ✅ Compleet | 100% | Premium features: Notities ✅, Trending ✅, Geavanceerd themabeheer ✅, Settings & DataStore ✅, UI Polish ✅ |
| **Week 5** | 🔲 Nog niet begonnen | 0% | GitHub Thema & 3 WOW Features |
| **Week 6** | ⚠️ In Progress | 40% | Dependencies bijgewerkt ✅, Experimental API warnings opgelost ✅, Pull-to-refresh geïmplementeerd ✅ |

**Totaal:** 72% compleet (Week 1-4 afgerond, Week 2.6, Week 5 volledig, en Week 6.2-6.4 nog te doen)

---

## 🎯 Prioriteiten voor Volgende Sessie
1. **Week 2.6**: Unit tests afronden voor GetUserReposUseCase en RepoMapper
2. **Week 5.1**: Het GitHub Thema Implementeren
3. **Week 5.2**: Rich Markdown Rendering toevoegen

---

## 📈 Success Metrics
- **Code Coverage**: >80% voor domain en data layers
- **Build Time**: < 2 minuten voor clean build
- **APK Size**: < 10MB voor debug, < 5MB voor release
- **UI Responsiveness**: < 100ms voor user interactions
- **Test Automation**: 100% van kritieke flows geautomatiseerd
