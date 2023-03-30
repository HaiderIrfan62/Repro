package com.example.repro

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class BagActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bag)
        supportActionBar?.hide()

        val backBtn = findViewById<Button>(R.id.back_bag_btn)
        val scanned = findViewById<TextView>(R.id.scanned_tokens_bag)
        val earned = findViewById<TextView>(R.id.earned_tokens_bag)
        val plastic_tv = findViewById<TextView>(R.id.plastic)
        val aluminum_tv = findViewById<TextView>(R.id.aluminum)
        val cardboard_tv = findViewById<TextView>(R.id.cardboard_tv)

        val bundle = intent.extras
        val scannedTokens = bundle!!.getInt("scanned")
        val earnedTokens = bundle!!.getInt("earned")
        val plastic = bundle!!.getInt("plastic")
        val aluminum = bundle!!.getInt("aluminum")
        val cardboard = bundle!!.getInt("cardboard")

        scanned.text = scannedTokens.toString()
        earned.text = earnedTokens.toString()
        plastic_tv.text = plastic.toString()
        aluminum_tv.text = aluminum.toString()
        cardboard_tv.text = cardboard.toString()

        scanned.setOnClickListener {
            val intent = Intent(this, TokensActivity::class.java).also{
                it.putExtra("scanned", scannedTokens)
                it.putExtra("earned", earnedTokens)
                it.putExtra("plastic", plastic)
                it.putExtra("aluminum", aluminum)
                it.putExtra("cardboard", cardboard)
                startActivity(it)
            }
        }

        earned.setOnClickListener {
            val intent = Intent(this, TokensActivity::class.java).also{
                it.putExtra("scanned", scannedTokens)
                it.putExtra("earned", earnedTokens)
                it.putExtra("plastic", plastic)
                it.putExtra("aluminum", aluminum)
                it.putExtra("cardboard", cardboard)
                startActivity(it)
            }
        }

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