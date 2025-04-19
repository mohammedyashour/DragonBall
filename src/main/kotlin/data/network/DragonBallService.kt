package data.network

import data.model.ApiResponse
import data.model.DragonBallCharacter
import data.model.Planet
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

class DragonBallService(private val client: HttpClient) {
    private val baseUrl = "https://dragonball-api.com/api"


    suspend fun getCharacter(id: Int): DragonBallCharacter {
        return client.get("$baseUrl/characters/$id").body()
    }

    suspend fun getAllCharacters(): List<DragonBallCharacter> {
        val allCharacters = mutableListOf<DragonBallCharacter>()
        var currentPage = 1
        var totalPages = 1

        do {
            val response = client.get("$baseUrl/characters") {
                parameter("page", currentPage)
                parameter("limit", 50)
            }.body<ApiResponse<DragonBallCharacter>>()

            allCharacters.addAll(response.items)
            totalPages = response.meta?.totalPages ?:
            currentPage++
        } while (currentPage <= totalPages)

        return allCharacters
    }
    suspend fun filterCharacters(
        race: String? = null,
        affiliation: String? = null,
        gender: String? = null
    ): List<Character> {
        return client.get("$baseUrl/characters") {
            race?.let { parameter("race", it) }
            affiliation?.let { parameter("affiliation", it) }
            gender?.let { parameter("gender", it) }
        }.body()
    }

    suspend fun getPlanet(id: Int): Planet {
        return client.get("$baseUrl/planets/$id").body()
    }

    suspend fun getAllPlanets(): List<Planet> {
        return client.get("$baseUrl/planets").body()
    }
    suspend fun getCharactersPaginated(page: Int, limit: Int): ApiResponse<DragonBallCharacter> {
        return client.get("$baseUrl/characters") {
            parameter("page", page)
            parameter("limit", limit)
        }.body()
    }
    suspend fun getPlanetsPaginated(page: Int, limit: Int): ApiResponse<Planet> {
        return client.get("$baseUrl/planets") {
            parameter("page", page)
            parameter("limit", limit)
        }.body()
    }
    suspend fun searchPlanetsByName(name: String): List<Planet> {
        return client.get("$baseUrl/planets") {
            parameter("name", name)
        }.body()
    }

}