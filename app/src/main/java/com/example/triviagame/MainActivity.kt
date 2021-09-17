package com.example.triviagame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.triviagame.databinding.ActivityMainBinding
import okhttp3.*


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private  val questionFragment = QuestionFragment()
    val clint = OkHttpClient()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction().add(R.id.fragment_container, questionFragment).commit()
    }
}