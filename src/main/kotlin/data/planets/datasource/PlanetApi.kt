package data.planets.datasource

import domain.model.ApiResponse
import domain.model.Planet
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

class PlanetApi(private val client: HttpClient) {
    private val baseUrl = "https://dragonball-api.com/api"

    suspend fun getPlanet(id: Int): Planet =
        client.get("$baseUrl/planets/$id").body()

    suspend fun getAllPlanets(): List<Planet> =
        client.get("$baseUrl/planets").body()

    suspend fun getPlanetsPaginated(page: Int, limit: Int): ApiResponse<Planet> =
        client.get("$baseUrl/planets") {
            parameter("page", page)
            parameter("limit", limit)
        }.body()

    suspend fun searchPlanetsByName(name: String): List<Planet> =
        client.get("$baseUrl/planets") {
            parameter("name", name)
        }.body()
}