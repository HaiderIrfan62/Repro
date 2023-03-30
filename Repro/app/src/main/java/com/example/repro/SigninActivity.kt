package com.example.repro

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import org.json.JSONObject
import android.os.Handler;

class SigninActivity : AppCompatActivity() {
    private val inputName: EditText? = null
    private val inputPassword: EditText? = null
    private val nextBtn: Button? = null
    private var isValid = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)
        supportActionBar?.hide()

        val back_btn = findViewById<Button>(R.id.back_btn)
        val inputName = findViewById<EditText>(R.id.inputName)
        val inputPassword = findViewById<EditText>(R.id.inputPassword)
        val nextBtn = findViewById<Button>(R.id.next_btn)

        var volleyRequestQueue: RequestQueue? = null
        var serverAPIURL: String = "https://haiderirfan.pythonanywhere.com/signin_users"
        var TAG = "Login into TABLE"

        fun SendSigninDataToServer(
            name: String,
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
                            var scanned = responseObj.getInt("scanned")
                            var earned = responseObj.getInt("earned")
                            var plastic = responseObj.getInt("plastic")
                            var aluminum = responseObj.getInt("aluminum")
                            var cardboard = responseObj.getInt("cardboard")

                            //{
                            //}

                            if(code == 62) {
                                //Toast.makeText(this, "Marked", Toast.LENGTH_LONG).show()
                                val intent = Intent(this, HomeActivity::class.java).also{
                                    it.putExtra("name", name)
                                    it.putExtra("scanned", scanned)
                                    it.putExtra("earned", earned)
                                    it.putExtra("plastic", plastic)
                                    it.putExtra("aluminum", aluminum)
                                    it.putExtra("cardboard", cardboard)
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

        val TextViewSignup = findViewById<TextView>(R.id.TextViewSignup)

        TextViewSignup.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }

        nextBtn.setOnClickListener {
            SendSigninDataToServer(inputName.text.toString(), inputPassword.text.toString())
        }
    }

    private fun checkFields() {
        if (inputPassword?.text.isNullOrEmpty() && inputName?.text.isNullOrEmpty()) {
            isValid = true
            nextBtn?.isEnabled = isValid
        } else {
            isValid = false
            nextBtn?.isEnabled = isValid
        }
    }
}