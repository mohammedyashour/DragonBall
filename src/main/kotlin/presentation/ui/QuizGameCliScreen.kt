package presentation.ui

import domain.player.PlayerManager
import domain.usecase.PlayQuizGameUseCase
import kotlinx.coroutines.runBlocking

class QuizGameCliScreen(
    private val playGame: PlayQuizGameUseCase,
    private val playerManager: PlayerManager
) {
    private var currentScore = 0
    private var hintsUsed = 0
    private val maxHints = 3
    private lateinit var currentPlayer: String

    fun start(): Unit = runBlocking {
        println("""
            **************************************
            *  DRAGON BALL CHARACTER QUIZ GAME   *
            **************************************
        """.trimIndent())

        currentPlayer = playerManager.getPlayerName().takeIf { it.isNotBlank() } ?: run {
            print("Enter your name: ")
            val name = readLine()?.takeIf { it.isNotBlank() } ?: "Player1"
            playerManager.savePlayerName(name)
            name
        }

        val previous = playerManager.getPlayerScore(currentPlayer)
        if (previous > 0) {
            println("\n🔁 Welcome back, $currentPlayer! Previous score: $previous")
        }

        playRound()
    }

    private fun playRound(): Unit = runBlocking {
        val character = playGame.getRandomCharacter()
        val maxAttempts = 3
        var attempts = 0
        var guessedCorrectly = false

        println("\nGuess the Dragon Ball character!")

        while (!guessedCorrectly && attempts < maxAttempts) {
            println("\n${playGame.getHint(character, hintsUsed)}")

            print("Your guess (attempt ${attempts + 1}/$maxAttempts): ")
            val input = readLine()?.trim()?.lowercase()

            when {
                input == null -> continue
                input == "hint" -> {
                    if (hintsUsed < maxHints) {
                        hintsUsed++
                        println("🆘 Hint used! (${maxHints - hintsUsed} hints remaining)")
                    } else {
                        println("❌ No more hints available!")
                    }
                    continue
                }
                input == character.name.lowercase() -> {
                    guessedCorrectly = true
                    val pointsEarned = playGame.calculateScore(hintsUsed)
                    currentScore += pointsEarned
                    println("""
                        🎉 CORRECT! It's ${character.name}!
                        💰 Points earned: $pointsEarned
                        🏆 Total score: $currentScore

                        ${playGame.characterDetails(character)}
                    """.trimIndent())
                }
                else -> {
                    attempts++
                    if (attempts < maxAttempts) {
                        println("❌ Incorrect! Try again or type 'hint' for a clue (${maxHints - hintsUsed} hints left)")
                    } else {
                        println("""
                            😢 Game Over! The character was ${character.name}
                            ${playGame.characterDetails(character)}
                        """.trimIndent())
                    }
                }
            }
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
        playerManager.saveScore(currentPlayer, currentScore)
        val top = playerManager.getHighScores()
        println("""
            **************************************
            *           GAME OVER               *
            **************************************
            🏆 Final score: $currentScore
            🏅 High Scores:
            ${top.entries.take(5).joinToString("\n") { (n, s) -> "${top.keys.indexOf(n) + 1}. $n: $s points" }}

            Thanks for playing, $currentPlayer!
        """.trimIndent())
    }
}