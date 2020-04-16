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
            // isCustomerAuthenticated(IDVal,passwordVal)
            switchToSelectService(IDVal)
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
        val API_URL="http://34.87.233.248:5000/auth?\"customer_id\"=\"${IDVal}\"&\"password\"=\"${passwordVal}\""
        val request = StringRequest(Request.Method.POST, API_URL,
            Response.Listener { response ->
                // TODO:Navigate to the Services page if Authenticated
                // TODO : if password or ID does not exist show error messaage
                // Move to next page if response is correct
                switchToSelectService(IDVal)
                Log.d("Hello","1 is executed ${response}")
            },
            Response.ErrorListener { error ->
                // TODO:show invalid login message please try again
                // TODO:show invalid login message please try again
                Log.d("Hello","That didn't work! ${error}")
            })
        queue.add(request)
        println("2 is executed")
    }
}
