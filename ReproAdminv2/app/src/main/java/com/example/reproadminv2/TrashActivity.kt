package com.example.reproadminv2

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import org.json.JSONObject

class TrashActivity : AppCompatActivity() {

    private val url = "https://haiderirfan.pythonanywhere.com/get_users" // Replace with the URL of your Flask server
    private val names = ArrayList<String>()
    private val addresses = ArrayList<String>()
    private val plastic = ArrayList<Int>()
    private val aluminum = ArrayList<Int>()
    private val card = ArrayList<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trash)
        supportActionBar?.hide()

        val listView = findViewById<ListView>(R.id.main_listview)
        val backBtn = findViewById<Button>(R.id.back_trash_btn)

        listView.setClickable(true)

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

        listView.setOnItemClickListener { parent, view, position, id ->
            // Here's the data for each item
            val name = names[position]
            val address = addresses[position]
            val plastic = plastic[position]
            val aluminum = aluminum[position]
            val card = card[position]

            val intent = Intent(this, UsersActivity::class.java).also{
                it.putExtra("name", name)
                it.putExtra("address", address)
                it.putExtra("plastic", plastic.toString())
                it.putExtra("aluminum", aluminum.toString())
                it.putExtra("card", card.toString())
                startActivity(it)
            }
        }
    }

    inner class MyCustomAdapter(val context: Context) : BaseAdapter() {
        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val view: View = LayoutInflater.from(context).inflate(R.layout.row_main, parent, false)
            val nameTextView = view.findViewById<TextView>(R.id.textName)
            val addressTextView = view.findViewById<TextView>(R.id.textAddress)
//            val plasticTextView = view.findViewById<TextView>(R.id.textPlastic)
//            val glassTextView = view.findViewById<TextView>(R.id.textGlass)
//            val cardTextView = view.findViewById<TextView>(R.id.textCard)
            nameTextView.text = names[position]
            addressTextView.text = addresses[position]
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


//class TrashActivity : AppCompatActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_trash)
//        supportActionBar?.hide()
//
//        val listView = findViewById<ListView>(R.id.main_listview)
//        val backBtn = findViewById<Button>(R.id.back_trash_btn)
//
//        listView.setClickable(true);
//
//        backBtn.setOnClickListener {
//            val intent = Intent(this, HomeActivity::class.java)
//            startActivity(intent)
//        }
//
//        listView.adapter = MyCustomAdapter(this)
//
//        listView.setOnItemClickListener { parent, view, position, id ->
//            // Here's the data for each item
//            val name = "Name " + (position + 1)
//            val address = "Address " + (position + 1)
//            val plastic = (position + 1) * 10
//            val glass = (position + 1) * 5
//            val card = (position + 1) * 15
//
//            val intent = Intent(this, UsersActivity::class.java).also{
//                it.putExtra("name", name)
//                it.putExtra("address", address)
//                it.putExtra("plastic", plastic.toString())
//                it.putExtra("glass", glass.toString())
//                it.putExtra("card", card.toString())
//                startActivity(it)
//            }
//        }
//    }
//
//    class MyCustomAdapter(val context: Context): BaseAdapter() {
//        private val names = arrayListOf<String>("Name 1", "Name 2", "Name 3", "Name 4", "Name 5", "Name 6", "Name 7", "Name 8", "Name 9", "Name 10")
//
//        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
//            val view: View = LayoutInflater.from(context).inflate(R.layout.row_main, parent, false)
//            val nameTextView = view.findViewById<TextView>(R.id.textName)
//            nameTextView.text = names[position]
//            return view
//        }
//
//        override fun getItem(position: Int): Any {
//            return names[position]
//        }
//
//        override fun getItemId(position: Int): Long {
//            return position.toLong()
//        }
//
//        override fun getCount(): Int {
//            return names.size
//        }
//    }
//
//}









//package com.example.reproadminv2
//
//import android.content.Context
//import android.content.Intent
//import android.os.AsyncTask
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import android.provider.ContactsContract.CommonDataKinds.Website.URL
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.*
//import androidx.cardview.widget.CardView
//import com.google.gson.Gson
//
//class TrashActivity : AppCompatActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_trash)
//        supportActionBar?.hide()
//
//        val listView = findViewById<ListView>(R.id.main_listview)
//        val backBtn = findViewById<Button>(R.id.back_trash_btn)
//
//        listView.setClickable(true)
//
//        backBtn.setOnClickListener {
//            val intent = Intent(this, HomeActivity::class.java)
//            startActivity(intent)
//        }
//
//        // Fetch data from the Flask server here and store it in a list
//        val dataList = fetchDataFromFlask()
//
//        listView.adapter = MyCustomAdapter(this, dataList)
//
//        listView.setOnItemClickListener { parent, view, position, id ->
//            val data = dataList[position]
//            val intent = Intent(this, UsersActivity::class.java).also {
//                it.putExtra("name", data.name)
//                it.putExtra("address", data.address)
//                it.putExtra("plastic", data.plastic)
//                //it.putExtra("card", data.cardboard)
//                startActivity(it)
//            }
//        }
//    }
//
//    private fun fetchDataFromFlask(): List<Data> {
//        // Implement the logic to fetch data from the Flask server here
//        return listOf()
//    }
//
//    class MyCustomAdapter(context: Context, dataList: List<Data>) : BaseAdapter() {
//        private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
//        private val data = dataList
//
//        override fun getCount(): Int {
//            return data.size
//        }
//
//        override fun getItemId(position: Int): Long {
//            return position.toLong()
//        }
//
//        override fun getItem(position: Int): Any {
//            return data[position]
//        }
//
//        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
//            val view = inflater.inflate(R.layout.row_main, parent, false)
//            val name = view.findViewById<TextView>(R.id.textName)
//            val address = view.findViewById<TextView>(R.id.textdate)
//
//            val currentData = data[position]
//            name.text = currentData.name
//            address.text = currentData.address
//
//            return view
//        }
//    }
//
//    data class Data(
//        val name: String,
//        val address: String,
//        val plastic: String,
//        private class GetTrashDataTask : AsyncTask<String, String, String>()
//    {
//
//        override fun doInBackground(vararg p0: String?): String {
//            var response: String?
//            try {
//                response = URL("http://your-flask-server-url/api/trashdata").readText()
//            } catch (e: Exception) {
//                return "Error: $e"
//            }
//            return response
//        }
//    }
//}
//
//
//    override fun onPostExecute(result: String?) {
//            super.onPostExecute(result)
//            if (result == "Error:") {
//                Toast.makeText(this@TrashActivity, "Could not fetch data from server", Toast.LENGTH_LONG).show()
//            } else {
//                val trashDataArray = Gson().fromJson(result, Array<TrashData>::class.java)
//                listView.adapter = TrashDataAdapter(this@TrashActivity, trashDataArray)
//            }
//        }
//    }














//package com.example.reproadminv2
//
//import android.content.Context
//import android.content.Intent
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.*
//import androidx.cardview.widget.CardView
//
//class TrashActivity : AppCompatActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_trash)
//        supportActionBar?.hide()
//
//        val listView = findViewById<ListView>(R.id.main_listview)
//        val backBtn = findViewById<Button>(R.id.back_trash_btn)
//
//        listView.setClickable(true);
//
//
//        backBtn.setOnClickListener {
//            val intent = Intent(this, HomeActivity::class.java)
//            startActivity(intent)
//        }
//
//        listView.adapter = MyCustomAdapter(this) // this needs to be my custom adapter telling my list what to render
//
//        listView.setOnItemClickListener { parent, view, position, id ->
//            if(position == 0){
//                val intent = Intent(this, UsersActivity::class.java).also{
//                    it.putExtra("name", "Haider Irfan")
//                    it.putExtra("address", "104A Hostel 9")
//                    it.putExtra("plastic", "30")
//                    it.putExtra("glass", "10")
//                    it.putExtra("card", "10")
//                    startActivity(it)
//                }
//            }
//            else if(position == 1){
//                val intent = Intent(this, UsersActivity::class.java).also{
//                    it.putExtra("name", "Hammad Anwar")
//                    it.putExtra("address", "10A Hostel 9")
//                    it.putExtra("plastic", "92")
//                    it.putExtra("glass", "45")
//                    it.putExtra("card", "39")
//                    startActivity(it)
//                }
//            }
//            else if(position == 2){
//                val intent = Intent(this, UsersActivity::class.java).also{
//                    it.putExtra("name", "Ali Hassan Khan")
//                    it.putExtra("address", "50A Hostel 10")
//                    it.putExtra("plastic", "83")
//                    it.putExtra("glass", "47")
//                    it.putExtra("card", "65")
//                    startActivity(it)
//                }
//            }
//            else if(position == 3){
//                val intent = Intent(this, UsersActivity::class.java).also{
//                    it.putExtra("name", "Ahab Sheraz")
//                    it.putExtra("address", "54B Hostel 9")
//                    it.putExtra("plastic", "85")
//                    it.putExtra("glass", "54")
//                    it.putExtra("card", "31")
//                    startActivity(it)
//                }
//            }
//            else if(position == 4){
//                val intent = Intent(this, UsersActivity::class.java).also{
//                    it.putExtra("name", "Shurahbeel")
//                    it.putExtra("address", "79B Hostel 8")
//                    it.putExtra("plastic", "90")
//                    it.putExtra("glass", "50")
//                    it.putExtra("card", "21")
//                    startActivity(it)
//                }
//            }
//            else if(position == 5){
//                val intent = Intent(this, UsersActivity::class.java).also{
//                    it.putExtra("name", "Ahmad Mashhood")
//                    it.putExtra("address", "38A Hostel 9")
//                    it.putExtra("plastic", "88")
//                    it.putExtra("glass", "58")
//                    it.putExtra("card", "33")
//                    startActivity(it)
//                }
//            }
//            else if(position == 6){
//                val intent = Intent(this, UsersActivity::class.java).also{
//                    it.putExtra("name", "Talha Wajid")
//                    it.putExtra("address", "78A Hostel 8")
//                    it.putExtra("plastic", "89")
//                    it.putExtra("glass", "45")
//                    it.putExtra("card", "36")
//                    startActivity(it)
//                }
//            }
//            else if(position == 7){
//                val intent = Intent(this, UsersActivity::class.java).also{
//                    it.putExtra("name", "Maaz Bin Jawad")
//                    it.putExtra("address", "78B Hostel 8")
//                    it.putExtra("plastic", "89")
//                    it.putExtra("glass", "45")
//                    it.putExtra("card", "25")
//                    startActivity(it)
//                }
//            }
//            else if(position == 8){
//                val intent = Intent(this, UsersActivity::class.java).also{
//                    it.putExtra("name", "Talal Tariq")
//                    it.putExtra("address", "68A Hostel 8")
//                    it.putExtra("plastic", "91")
//                    it.putExtra("glass", "41")
//                    it.putExtra("card", "20")
//                    startActivity(it)
//                }
//            }
//            else if(position == 9){
//                val intent = Intent(this, UsersActivity::class.java).also{
//                    it.putExtra("name", "Umer Hamid")
//                    it.putExtra("address", "74B Hostel 10")
//                    it.putExtra("plastic", "84")
//                    it.putExtra("glass", "50")
//                    it.putExtra("card", "37")
//                    startActivity(it)
//                }
//            }
//            else if(position == 10){
//                val intent = Intent(this, UsersActivity::class.java).also{
//                    it.putExtra("name", "Ahmad Jabbar")
//                    it.putExtra("address", "74A Hostel 10")
//                    it.putExtra("plastic", "86")
//                    it.putExtra("glass", "59")
//                    it.putExtra("card", "16")
//                    startActivity(it)
//                }
//            }
//            else if(position == 11){
//                val intent = Intent(this, UsersActivity::class.java).also{
//                    it.putExtra("name", "Ibrahim Asif")
//                    it.putExtra("address", "40B Hostel 8")
//                    it.putExtra("plastic", "97")
//                    it.putExtra("glass", "42")
//                    it.putExtra("card", "25")
//                    startActivity(it)
//                }
//            }
//        }
//    }
//
//    private class MyCustomAdapter(context: Context): BaseAdapter() {
//
//        public val mContext: Context
//
//        public val names = arrayListOf<String>(
//            "HaiderIrfan", "HammadAnwar", "AliHassanKhan", "AhabSheraz", "Shurahbeel", "AhmadMashhood", "TalhaWajid", "MaazBinJawad", "TalalTariq", "UmerHamid", "AhmadJabbar", "IbrahimAsif"
//        )
//
//        private val addresses = arrayListOf<String>(
//            "104A Hostel 9", "10A Hostel 9", "44A Hostel 10", "54B Hostel 9", "79B Hostel 8", "38A Hostel 9", "78A Hostel 8", "78B Hostel 8", "68A Hostel 8", "74B Hostel 10", "74A Hostel 10", "40B Hostel 8"
//        )
//
//        private val plastic = arrayListOf<String>(
//            "1", "92", "83", "85", "90", "88", "89", "91", "84", "85", "86", "97"
//        )
//
//        private val glass = arrayListOf<String>(
//            "1", "45", "47", "54", "50", "58", "45", "41", "50", "46", "59", "42"
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
//            val rowMain = layoutInflater.inflate(R.layout.row_main, viewGroup, false)
//
//            val nameTextView = rowMain.findViewById<TextView>(R.id.textName)
//            nameTextView.text = names.get(position)
//
//            val addressesTextView = rowMain.findViewById<TextView>(R.id.textdate)
//            addressesTextView.text = "Address: ${addresses.get(position)}"
//
//            //val plasticTextView = rowMain.findViewById<TextView>(R.id.textPlastic)
//            //plasticTextView.text = "Number of plastic: ${plastic.get(position)}"
//
//            //val glassTextView = rowMain.findViewById<TextView>(R.id.textGlass)
//            //glassTextView.text = "Number of glass: ${glass.get(position)}"
//
//            return rowMain
////            val textView = TextView(mContext)
////            textView.text = "HERE is my ROW for my LISTVIEW"
////            return textView
//        }
//
//    }
//}