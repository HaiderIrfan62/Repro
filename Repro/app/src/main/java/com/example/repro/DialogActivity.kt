package com.example.repro

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Button
import android.widget.RadioButton
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import org.json.JSONObject

class DialogActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dialog)
        supportActionBar?.hide()

        val aluminum_rb = findViewById<RadioButton>(R.id.aluminum_rb)
        val plastic_rb = findViewById<RadioButton>(R.id.plastic_rb)
        val cardboard_rb = findViewById<RadioButton>(R.id.cardboard_rb)
        val other_rb = findViewById<RadioButton>(R.id.other_rb)
        val submit = findViewById<Button>(R.id.submit)
        val cancel = findViewById<Button>(R.id.cancelButton)

        val bundle = intent.extras
        val name = bundle!!.getString("name")
        val index: Int= bundle!!.getInt("index")
        var Object: String= bundle!!.getString("object").toString()
        var scannedTokens = bundle!!.getInt("scanned")
        var earnedTokens = bundle!!.getInt("earned")
        var plastic = bundle!!.getInt("plastic")
        var aluminum = bundle!!.getInt("aluminum")
        var cardboard = bundle!!.getInt("cardboard")

        var volleyRequestQueue: RequestQueue? = null
        var serverAPIURL: String = "https://haiderirfan.pythonanywhere.com/update_users"
        var TAG = "Login into TABLE"

        fun UpdateDataInServer(
            username: String,
            scanned: Int,
            plastic1: Int,
            aluminum1: Int,
            cardboard1: Int,
        ) {
            volleyRequestQueue = Volley.newRequestQueue(this)
            var dialogCancelled = false
            val dialog = ProgressDialog.show(this, "", "Please wait...", true)
            dialog.setOnDismissListener {
                dialogCancelled = true
            }
            Handler().postDelayed({
                if (!dialogCancelled) {
                    dialog.dismiss()
                    Toast.makeText(applicationContext, "Server Error", Toast.LENGTH_SHORT).show()
                }
            }, 10000L) // 10 seconds
            val parameters: MutableMap<String, String> = HashMap()
            // Add your parameters in HashMap
            parameters.put("name", username)
            parameters.put("scanned", scanned.toString())
            parameters.put("plastic", plastic1.toString())
            parameters.put("aluminum", aluminum1.toString())
            parameters.put("cardboard", cardboard1.toString())

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

                            if(code == 62) {
                                Toast.makeText(this, "Added", Toast.LENGTH_SHORT).show()
                                val intent = Intent(this, HomeActivity::class.java).also {
                                    it.putExtra("name", name)
                                    it.putExtra("scanned", scanned)
                                    it.putExtra("earned", earnedTokens)
                                    it.putExtra("plastic", plastic1)
                                    it.putExtra("aluminum", aluminum1)
                                    it.putExtra("cardboard", cardboard1)
                                    startActivity(it)
                                }
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

        cancel.setOnClickListener {
            val intent = Intent(this, ConfirmActivity::class.java).also {
                it.putExtra("name", name)
                it.putExtra("scanned", scannedTokens)
                it.putExtra("earned", earnedTokens)
                it.putExtra("plastic", plastic)
                it.putExtra("aluminum", aluminum)
                it.putExtra("cardboard", cardboard)
                startActivity(it)
            }
        }

        submit.setOnClickListener {
            if(aluminum_rb.isChecked){
                scannedTokens += 10
                aluminum += 1
                UpdateDataInServer(name.toString(), scannedTokens, plastic, aluminum, cardboard)
            }
            else if(plastic_rb.isChecked){
                scannedTokens += 5
                plastic += 1
                UpdateDataInServer(name.toString(), scannedTokens, plastic, aluminum, cardboard)
            }
            else if(cardboard_rb.isChecked){
                scannedTokens += 1
                cardboard += 1
                UpdateDataInServer(name.toString(), scannedTokens, plastic, aluminum, cardboard)
            }
            else if(other_rb.isChecked){
                Toast.makeText(this, "Invalid Category", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, ConfirmActivity::class.java).also {
                    it.putExtra("name", name)
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
}