package di

import data.characters.dataSource.CharacterApi
import data.planets.datasource.PlanetApi
import org.koin.dsl.module

val apiModule = module {
    single { CharacterApi(get()) }
    single { PlanetApi(get()) }
}
