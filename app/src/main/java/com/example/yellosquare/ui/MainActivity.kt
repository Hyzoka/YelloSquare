package com.example.yellosquare.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View.OnTouchListener
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.yellosquare.databinding.ActivityMainBinding
import com.example.yellosquare.model.Square
import com.example.yellosquare.repo.MoveSqaureRepo
import com.example.yellosquare.ui.profil.ProfileActivity
import com.google.firebase.auth.FirebaseAuth


@SuppressLint("ClickableViewAccessibility")
class MainActivity : AppCompatActivity() {

    var mUserRepo : MoveSqaureRepo? = null
    var auth: FirebaseAuth? = null

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUi()
        setupListener()
    }

    private fun initUi(){
        mUserRepo = MoveSqaureRepo()
        auth = FirebaseAuth.getInstance()
    }

    private fun setupListener(){
        binding.yellowSquare.setOnTouchListener(onTouchListener())

        binding.userData.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }
    }

    private fun onTouchListener(): OnTouchListener? {
        return OnTouchListener { view, event ->
            when (event.action and MotionEvent.ACTION_MASK) {
                MotionEvent.ACTION_DOWN -> {
                    view.scaleX =  1.2f
                    view.scaleY =  1.2f

                }
                MotionEvent.ACTION_UP -> {
                    view.scaleX =  1f
                    view.scaleY =  1f
                    mUserRepo!!.addNewPosition(auth!!.uid.toString(),listOf(Square(view.x.toInt(),view.y.toInt())))
                    Toast.makeText(this, "New loc : ${view.x.toInt() } and ${view.y.toInt()}", Toast.LENGTH_SHORT).show()
                }

                MotionEvent.ACTION_MOVE -> {
                    view.y = event.rawY - view.height/2
                    view.x = event.rawX - view.width/2
                }
            }
            true
        }
    }
}