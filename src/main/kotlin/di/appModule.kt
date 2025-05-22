package di

import data.characters.dataSource.CharacterApi
import data.characters.dataSource.CharacterRemoteDataSource
import data.characters.repository.CharacterRepositoryImpl
import data.planets.datasource.PlanetApi
import data.planets.datasource.PlanetRemoteDataSource
import data.planets.repository.PlanetRepositoryImpl
import domain.repository.CharacterRepository
import domain.repository.PlanetRepository
import domain.usecase.FilterCharactersUseCase
import domain.usecase.GetCharacterUseCase
import domain.usecase.GetCharactersPaginatedUseCase
import domain.usecase.GetPlanetUseCase
import domain.usecase.GetPlanetsPaginatedUseCase
import domain.usecase.PlayQuizGameUseCase
import domain.usecase.PlayWordScrambleGameUseCase
import domain.usecase.SearchPlanetsByNameUseCase
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.koin.dsl.module
import presentation.ui.CharacterCliScreen
import presentation.ui.DragonBallApp
import presentation.ui.PlanetCliScreen
import presentation.ui.QuizGameCliScreen
import presentation.ui.WordScrambleCliScreen

val appModule = module {
    single {
        HttpClient(CIO) {
            install(ContentNegotiation) {
                json(get())
            }
        }
    }

    single {
        Json { ignoreUnknownKeys = true }
    }
    // APIs
    single { CharacterApi(get()) }
    single { PlanetApi(get()) }

    // Remote Data Sources
    single { CharacterRemoteDataSource(get()) }
    single { PlanetRemoteDataSource(get()) }

    // Repositories
    single<CharacterRepository> { CharacterRepositoryImpl(get()) }
    single<PlanetRepository> { PlanetRepositoryImpl(get()) }

    // Use Cases
    single { GetCharacterUseCase(get()) }
    single { GetCharactersPaginatedUseCase(get()) }
    single { FilterCharactersUseCase(get()) }
    single { GetPlanetUseCase(get()) }
    single { GetPlanetsPaginatedUseCase(get()) }
    single { SearchPlanetsByNameUseCase(get()) }
    single { PlayQuizGameUseCase(get()) }
    single { PlayWordScrambleGameUseCase(get()) }

    // CLI Screens
    single { CharacterCliScreen(get(), get()) }
    single { PlanetCliScreen(get(), get(), get()) }
    single { QuizGameCliScreen(get()) }
    single { WordScrambleCliScreen(get()) }

    // Launcher App (optional)
    single {
        DragonBallApp(
            get(), // CharacterCliScreen
            get(), // PlanetCliScreen
            get(), // QuizGameCliScreen
            get()  // WordScrambleCliScreen
        )
    }
}
