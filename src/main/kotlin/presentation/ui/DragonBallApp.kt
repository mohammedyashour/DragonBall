package presentation.ui

import presentation.ui.screens.AboutDeveloperCliScreen
import presentation.ui.screens.CharacterCliScreen
import presentation.ui.screens.LeaderboardCliScreen
import presentation.ui.screens.PlanetCliScreen
import presentation.ui.screens.QuizGameCliScreen
import presentation.ui.screens.WordScrambleCliScreen
import presentation.utils.BoxSymbols
import presentation.utils.Symbols
import presentation.utils.TerminalColor
import presentation.utils.withStyle

class DragonBallApp(
    private val characterScreen: CharacterCliScreen,
    private val planetScreen: PlanetCliScreen,
    private val quizGame: QuizGameCliScreen,
    private val wordScrambleGame: WordScrambleCliScreen,
    private val aboutDeveloperCliScreen: AboutDeveloperCliScreen,
    private val leaderboardCliScreen: LeaderboardCliScreen

) : UILauncher {
    override suspend fun start() {
        val actions = listOf(
            UiAction("${Symbols.Person} Get Character by ID") {
                val id = promptForId("Enter Character ID")
                characterScreen.showCharacter(id)
            },
            UiAction("${Symbols.Astronaut} Show All Characters") {
                characterScreen.showAllCharactersPaginated()
            },
            UiAction("${Symbols.Planet} Get Planet by ID") {
                val id = promptForId("Enter Planet ID")
                planetScreen.showPlanet(id)
            },
            UiAction("${Symbols.Earth} Show All Planets") {
                planetScreen.showAllPlanetsPaginated()
            },
            UiAction("${Symbols.Game} Quiz Game") {
                quizGame.start()
            },
            UiAction("${Symbols.Scramble} Word Scramble Game") {
                wordScrambleGame.start()
            },
            UiAction("${Symbols.Cup} Leaderboard") {
                leaderboardCliScreen.start()
            },
            UiAction("${Symbols.Developer} About Developer") {
                aboutDeveloperCliScreen.start()
            },
            UiAction("${Symbols.Exit} Exit") {
                println("👋 Goodbye! May your power be over 9000!".withStyle(TerminalColor.Green))
                return@UiAction
            }
        )

        while (true) {
            printMenu(actions)
            val choice = readLine()?.toIntOrNull()?.takeIf { it in 1..actions.size }
            if (choice != null) {
                println("\n➡ You selected: ${actions[choice - 1].name}".withStyle(TerminalColor.Cyan))
                actions[choice - 1].action()
                println("\n🔄 Press Enter to return to menu...".withStyle(TerminalColor.Reset))
                readLine()
            } else {
                println("❌ Invalid choice. Try again.".withStyle(TerminalColor.Red))
            }
        }
    }

    private fun promptForId(label: String): Int {
        print("$label: ".withStyle(TerminalColor.Yellow))
        return readLine()?.toIntOrNull() ?: 1
    }

    private fun printMenu(actions: List<UiAction>) {
        val boxWidth = 50
        val borderColor = TerminalColor.Blue

        println(BoxSymbols.TopLeft + BoxSymbols.Horizontal.repeat(boxWidth - 2) + BoxSymbols.TopRight.withStyle(borderColor))
        println(BoxSymbols.Vertical + " Dragon Ball CLI Menu".padEnd(boxWidth - 2) + BoxSymbols.Vertical.withStyle(borderColor))
        println(BoxSymbols.VerticalLeft + BoxSymbols.Horizontal.repeat(boxWidth - 2) + BoxSymbols.VerticalRight.withStyle(borderColor))

        actions.forEachIndexed { i, action ->
            val line = "${(i + 1).toString().padStart(2, '0')}. ${action.name}".padEnd(boxWidth - 2)
            println(BoxSymbols.Vertical + " $line" + BoxSymbols.Vertical.withStyle(TerminalColor.entries.random()))
        }

        println(BoxSymbols.BottomLeft + BoxSymbols.Horizontal.repeat(boxWidth - 2) + BoxSymbols.BottomRight.withStyle(borderColor))
        print("\n👉 Enter your choice (1-${actions.size}): ".withStyle(TerminalColor.Yellow))
    }
}
