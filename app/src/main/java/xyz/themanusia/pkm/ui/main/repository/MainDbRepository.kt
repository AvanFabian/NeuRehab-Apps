package xyz.themanusia.pkm.ui.main.repository

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import xyz.themanusia.pkm.model.BPM
import xyz.themanusia.pkm.model.BpmResponse
import xyz.themanusia.pkm.model.UserResponse
import xyz.themanusia.pkm.utils.ResultState
import xyz.themanusia.pkm.utils.Utils
import xyz.themanusia.pkm.utils.Utils.Companion.getCurrentEpoch
import java.util.Calendar
import javax.inject.Inject

class MainDbRepository @Inject constructor(
    private val db: DatabaseReference
) : MainRepository {

    override fun getBPM(uuid: String): Flow<ResultState<BpmResponse>> = callbackFlow {
        trySend(ResultState.Loading)
        var bpm = 0

        val valueEvent = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val item = BpmResponse(
                    item = BpmResponse.BpmItems(
                        bpm = snapshot.getValue(Int::class.java) ?: 0,
                    ),
                    key = snapshot.key
                )

                bpm = item.item?.bpm ?: 0

                val currentEpoch = System.currentTimeMillis()
                val currentDate = Utils.epochToDate(currentEpoch)
                val bpms = BPM(bpm, currentDate)

                db.child("users").child(uuid).child("bpm").child("$currentEpoch").setValue(bpms)

                trySend(ResultState.Success(item))
            }

            override fun onCancelled(error: DatabaseError) {
                trySend(ResultState.Failure(error.toException()))
            }
        }

        db.child("bpm").addValueEventListener(valueEvent)

        awaitClose {
            db.removeEventListener(valueEvent)
            close()
        }
    }

    override fun getBPMHistory(uuid: String): Flow<ResultState<List<BpmResponse>>> = callbackFlow {
        trySend(ResultState.Loading)

        val valueEvent = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val items = snapshot.children.map {
                    BpmResponse(
                        it.getValue(BpmResponse.BpmItems::class.java),
                        key = it.key
                    )
                }

                trySend(ResultState.Success(items))
            }

            override fun onCancelled(error: DatabaseError) {
                trySend(ResultState.Failure(error.toException()))
            }
        }

        val today = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)

        val lastWeekEpoch = Calendar.getInstance().apply {
            add(Calendar.DAY_OF_MONTH, today - 7)
        }.timeInMillis

        db.child("users").child(uuid).child("bpm")
            .orderByKey().startAfter(lastWeekEpoch.toString())
            .addListenerForSingleValueEvent(valueEvent)
        awaitClose {
            db.removeEventListener(valueEvent)
            close()
        }
    }

    override fun insert(uuid: String, bpm: Int) {

        val currentEpoch = System.currentTimeMillis()
        val currentDate = Utils.epochToDate(currentEpoch)
        val bpms = BPM(bpm, currentDate)

        db.child("users").child(uuid).child("bpm").child("$currentEpoch").setValue(bpms)

    }
}