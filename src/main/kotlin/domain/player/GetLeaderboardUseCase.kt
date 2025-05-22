package domain.player

class GetLeaderboardUseCase(private val playerManager: PlayerManager) {
    fun execute(): Map<String, Int> = playerManager.getHighScores()
}