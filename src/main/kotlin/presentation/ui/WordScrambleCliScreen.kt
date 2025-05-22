package presentation.ui

import domain.player.PlayerManager
import domain.usecase.PlayWordScrambleGameUseCase
import kotlinx.coroutines.runBlocking

class WordScrambleCliScreen(
    private val playGame: PlayWordScrambleGameUseCase,
    private val playerManager: PlayerManager
) {
    private var score = 0
    private var hintsUsed = 0
    private val maxHints = 3
    private lateinit var currentPlayer: String

    fun start(): Unit = runBlocking {
        println(
            """
            **************************************
            *       DRAGON BALL WORD SCRAMBLE    *
            *   Unscramble the names to win!     *
            **************************************
        """.trimIndent()
        )

        currentPlayer = playerManager.getPlayerName().takeIf { it.isNotBlank() } ?: run {
            print("Enter your name: ")
            val name = readLine()?.takeIf { it.isNotBlank() } ?: "Player1"
            playerManager.savePlayerName(name)
            name
        }

        println("\nWelcome $currentPlayer! Let's begin!")
        playRound()
    }

    private fun playRound(): Unit = runBlocking {
        val character = playGame.getRandomCharacter()
        val scrambled = playGame.scrambleName(character.name)
        var revealed = 0
        var attempts = 5
        var guessedCorrectly = false

        println("\n\n=== NEW ROUND ===")
        println("💡 Race: ${character.race}")
        println("🔤 Scrambled Name: $scrambled")

        while (!guessedCorrectly && attempts > 0) {
            print("\nYour guess (attempt ${6 - attempts}/5) or type 'hint': ")
            val input = readLine()?.trim()?.uppercase()

            when {
                input == null -> continue
                input == "HINT" -> {
                    if (hintsUsed < maxHints) {
                        hintsUsed++
                        revealed++
                        val hint = playGame.generateHint(character.name, revealed)
                        println("🆘 Hint $hintsUsed: $hint")
                    } else {
                        println("❌ No more hints available!")
                    }
                }

                input == character.name.uppercase() -> {
                    guessedCorrectly = true
                    val pointsEarned = playGame.calculateScore(hintsUsed)
                    score += pointsEarned
                    println(
                        """
                        🎉 Correct! Answer: ${character.name}
                        💰 Points earned: $pointsEarned
                        🏆 Total score: $score

                        ${playGame.characterDetails(character)}
                    """.trimIndent()
                    )
                }

                else -> {
                    attempts--
                    println("❌ Wrong! Attempts left: $attempts")
                }
            }
        }

        if (!guessedCorrectly) {
            println("\n😢 Out of attempts! The answer was: ${character.name}")
        }

        print("\nPlay another round? (Y/N): ")
        when (readLine()?.uppercase()) {
            "Y" -> {
                hintsUsed = 0
                playRound()
            }

            else -> endGame()
        }
    }

    private fun endGame() {
        playerManager.saveScore(currentPlayer, score)
        val top = playerManager.getHighScores()
        println(
            """
            **************************************
            *           GAME OVER              *
            **************************************
            🏆 Final score: $score
            🏅 High Scores:
            ${top.entries.take(5).joinToString("\n") { (n, s) -> "${top.keys.indexOf(n) + 1}. $n: $s points" }}

            Thanks for playing, $currentPlayer!
        """.trimIndent()
        )
    }
}