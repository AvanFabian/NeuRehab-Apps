package xyz.themanusia.pkm.model

import com.google.firebase.database.PropertyName

data class BPM(
    @PropertyName("bpm") val bpm: Int,
    @PropertyName("time") val time: String,
    val avg: Int = 0, val max: Int = 0, val min: Int = 0,
) {
    constructor() : this(0, "")

    override fun toString(): String {
        return "BPM(bpm=$bpm, time='$time', avg=$avg, max=$max, min=$min)"
    }
}
