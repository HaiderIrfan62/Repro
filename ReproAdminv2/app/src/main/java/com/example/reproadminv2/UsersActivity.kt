package com.example.reproadminv2

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import org.json.JSONObject

class UsersActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users)
        supportActionBar?.hide()

        val backBtn = findViewById<Button>(R.id.back_users_btn)
        val collect = findViewById<Button>(R.id.collect_btn)
        val name = findViewById<TextView>(R.id.UserName)
        val address = findViewById<TextView>(R.id.UserAdd)
        val plastic = findViewById<TextView>(R.id.user_plastic)
        val glass = findViewById<TextView>(R.id.user_glass)
        val card = findViewById<TextView>(R.id.user_card)

        val bundle = intent.extras
        name.text = bundle!!.getString("name")
        address.text = bundle!!.getString("address")
        plastic.text = bundle!!.getString("plastic")
        glass.text = bundle!!.getString("aluminum")
        card.text = bundle!!.getString("card")

        val username = bundle!!.getString("name")

        var volleyRequestQueue: RequestQueue? = null
        var dialog: ProgressDialog? = null
        var serverAPIURL: String = "https://haiderirfan.pythonanywhere.com/collect_riders"
        var TAG = "Login into TABLE"

        fun CollectTrash(
            name: String,
        ) {
            volleyRequestQueue = Volley.newRequestQueue(this)
            dialog = ProgressDialog.show(this, "", "Please wait...", true);
            val parameters: MutableMap<String, String> = HashMap()
            // Add your parameters in HashMap
            parameters.put("name", name)

            val gson = Gson()
            val jsonBody = JSONObject(gson.toJson(parameters))

            val mQueue = Volley.newRequestQueue(applicationContext)

            val jsonObjectRequest: JsonObjectRequest =
                object : JsonObjectRequest(serverAPIURL, jsonBody,
                    Response.Listener{ response ->
                        Log.e(TAG, "response: $response")
                        dialog?.dismiss()
                        try {
                            val responseObj = JSONObject(response.toString())
                            val code = responseObj.getInt("code")

                            //{
                            //}

                            if(code == 62) {
                                val intent = Intent(this, HomeActivity::class.java)
                                startActivity(intent)
                                Toast.makeText(this, "Collected", Toast.LENGTH_SHORT).show()
                            }
                            else{
                                Toast.makeText(this, "Error", Toast.LENGTH_LONG).show()
                            }
                        } catch (e: Exception) { // caught while parsing the response
                            Log.e(TAG, "problem occurred")
                            e.printStackTrace()
                        }

                    },
                    Response.ErrorListener { error -> Log.e("TAG", error.message, error) }) {
                    //no semicolon or coma
                    @Throws(AuthFailureError::class)
                    override fun getHeaders(): Map<String, String> {
                        val params: MutableMap<String, String> = HashMap()
                        params["Content-Type"] = "application/json"
                        return params
                    }
                }
            mQueue.add(jsonObjectRequest)
        }

        backBtn.setOnClickListener {
            val intent = Intent(this, TrashActivity::class.java)
            startActivity(intent)
        }

        collect.setOnClickListener {
            CollectTrash(username.toString())
        }
    }
}