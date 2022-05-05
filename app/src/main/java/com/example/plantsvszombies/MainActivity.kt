package com.example.plantsvszombies

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    lateinit var playButton : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_menu)
        playButton = findViewById(R.id.button)

        playButton.setOnClickListener{
            startActivity(Intent(this@MainActivity, LevelChoiceActivity::class.java))
        }
    }
}