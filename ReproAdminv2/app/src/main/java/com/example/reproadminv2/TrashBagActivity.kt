package com.example.reproadminv2

import android.R
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.reproadminv2.databinding.ActivityTrashBagBinding


abstract class TrashBagActivity : AppCompatActivity() {
    abstract var binding: ActivityTrashBagBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_trash_bag)
        binding = ActivityTrashBagBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        val name = arrayOf(
            "Christopher",
            "Craig",
            "Sergio",
            "Mubariz",
            "Mike",
            "Michael",
            "Toa",
            "Ivana",
            "Alex"
        )
        val lastMessage = arrayOf(
            "Heye", "Supp", "Let's Catchup", "Dinner tonight?", "Gotta go",
            "i'm in meeting", "Gotcha", "Let's Go", "any Weekend Plans?"
        )
        val lastmsgTime = arrayOf(
            "8:45 pm", "9:00 am", "7:34 pm", "6:32 am", "5:76 am",
            "5:00 am", "7:34 pm", "2:32 am", "7:76 am"
        )
        val phoneNo = arrayOf(
            "7656610000", "9999043232", "7834354323", "9876543211", "5434432343",
            "9439043232", "7534354323", "6545543211", "7654432343"
        )
        val country = arrayOf(
            "United States",
            "Russia",
            "India",
            "England",
            "Germany",
            "Thailand",
            "Canada",
            "France",
            "Switzerland"
        )

    }
}