import data.local.characters.CharacterLocalDataSource
import data.remote.characters.dataSource.CharacterRemoteDataSource
import domain.model.ApiResponse
import domain.model.DragonBallCharacter
import domain.repository.CharacterRepository
import java.net.InetAddress

class CharacterRepositoryImpl(
    private val remote: CharacterRemoteDataSource,
    private val local: CharacterLocalDataSource
) : CharacterRepository {

    private fun isOnline(): Boolean = runCatching {
        val address = InetAddress.getByName("google.com")
        address.toString().isNotEmpty()
    }.getOrDefault(false)

    override suspend fun getCharacter(id: Int): DragonBallCharacter {
        return local.getCharacterById(id)
            ?: if (isOnline()) remote.getCharacter(id)
            else throw IllegalStateException("Character not found locally and no internet connection.")
    }


    override suspend fun getAllCharacters(): List<DragonBallCharacter> {
        return if (isOnline()) {
            runCatching { remote.getAllCharacters() }.getOrElse { local.getAllCharacters() }
        } else {
            local.getAllCharacters()
        }
    }

    override suspend fun filterCharacters(race: String?, affiliation: String?, gender: String?) =
        if (isOnline()) remote.filterCharacters(race, affiliation, gender)
        else local.filterCharacters(race, affiliation, gender)

    override suspend fun getCharactersPaginated(page: Int, limit: Int): ApiResponse<DragonBallCharacter> {
        return if (isOnline()) {
            remote.getCharactersPaginated(page, limit)
        } else {
            val cached = local.getAllCharacters()
            val from = (page - 1) * limit
            val to = (from + limit).coerceAtMost(cached.size)
            val items = if (from < cached.size) cached.subList(from, to) else emptyList()

            ApiResponse(
                items = items,
                meta = domain.model.MetaData(
                    totalItems = cached.size,
                    itemCount = items.size,
                    itemsPerPage = limit,
                    totalPages = (cached.size + limit - 1) / limit,
                    currentPage = page
                )
            )
        }
    }
}
