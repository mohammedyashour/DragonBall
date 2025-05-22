package domain.model

import kotlinx.serialization.Serializable

@Serializable
data class DragonBallCharacter(
    val id: Int,
    val name: String,
    val ki: String,
    val maxKi: String,
    val race: String,
    val gender: String,
    val description: String,
    val image: String,
    val affiliation: String,
    val deletedAt: String? = null,
    val originPlanet: Planet? = null,
    val transformations: List<Transformation> = emptyList()
) {

    override fun toString(): String {
        return "Character(id=$id, name='$name', race='$race')"
    }
}