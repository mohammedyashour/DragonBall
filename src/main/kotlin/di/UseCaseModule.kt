package di

import domain.player.GetLeaderboardUseCase
import domain.player.PlayerManager
import domain.usecase.characters.FilterCharactersUseCase
import domain.usecase.characters.GetCharacterUseCase
import domain.usecase.characters.GetCharactersPaginatedUseCase
import domain.usecase.games.PlayQuizGameUseCase
import domain.usecase.games.PlayWordScrambleGameUseCase
import domain.usecase.planets.GetPlanetUseCase
import domain.usecase.planets.GetPlanetsPaginatedUseCase
import domain.usecase.planets.SearchPlanetsByNameUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single { GetCharacterUseCase(get()) }
    single { GetCharactersPaginatedUseCase(get()) }
    single { FilterCharactersUseCase(get()) }
    single { GetPlanetUseCase(get()) }
    single { GetPlanetsPaginatedUseCase(get()) }
    single { SearchPlanetsByNameUseCase(get()) }
    single { PlayQuizGameUseCase(get()) }
    single { PlayWordScrambleGameUseCase(get()) }
    single { GetLeaderboardUseCase(get()) }
    single { PlayerManager }
}
