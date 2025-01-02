package xyz.themanusia.pkm.ui.main.repository

import kotlinx.coroutines.flow.Flow
import xyz.themanusia.pkm.model.BpmResponse
import xyz.themanusia.pkm.utils.ResultState

interface MainRepository {
    fun getBPM(uuid: String): Flow<ResultState<BpmResponse>>
    fun getBPMHistory(uuid: String): Flow<ResultState<List<BpmResponse>>>
    fun insert(uuid: String, bpm: Int)
}