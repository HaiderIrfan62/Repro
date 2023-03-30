package com.example.repro

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class ShopActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop)
        supportActionBar?.hide()

        val backBtn = findViewById<Button>(R.id.back_shop_btn)
        val shirtBtn = findViewById<Button>(R.id.buy_shirt)
        val mugBtn = findViewById<Button>(R.id.buy_mug)
        val bookBtn = findViewById<Button>(R.id.buy_notebook)
        val scanned = findViewById<TextView>(R.id.scanned_tokens_shop)
        val earned = findViewById<TextView>(R.id.earned_tokens_shop)

        val bundle = intent.extras
        val scannedTokens = bundle!!.getInt("scanned")
        val earnedTokens = bundle!!.getInt("earned")
        val plastic = bundle!!.getInt("plastic")
        val aluminum = bundle!!.getInt("aluminum")
        val cardboard = bundle!!.getInt("cardboard")

        scanned.text = scannedTokens.toString()
        earned.text = earnedTokens.toString()

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

        shirtBtn.setOnClickListener {
            Toast.makeText(this, "Not enough repro tokens", Toast.LENGTH_SHORT).show()
        }

        mugBtn.setOnClickListener {
            Toast.makeText(this, "Not enough repro tokens", Toast.LENGTH_SHORT).show()
        }

        bookBtn.setOnClickListener {
            Toast.makeText(this, "Not enough repro tokens", Toast.LENGTH_SHORT).show()
        }
    }
}