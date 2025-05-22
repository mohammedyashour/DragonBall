package data.remote.planets.datasource

import core.ApiRoutes
import domain.model.ApiResponse
import domain.model.Planet
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

class PlanetApi(private val client: HttpClient) {

    suspend fun getPlanet(id: Int): Planet = client.get("${ApiRoutes.PLANETS}/$id").body()

    suspend fun getAllPlanets(): List<Planet> = client.get(ApiRoutes.PLANETS).body()

    suspend fun getPlanetsPaginated(page: Int, limit: Int): ApiResponse<Planet> = client.get(ApiRoutes.PLANETS) {
        parameter("page", page)
        parameter("limit", limit)
    }.body()

    suspend fun searchPlanetsByName(name: String): List<Planet> = client.get(ApiRoutes.PLANETS) {
        parameter("name", name)
    }.body()
}