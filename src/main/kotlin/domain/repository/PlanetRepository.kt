package domain.repository

import domain.model.ApiResponse
import domain.model.Planet

interface PlanetRepository {
    suspend fun getPlanet(id: Int): Planet
    suspend fun getAllPlanets(): List<Planet>
    suspend fun getPlanetsPaginated(page: Int, limit: Int): ApiResponse<Planet>
    suspend fun searchPlanetsByName(name: String): List<Planet>
}
