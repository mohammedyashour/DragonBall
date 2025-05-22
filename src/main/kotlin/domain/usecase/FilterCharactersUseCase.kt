package domain.usecase

import domain.repository.CharacterRepository

class FilterCharactersUseCase(
    private val repository: CharacterRepository
) {
    suspend operator fun invoke(race: String?, affiliation: String?, gender: String?) =
        repository.filterCharacters(race, affiliation, gender)
}