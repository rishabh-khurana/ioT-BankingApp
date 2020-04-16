package com.example.bankassist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_display_details.*

class DisplayDetails : AppCompatActivity() {

    val queueNumber = findViewById<TextView>(R.id.queueNumberDisplay)
    val ticketNumber = findViewById<TextView>(R.id.ticketNumberDisplay)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_details)
        // get details from previous activity
        // display details
        queueNumber.text=SelectService().getKioskNumber()
        ticketNumber.text=SelectService().getTicketNumnber()



        OkButton.setOnClickListener {
            // Move to feedback page
            switchToFeedbackPage()
        }
    }


    fun switchToFeedbackPage(){
        val selectFeedbackPage = Intent(this , FeedbackPage::class.java)
        startActivity(selectFeedbackPage)
    }

}
