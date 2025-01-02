package xyz.themanusia.pkm.model

data class UserResponse(
    val item: UserItems?,
    val key: String? = ""
) {
    data class UserItems(
        val name: String? = "",
        val uuid: String? = "",
    )
}