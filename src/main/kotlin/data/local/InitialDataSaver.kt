package data.local

import core.ApiRoutes
import domain.model.ApiResponse
import domain.model.DragonBallCharacter
import domain.model.Planet
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
import java.net.InetAddress

object InitialDataSaver {

    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private val json = Json { ignoreUnknownKeys = true; prettyPrint = true }

    private val charactersFile = File("data/characters.json")
    private val planetsFile = File("data/planets.json")

    fun fetchAndCacheDataIfOnline(client: HttpClient) {

        scope.launch {
            runCatching { isOnline() }
                .onFailure { println("❌ Failed to check internet: ${it.message}") }
                .onSuccess { online ->
                    if (!online) {
                        println("🌐 No internet connection. Skipping data fetch.")
                        return@launch
                    }

                    runCatching {
                        val characterBody = client.get(ApiRoutes.CHARACTERS) {
                            parameter("limit", 50)
                        }.bodyAsText()

                        val planetBody = client.get(ApiRoutes.PLANETS) {
                            parameter("limit", 50)
                        }.bodyAsText()

                        val characters = json.decodeFromString(
                            ApiResponse.serializer(DragonBallCharacter.serializer()), characterBody
                        ).items

                        val planets = json.decodeFromString(
                            ApiResponse.serializer(Planet.serializer()), planetBody
                        ).items

                        saveFile(charactersFile, characters, DragonBallCharacter.serializer())
                        saveFile(planetsFile, planets, Planet.serializer())

                        println("✅ Data refreshed and saved successfully.")
                    }.onFailure { error ->
                        println("❌ Failed to fetch new data:")
                        error.printStackTrace()
                    }
                }
        }
    }

    private fun <T> saveFile(file: File, data: List<T>, serializer: kotlinx.serialization.KSerializer<T>) {
        file.parentFile.mkdirs()
        file.writeText(json.encodeToString(ListSerializer(serializer), data))
    }

    fun loadCachedCharacters(): List<DragonBallCharacter> =
        loadFile(charactersFile, DragonBallCharacter.serializer())

    fun loadCachedPlanets(): List<Planet> =
        loadFile(planetsFile, Planet.serializer())

    private fun <T> loadFile(file: File, serializer: kotlinx.serialization.KSerializer<T>): List<T> {
        return runCatching {
            if (!file.exists()) emptyList()
            else json.decodeFromString(ListSerializer(serializer), file.readText())
        }.getOrElse {
            println("❌ Failed to load cached file ${file.name}: ${it.message}")
            emptyList()
        }
    }

    private fun isOnline(): Boolean = runCatching {
        val address = InetAddress.getByName("google.com")
        address.toString().isNotEmpty()
    }.getOrDefault(false)
}
