package di

import data.remote.characters.dataSource.CharacterRemoteDataSource
import data.remote.planets.datasource.PlanetRemoteDataSource
import org.koin.dsl.module

val dataSourceModule = module {
    single { CharacterRemoteDataSource(get()) }
    single { PlanetRemoteDataSource(get()) }
}
