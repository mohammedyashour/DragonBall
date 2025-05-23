package di

import CharacterRepositoryImpl
import data.remote.planets.repository.PlanetRepositoryImpl
import domain.repository.CharacterRepository
import domain.repository.PlanetRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<CharacterRepository> {
        CharacterRepositoryImpl(
            get(),
            get()
        )
    }
    single<PlanetRepository>
    {
        PlanetRepositoryImpl(
            get(),
            get()
        )
    }
}
