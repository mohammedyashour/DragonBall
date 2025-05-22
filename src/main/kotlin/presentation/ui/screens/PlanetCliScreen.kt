package presentation.ui.screens

import domain.usecase.planets.GetPlanetUseCase
import domain.usecase.planets.GetPlanetsPaginatedUseCase
import domain.usecase.planets.SearchPlanetsByNameUseCase
import kotlinx.coroutines.runBlocking

class PlanetCliScreen(
    private val getPlanet: GetPlanetUseCase,
    private val searchPlanets: SearchPlanetsByNameUseCase,
    private val getPaginated: GetPlanetsPaginatedUseCase
) {
    fun showPlanet(id: Int) = runBlocking {
        try {
            val planet = getPlanet(id)
            println("\n🪐 ${planet.name} - ${planet.description}")
        } catch (e: Exception) {
            println("❌ Error: ${e.message}")
        }
    }

    fun searchPlanetsByName() = runBlocking {
        print("Enter name: ")
        val name = readLine()?.takeIf { it.isNotBlank() } ?: return@runBlocking
        val planets = searchPlanets(name)
        if (planets.isEmpty()) println("No results.")
        else planets.forEach { println("🔎 ${it.name} - ${it.description.take(50)}") }
    }

    fun showAllPlanetsPaginated() = runBlocking {
        var page = 1
        val limit = 10
        var total = 0
        while (true) {
            val response = getPaginated(page, limit)
            response.meta?.let { total = it.totalItems }
            val start = (page - 1) * limit + 1
            println("\n🌍 Planets $start-${start + response.items.size - 1} of $total")
            response.items.forEachIndexed { index, p ->
                println("${start + index}. ${p.name} ${if (p.isDestroyed) "💥" else "🌱"}")
            }
            if (page * limit >= total) break
            print("⏭ Press any key to continue or 'q' to quit: ")
            if (readLine()?.lowercase() == "q") break
            page++
        }
    }
}
