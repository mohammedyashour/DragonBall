package domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Transformation(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
    @SerialName("image") val image: String,
    @SerialName("ki") val ki: String,
    @SerialName("deletedAt") val deletedAt: String? = null
)