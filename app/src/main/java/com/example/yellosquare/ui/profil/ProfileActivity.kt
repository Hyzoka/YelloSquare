package com.example.yellosquare.ui.profil

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.yellosquare.databinding.ActivityLoginBinding
import com.example.yellosquare.databinding.ActivityMainBinding
import com.example.yellosquare.databinding.ActivityProfilBinding
import com.mikepenz.fastadapter.IItem
import com.mikepenz.fastadapter.commons.adapters.FastItemAdapter

class ProfileActivity : AppCompatActivity(){

    private val adapter = FastItemAdapter<IItem<*, *>>()
    private lateinit var binding: ActivityProfilBinding
    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfilBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.i("DATA_PROFILE","ON CREATED")
        setupViewModel()
        initList()
    }

    private fun setupViewModel(){
        Log.i("DATA_PROFILE","ON SETUP VIEW MODEL")
        viewModel.userData.observe(this, Observer { user ->
            Log.i("DATA_PROFILE",user.toString())
            adapter.add(user.squarePositions?.map { ListPositionItem(it) })
            binding.welcome.text = "Welcome ${user.userName}!!"
            binding.updateDate.text = "Your last game end at ${user.dateUpdated}"
        })
    }

    private fun initList(){
        binding.rvPosition.layoutManager = LinearLayoutManager(this)
        adapter.withSelectable(true)
        adapter.withSelectWithItemUpdate(true)
        binding.rvPosition.adapter = adapter
        adapter.withOnPreClickListener { _, _, _, _ ->
            false
        }
    }
}