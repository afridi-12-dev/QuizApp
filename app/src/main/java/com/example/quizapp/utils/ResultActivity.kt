package com.example.quizapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val score = intent.getIntExtra("score", 0)
        val total = intent.getIntExtra("total", 0)

        val scoreText: TextView = findViewById(R.id.tv_score)
        val restartBtn: Button = findViewById(R.id.btn_restart)
        val exitBtn: Button = findViewById(R.id.btn_exit)

        scoreText.text = "Score $score / $total"

        restartBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        exitBtn.setOnClickListener {
            finishAffinity()
        }
    }
}
