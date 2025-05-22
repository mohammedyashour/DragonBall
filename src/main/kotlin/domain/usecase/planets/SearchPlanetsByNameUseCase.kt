package domain.usecase.planets

import domain.repository.PlanetRepository

class SearchPlanetsByNameUseCase(
    private val planetRepository: PlanetRepository
) {
    suspend operator fun invoke(name: String) = planetRepository.searchPlanetsByName(name)
}