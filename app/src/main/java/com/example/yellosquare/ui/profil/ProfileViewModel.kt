package com.example.yellosquare.ui.profil

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.yellosquare.model.User
import com.example.yellosquare.repo.MoveSqaureRepo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.toObject

class ProfileViewModel : ViewModel(){

    var userRepo: MoveSqaureRepo = MoveSqaureRepo()
    var auth: FirebaseAuth? = null
    var userData : MutableLiveData<User> = MutableLiveData()

    init {
        auth = FirebaseAuth.getInstance()
        getData()
    }

    fun getData(){
        userRepo.getUser(auth!!.uid.toString())
            .addOnSuccessListener { result ->
                userData.postValue(result.toObject(User::class.java))
            }
            .addOnFailureListener { exception ->
                Log.i("DATA_PROFILE",exception.message.toString())
            }
    }
}