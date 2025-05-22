package domain.repository

import domain.model.ApiResponse
import domain.model.DragonBallCharacter

interface CharacterRepository {
    suspend fun getCharacter(id: Int): DragonBallCharacter
    suspend fun getAllCharacters(): List<DragonBallCharacter>
    suspend fun filterCharacters(
        race: String? = null,
        affiliation: String? = null,
        gender: String? = null
    ): List<DragonBallCharacter>

    suspend fun getCharactersPaginated(page: Int, limit: Int): ApiResponse<DragonBallCharacter>
}
