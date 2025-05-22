package data.remote.planets.datasource

class PlanetRemoteDataSource(private val api: PlanetApi) {
    suspend fun getPlanet(id: Int) = api.getPlanet(id)
    suspend fun getAllPlanets() = api.getAllPlanets()
    suspend fun getPlanetsPaginated(page: Int, limit: Int) = api.getPlanetsPaginated(page, limit)
    suspend fun searchPlanetsByName(name: String) = api.searchPlanetsByName(name)
}