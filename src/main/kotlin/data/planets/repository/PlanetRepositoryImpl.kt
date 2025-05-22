package data.planets.repository

import data.planets.datasource.PlanetRemoteDataSource
import domain.repository.PlanetRepository

class PlanetRepositoryImpl(private val remote: PlanetRemoteDataSource) : PlanetRepository {
    override suspend fun getPlanet(id: Int) = remote.getPlanet(id)
    override suspend fun getAllPlanets() = remote.getAllPlanets()
    override suspend fun getPlanetsPaginated(page: Int, limit: Int) = remote.getPlanetsPaginated(page, limit)
    override suspend fun searchPlanetsByName(name: String) = remote.searchPlanetsByName(name)
}