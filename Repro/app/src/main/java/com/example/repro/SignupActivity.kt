package com.example.repro

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap
import okhttp3.Response as Response1

class SignupActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        supportActionBar?.hide()

        val back_btn = findViewById<Button>(R.id.back_signup_btn)
        val inputName = findViewById<EditText>(R.id.inputNameSignup)
        val inputEmail = findViewById<EditText>(R.id.inputEmailSignup)
        val inputAddress = findViewById<EditText>(R.id.inputAddressSignup)
        val inputPassword = findViewById<EditText>(R.id.inputPasswordSignup)
        val nextBtn = findViewById<Button>(R.id.next_btn_signup)

        var volleyRequestQueue: RequestQueue? = null
        var serverAPIURL: String = "https://haiderirfan.pythonanywhere.com/signup_users"
        var TAG = "Insert into TABLE"

        fun SendSignupDataToServer(
            name: String,
            email: String,
            address: String,
            password: String,
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
            parameters.put("name", name)
            parameters.put("email", email)
            parameters.put("address", address)
            parameters.put("password", password)

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
                                Toast.makeText(this, "Marked", Toast.LENGTH_LONG).show()
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

        back_btn.setOnClickListener {
            val intent = Intent(this, SigninActivity::class.java)
            startActivity(intent)
        }

        nextBtn.setOnClickListener {
            SendSignupDataToServer(inputName.text.toString(), inputEmail.text.toString(), inputAddress.text.toString(), inputPassword.text.toString())
            val intent = Intent(this, SigninActivity::class.java)
            startActivity(intent)
        }
    }
}