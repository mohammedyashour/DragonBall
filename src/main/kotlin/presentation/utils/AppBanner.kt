package presentation.utils

import kotlin.random.Random

object AppBanner {


    private val colors = TerminalColor.entries.filter { it != TerminalColor.Reset }

    private fun getRandomColor(): TerminalColor = colors[Random.nextInt(colors.size)]
    fun printAscii(): String {
        return """
            ${getRandomColor()}·▄▄▄▄  ▄▄▄   ▄▄▄·  ▄▄ •        ▐ ▄     ▄▄▄▄·  ▄▄▄· ▄▄▌  ▄▄▌  
            ${getRandomColor()}██▪ ██ ▀▄ █·▐█ ▀█ ▐█ ▀ ▪▪     •█▌▐█    ▐█ ▀█▪▐█ ▀█ ██•  ██•  
            ${getRandomColor()}▐█· ▐█▌▐▀▀▄ ▄█▀▀█ ▄█ ▀█▄ ▄█▀▄ ▐█▐▐▌    ▐█▀▀█▄▄█▀▀█ ██▪  ██▪  
            ${getRandomColor()}██. ██ ▐█•█▌▐█ ▪▐▌▐█▄▪▐█▐█▌.▐▌██▐█▌    ██▄▪▐█▐█ ▪▐▌▐█▌▐▌▐█▌▐▌
            ${getRandomColor()}▀▀▀▀▀• .▀  ▀ ▀  ▀ ·▀▀▀▀  ▀█▄▀▪▀▀ █▪    ·▀▀▀▀  ▀  ▀ .▀▀▀ .▀▀▀ ${TerminalColor.Reset}
        """.trimIndent()
    }

    fun aboutDeveloper(): String {
        return """
            About the Developer

            👨‍💻 Name: Mohammed Ashour
            🚀 Role: Kotlin/Android Developer
            📧 Contact: medo.ash.2019@gmail.com
            🔗 GitHub: github.com/mohammedyashour
            💼 LinkedIn: linkedin.com/in/mohammedyehiaashour

            Skills:
            - Kotlin, Java, Python
            - Jetpack Compose, Room, Ktor, Coroutines, Flow
            - Clean Architecture, MVVM, Modularization

            Passionate about clean, scalable code and impactful Android apps.
        """.trimIndent()
    }
}
