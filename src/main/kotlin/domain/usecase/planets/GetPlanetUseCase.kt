package domain.usecase.planets

import domain.repository.PlanetRepository

class GetPlanetUseCase(
    private val planetRepository: PlanetRepository
) {
    suspend operator fun invoke(id: Int) = planetRepository.getPlanet(id)
}
