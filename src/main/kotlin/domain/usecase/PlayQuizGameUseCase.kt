package domain.usecase


import domain.model.DragonBallCharacter
import domain.repository.CharacterRepository
import kotlin.random.Random

class PlayQuizGameUseCase(private val repository: CharacterRepository) {

    suspend fun getRandomCharacter(minId: Int = 1, maxId: Int = 50): DragonBallCharacter {
        val id = Random.nextInt(minId, maxId + 1)
        return repository.getCharacter(id)
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
}
