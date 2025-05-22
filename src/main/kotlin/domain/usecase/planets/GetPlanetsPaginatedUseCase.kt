package domain.usecase.planets

import domain.repository.PlanetRepository

class GetPlanetsPaginatedUseCase(
    private val repository: PlanetRepository
) {
    suspend operator fun invoke(page: Int, limit: Int) = repository.getPlanetsPaginated(page, limit)
}