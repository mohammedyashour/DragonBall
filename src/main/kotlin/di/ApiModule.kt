package di

import data.remote.characters.dataSource.CharacterApi
import data.remote.planets.datasource.PlanetApi
import org.koin.dsl.module

val apiModule = module {
    single { CharacterApi(get()) }
    single { PlanetApi(get()) }
}
