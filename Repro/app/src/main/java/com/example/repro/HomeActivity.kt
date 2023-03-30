package com.example.repro

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        supportActionBar?.hide()

        val backBtn = findViewById<Button>(R.id.back_home_btn)
        val scanBtn = findViewById<Button>(R.id.scan_btn)
        val bagBtn = findViewById<Button>(R.id.bag_btn)
        val shopBtn = findViewById<Button>(R.id.shop_btn)
        val scanned = findViewById<TextView>(R.id.scanned_tokens)
        val earned = findViewById<TextView>(R.id.earned_tokens)

        val bundle = intent.extras
        val name = bundle!!.getString("name")
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

        scanBtn.setOnClickListener {
            val intent = Intent(this, ScanActivity::class.java).also{
                it.putExtra("name", name)
                it.putExtra("scanned", scannedTokens)
                it.putExtra("earned", earnedTokens)
                it.putExtra("plastic", plastic)
                it.putExtra("aluminum", aluminum)
                it.putExtra("cardboard", cardboard)
                startActivity(it)
            }
        }

        bagBtn.setOnClickListener {
            val intent = Intent(this, BagActivity::class.java).also{
                it.putExtra("scanned", scannedTokens)
                it.putExtra("earned", earnedTokens)
                it.putExtra("plastic", plastic)
                it.putExtra("aluminum", aluminum)
                it.putExtra("cardboard", cardboard)
                startActivity(it)
            }
        }

        backBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        shopBtn.setOnClickListener {
            val intent = Intent(this, ShopActivity::class.java).also{
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