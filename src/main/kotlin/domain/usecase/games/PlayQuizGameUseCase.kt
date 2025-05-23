package domain.usecase.games


import domain.model.DragonBallCharacter
import domain.repository.CharacterRepository
import kotlin.random.Random

class PlayQuizGameUseCase(private val characterRepository: CharacterRepository) {

    suspend fun getRandomCharacter(): DragonBallCharacter {
        val allCharacters = characterRepository.getAllCharacters()
        if (allCharacters.isEmpty()) {
            throw IllegalStateException("❌ No characters available to choose from.")
        }
        return allCharacters.random()
    }


    fun getHint(character: DragonBallCharacter, level: Int): String {
        return when (level) {
            0 -> "💡 Hint 1: Race - ${character.race}"
            1 -> "💡 Hint 2: Affiliation - ${character.affiliation}"
            2 -> "💡 Hint 3: Power Level - ${character.ki}"
            else -> "💡 Final Hint: ${character.description.take(100)}..."
        }
    }

    fun characterDetails(character: DragonBallCharacter): String {
        return """
            🧑 Name: ${character.name}
            👽 Race: ${character.race}
            💪 Power: ${character.ki}
            🚀 Max Power: ${character.maxKi}
            🏰 Affiliation: ${character.affiliation}
            📝 Description: ${character.description}
        """.trimIndent()
    }

    fun calculateScore(hintsUsed: Int, maxHints: Int = 3): Int {
        return (maxHints - hintsUsed) * 10
    }
}
