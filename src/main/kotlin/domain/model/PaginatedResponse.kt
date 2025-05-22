package domain.model


import kotlinx.serialization.Serializable
@Serializable
data class ApiResponse<T>(
    val items: List<T>,
    val meta: MetaData? = null,
    val links: PaginationLinks? = null
)

@Serializable
data class MetaData(
    val totalItems: Int,
    val itemCount: Int,
    val itemsPerPage: Int,
    val totalPages: Int,
    val currentPage: Int
)

@Serializable
data class PaginationLinks(
    val first: String,
    val previous: String? = null,
    val next: String? = null,
    val last: String
)