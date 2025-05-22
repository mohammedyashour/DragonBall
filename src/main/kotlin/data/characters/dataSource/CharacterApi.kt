package data.characters.dataSource

import domain.model.ApiResponse
import domain.model.DragonBallCharacter
import io.ktor.client.*
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class CharacterApi(private val client: HttpClient) {
    private val baseUrl = "https://dragonball-api.com/api"

    suspend fun getCharacter(id: Int): DragonBallCharacter =
        client.get("$baseUrl/characters/$id").body()

    suspend fun getAllCharacters(page: Int): ApiResponse<DragonBallCharacter> =
        client.get("$baseUrl/characters") {
            parameter("page", page)
            parameter("limit", 50)
        }.body()

    suspend fun filterCharacters(race: String?, affiliation: String?, gender: String?): List<DragonBallCharacter> =
        client.get("$baseUrl/characters") {
            race?.let { parameter("race", it) }
            affiliation?.let { parameter("affiliation", it) }
            gender?.let { parameter("gender", it) }
        }.body()

    suspend fun getCharactersPaginated(page: Int, limit: Int): ApiResponse<DragonBallCharacter> =
        client.get("$baseUrl/characters") {
            parameter("page", page)
            parameter("limit", limit)
        }.body()
}
