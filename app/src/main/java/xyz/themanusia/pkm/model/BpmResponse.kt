package xyz.themanusia.pkm.model

data class BpmResponse(
    val item: BpmItems?, val key: String? = ""
) {
    data class BpmItems(
        val bpm: Int = 0,
        val time: String = "",
        val avg: Int = 0, val max: Int = 0, val min: Int = 0,
    )
}