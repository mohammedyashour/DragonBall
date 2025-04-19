import data.Constants
import kotlin.random.Random
import presentation.CharacterView
import presentation.GameView
import presentation.PlanetView
import presentation.WordScrambleGame
import data.network.ApiClient
import data.network.DragonBallService
import io.ktor.http.content.Version

// ANSI Colors
const val RESET = "\u001B[0m"
const val RED = "\u001B[31m"
const val GREEN = "\u001B[32m"
const val YELLOW = "\u001B[33m"
const val BLUE = "\u001B[34m"
const val CYAN = "\u001B[36m"
const val PURPLE = "\u001B[35m"
const val BOLD = "\u001B[1m"
const val WHITE_BG = "\u001B[47m"
const val BLACK = "\u001B[30m"

val colors = listOf(RED, GREEN, YELLOW, BLUE, CYAN, PURPLE)

// Box Drawing Characters
const val TOP_LEFT = "╭"
const val TOP_RIGHT = "╮"
const val BOTTOM_LEFT = "╰"
const val BOTTOM_RIGHT = "╯"
const val HORIZONTAL = "─"
const val VERTICAL = "│"
const val VERTICAL_LEFT = "├"
const val VERTICAL_RIGHT = "┤"
const val HORIZONTAL_DOWN = "┬"
const val HORIZONTAL_UP = "┴"
const val CROSS = "┼"

fun getRandomColor(): String = colors[Random.nextInt(colors.size)]


fun main() {
    val constants:Constants= Constants
    println(printAscii())
    val service = DragonBallService(ApiClient.instance)
    val character = CharacterView(service)
    val planet = PlanetView(service)
    val quiz = GameView(service)
    val wordScrambleGame = WordScrambleGame(service)
    while (true) {
        printMenu(version = constants.AppVersion)
        print("\n${getRandomColor()}${BOLD}➤$RESET ${getRandomColor()}Enter choice:$RESET ")
        when (readLine()) {
            "1" -> {
                print("\n${getRandomColor()}${BOLD}➤$RESET ${getRandomColor()}Enter Character ID:$RESET ")
                val id = readLine()?.toIntOrNull() ?: run {
                    println("Invalid ID. Using default ID 1")
                    1
                }
                character.showCharacter(id)
            }
            "2" -> character.showAllCharactersPaginated()
            "3" -> {
                print("\n${getRandomColor()}${BOLD}➤$RESET ${getRandomColor()}Enter Planets ID:$RESET ")

                val id = readLine()?.toIntOrNull() ?: run {
                    println("${getRandomColor()}${BOLD}➤$RESET Invalid ID. Using default ID 1")
                    1
                }
                planet.showPlanet(id)
            }
            "4" -> planet.showAllPlanetsPaginated()
            "5" -> quiz.startGame()
            "6" -> wordScrambleGame.startGame()
            "7" -> wordScrambleGame.startGame()
            "8" -> println( aboutDeveloper())
            "0" -> {
                println("\n${getRandomColor()}${BOLD}✧ Thank you for using DragonBall Z CLI! ✧$RESET")
                println("${getRandomColor()}   May your power level be over 9000!$RESET\n")
                return
            }

            else -> println("${getRandomColor()}⚠ Invalid choice. Try again.$RESET")
        }
    }
}

fun printMenu(version: String) {

    val version = "${getRandomColor()}$version ${RESET}"
    val width = 60
    // Top border with random color
    val borderColor = getRandomColor()
    println("$borderColor$TOP_LEFT${HORIZONTAL.repeat(width - 2)}$TOP_RIGHT$RESET")

    // Version line
    val versionPadding = ((width - version.length + 7) / 2)
    println("$borderColor$VERTICAL${" ".repeat(versionPadding)}$version${" ".repeat(versionPadding)}$borderColor$VERTICAL$RESET")

    // Separator
    println("$borderColor$VERTICAL_LEFT${HORIZONTAL.repeat(width - 2)}$VERTICAL_RIGHT$RESET")

    // Menu items with random colors
    printMenuItem("1", "👤", "Get Single Character by ID")
    printMenuItem("2", "🧑‍🚀", "Show All Characters")
    printMenuItem("3", "🪐", "Get Single Planet by ID")
    printMenuItem("4", "🌍", "Show All Planets")
    printMenuItem("5", "🎮", "Characters Quiz Game")
    printMenuItem("6", "🕹️", "Word Scramble Game")
    printMenuItem("7", "🏆️", "Leaderboard")
    printMenuItem("8", "👨🏻‍💻️", "About Developer ")
    //   printMenuItem("6", "🔍", "Filter Characters")
    printMenuItem("0", "🚪", "Exit")

    // Bottom border
    println("$borderColor$BOTTOM_LEFT${HORIZONTAL.repeat(width - 2)}$BOTTOM_RIGHT$RESET")
}
fun printAscii():String {
    val dragonBallAscii = """
        ${getRandomColor()}·▄▄▄▄  ▄▄▄   ▄▄▄·  ▄▄ •        ▐ ▄     ▄▄▄▄·  ▄▄▄· ▄▄▌  ▄▄▌  
        ${getRandomColor()}██▪ ██ ▀▄ █·▐█ ▀█ ▐█ ▀ ▪▪     •█▌▐█    ▐█ ▀█▪▐█ ▀█ ██•  ██•  
        ${getRandomColor()}▐█· ▐█▌▐▀▀▄ ▄█▀▀█ ▄█ ▀█▄ ▄█▀▄ ▐█▐▐▌    ▐█▀▀█▄▄█▀▀█ ██▪  ██▪  
        ${getRandomColor()}██. ██ ▐█•█▌▐█ ▪▐▌▐█▄▪▐█▐█▌.▐▌██▐█▌    ██▄▪▐█▐█ ▪▐▌▐█▌▐▌▐█▌▐▌
        ${getRandomColor()}▀▀▀▀▀• .▀  ▀ ▀  ▀ ·▀▀▀▀  ▀█▄▀▪▀▀ █▪    ·▀▀▀▀  ▀  ▀ .▀▀▀ .▀▀▀ 
    """.trimIndent()
return dragonBallAscii
}
fun aboutDeveloper():String{
    return  """
        About the Developer

        👨‍💻 Name: Mohammed Ashour
        🚀 Role: Kotlin/Android Developer
        📧 Contact:medo.ash.2019@gmail.com
        🔗 GitHub:github.com/mohammedyashour
        💼 LinkedIn: linkedin.com/in/mohammedyehiaashour
        Skills & Expertise

            Languages: Kotlin, Java, Python

            Android Development: Jetpack Compose, Room, Retrofit, Coroutines, Flow

            Backend: Ktor, Spring Boot, SQL

            Tools: Android Studio, IntelliJ IDEA, Git, Gradle

        About This Project

        This Dragon Ball App is a Kotlin-based application that demonstrates:
        ✔ Modern Android Architecture (MVVM, Clean Architecture)
        ✔ Database Management (Exposed ORM, SQLite)
        ✔ API Integration (Ktor/Retrofit, JSON Serialization)
        ✔ Best Practices (Coroutines, Dependency Injection, Modularization)
        Get in Touch

        I’m passionate about building clean, efficient, and scalable Android apps. Feel free to reach out for collaborations, feedback, or opportunities!

        📩 Email: medo.ash.2019@gmail.com
        🔗 GitHub: github.com/mohammedyashour
        🔗 LinkedIn: linkedin.com/in/mohammedyehiaashour
    """.trimIndent()
}
fun printMenuItem(number: String, icon: String, text: String) {
    val paddedText = text.padEnd(51)
    val numberColor = getRandomColor()
    val iconColor = getRandomColor()
    val textColor = getRandomColor()
    println("${PURPLE}$VERTICAL $numberColor$number.$RESET $iconColor$icon $textColor$paddedText${PURPLE}$VERTICAL$RESET")
}

