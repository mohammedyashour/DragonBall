package domain.usecase

import domain.repository.PlanetRepository

class GetPlanetUseCase(
    private val repository: PlanetRepository
) {
    suspend operator fun invoke(id: Int) = repository.getPlanet(id)
}
