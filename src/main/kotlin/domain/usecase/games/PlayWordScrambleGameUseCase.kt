package domain.usecase.games

import domain.model.DragonBallCharacter
import domain.repository.CharacterRepository
import kotlin.random.Random

class PlayWordScrambleGameUseCase(private val characterRepository: CharacterRepository) {

    suspend fun getRandomCharacter(): DragonBallCharacter {
        val allCharacters = characterRepository.getAllCharacters()
        if (allCharacters.isEmpty()) {
            throw IllegalStateException("❌ No characters available to choose from.")
        }
        return allCharacters.random()
    }


    fun scrambleName(name: String): String {
        return name.uppercase().toCharArray().apply {
            for (i in indices) {
                val j = Random.nextInt(i, size)
                val tmp = this[i]
                this[i] = this[j]
                this[j] = tmp
            }
        }.concatToString()
    }

    fun generateHint(name: String, lettersToShow: Int): String {
        val hint = StringBuilder()
        for (i in name.indices) {
            hint.append(if (i < lettersToShow) name[i].uppercase() else "_")
            hint.append(" ")
        }
        return hint.toString()
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

    fun calculateScore(hintsUsed: Int, maxHints: Int = 3): Int {
        return 50 - (hintsUsed * 10)
    }
}