package xyz.themanusia.pkm.model

import com.google.firebase.database.PropertyName

data class User(
    @PropertyName("uuid") val uuid: String = "",
    @PropertyName("name") val name: String = "",
) {
    constructor() : this("", "")

    override fun toString(): String {
        return "User(uuid='$uuid', name='$name')"
    }
}
