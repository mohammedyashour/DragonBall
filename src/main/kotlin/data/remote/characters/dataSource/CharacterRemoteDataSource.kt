package data.remote.characters.dataSource

import domain.model.DragonBallCharacter

class CharacterRemoteDataSource(private val api: CharacterApi) {
    suspend fun getCharacter(id: Int) = api.getCharacter(id)
    suspend fun getAllCharacters(): List<DragonBallCharacter> {
        val allCharacters = mutableListOf<DragonBallCharacter>()
        var currentPage = 1
        var totalPages: Int
        do {
            val response = api.getAllCharacters(currentPage)
            allCharacters.addAll(response.items)
            totalPages = response.meta?.totalPages ?: break
            currentPage++
        } while (currentPage <= totalPages)
        return allCharacters
    }

    suspend fun filterCharacters(race: String?, affiliation: String?, gender: String?) =
        api.filterCharacters(race, affiliation, gender)

    suspend fun getCharactersPaginated(page: Int, limit: Int) = api.getCharactersPaginated(page, limit)
}