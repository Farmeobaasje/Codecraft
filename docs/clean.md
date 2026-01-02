De Gouden Regel: De Afhankelijkheidspijl
Onthoud dit simpele, visuele idee: Afhankelijkheidspijlen mogen alleen naar binnen wijzen.


      ----> [ :app ] ----> [ :data ]
             ^             ^
             |             |
             +----- [ :domain ] <-----+
De :app (Presentation) mag weten van de :domain en :data lagen.
De :data laag mag weten van de :domain laag.
De :domain laag mag niets weten van de andere twee. Het is het pure, onafhankelijke hart van de app.
Dit is niet zomaar een concept; we gaan dit afdwingen met concrete regels.

Hoe we dit handhaven: De Praktische Regels
Regel 1: De Module-Fortificatie (De Harde Muren)
De Gradle-modulestructuur is onze eerste en sterkste verdediger. We definiëren de afhankelijkheden strikt in de build.gradle.kts-bestanden.

:domain/build.gradle.kts:
kotlin

dependencies {
// Alleen pure Kotlin libraries
implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:...")
// GEEN Android-afhankelijkheden!
// implementation("androidx.lifecycle:lifecycle-viewmodel:...") // VERBODEN!
// implementation("androidx.room:room-runtime:...") // VERBODEN!
}
:data/build.gradle.kts:
kotlin

dependencies {
implementation(project(":domain")) // Data kent Domain
implementation("com.squareup.retrofit2:retrofit:...") // Data kent zijn eigen tools
implementation("androidx.room:room-runtime:...")
// Data kent de UI (app) NIET
// implementation(project(":app")) // VERBODEN!
}
:app/build.gradle.kts:
kotlin

dependencies {
implementation(project(":domain"))
implementation(project(":data"))
// App kent alles, en dat is oké. Het is de samensteller.
}
Resultaat: Als je per ongeluk een Android-klasse (zoals Context) probeert te gebruiken in de :domain module, zal de code niet compileren. De architectuur bescheramt zichzelf.

Regel 2: De Data-Karavaan (Geen Smokkelaars)
Data moet altijd op een gecontroleerde manier van laag naar laag reizen. We gebruiken hiervoor specifieke data-typen en mappers.

Gebruik geen "Platform-specifieke" data in de Domain Layer.
Fout: Een @Entity class in :domain.
Goed: Een pure data class Repo in :domain.
kotlin

// In :domain module
data class Repo(
val id: Long,
val name: String,
val description: String?,
val language: String?
)
Gebruik Data Transfer Objects (DTOs) en Mappers.
De :data laag definieert zijn eigen data-klassen die passen bij de bron.
kotlin

// In :data module, voor het netwerk
@Json(name = "id")
val id: Long,
@Json(name = "name")
val name: String,
// ...

// In :data module, voor de database
@Entity(tableName = "repos")
data class RepoEntity(
@PrimaryKey val id: Long,
val name: String,
// ...
)
We maken Mapper objecten (of extensiefuncties) om te converteren.
kotlin

// In :data module
fun RepoDto.toRepo(): Repo = Repo(
id = id,
name = name,
// ...
)
fun RepoEntity.toRepo(): Repo = Repo(
id = id,
name = name,
// ...
)
Waarom? Als de GitHub API een veld verandert, hoeven we alleen de RepoDto en de mapper aan te passen. De :domain laag en de :app laag blijven ongewijzigd. Dit is isolatie.

Regel 3: De Eenrichtingsverkeerswet (UDF)
Data en events stromen altijd in één richting: UI → ViewModel → Use Case → Repository → Data Source. De UI luistert naar reacties.

Hoe we data omhoog sturen (van UI naar Data):
De UI roept een functie aan op de ViewModel.
De ViewModel roept een suspend functie aan op een Use Case.
Hoe we data omlaag sturen (van Data naar UI):
De Repository stelt een Flow beschikbaar.
De Use Case kan deze Flow eventueel transformeren.
De ViewModel converteert de Flow naar een StateFlow voor de UI.
De UI "collecteert" deze StateFlow en update zichzelf.
Wat we NIET doen:

Geen callbacks van de Repository naar de ViewModel.
De Repository roept geen functies aan op de ViewModel.
De Use Case weet niets van StateFlow of LiveData. Het werkt met Flow of suspend functies.
Regel 4: De Verantwoordelijkheids-Scheiding (Het Juiste Gereedschap)
Elke component heeft één taak en gebruikt alleen de tools die daarvoor bedoeld zijn.

Component
Taak
Mag gebruiken
Mag NIET gebruiken
Composable	UI tekenen, user input vastleggen	@Composable functies, remember, ViewModel	Business logic, directe API calls
ViewModel	UI state beheren, Use Cases aanroepen	StateFlow, CoroutineScope, Use Cases	Context, directe Repository implementatie
Use Case	Eén stukje business logic uitvoeren	Repository (interface), Flow, Coroutines	Android Context, ViewModel
Repository	Data aanleveren (cache/netwerk)	DataSources, Mappers, Flow	ViewModel, Context (alleen via DataSource)
DataSource	Met één specifieke bron praten	Retrofit, Room DAO, OkHttp	Andere DataSources, business logic

