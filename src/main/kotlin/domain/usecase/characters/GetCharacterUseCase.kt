package domain.usecase.characters

import domain.repository.CharacterRepository

class GetCharacterUseCase(
    private val repository: CharacterRepository
) {
    suspend operator fun invoke(id: Int) = repository.getCharacter(id)
}
