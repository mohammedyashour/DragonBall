package di

import data.characters.repository.CharacterRepositoryImpl
import data.planets.repository.PlanetRepositoryImpl
import domain.repository.CharacterRepository
import domain.repository.PlanetRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<CharacterRepository> { CharacterRepositoryImpl(get()) }
    single<PlanetRepository> { PlanetRepositoryImpl(get()) }
}
