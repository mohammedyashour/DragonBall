package domain.usecase.characters

import domain.repository.CharacterRepository

class GetCharacterUseCase(
    private val characterRepository: CharacterRepository
) {
    suspend operator fun invoke(id: Int) = characterRepository.getCharacter(id)
}
