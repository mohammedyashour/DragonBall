package presentation.ui

import domain.usecase.PlayQuizGameUseCase
import kotlinx.coroutines.runBlocking

class QuizGameCliScreen(private val playGame: PlayQuizGameUseCase) {
    private var currentScore = 0
    private var maxHints = 3
    private var hintsUsed = 0

    fun start(): Unit = runBlocking {
        println("""
            **************************************
            *  DRAGON BALL CHARACTER QUIZ GAME   *
            **************************************
        """.trimIndent())

        println("\nWelcome! Let's begin!")
        playRound()
    }

    private fun playRound(): Unit = runBlocking {
        val character = playGame.getRandomCharacter()
        println("\nGuess the Dragon Ball character!")

        val maxAttempts = 3
        var attempts = 0
        var guessedCorrectly = false

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
                    val pointsEarned = (maxHints - hintsUsed) * 10
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
        println("""
            **************************************
            *           GAME OVER               *
            **************************************
            🏆 Final score: $currentScore
            Thanks for playing!
        """.trimIndent())
    }
}
