package com.example.plantsvszombies

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class LevelChoiceActivity : AppCompatActivity() {
    lateinit var level1Button : Button
    lateinit var level2Button : Button
    lateinit var level3Button : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.level_choice)
        level1Button = findViewById(R.id.button1)
        level2Button = findViewById(R.id.button2)
        level3Button = findViewById(R.id.button3)

        level1Button.setOnClickListener{
            val intent = Intent(this@LevelChoiceActivity, GameActivity::class.java)
            intent.putExtra("level", 0)
            startActivity(intent)
        }
        level2Button.setOnClickListener{
            val intent = Intent(this@LevelChoiceActivity, GameActivity::class.java)
            intent.putExtra("level", 1)
            startActivity(intent)
        }
        level3Button.setOnClickListener{
            val intent = Intent(this@LevelChoiceActivity, GameActivity::class.java)
            intent.putExtra("level", 2)
            startActivity(intent)
        }
    }
}