package xyz.themanusia.pkm.ui.homepage

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import xyz.themanusia.pkm.model.UserResponse
import xyz.themanusia.pkm.ui.homepage.repository.HomepageRepository
import xyz.themanusia.pkm.utils.ResultState
import javax.inject.Inject

@HiltViewModel
class HomepageViewModel @Inject constructor(
    private val repository: HomepageRepository
) : ViewModel() {
    private val _res: MutableState<ItemState> = mutableStateOf(ItemState())
    private val _res2: MutableState<ItemState> = mutableStateOf(ItemState())
    val res: State<ItemState> = _res
    val res2: State<ItemState> = _res2

    fun insert(items: UserResponse.UserItems) = repository.insert(items)

    fun delete(uuid: String) = repository.deleteItems(uuid)

    init {
        viewModelScope.launch {
            repository.getItems().collect{
                when(it){
                    is ResultState.Success -> {
                        _res.value = ItemState(item = it.data)
                    }
                    is ResultState.Failure -> {
                        _res.value = ItemState(error = it.msg.toString())
                    }
                    ResultState.Loading -> {
                        _res.value = ItemState(isLoading = true)
                    }
                }
            }
        }
        viewModelScope.launch {
            repository.getTimestamp().collect {
                when(it){
                    is ResultState.Success -> {
                        _res2.value = ItemState(timestamp = it.data)
                    }
                    is ResultState.Failure -> {
                        _res2.value = ItemState(error = it.msg.toString())
                    }
                    ResultState.Loading -> {

                    }
                }
            }

        }
    }

}

data class ItemState(
    val item:List<UserResponse> = emptyList(),
    val timestamp:Long = 0,
    val error:String  = "",
    val isLoading:Boolean = false
)