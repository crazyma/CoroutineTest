package json

import kotlinx.serialization.Serializable

@Serializable
data class DataModel(
    val name: String,
    val age: Int
)