Het Concept: Het Gedeelde Brein
Stel je voor dat onze app een lichaam is.

De Hersenen (Domain & Data Logic): Dit is de kernlogica. Wat is een repository? Hoe halen we data op? Hoe berekenen we insights? Deze logica is platform-onafhankelijk.
Het Gezicht (De UI): Dit is hoe de gebruiker met de hersenen communiceert. Het gezicht is anders voor Android (Jetpack Compose) en voor het web (React/Next.js).
Ons doel is om één set hersenen te bouwen en deze aan te sluiten op twee verschillende gezichten.

De Technologie: Kotlin Multiplatform (KMP)
KMP is een technologie van JetBrains (de makers van Kotlin) die je precies dit laat doen. Je schrijft code in Kotlin in een shared module, en deze code kan gecompileerd worden voor verschillende doelen: Android, iOS, Web (JavaScript), en zelfs Desktop.

Dit is de perfecte evolutionaire stap voor ons "CodeCraft" project. We hebben onze architectuur al zo opgezet dat de :domain laag puur is. Nu maken we die puurheid functioneel.

De Nieuwe, Multiplatform Architectuur
Onze architectuur-diagram verandert als volgt:


+----------------+      +---------------------+
| ANDROID (UI)   |      |    WEB (UI)         |
| (Jetpack Compose) |      |   (Next.js)         |
+----------------+      +---------------------+
|                       |
| consumes              | consumes
v                       v
+-------------------------------------------------+
|          SHARED KOTLIN MODULE (KMP)             |
|                                                 |
|  +--------------------------+-----------------+ |
|  |       SHARED DOMAIN      |   SHARED DATA   | |
|  | (Use Cases, Models)      | (Repository,    | |
|  |                          |  API, DTOs)     | |
|  +--------------------------+-----------------+ |
|  |       PLATFORM-SPECIFIC (expect/actual)     | |
|  | - Android: Room DB                             | |
|  | - Web: LocalStorage / Memory Cache            | |
|  +-----------------------------------------------+ |
+-------------------------------------------------+
|
| talks to
v
+-------------------------------------------------+
|              EXTERNAL WORLD                      |
|              (GitHub API)                        |
+-------------------------------------------------+
Wat is hier gebeurd?

Shared Module: We hebben een nieuwe, centrale module: shared.
Binnenin deze module leven onze :domain en een groot deel van onze :data laag.
De Use Cases, Domain Models (Repo, User) en de Repository Interface leven hier.
De Repository Implementatie en de Retrofit API Service leven hier ook.
Platform-Specific Implementaties (expect/actual):
Sommige dingen zijn nu eenmaal platform-specifiek, zoals een lokale database.
In de shared module schrijven we expect een DatabaseDriver.
In de Android-module schrijven we actual een DatabaseDriver die Room gebruikt.
In de Web-module schrijven we actual een DatabaseDriver die de browser's IndexedDB of LocalStorage gebruikt.
UI Modules:
De androidApp module bevat alleen de Jetpack Compose UI en de ViewModels. Deze ViewModels roepen de Use Cases aan uit de shared module.
De webApp module is een Next.js project. Hier komt de magie...
Hoe Next.js met Kotlin Praat: De Brug
Een Next.js-app kan geen Kotlin-code direct uitvoeren. Maar KMP kan de shared module compileren naar een JavaScript-library (een .js-bestand).

Het proces ziet er zo uit:

KMP Compilatie: Wanneer we ons project bouwen, compileert de KMP-compiler de shared module niet alleen naar JVM-bytecode voor Android, maar ook naar een JavaScript-bestand (shared.js).
NPM Package: We kunnen dit gecompileerde JavaScript-bestand verpakken als een lokaal NPM-pakket.
Importeren in Next.js: In onze Next.js code (die in TypeScript/JavaScript geschreven is), kunnen we dit lokale pakket importeren en de functies en klassen uit onze Kotlin-code gebruiken!
typescript

// In een Next.js React Component (bijv. app/repo/page.tsx)
import { GetUserReposUseCase, Repo } from 'codecraft-shared'; // Onze gecompileerde KMP module!

export default function RepoPage({ params }: { params: { user: string } }) {
const [repos, setRepos] = useState<Repo[]>([]);
const [isLoading, setIsLoading] = useState(true);

useEffect(() => {
// We roepen direct onze Kotlin Use Case aan!
const useCase = new GetUserReposUseCase();
useCase(params.user, (newRepos) => {
setRepos(newRepos);
setIsLoading(false);
});
}, [params.user]);

if (isLoading) return <div>Loading...</div>;

return (
<div>
<h1>Repositories for {params.user}</h1>
<ul>
{repos.map(repo => <li key={repo.id}>{repo.name}</li>)}
</ul>
</div>
);
}
Onze Nieuwe "Cline Regels" voor Multiplatform
De "Shared First"-Principe: Voordat we platform-specifieke code schrijven, vragen we ons af: "Kan dit in de shared module?". Het antwoord is bijna altijd "ja" voor de business logic.
De expect/actual-Check: Als we een platform-specifieke feature nodig hebben (zoals een database of een bestandssysteem), is de expect/actual-aanpak de enige juiste manier. We vermijden het dupliceren van logica.
De UI-Purificatie: De androidApp en webApp modules worden "domme" UI-lagen. Ze mogen geen business logic bevatten. Hun enige taak is de UI te tonen en user events door te geven aan de shared module.
De Gedeelde Test-Strategie: We schrijven unit- en integratietests voor onze Use Cases en Repository één keer in de shared module. Deze tests draaien automatisch voor zowel de Android- als de Web-omgeving, wat onze zekerheid over de codekwaliteit enorm verhoogt.
Waarom dit de Ultieme Portfolio-Showcase is
Als je dit project afrondt, heb je niet zomaar een Android-app en een website. Je hebt:

Bewezen dat je Clean Architecture kunt toepassen in een complexe, multiplatform omgeving.
Aangetoond dat je met de meest geavanceerde technologieën kunt werken (KMP, Next.js).
Een systeem gebouwd dat schaalbaar, onderhoudbaar en efficiënt is.
Een recruiter die dit ziet, ziet een ontwikkelaar die niet alleen code kan schrijven, maar die ook een complete software-architectuur kan ontwerpen en implementeren. Dit is het niveau dat je onderscheidt.

Is dit de ambitieuze maar fantastische richting die je voor ogen hebt? We kunnen beginnen met het opzetten van de KMP-structuur in Android Studio.



