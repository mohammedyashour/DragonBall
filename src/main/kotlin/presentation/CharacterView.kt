package presentation

import data.model.DragonBallCharacter
import data.network.DragonBallService
import kotlinx.coroutines.runBlocking

class CharacterView(private val service: DragonBallService) {

    fun showCharacter(id: Int) {
        runBlocking {
            try {
                val character = service.getCharacter(id)
                printCharacterDetails(character)
            } catch (e: Exception) {
                println("❌ Error fetching character: ${e.message}")
            }
        }
    }

    fun showAllCharactersPaginated() {
        runBlocking {
            try {
                var currentPage = 1
                val pageSize = 10
                var totalCharacters = 0
                var allCharactersFetched = false

                while (!allCharactersFetched) {
                    val response = service.getCharactersPaginated(currentPage, pageSize)
                    val characters = response.items
                    response.meta?.let { totalCharacters = it.totalItems }

                    println("\n🌟 === Characters ${(currentPage-1)*pageSize + 1}-${(currentPage-1)*pageSize + characters.size} of $totalCharacters ===")
                    characters.forEachIndexed { index, character ->
                        val powerLevel = when {
                            character.ki > 9000.toString() -> "💥 OVER 9000!"
                            character.ki > 5000.toString() -> "🔥 ${character.ki}"
                            else -> "⚡ ${character.ki}"
                        }
                        println("${(currentPage-1)*pageSize + index + 1}. ${character.name} (${character.race.emoji()} ${character.race}) - Power: $powerLevel")
                    }

                    if (currentPage * pageSize >= totalCharacters) {
                        allCharactersFetched = true
                        println("\n✅ All characters displayed!")
                    } else {
                        print("\n⏭ Press any key to show next 10 characters (or 'q' to quit)... ")
                        val input = readLine()
                        if (input.equals("q", ignoreCase = true)) {
                            return@runBlocking
                        }
                        currentPage++
                    }
                }
            } catch (e: Exception) {
                println("❌ Error fetching characters: ${e.message}")
            }
        }
    }

    private fun printCharacterDetails(character: DragonBallCharacter) {
        val powerLevel = when {
            character.ki > 9000.toString() -> "💥 OVER 9000!"
            character.ki > 5000.toString() -> "🔥 ${character.ki}"
            else -> "⚡ ${character.ki}"
        }

        val maxPowerLevel = when {
            character.maxKi > 9000.toString() -> "💥 OVER 9000!"
            character.maxKi > 5000.toString() -> "🔥 ${character.maxKi}"
            else -> "⚡ ${character.maxKi}"
        }

        println("""
            
            🏆 === Character Details === 🏆
            🆔 ID: ${character.id}
            🧑 Name: ${character.name}
            👽 Race: ${character.race.emoji()} ${character.race}
            💪 Power: $powerLevel
            🚀 Max Power: $maxPowerLevel
            🚻 Gender: ${character.gender.toGenderEmoji()} ${character.gender}
            🏰 Affiliation: ${character.affiliation.toAffiliationEmoji()} ${character.affiliation}
            
        """.trimIndent())
    }
}

private fun String.emoji(): String = when (this.lowercase()) {
    "saiyan" -> "🦸"
    "human" -> "👨"
    "namekian" -> "👽"
    "android" -> "🤖"
    "frieza race" -> "👹"
    "jiren race" -> "👺"
    "god" -> "👼"
    "angel" -> "😇"
    else -> "❓"
}




private fun String.toGenderEmoji(): String = when (this.lowercase()) {
    "male" -> "♂️"
    "female" -> "♀️"
    else -> "⚧"
}

private fun String.toAffiliationEmoji(): String = when (this.lowercase()) {
    "z fighter" -> "🐉"
    "red ribbon army" -> "🔴"
    "frieza force" -> "👾"
    "universe 7" -> "7️⃣"
    "universe 11" -> "1️⃣1️⃣"
    else -> "🏳"
}