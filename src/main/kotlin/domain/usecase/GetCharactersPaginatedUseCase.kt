package domain.usecase

import domain.repository.CharacterRepository

class GetCharactersPaginatedUseCase(
    private val repository: CharacterRepository
) {
    suspend operator fun invoke(page: Int, limit: Int) = repository.getCharactersPaginated(page, limit)
}