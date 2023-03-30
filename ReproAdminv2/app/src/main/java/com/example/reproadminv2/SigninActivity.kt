package com.example.reproadminv2

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

class SigninActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)
        supportActionBar?.hide()

        val back_btn = findViewById<Button>(R.id.back_btn)
        val inputName = findViewById<EditText>(R.id.inputName)
        val inputPassword = findViewById<EditText>(R.id.inputPassword)
        val nextBtn = findViewById<Button>(R.id.next_btn)

        var volleyRequestQueue: RequestQueue? = null
        var dialog: ProgressDialog? = null
        var serverAPIURL: String = "https://haiderirfan.pythonanywhere.com/signin_riders"
        var TAG = "Login into TABLE"

        fun SendSigninDataToServer(
            name: String,
            password: String,
        ) {
            volleyRequestQueue = Volley.newRequestQueue(this)
            dialog = ProgressDialog.show(this, "", "Please wait...", true);
            val parameters: MutableMap<String, String> = HashMap()
            // Add your parameters in HashMap
            parameters.put("name", name)
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

                            //{
                            //}

                            if(code == 62) {
                                //Toast.makeText(this, "Marked", Toast.LENGTH_LONG).show()
                                val intent = Intent(this, HomeActivity::class.java)
                                startActivity(intent)

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
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }


        back_btn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

//        nextBtn.isEnabled = isValid
//
//        inputName.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { checkFields() }
//            override fun afterTextChanged(s: Editable?) {}
//        })
//
//        inputPassword.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { checkFields() }
//            override fun afterTextChanged(s: Editable?) {}
//        })

        nextBtn.setOnClickListener {
            SendSigninDataToServer(inputName.text.toString(), inputPassword.text.toString())
        }
    }
}