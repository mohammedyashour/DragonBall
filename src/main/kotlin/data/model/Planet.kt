package data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Planet(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
    @SerialName("isDestroyed") val isDestroyed: Boolean,
    @SerialName("description") val description: String,
    @SerialName("image") val image: String,
    @SerialName("deletedAt") val deletedAt: String? = null
)
