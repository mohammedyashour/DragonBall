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
                println("\r${frame.withStyle(color)}")
                delay(300)
            }
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
                println("\rCharging ki: ${frame.withStyle(color)} ${powerLevel + Random.nextInt(100)}")
                delay(100)
            }
        },

        { _, color ->
            val dragonBalls = listOf("🔴", "🟠", "🟡", "🟢", "🔵", "🟣", "⭐")
            for ((index, ball) in dragonBalls.withIndex()) {
                if (!scope.isActive) return@listOf
                println("\rFinding Dragon Balls: ${dragonBalls.take(index + 1).joinToString("")} ${7 - index - 1} left")
                delay(400)
            }
        },

        { _, color ->
            val wave = listOf("(", "💢", "≡", "🌊", "≡", "💢", ")")
            for (i in 1..10) {
                if (!scope.isActive) return@listOf
                val waveSize = i % 5 + 1
                println("\rKamehameha: ${wave.joinToString("").repeat(waveSize).withStyle(TerminalColor.Cyan)}")
                delay(150)
            }
        },

        { _, color ->
            val basePower = Random.nextInt(100, 1000)
            for (i in 1..5) {
                if (!scope.isActive) return@listOf
                val power = basePower * i * Random.nextInt(1, 10)
                println("\r👓 Power Level: $power".withStyle(TerminalColor.Green))
                delay(300)
            }
            if (!scope.isActive) return@listOf
            println("\r👓 IT'S OVER 9000!!!".withStyle(TerminalColor.Red))
            delay(500)
        }
    )

    return scope.launch {
        while (isActive) {
            val randomAnimation = animations[Random.nextInt(animations.size)]
            randomAnimation(scope, color)

            if (!isActive) return@launch
            delay(200)
        }
    }
}