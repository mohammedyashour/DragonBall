package data.characters.repository

import data.characters.dataSource.CharacterRemoteDataSource
import domain.repository.CharacterRepository

class CharacterRepositoryImpl(private val remote: CharacterRemoteDataSource) : CharacterRepository {
    override suspend fun getCharacter(id: Int) = remote.getCharacter(id)
    override suspend fun getAllCharacters() = remote.getAllCharacters()
    override suspend fun filterCharacters(race: String?, affiliation: String?, gender: String?) =
        remote.filterCharacters(race, affiliation, gender)
    override suspend fun getCharactersPaginated(page: Int, limit: Int) = remote.getCharactersPaginated(page, limit)
}