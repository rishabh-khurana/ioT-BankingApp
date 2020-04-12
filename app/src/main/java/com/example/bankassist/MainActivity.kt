package com.example.bankassist
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.json.JSONObject


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // customer details from database for testing
        val cutomerNumber = 123456.toString()
        val pass:String =  "password".toString()

        // customer number entered by user
        val customerIDInput = findViewById<EditText>(R.id.customerIDInput)
        // password entered by user
        val passwordInput = findViewById<EditText>(R.id.passwordInput)
        // TODO : check if customer ID exists in database and then password
        // TODO : if password or ID does not exist show error messaage
        val IDVal=customerIDInput.text.toString()
        val passwordVal=passwordInput.text.toString()

        loginButton.setOnClickListener {
            isCustomerAuthenticated()
            if(true){
                // Navigate to the Services page
                println("Navigate to next View")
                switchToSelectService()
            }else{
                showError("Please check if you have entered the correct ID and password")
            }
        }
    }

    fun getLoginDetails() {
        val queue = Volley.newRequestQueue(this)
        val API_URL="http://dummy.restapiexample.com/api/v1/employees"
        val stringRequest = StringRequest(Request.Method.GET, API_URL,
            Response.Listener<String> { response ->
                // Print my response
                Log.d("Hello","Response is: ${response}")
            },
            Response.ErrorListener {  Log.d("Hello","That didn't work!") })
        queue.add(stringRequest)
    }

    fun showError(Message:String){
        //Show error message on view
        println(Message)
    }

    fun switchToSelectService(){
        val selectServiceScreen = Intent(this , SelectService::class.java)
        startActivity(selectServiceScreen)
    }

    fun isCustomerAuthenticated() {
        val params = mapOf("customerID" to "123456", "password" to "password")
        val jsonObject = JSONObject(params)
        var myresp:Boolean = false
        val queue = Volley.newRequestQueue(this)
        val API_URL="https://postman-echo.com/post"

        CoroutineScope(IO).launch {

            val result1 = async {
                val request = JsonObjectRequest(Request.Method.POST, API_URL,jsonObject,
                    Response.Listener { response ->
                        // Print my response
                        Log.d("Hello","Response is: ${response}")
                        myresp = true
                        println("1 is executed")
                    },
                    Response.ErrorListener {  Log.d("Hello","That didn't work!") })
                queue.add(request)

            }.await()
            println("2 is executed")

        }
    }



}
