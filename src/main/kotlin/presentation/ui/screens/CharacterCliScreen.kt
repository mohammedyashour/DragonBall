package presentation.ui.screens

import domain.usecase.characters.GetCharacterUseCase
import domain.usecase.characters.GetCharactersPaginatedUseCase
import kotlinx.coroutines.runBlocking

class CharacterCliScreen(
    private val getCharacter: GetCharacterUseCase,
    private val getCharactersPaginated: GetCharactersPaginatedUseCase
) {
    fun showCharacter(id: Int) = runBlocking {
        try {
            val character = getCharacter(id)
            println("\n👤 ${character.name} - ${character.race} - ${character.ki}")
        } catch (e: Exception) {
            println("❌ Error: ${e.message}")
        }
    }

    fun showAllCharactersPaginated() = runBlocking {
        var page = 1
        val pageSize = 10
        var total = 0
        while (true) {
            val response = getCharactersPaginated(page, pageSize)
            response.meta?.let { total = it.totalItems }
            val start = (page - 1) * pageSize + 1
            println("\n🌟 Characters $start-${start + response.items.size - 1} of $total")
            response.items.forEachIndexed { index, c ->
                println("${start + index}. ${c.name} (${c.race}) - Power: ${c.ki}")
            }
            if (page * pageSize >= total) break
            print("⏭ Press any key to continue or 'q' to quit: ")
            if (readLine()?.lowercase() == "q") break
            page++
        }
    }
}