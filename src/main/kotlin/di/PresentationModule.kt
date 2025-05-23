package di

import org.koin.dsl.module
import presentation.ui.DragonBallApp
import presentation.ui.screens.*

val presentationModule = module {
    single {
        CharacterCliScreen(
            get(), get()
        )
    }
    single {
        PlanetCliScreen(
            get(), get(), get()
        )
    }
    single {
        QuizGameCliScreen(
            get(), get()
        )
    }
    single {
        WordScrambleCliScreen(
            get(), get()
        )
    }
    single { AboutDeveloperCliScreen() }
    single { LeaderboardCliScreen(get()) }
    single {
        DragonBallApp(
            get(),
            get(),
            get(),
            get(),
            get(),
            get()
        )
    }
}
