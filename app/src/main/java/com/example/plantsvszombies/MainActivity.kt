package com.example.plantsvszombies

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    lateinit var playButton : Button
    lateinit var regleButton : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_menu)
        playButton = findViewById(R.id.button)
        regleButton = findViewById(R.id.buttonregle)

        playButton.setOnClickListener{
            startActivity(Intent(this@MainActivity, LevelChoiceActivity::class.java))
        }
        regleButton.setOnClickListener{
            setContentView(R.layout.rules_layout)

            val tv = findViewById<TextView>(R.id.textView2)
            tv.movementMethod = ScrollingMovementMethod()

            val buttonQuit = findViewById<Button>(R.id.buttonRulesQuit)
            buttonQuit.setOnClickListener{
                startActivity(Intent(this@MainActivity, MainActivity::class.java))
            }
        }
    }
}