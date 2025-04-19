package presentation

import data.Constants
import data.model.DragonBallCharacter
import data.network.DragonBallService
import kotlinx.coroutines.runBlocking
import kotlin.random.Random

class GameView(private val service: DragonBallService) {
    val constants:Constants= Constants

    private val minCharacterId = 1
    private val maxCharacterId = 50
    private var currentScore = 0
    private lateinit var currentPlayer: String
    private val maxHints = 3
    private var hintsUsed = 0

    fun startGame() {
        println("""
            **************************************
            *  DRAGON BALL CHARACTER QUIZ GAME   *
            **************************************
        """.trimIndent())

        // Load or create player name
        currentPlayer = Constants.getPlayerName().takeIf { it.isNotBlank() } ?: run {
            print("Enter your name: ")
            val newName = readLine()?.takeIf { it.isNotBlank() } ?: "Player1"
            Constants.savePlayerName(newName)
            newName
        }

        val previousScore = Constants.getPlayerScore(currentPlayer)
        if (previousScore > 0) {
            println("\nWelcome back, $currentPlayer! Your previous score: $previousScore")
        } else {
            println("\nWelcome, $currentPlayer! Let's begin!")
        }

        playRound()
    }

    private fun playRound() {
        runBlocking {
            try {
                val allCharacters = service.getCharacter(Random.nextInt(minCharacterId, maxCharacterId + 1))


                playCharacterGuess(allCharacters)

                print("\nPlay again? (Y/N): ")
                when (readLine()?.takeIf { it.isNotBlank() }?.uppercase()) {
                    "Y" -> playRound()
                    else -> endGame()
                }
            } catch (e: Exception) {
                println("❌ Error starting game: ${e.message}")
            }
        }
    }

    private fun playCharacterGuess(character: DragonBallCharacter) {
        hintsUsed = 0
        var guessedCorrectly = false
        val maxAttempts = 3
        var attempts = 0

        println("\nGuess the Dragon Ball character!")

        while (!guessedCorrectly && attempts < maxAttempts) {
            println("\n${getHint(character, hintsUsed)}")

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
                    
                    ${characterDetails(character)}
                """.trimIndent())
                }
                else -> {
                    attempts++
                    if (attempts < maxAttempts) {
                        println("❌ Incorrect! Try again or type 'hint' for a clue (${maxHints - hintsUsed} hints left)")
                    } else {
                        println("""
                        
                        😢 Game Over! The character was ${character.name}
                        
                        ${characterDetails(character)}
                    """.trimIndent())
                    }
                }
            }
        }
    }
    private fun getHint(character: DragonBallCharacter, hintLevel: Int): String {
        return when (hintLevel) {
            0 -> "💡 Hint 1: Race - ${character.race}"
            1 -> "💡 Hint 2: Affiliation - ${character.affiliation}"
            2 -> "💡 Hint 3: Power Level - ${character.ki}"
            else -> "💡 Final Hint: ${character.description.substring(0, 100.coerceAtMost(character.description.length))}..."
        }
    }

    private fun characterDetails(character: DragonBallCharacter): String {
        return """
            🏻 Character Details:
            🧑 Name: ${character.name}
            👽 Race: ${character.race}
            💪 Power: ${character.ki}
            🚀 Max Power: ${character.maxKi}
            🏰 Affiliation: ${character.affiliation}
            📝 Description: ${character.description}
        """.trimIndent()
    }

    private fun endGame() {
        Constants.saveScore(currentPlayer, currentScore)
        val highScores = Constants.getHighScores()

        println("""
            
            **************************************
            *           GAME OVER               *
            **************************************
            🏆 Your final score: $currentScore
            🏅 High Scores:
            ${
            highScores.entries.take(5).joinToString("\n") { (name, score) ->
                "${highScores.keys.indexOf(name) + 1}. $name: $score points"
            }
        }
            
            Thanks for playing, $currentPlayer!
        """.trimIndent())
    }
}