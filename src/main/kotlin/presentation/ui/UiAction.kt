package presentation.ui

data class UiAction(
    val name: String,
    val action: suspend () -> Unit
)