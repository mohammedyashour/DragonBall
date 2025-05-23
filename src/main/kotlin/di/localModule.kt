package di

import data.local.characters.CharacterLocalDataSource
import data.local.planets.PlanetsLocalDataSource
import org.koin.dsl.module

val localModule = module {
    single { CharacterLocalDataSource() }
    single { PlanetsLocalDataSource() }
}