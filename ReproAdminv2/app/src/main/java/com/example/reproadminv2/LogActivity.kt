package com.example.reproadminv2

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class LogActivity : AppCompatActivity() {
    private val url = "https://haiderirfan.pythonanywhere.com/get_logs" // Replace with the URL of your Flask server
    private val names = ArrayList<String>()
    private val dates = ArrayList<String>()
    private val addresses = ArrayList<String>()
    private val plastic = ArrayList<Int>()
    private val aluminum = ArrayList<Int>()
    private val card = ArrayList<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log)
        supportActionBar?.hide()

        val listView = findViewById<ListView>(R.id.listview)
        val backBtn = findViewById<Button>(R.id.back_log_btn)

//        listView.setClickable(true)

        backBtn.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

        val queue = Volley.newRequestQueue(this) // Instantiate a RequestQueue object

        val request = JsonObjectRequest(url, JSONObject(), // Create a JsonObjectRequest object
            Response.Listener { response ->
                val jsonArray = response.getJSONArray("data")
                for (i in 0 until jsonArray.length()) {
                    val jsonObject = jsonArray.getJSONObject(i)
                    names.add(jsonObject.getString("name"))
                    dates.add(jsonObject.getString("date"))
                    addresses.add(jsonObject.getString("address"))
                    plastic.add(jsonObject.getInt("plastic"))
                    aluminum.add(jsonObject.getInt("aluminum"))
                    card.add(jsonObject.getInt("cardboard"))
                }
                listView.adapter = MyCustomAdapter(this) // Update the adapter with the data
            },
            Response.ErrorListener { error ->
                Log.e("Volley", error.message ?: "Unknown error") // Handle errors
            })

        queue.add(request) // Add the request to the queue

//        listView.setOnItemClickListener { parent, view, position, id ->
//            // Here's the data for each item
//            val name = names[position]
//            val address = addresses[position]
//            val plastic = plastic[position]
//            val aluminum = aluminum[position]
//            val card = card[position]
//
//            val intent = Intent(this, UsersActivity::class.java).also{
//                it.putExtra("name", name)
//                it.putExtra("address", address)
//                it.putExtra("plastic", plastic.toString())
//                it.putExtra("aluminum", aluminum.toString())
//                it.putExtra("card", card.toString())
//                startActivity(it)
//            }
//        }
    }

    inner class MyCustomAdapter(val context: Context) : BaseAdapter() {
        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val view: View = LayoutInflater.from(context).inflate(R.layout.activity_row_main_log, parent, false)
            val nameTextView = view.findViewById<TextView>(R.id.textName1)
            val addressTextView = view.findViewById<TextView>(R.id.textAddress1)
            val dateTextView = view.findViewById<TextView>(R.id.textDate1)
//            val plasticTextView = view.findViewById<TextView>(R.id.textPlastic)
//            val glassTextView = view.findViewById<TextView>(R.id.textGlass)
//            val cardTextView = view.findViewById<TextView>(R.id.textCard)
            nameTextView.text = names[position]
            addressTextView.text = addresses[position]
            dateTextView.text = dates[position]
//            plasticTextView.text = "Plastic: ${plastic[position]}"
//            glassTextView.text = "Glass: ${glass[position]}"
//            cardTextView.text = "Cardboard: ${card[position]}"
            return view
        }

        override fun getItem(position: Int): Any {
            return names[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return names.size
        }
    }
}

//        val backBtn = findViewById<Button>(R.id.back_log_btn)
//
//        backBtn.setOnClickListener {
//            val intent = Intent(this, HomeActivity::class.java)
//            startActivity(intent)
//        }
//
//        val listView = findViewById<ListView>(R.id.listview)
////        val redColor = Color.parseColor("#FF0000")
////        listView.setBackgroundColor(redColor)
//        listView.adapter = LogActivity.Adapter(this)
//    }
//    private class Adapter(context: Context): BaseAdapter() {
//
//        private val mContext: Context
//
//        private val names = arrayListOf<String>(
//            "AliHaider", "KashifAli", "BehroseKhan", "Haider", "TalhaImtiaz", "BabarAyaz", "AmmarButt", "TayyabMunj", "UzairShahid", "SaadUsman", "AbdulAhad", "UmairPervaiz"
//        )
//
//        private val addresses = arrayListOf<String>(
//            "79A Hostel 8", "10A Hostel 9", "44A Hostel 10", "54B Hostel 9", "13A Hostel 8", "38A Hostel 9", "78A Hostel 8", "78B Hostel 8", "68A Hostel 8", "74B Hostel 10", "74B Hostel 10", "40B Hostel 8"
//        )
//
//        private val plastic = arrayListOf<String>(
//            "80", "92", "83", "85", "90", "88", "89", "91", "84", "85", "86", "97"
//        )
//
//        private val glass = arrayListOf<String>(
//            "57", "45", "47", "54", "50", "58", "45", "41", "50", "46", "59", "42"
//        )
//
//        private val dates = arrayListOf<String>(
//            "January-20-23", "January-20-23", "January-20-23", "January-19-23", "January-19-23", "January-19-23", "January-18-23", "January-18-23", "January-17-23", "January-17-23", "January-17-23", "January-16-23"
//        )
//
//        init {
//            mContext = context
//        }
//
//        // responsible for how many rows in my list
//        override fun getCount(): Int {
//            return names.size
//        }
//
//        // you can also ignore this
//        override fun getItemId(position: Int): Long {
//            return position.toLong()
//        }
//
//        // you can ignore this for now
//        override fun getItem(position: Int): Any {
//            return "TEST STRING"
//        }
//
//        // responsible for rendering out each row
//        override fun getView(position: Int, convertView: View?, viewGroup: ViewGroup?): View {
//            val layoutInflater = LayoutInflater.from(mContext)
//            val rowMain = layoutInflater.inflate(R.layout.activity_row_main_log, viewGroup, false)
//
//            val nameTextView = rowMain.findViewById<TextView>(R.id.textName1)
//            nameTextView.text = names.get(position)
//
//            val addressesTextView = rowMain.findViewById<TextView>(R.id.textAddress1)
//            addressesTextView.text = "Address: ${addresses.get(position)}"
//
////            val plasticTextView = rowMain.findViewById<TextView>(R.id.textPlastic1)
////            plasticTextView.text = "Number of plastic: ${plastic.get(position)}"
////
////            val glassTextView = rowMain.findViewById<TextView>(R.id.textGlass1)
////            glassTextView.text = "Number of glass: ${glass.get(position)}"
//
//            val datesTextView = rowMain.findViewById<TextView>(R.id.textDate1)
//            datesTextView.text = "Date Collected: ${dates.get(position)}"
//
//            return rowMain
////            val textView = TextView(mContext)
////            textView.text = "HERE is my ROW for my LISTVIEW"
////            return textView
//        }
//
//    }
//}