package com.example.reproadminv2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        supportActionBar?.hide()

        val backBtn = findViewById<Button>(R.id.back_home_btn)
        val scanBtn = findViewById<Button>(R.id.scan_btn)
        val bagBtn = findViewById<Button>(R.id.bag_btn)

        scanBtn.setOnClickListener {
            val intent = Intent(this, TrashActivity::class.java)
            startActivity(intent)
        }

        bagBtn.setOnClickListener {
            val intent = Intent(this, LogActivity::class.java)
            startActivity(intent)
        }

        backBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}