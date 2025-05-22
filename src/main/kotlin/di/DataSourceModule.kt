package di

import data.characters.dataSource.CharacterRemoteDataSource
import data.planets.datasource.PlanetRemoteDataSource
import org.koin.dsl.module

val dataSourceModule = module {
    single { CharacterRemoteDataSource(get()) }
    single { PlanetRemoteDataSource(get()) }
}
