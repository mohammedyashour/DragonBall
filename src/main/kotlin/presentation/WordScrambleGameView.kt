package presentation


import data.Constants
import data.model.DragonBallCharacter
import data.network.DragonBallService
import kotlinx.coroutines.runBlocking
import kotlin.random.Random

class WordScrambleGame(private val service: DragonBallService) {
    private var score = 0
    private lateinit var currentPlayer: String
    private val maxHints = 3
    private var hintsUsed = 0
    private val minCharacterId = 1
    private val maxCharacterId = 50

    fun startGame() {
        println("""
            **************************************
            *       DRAGON BALL WORD SCRAMBLE    *
            *   Unscramble the names to win!     *
            **************************************
        """.trimIndent())

        currentPlayer = Constants.getPlayerName().takeIf { it.isNotBlank() } ?: run {
            print("Enter your name: ")
            val newName = readLine()?.takeIf { it.isNotBlank() } ?: "Player1"
            Constants.savePlayerName(newName)
            newName
        }

        println("\nWelcome $currentPlayer! Let's begin!")
        playRound()
    }

    private fun playRound() {
        runBlocking {
            try {
                val character = service.getCharacter(Random.nextInt(minCharacterId, maxCharacterId + 1))
                val scrambledName = scrambleName(character.name)
                var revealedLetters = 0

                println("\n\n=== NEW ROUND ===")
                println("💡 Race: ${character.race}")
                println("🔤 Scrambled Name: $scrambledName")

                var guessedCorrectly = false
                val maxAttempts = 5
                var attempts = 0

                while (!guessedCorrectly && attempts < maxAttempts) {
                    print("\nYour guess (attempt ${attempts + 1}/$maxAttempts) or type 'hint': ")
                    val input = readLine()?.trim()?.uppercase()

                    when {
                        input == null -> continue
                        input == "HINT" -> {
                            if (hintsUsed < maxHints) {
                                hintsUsed++
                                revealedLetters++
                                val hint = getHint(character.name, revealedLetters)
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
                                
                                ${characterDetails(character)}
                            """.trimIndent())
                        }
                        else -> {
                            attempts++
                            if (attempts < maxAttempts) {
                                println("❌ Wrong! Try again (${maxAttempts - attempts} attempts left)")
                            }
                        }
                    }
                }

                if (!guessedCorrectly) {
                    println("\n😢 Out of attempts! The answer was: ${character.name}")
                }

                print("\nPlay another round? (Y/N): ")
                when (readLine()?.takeIf { it.isNotBlank() }?.uppercase()) {
                    "Y" -> {
                        hintsUsed = 0
                        playRound()
                    }
                    else -> endGame()
                }
            } catch (e: Exception) {
                println("❌ Error loading character: ${e.message}")
            }
        }
    }

    private fun scrambleName(name: String): String {
        return name.uppercase().toCharArray().apply {
            for (i in indices) {
                val j = Random.nextInt(i, size)
                val temp = this[i]
                this[i] = this[j]
                this[j] = temp
            }
        }.concatToString()
    }

    private fun getHint(name: String, lettersToShow: Int): String {
        val hint = StringBuilder()
        for (i in name.indices) {
            hint.append(if (i < lettersToShow) name[i].uppercase() else "_")
            hint.append(" ")
        }
        return hint.toString()
    }

    private fun characterDetails(character: DragonBallCharacter): String {
        return """
            Character Details:
            🏻 Name: ${character.name}
            👽 Race: ${character.race}
            💪 Power: ${character.ki}
            🏰 Affiliation: ${character.affiliation}
            📝 Description: ${character.description.take(100)}...
        """.trimIndent()
    }

    private fun endGame() {
        println("""
            
            **************************************
            *           GAME OVER              *
            **************************************
            🏆 Your final score: $score
            Thanks for playing, $currentPlayer!
        """.trimIndent())
    }
}