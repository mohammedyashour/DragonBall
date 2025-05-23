package presentation.utils

import kotlinx.coroutines.*
import kotlin.random.Random

fun showDragonBallLoading(
    scope: CoroutineScope,
    color: TerminalColor = TerminalColor.Yellow
): Job {
    val animations: List<suspend (CoroutineScope, TerminalColor) -> Unit> = listOf(
        { _, color ->
            val frames = listOf(
                "💥  Powering up...",
                "🔥  Energy rising!",
                "⚡  KA...",
                "💫  ME...",
                "🌟  HA...",
                "☄️  ME...",
                "🌠  HAAAA!!!",
                "🐉  DRAGON BALL Z!"
            )
            for (frame in frames) {
                if (!scope.isActive) return@listOf
                print("\r${frame.withStyle(color)}")
                delay(300)
            }
            print("\r${" ".repeat(30)}\r")
        },

        { _, color ->
            val frames = listOf(
                "▁ ▂ ▃ ▄ ▅ ▆ ▇",
                "▂ ▃ ▄ ▅ ▆ ▇ ▆",
                "▃ ▄ ▅ ▆ ▇ ▆ ▅",
                "▄ ▅ ▆ ▇ ▆ ▅ ▄",
                "▅ ▆ ▇ ▆ ▅ ▄ ▃",
                "▆ ▇ ▆ ▅ ▄ ▃ ▂",
                "▇ ▆ ▅ ▄ ▃ ▂ ▁"
            )
            var powerLevel = Random.nextInt(500, 5000)
            for (frame in frames) {
                if (!scope.isActive) return@listOf
                print("\rCharging ki: ${frame.withStyle(color)} ${(powerLevel + Random.nextInt(100)).toString().withStyle(TerminalColor.Green)}")
                delay(100)
            }
            print("\r${" ".repeat(40)}\r")
        },

        { _, color ->
            val dragonBalls = listOf("🔴", "🟠", "🟡", "🟢", "🔵", "🟣", "⭐")
            for ((index, ball) in dragonBalls.withIndex()) {
                if (!scope.isActive) return@listOf
                print("\rFinding Dragon Balls: ${dragonBalls.take(index + 1).joinToString("")} ${7 - index - 1} left")
                delay(400)
            }
            print("\r${" ".repeat(40)}\r")
        },

        { _, color ->
            val wave = listOf("(", "💢", "≡", "🌊", "≡", "💢", ")")
            for (i in 1..10) {
                if (!scope.isActive) return@listOf
                val waveSize = i % 5 + 1
                print("\rKamehameha: ${wave.joinToString("").repeat(waveSize).withStyle(TerminalColor.Cyan)}")
                delay(150)
            }
            print("\r${" ".repeat(40)}\r")
        },

        { _, color ->
            val basePower = Random.nextInt(100, 1000)
            for (i in 1..5) {
                if (!scope.isActive) return@listOf
                val power = basePower * i * Random.nextInt(1, 10)
                print("\r👓 Power Level: ${power.toString().withStyle(TerminalColor.Green)}")
                delay(300)
            }
            if (!scope.isActive) return@listOf
            print("\r👓 ${"IT'S OVER 9000!!!".withStyle(TerminalColor.Red)}")
            delay(500)
            print("\r${" ".repeat(30)}\r")
        }
    )

    return scope.launch {
        val randomAnimation = animations[Random.nextInt(animations.size)]
        randomAnimation(scope, color)
    }
}