package presentation.ui.screens

import i18n.LanguageManager.text
import presentation.ui.UILauncher
import presentation.utils.TerminalColor
import presentation.utils.withStyle

class AboutDeveloperCliScreen : UILauncher {

    override suspend fun start() {
        println(
            """
            ${"👨‍💻 About the Developer".withStyle(TerminalColor.Cyan)}

            👤 ${"Name:".withStyle(TerminalColor.Yellow)}  ${text("developer.name")}
            🚀 ${"Role:".withStyle(TerminalColor.Yellow)} Kotlin/Android Developer
            📧 ${"Contact:".withStyle(TerminalColor.Yellow)} medo.ash.2019@gmail.com
            🔗 ${"GitHub:".withStyle(TerminalColor.Yellow)} github.com/mohammedyashour
            💼 ${"LinkedIn:".withStyle(TerminalColor.Yellow)} linkedin.com/in/mohammedyehiaashour

            ${"Skills:".withStyle(TerminalColor.Green)}
            - Kotlin, Java, Python
            - Jetpack Compose, Room, Ktor, Coroutines, Flow
            - Clean Architecture, MVVM, Modularization

            ${"I'm passionate about building clean, scalable, and impactful Android apps.".withStyle(TerminalColor.Purple)}
            """.trimIndent()
        )

        println("\n🔁 Press Enter to return to menu...")
        readlnOrNull()
    }
}
