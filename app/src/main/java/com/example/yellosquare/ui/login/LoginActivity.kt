package com.example.yellosquare.ui.login

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.yellosquare.R
import com.example.yellosquare.databinding.ActivityLoginBinding
import com.example.yellosquare.databinding.ActivityMainBinding
import com.example.yellosquare.repo.MoveSqaureRepo
import com.example.yellosquare.ui.MainActivity
import com.example.yellosquare.utils.RC_GOOGLE_SIGN_IN
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class LoginActivity : AppCompatActivity() {

    var auth: FirebaseAuth? = null
    var mUserRepo : MoveSqaureRepo? = null
    lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        currentUser()
        initGoogle()
        setupListener()
    }

    private fun setupListener(){
        binding.login.setOnClickListener {
            signIn()
        }
    }

    private fun initView(){
        auth = FirebaseAuth.getInstance()
        mUserRepo = MoveSqaureRepo()
    }

    private fun initGoogle(){
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(this.getString(R.string.default_web_client_idd))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_GOOGLE_SIGN_IN)
    }

    // [START auth_with_google]
    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth!!.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    succesCreateAccount()
                } else {
                    // If sign in fails, display a message to the user.
                }
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_GOOGLE_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
            }
        }
    }

        private fun succesCreateAccount(){
            createUserInFirestore()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
    }

    private fun createUserInFirestore() {
        val username = auth!!.currentUser!!.displayName
        val uid = auth!!.currentUser!!.uid
        mUserRepo?.createUser(uid, username, listOf(),"")
    }

    private fun currentUser(){
        Log.i("TAEREA","${auth?.uid}")
        if (auth?.currentUser != null){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}