package xyz.themanusia.pkm.ui.main

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jaikeerthick.composable_graphs.composables.bar.model.BarData
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import xyz.themanusia.pkm.model.BPM
import xyz.themanusia.pkm.model.BpmResponse
import xyz.themanusia.pkm.ui.main.repository.MainRepository
import xyz.themanusia.pkm.utils.ResultState
import xyz.themanusia.pkm.utils.Utils
import xyz.themanusia.pkm.utils.Utils.Companion.getCurrentEpoch

@HiltViewModel(assistedFactory = MainViewModel.Factory::class)
class MainViewModel @AssistedInject constructor(
    private val repository: MainRepository,
    @Assisted private val uuid: String
) : ViewModel() {
    private val _res: MutableState<ItemState> = mutableStateOf(ItemState())
    private val _res2: MutableState<ItemState> = mutableStateOf(ItemState())
    val res: State<ItemState> = _res
    val res2: State<ItemState> = _res2

    @AssistedFactory
    interface Factory {
        fun create(uuid: String): MainViewModel
    }

    fun insert(uuid: String, bpm: Int) = repository.insert(uuid, bpm)


    init {
        viewModelScope.launch {
            repository.getBPM(uuid).collect {
                when (it) {
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
            repository.getBPMHistory(uuid).collect {
                Log.d("MainViewModel", "init: $it")
                when (it) {
                    is ResultState.Success -> {

                        val bpmEachDay =
                            mutableStateListOf<List<BpmResponse>>()

                        val bpmList = it.data
                        val bpmHistory = mutableStateListOf<BPM>()
                        val bpmSummary = mutableStateListOf<BPM>()
                        val data = mutableStateListOf<BarData>()

                        bpmList.forEach { bpm ->
                            val date = Utils.getDateByEpoch(Utils.dateToEpoch(bpm.item?.time ?: ""))
                            if (bpmHistory.find { asd -> Utils.getDateByEpoch(Utils.dateToEpoch(asd.time)) == date } == null) {
                                bpmHistory.add(
                                    BPM(
                                        bpm.item?.bpm ?: 0,
                                        bpm.item?.time ?: "",
                                        0,
                                        0,
                                        0
                                    )
                                )
                            }
                        }
                        bpmEachDay.clear()
                        for (i in 1..7) {
                            val day = bpmList.filter { asd ->
                                Utils.getDayByEpoch(Utils.dateToEpoch(asd.item?.time ?: "")) == i
                            }
                            bpmEachDay.add(day)
                        }
                        bpmSummary.clear()
                        bpmEachDay.forEach { bpm ->
                            if (bpm.isEmpty()) {
                                bpmSummary.add(BPM(0, "", 0, 0, 0))
                                return@forEach
                            } else {
                                var sum = 0
                                var max = 0
                                var min = 0

                                bpm.forEach { bpms ->
                                    val asd = bpms.item?.bpm ?: 0
                                    sum += asd
                                    if (asd > max) max = asd
                                    if (asd < min) min = asd
                                }
                                val avg = sum / bpm.size
                                bpmSummary.add(BPM(avg, "", avg, max, min))
                            }
                        }

                        data.clear()
                        bpmSummary.forEachIndexed { index, bpms ->
                            data.add(BarData(Utils.getDay2(index + 1), bpms.avg.toFloat()))
                        }

                        _res2.value =
                            ItemState(bpmSummary = bpmSummary, bpmHistory = bpmHistory, data = data)
                    }

                    is ResultState.Failure -> {
                        _res2.value = ItemState(error = it.msg.toString())
                    }

                    ResultState.Loading -> {
                        _res2.value = ItemState(isLoading = true)
                    }
                }
            }
        }
    }
}

data class ItemState(
    val item: BpmResponse? = null,
    val data: List<BarData> = listOf(
        BarData("Sun", 70f),
        BarData("Mon", 10f),
        BarData("Tue", 20f),
        BarData("Wed", 30f),
        BarData("Thu", 40f),
        BarData("Fri", 50f),
        BarData("Sat", 60f),
    ),
    val bpmSummary: List<BPM> = listOf(
        BPM(0, "", 0, 0, 0),
        BPM(0, "", 0, 0, 0),
        BPM(0, "", 0, 0, 0),
        BPM(0, "", 0, 0, 0),
        BPM(0, "", 0, 0, 0),
        BPM(0, "", 0, 0, 0),
        BPM(0, "", 0, 0, 0),
    ),
    val bpmHistory: List<BPM> = emptyList(),
    val error: String = "",
    val isLoading: Boolean = false
)