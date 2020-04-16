package com.example.bankassist
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*



class MainActivity : AppCompatActivity() {
    // customer number entered by user
    val customerIDInput = findViewById<EditText>(R.id.customerIDInput)
    // password entered by user
    val passwordInput = findViewById<EditText>(R.id.passwordInput)

    val IDVal=customerIDInput.text.toString()
    val passwordVal=passwordInput.text.toString()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // check if customer ID exists in database and then password
        loginButton.setOnClickListener {
            isCustomerAuthenticated(IDVal,passwordVal)
        }
    }

    fun switchToSelectService(){
        val selectServiceScreen = Intent(this , SelectService::class.java)
        startActivity(selectServiceScreen)
    }


    // login details api call
    fun isCustomerAuthenticated(IDVal: String,passwordVal:String) {
        val queue = Volley.newRequestQueue(this)
        val API_URL="http://34.87.233.248:5000/queue?queue_data={\"name\": \"Wow\", \"customer_id\": \"31111\", \"service\": \"loans\"}"
        val request = StringRequest(Request.Method.POST, API_URL,
            Response.Listener { response ->
                // TODO:Navigate to the Services page if Authenticated
                // TODO : if password or ID does not exist show error messaage
                // Move to next page if response is correct
                switchToSelectService()
                Log.d("Hello","1 is executed ${response}")
            },
            Response.ErrorListener { error ->
                Log.d("Hello","That didn't work! ${error}")
            })
        queue.add(request)
        println("2 is executed")
    }


    fun showError(Message:String){
        //Show error message on view
        val alert:AlertDialog.Builder = AlertDialog.Builder(this)
        alert.setTitle("Login Issue")
        alert.setMessage(Message)
        alert.setPositiveButton("OK",{dialog: DialogInterface?, which: Int ->
            finish()
        })
    }

    public fun getCustomerID():String{
        return IDVal
    }

}
