package com.example.repro

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class TokensActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tokens)
        supportActionBar?.hide()

        val backBtn = findViewById<Button>(R.id.back_tokens_btn)
        val scanned = findViewById<TextView>(R.id.scanned_tokens_tokens)
        val earned = findViewById<TextView>(R.id.earned_tokens_tokens)

        val bundle = intent.extras
        val scannedTokens = bundle!!.getInt("scanned")
        val earnedTokens = bundle!!.getInt("earned")
        val plastic = bundle!!.getInt("plastic")
        val aluminum = bundle!!.getInt("aluminum")
        val cardboard = bundle!!.getInt("cardboard")

        scanned.text = scannedTokens.toString()
        earned.text = earnedTokens.toString()

        backBtn.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java).also {
                it.putExtra("scanned", scannedTokens)
                it.putExtra("earned", earnedTokens)
                it.putExtra("plastic", plastic)
                it.putExtra("aluminum", aluminum)
                it.putExtra("cardboard", cardboard)
                startActivity(it)
            }
        }
    }
}