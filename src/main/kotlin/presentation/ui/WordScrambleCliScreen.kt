package presentation.ui

import domain.usecase.PlayWordScrambleGameUseCase
import kotlinx.coroutines.runBlocking

class WordScrambleCliScreen(
    private val playGame: PlayWordScrambleGameUseCase
) {
    private var score = 0
    private var hintsUsed = 0
    private val maxHints = 3

    suspend fun start() = runBlocking {
        println("""
            **************************************
            *       DRAGON BALL WORD SCRAMBLE    *
            *   Unscramble the names to win!     *
            **************************************
        """.trimIndent())

        playRound()
    }

    private suspend fun playRound() {
        val character = playGame.getRandomCharacter()
        val scrambled = playGame.scrambleName(character.name)
        var revealed = 0
        var attempts = 5
        var guessedCorrectly = false

        println("\n💡 Race: ${character.race}")
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
                    val pointsEarned = 50 - (hintsUsed * 10)
                    score += pointsEarned
                    println("""
                        🎉 Correct! Answer: ${character.name}
                        💰 Points earned: $pointsEarned
                        🏆 Total score: $score

                        ${playGame.characterDetails(character)}
                    """.trimIndent())
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
        println("""
            **************************************
            *           GAME OVER              *
            **************************************
            🏆 Final score: $score
            Thanks for playing!
        """.trimIndent())
    }
}
