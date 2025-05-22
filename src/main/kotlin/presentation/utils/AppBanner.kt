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

}
