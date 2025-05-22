package domain.usecase.planets

import domain.repository.PlanetRepository

class SearchPlanetsByNameUseCase(
    private val repository: PlanetRepository
) {
    suspend operator fun invoke(name: String) = repository.searchPlanetsByName(name)
}