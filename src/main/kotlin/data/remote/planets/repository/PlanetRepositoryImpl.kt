package data.remote.planets.repository

import data.local.planets.PlanetsLocalDataSource
import data.remote.planets.datasource.PlanetRemoteDataSource
import domain.model.ApiResponse
import domain.model.Planet
import domain.repository.PlanetRepository
import java.net.InetAddress

class PlanetRepositoryImpl(
    private val remote: PlanetRemoteDataSource,
    private val local: PlanetsLocalDataSource
) : PlanetRepository {

    private fun isOnline(): Boolean = runCatching {
        val address = InetAddress.getByName("google.com")
        address.toString().isNotEmpty()
    }.getOrDefault(false)

    override suspend fun getPlanet(id: Int): Planet {
        val localPlanet = local.getPlanetById(id)
        return localPlanet ?: remote.getPlanet(id)
    }

    override suspend fun getAllPlanets(): List<Planet> {
        return try {
            val planets = remote.getAllPlanets()
            planets.ifEmpty { local.getAllPlanets() }
        } catch (e: Exception) {
            local.getAllPlanets()
        }
    }

    override suspend fun getPlanetsPaginated(page: Int, limit: Int): ApiResponse<Planet> {
        return if (isOnline()) {
            remote.getPlanetsPaginated(page, limit)
        } else {
            val cached = local.getAllPlanets()
            val from = (page - 1) * limit
            val to = (from + limit).coerceAtMost(cached.size)
            ApiResponse(cached.subList(from, to))
        }
    }

    override suspend fun searchPlanetsByName(name: String): List<Planet> {
        return if (isOnline()) {
            remote.searchPlanetsByName(name)
        } else {
            local.filterPlanets(name)
        }
    }
}
