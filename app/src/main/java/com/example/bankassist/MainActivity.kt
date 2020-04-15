package com.example.bankassist
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.RequestFuture
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.util.concurrent.ExecutionException
import java.util.concurrent.TimeUnit


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
        val queue = Volley.newRequestQueue(this)
        val API_URL="https://postman-echo.com/post"
        val request = JsonObjectRequest(Request.Method.POST, API_URL,jsonObject,
            Response.Listener { response ->
                // TODO:Navigate to the Services page if Authenticated
                // Move to next page if response is correct
                switchToSelectService()
            },
            Response.ErrorListener { error ->
                Log.d("Hello","That didn't work! ${error}")
                showError("Please check customer Id and password")
            })
        queue.add(request)
        println("2 is executed")
    }

    fun NewPost(){
        val queue = Volley.newRequestQueue(this)
        var myresp = false
        val params = mapOf("customerID" to "123456", "password" to "password")
        val jsonObject = JSONObject(params)
        val future:RequestFuture<JSONObject> = RequestFuture.newFuture();
        val API_URL="https://postman-echo.com/post"
        val  request:JsonObjectRequest = JsonObjectRequest(Request.Method.POST, API_URL, jsonObject, future, future)
        queue.add(request);

        try {
            val response: JSONObject = future.get(20, TimeUnit.SECONDS)
            myresp = true
            Log.d("Hello", "Response is: ${response}")
        } catch (error:InterruptedException) {
            Log.d("Hello","That didn't work! ${error}")
        } catch (error: ExecutionException) {
            Log.d("Hello","That didn't work! ${error}")
        }
    }



}
