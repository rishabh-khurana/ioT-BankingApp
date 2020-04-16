package com.example.bankassist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.RadioButton
import android.widget.RadioGroup
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_feedback_page.*

class FeedbackPage : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feedback_page)

        // load group
        val FEEDBACK_GROUP = findViewById<RadioGroup>(R.id.feedbackGroup)

        // get customer ID from main
        val CUSTOMER_ID = MainActivity().getCustomerID()

        closeAppButton.setOnClickListener {
            // get selected radio button from radioGroup
            val selectedId:Int = FEEDBACK_GROUP.checkedRadioButtonId

            // find the radiobutton by returned id
            val radioButton = findViewById<RadioButton>(selectedId)

            // get user selection
            val FEEDBACK_INPUT:String = radioButton.getText().toString()

            // Post User Input
            PostFeedback(CUSTOMER_ID,FEEDBACK_INPUT)
            // go to thankyou page
        }
    }

    fun PostFeedback(custID:String,feedbackVal:String) {
        val queue = Volley.newRequestQueue(this)
        // API_URL="http://34.87.233.248:5000/queue?feedback_data={ \"customer_id\": \"31111\", \"feedback\": \"Good\"}"
        val API_URL="http://34.87.233.248:5000/queue?feedback_data={ \"customer_id\": \"${custID}\", \"feedback\": \"${feedbackVal}\"}"
        val request = StringRequest(
            Request.Method.POST, API_URL,
            Response.Listener { response ->
                // TODO:Navigate to the Thankyou Page
                // Move to next page if response is correct
                Log.d("Hello","1 is executed ${response}")
            },
            Response.ErrorListener { error ->
                Log.d("Hello","That didn't work! ${error}")
            })
        queue.add(request)
        println("2 is executed")
    }
}
