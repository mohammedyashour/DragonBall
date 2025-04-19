package presentation

import data.model.Planet
import data.network.DragonBallService
import kotlinx.coroutines.runBlocking

class PlanetView(private val service: DragonBallService) {

    fun showPlanet(id: Int) {
        runBlocking {
            try {
                val planet = service.getPlanet(id)
                printPlanetDetails(planet)
            } catch (e: Exception) {
                println("❌ Error fetching planet: ${e.message}")
            }
        }
    }
    fun searchPlanetsByName() {
        runBlocking {
            try {
                print("Enter planet name to search: ")
                val name = readLine()?.takeIf { it.isNotBlank() } ?: return@runBlocking

                println("\n🔍 Searching for planets named '$name'...")
                val planets = service.searchPlanetsByName(name)

                if (planets.isEmpty()) {
                    println("\n🌌 No planets found with name '$name'")
                } else {
                    println("\n🪐 === Search Results (${planets.size} planets) ===")
                    planets.forEachIndexed { index, planet ->
                        println("${index + 1}. ${planet.name} ${planet.statusEmoji()} - ${planet}")
                        println("   ${planet.description.take(60)}...")
                    }
                }
            } catch (e: Exception) {
                println("❌ Error searching planets: ${e.message}")
            }
        }
    }

    fun showAllPlanetsPaginated() {
        runBlocking {
            try {
                var currentPage = 1
                val pageSize = 10
                var totalPlanets = 0
                var allPlanetsFetched = false

                while (!allPlanetsFetched) {
                    val response = service.getPlanetsPaginated(currentPage, pageSize)
                    val planets = response.items
                    response.meta?.let { totalPlanets = it.totalItems }

                    println("\n🌍 === Planets ${(currentPage-1)*pageSize + 1}-${(currentPage-1)*pageSize + planets.size} of $totalPlanets ===")
                    planets.forEachIndexed { index, planet ->
                        println("${(currentPage-1)*pageSize + index + 1}. ${planet.name} ${planet.statusEmoji()}")
                    }

                    if (currentPage * pageSize >= totalPlanets) {
                        allPlanetsFetched = true
                        println("\n✅ All planets displayed!")
                    } else {
                        print("\n⏭ Press any key to show next 10 planets (or 'q' to quit)... ")
                        val input = readLine()
                        if (input.equals("q", ignoreCase = true)) {
                            return@runBlocking
                        }
                        currentPage++
                    }
                }
            } catch (e: Exception) {
                println("❌ Error fetching planets: ${e.message}")
            }
        }
    }

    private fun printPlanetDetails(planet: Planet) {
        println("""
            
            🪐 === Planet Details === 🪐
            🆔 ID: ${planet.id}
            🏷️ Name: ${planet.name}
            🚨 Status: ${planet.statusEmoji()} ${if (planet.isDestroyed) "Destroyed" else "Intact"}
            📝 Description: ${planet.description}
            ${if (planet.deletedAt != null) "🗑️ Deleted at: ${planet.deletedAt}" else ""}
            
            ${planet.imageEmoji()} ${planet.image.takeIf { it.isNotBlank() } ?: "No image available"}
        """.trimIndent())
    }

    private fun Planet.statusEmoji(): String = when {
        this.isDestroyed -> "💥"
        this.deletedAt != null -> "🗑️"
        else -> "🌱"
    }

    private fun Planet.imageEmoji(): String = when {
        this.image.contains("earth", ignoreCase = true) -> "🌎"
        this.image.contains("namek", ignoreCase = true) -> "👽"
        this.image.contains("vegeta", ignoreCase = true) -> "👑"
        this.image.isNotBlank() -> "🖼️"
        else -> ""
    }
}