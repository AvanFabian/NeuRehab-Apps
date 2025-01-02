package xyz.themanusia.pkm.ui.homepage.repository

import kotlinx.coroutines.flow.Flow
import xyz.themanusia.pkm.model.UserResponse
import xyz.themanusia.pkm.utils.ResultState

interface HomepageRepository {

    fun insert(
        item: UserResponse.UserItems
    ): Flow<ResultState<String>>

    fun getItems(): Flow<ResultState<List<UserResponse>>>

    fun deleteItems(
        uuid: String
    ): Flow<ResultState<String>>

    fun getTimestamp(): Flow<ResultState<Long>>
}