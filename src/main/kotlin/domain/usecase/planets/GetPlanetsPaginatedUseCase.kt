package domain.usecase.planets

import domain.repository.PlanetRepository

class GetPlanetsPaginatedUseCase(
    private val planetRepository: PlanetRepository
) {
    suspend operator fun invoke(page: Int, limit: Int) = planetRepository.getPlanetsPaginated(page, limit)
}