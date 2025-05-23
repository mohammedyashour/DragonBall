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
        val limit = 10
        var page = 1

        while (true) {
            val response = getPaginated(page, limit)
            val totalItems = response.meta?.totalItems ?: response.items.size

            if (response.items.isEmpty()) {
                println("❌ No planets found.")
                break
            }

            val start = (page - 1) * limit + 1
            val end = (start + response.items.size - 1).coerceAtMost(totalItems)

            println("\n🌍 Planets $start-$end of $totalItems")
            response.items.forEachIndexed { index, p ->
                println("${start + index}. ${p.name} ${if (p.isDestroyed) "💥" else "🌱"}")
            }

            if (end >= totalItems) break

            print("⏭ Press any key to continue or 'q' to quit: ")
            if (readLine()?.lowercase() == "q") break
            page++
        }
    }
}
