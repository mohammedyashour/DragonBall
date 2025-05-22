package domain.usecase


import domain.model.DragonBallCharacter
import domain.repository.CharacterRepository
import kotlin.random.Random

class PlayWordScrambleGameUseCase(
    private val repository: CharacterRepository
) {
    suspend fun getRandomCharacter(minId: Int = 1, maxId: Int = 50): DragonBallCharacter {
        val randomId = Random.nextInt(minId, maxId + 1)
        return repository.getCharacter(randomId)
    }

    fun scrambleName(name: String): String {
        return name.uppercase().toCharArray().apply {
            for (i in indices) {
                val j = Random.nextInt(i, size)
                val temp = this[i]
                this[i] = this[j]
                this[j] = temp
            }
        }.concatToString()
    }

    fun generateHint(name: String, lettersToShow: Int): String {
        return name.mapIndexed { i, c ->
            if (i < lettersToShow) c.uppercaseChar() else '_'
        }.joinToString(" ")
    }

    fun characterDetails(character: DragonBallCharacter): String {
        return """
            Character Details:
            🧑 Name: ${character.name}
            👽 Race: ${character.race}
            💪 Power: ${character.ki}
            🏰 Affiliation: ${character.affiliation}
            📝 Description: ${character.description.take(100)}...
        """.trimIndent()
    }
}
