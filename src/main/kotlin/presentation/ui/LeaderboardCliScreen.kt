package presentation.ui


import domain.player.GetLeaderboardUseCase
import presentation.utils.TerminalColor
import presentation.utils.withStyle

class LeaderboardCliScreen(
    private val getLeaderboardUseCase: GetLeaderboardUseCase
) : UILauncher {

    override suspend fun start() {
        val scores = getLeaderboardUseCase.execute()

        println("""
            ${"🏆 Leaderboard".withStyle(TerminalColor.Green)}
            ${"=".repeat(30)}
        """.trimIndent())

        if (scores.isEmpty()) {
            println("${"No players found yet.".withStyle(TerminalColor.Red)}\n")
        } else {
            scores.entries.sortedByDescending { it.value }.forEachIndexed { index, entry ->
                println("${(index + 1).toString().padEnd(2)}. ${entry.key.padEnd(15)} ➜ ${entry.value} pts")
            }
        }

        println("\n🔁 Press Enter to return to menu...")
        readlnOrNull()
    }
}
