package com.example.bankassist
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // customer number entered by user
        val customerIDInput = findViewById<EditText>(R.id.customerIDInput)
        // password entered by user
        val passwordInput = findViewById<EditText>(R.id.passwordInput)

        loginButton.setOnClickListener {
            val IDVal=customerIDInput.text.toString()
            val passwordVal=passwordInput.text.toString()
            // check if customer ID exists in database and then password
            // TODO:TEMP
            isCustomerAuthenticated(IDVal,passwordVal)
            //switchToSelectService(IDVal)
        }
    }


    fun switchToSelectService(customer_ID:String){
        val selectServiceScreen = Intent(this , SelectService::class.java)
        selectServiceScreen.putExtra("customer_ID",customer_ID)
        startActivity(selectServiceScreen)
    }

    fun isCustomerAuthenticated(IDVal: String,passwordVal:String) {
        val queue = Volley.newRequestQueue(this)
        // format is of type http://34.87.233.248:5000/auth?"customer_id"="12345"&"password"="Pass123"
        val API_URL="http://34.87.233.248:5000/auth?password=${passwordVal}&customer_id=${IDVal}"
        val request = StringRequest(Request.Method.POST, API_URL,
            Response.Listener { response ->
                // Navigate to the Services page if Authenticated
                val mapper = jacksonObjectMapper()

                val details:Status = mapper.readValue(response)

                val status = details.statusOK
                // Move to next page if response is correct
                if (status == "Access can be granted"){
                    switchToSelectService(IDVal)
                }
                else{
                    // TODO : if password or ID does not exist show error messaage
                }
                Log.d("Hello","1 is executed ${response}")
            },
            Response.ErrorListener { error ->
                // TODO:show network error
                Log.d("Hello","That didn't work! ${error}")
            })
        queue.add(request)
        println("2 is executed")
    }
}

data class Status(
    var statusOK:String
)
