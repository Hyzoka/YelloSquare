package com.example.yellosquare.repo

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import com.example.yellosquare.model.Square
import com.example.yellosquare.utils.COLLECTION_USER
import com.example.yellosquare.utils.DATE_UPDATED
import com.example.yellosquare.utils.SQUARE_POSITION
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MoveSqaureRepo {

    private  val db = FirebaseFirestore.getInstance()
    var listSquare : MutableList<Square> = mutableListOf()
    var updateListSquare : MutableLiveData<MutableList<Square>> = MutableLiveData()

    // --- CREATE ---
    fun createUser(id: String, username: String?,list : List<Square>, date: String) {
        val user = hashMapOf("userName" to username, "squarePositions" to list, "dateUpdated" to date)
        db.collection(COLLECTION_USER)
            .document(id).set(user)
            .addOnSuccessListener { documentReference ->
            }
            .addOnFailureListener { e ->
            }
    }

    fun getPositionsSquare(id : String): Task<QuerySnapshot> {
        return db.collection(COLLECTION_USER).document(id).collection(SQUARE_POSITION).get()
    }

    fun getUser(id :String): Task<DocumentSnapshot> {
        return db.collection(COLLECTION_USER)
            .document(id)
            .get()
    }

    fun addNewPosition(id : String, list :List<Square>){

        getPositionsSquare(id)
            .addOnSuccessListener { result ->
                for (document in result) {
                    listSquare.add(document.toObject(Square::class.java))
                }
                list.forEach {
                    listSquare.add(it)
                }

                db.collection(COLLECTION_USER).document(id).update(DATE_UPDATED , currentDate())
                db.collection(COLLECTION_USER).document(id).update(SQUARE_POSITION , listSquare)
                updateListSquare.postValue(listSquare)
            }
            .addOnFailureListener { exception ->
                Log.w("TAG", "Error getting documents.", exception)
            }
    }

    private fun currentDate() : String{
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")
        return current.format(formatter)
    }
}