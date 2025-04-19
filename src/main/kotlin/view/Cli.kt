package view

import data.network.ApiClient
import data.network.DragonBallService
import kotlinx.coroutines.runBlocking
import presentation.CharacterView
import presentation.GameView
import presentation.PlanetView
import presentation.WordScrambleGame

fun main() = runBlocking {
    val service = DragonBallService(ApiClient.instance)
    val view = CharacterView(service)
    val planetview = PlanetView(service)
    val game = GameView(service)
    val wordScrambleGame = WordScrambleGame(service)

    var option: String?
    do {
        println("\nDragon Ball Z Character Explorer")
        println("1. Show character details")
        println("2. List all characters (paginated)")
        println("3. Show All planet ")
        println("4. Search planet By ID")
        println("5. Search planet By Name")
        println("6. Play Quiz Game")
        println("7. Word Scramble Game")
        println("0. Exit")
        print("Choose an option: ")

        option = readLine()
        when (option) {
            "1" -> {
                print("Enter character ID: ")
                val id = readLine()?.toIntOrNull() ?: run {
                    println("Invalid ID. Using default ID 1")
                    1
                }
                view.showCharacter(id)
            }
            "2" -> view.showAllCharactersPaginated()
            "3" -> planetview.showAllPlanetsPaginated()
            "4" -> {
                print("Enter Planet ID: ")
                val id = readLine()?.toIntOrNull() ?: run {
                    println("Invalid ID. Using default ID 1")
                    1
                }
                planetview.showPlanet(id)}
            "5" -> planetview.searchPlanetsByName()
            "6" -> game.startGame()
            "7" -> wordScrambleGame.startGame()
            "0" -> println("Exiting...")
            else -> println("Invalid option, please try again")
        }
    } while (option != "0")
}

