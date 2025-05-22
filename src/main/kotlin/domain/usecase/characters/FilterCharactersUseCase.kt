package domain.usecase.characters

import domain.repository.CharacterRepository

class FilterCharactersUseCase(
    private val characterRepository: CharacterRepository
) {
    suspend operator fun invoke(race: String?, affiliation: String?, gender: String?) =
        characterRepository.filterCharacters(race, affiliation, gender)
}