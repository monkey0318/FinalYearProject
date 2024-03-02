package com.example.finalyearproject.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.finalyearproject.R
import com.example.finalyearproject.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private val nav by lazy {
        supportFragmentManager.findFragmentById(R.id.nav_selection)!!.findNavController()
    }
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupActionBarWithNavController(nav)

    }
    override fun onSupportNavigateUp(): Boolean {
        return nav.navigateUp() || super.onSupportNavigateUp()
    }
}