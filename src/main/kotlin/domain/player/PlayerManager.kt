package domain.player

object PlayerManager {
    val AppVersion = "DragonBall v1.1.0"

    private val playerScores = mutableMapOf<String, Int>()
    private var currentPlayerName: String = ""

    fun savePlayerName(name: String) {
        currentPlayerName = name
    }

    fun getPlayerName(): String {
        return currentPlayerName
    }

    fun saveScore(playerName: String, score: Int) {
        playerScores[playerName] = playerScores.getOrDefault(playerName, 0) + score
    }

    fun getPlayerScore(playerName: String): Int {
        return playerScores[playerName] ?: 0
    }

    fun getHighScores(): Map<String, Int> {
        return playerScores.toSortedMap(compareByDescending { playerScores[it] })
    }
}
