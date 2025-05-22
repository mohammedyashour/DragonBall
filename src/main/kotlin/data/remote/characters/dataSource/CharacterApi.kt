package data.remote.characters.dataSource

import core.ApiRoutes
import domain.model.ApiResponse
import domain.model.DragonBallCharacter
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

class CharacterApi(private val client: HttpClient) {

    suspend fun getCharacter(id: Int): DragonBallCharacter = client.get("${ApiRoutes.CHARACTERS}/$id").body()

    suspend fun getAllCharacters(page: Int): ApiResponse<DragonBallCharacter> = client.get(ApiRoutes.CHARACTERS) {
        parameter("page", page)
        parameter("limit", 50)
    }.body()

    suspend fun filterCharacters(race: String?, affiliation: String?, gender: String?): List<DragonBallCharacter> =
        client.get(ApiRoutes.CHARACTERS) {
            race?.let { parameter("race", it) }
            affiliation?.let { parameter("affiliation", it) }
            gender?.let { parameter("gender", it) }
        }.body()

    suspend fun getCharactersPaginated(page: Int, limit: Int): ApiResponse<DragonBallCharacter> =
        client.get(ApiRoutes.CHARACTERS) {
            parameter("page", page)
            parameter("limit", limit)
        }.body()
}
