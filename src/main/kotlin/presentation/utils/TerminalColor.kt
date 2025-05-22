package presentation.utils

enum class TerminalColor(val code: String) {
    Reset("\u001B[0m"),
    Red("\u001B[31m"),
    Green("\u001B[32m"),
    Yellow("\u001B[33m"),
    Blue("\u001B[34m"),
    Purple("\u001B[35m"),
    Cyan("\u001B[36m")
}

fun String.withStyle(color: TerminalColor): String = "${color.code}$this${TerminalColor.Reset.code}"
