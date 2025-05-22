package domain.usecase.characters

import domain.repository.CharacterRepository

class GetCharactersPaginatedUseCase(
    private val characterRepository: CharacterRepository
) {
    suspend operator fun invoke(page: Int, limit: Int) = characterRepository.getCharactersPaginated(page, limit)
}