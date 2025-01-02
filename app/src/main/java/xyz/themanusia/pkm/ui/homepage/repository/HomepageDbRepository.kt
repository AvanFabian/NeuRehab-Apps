package xyz.themanusia.pkm.ui.homepage.repository

import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import xyz.themanusia.pkm.model.UserResponse
import xyz.themanusia.pkm.utils.ResultState
import javax.inject.Inject

class HomepageDbRepository @Inject constructor(
    private val db: DatabaseReference
) : HomepageRepository {

    override fun insert(item: UserResponse.UserItems): Flow<ResultState<String>> =
        callbackFlow {
            trySend(ResultState.Loading)

            db.child("users").child(item.uuid.toString()).setValue(
                item
            ).addOnCompleteListener {
                if (it.isSuccessful)
                    trySend(ResultState.Success("Data inserted Successfully.."))
            }.addOnFailureListener {
                trySend(ResultState.Failure(it))
            }

            awaitClose {
                close()
            }
        }

    override fun getItems(): Flow<ResultState<List<UserResponse>>> = callbackFlow {
        trySend(ResultState.Loading)

        val valueEvent = object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                val items = snapshot.children.map {
                    UserResponse(
                        it.getValue(UserResponse.UserItems::class.java),
                        key = it.key
                    )
                }
                trySend(ResultState.Success(items))
            }

            override fun onCancelled(error: DatabaseError) {
                trySend(ResultState.Failure(error.toException()))
            }

        }

        db.child("users").addValueEventListener(valueEvent)
        awaitClose {
            db.removeEventListener(valueEvent)
            close()
        }

    }

    override fun deleteItems(uuid: String): Flow<ResultState<String>> = callbackFlow {
        trySend(ResultState.Loading)

        db.child("users").child(uuid).removeValue().addOnCompleteListener {
            if (it.isSuccessful)
                trySend(ResultState.Success("Data deleted Successfully.."))
        }.addOnFailureListener {
            trySend(ResultState.Failure(it))
        }

        awaitClose {
            close()
        }
    }

    override fun getTimestamp(): Flow<ResultState<Long>> {
        return callbackFlow {
            trySend(ResultState.Loading)

            db.child("timestamp").addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val value = snapshot.value as String
                    trySend(ResultState.Success(value.toLong()))
                }

                override fun onCancelled(error: DatabaseError) {
                    trySend(ResultState.Failure(error.toException()))
                }
            })

            awaitClose {
                close()
            }
        }
    }
}