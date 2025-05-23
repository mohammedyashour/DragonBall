package data.local.planets

import data.local.InitialDataSaver
import domain.model.Planet

class PlanetsLocalDataSource {

    fun getAllPlanets(): List<Planet> {
        return InitialDataSaver.loadCachedPlanets()
    }

    fun getPlanetById(id: Int): Planet? {
        return InitialDataSaver.loadCachedPlanets().firstOrNull { it.id == id }
    }

    fun filterPlanets(byName: String): List<Planet> {
        return InitialDataSaver.loadCachedPlanets().filter {
            it.name.contains(byName, ignoreCase = true)
        }
    }
}
