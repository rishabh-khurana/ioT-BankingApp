package com.example.bankassist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_display_details.*

class DisplayDetails : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_details)
        // Assign the values after API call
        DisplayDetails()

        OkButton.setOnClickListener {
            // Move to feedback page
        }
    }

    fun getTicketNumber(urlVal:String):Array<String>{
        // TODO: DO the API call here
        val queueNumber:String = "1F".toString()
        val ticketNumber:String = "135".toString()
        val details = arrayOf(queueNumber,ticketNumber)
        return details
    }

    fun DisplayDetails(){
        var details = getTicketNumber("UrlVal")
        val queueNumber = findViewById<TextView>(R.id.queueNumberDisplay)
        val ticketNumber = findViewById<TextView>(R.id.ticketNumberDisplay)
        queueNumber.text=details[1]
        ticketNumber.text=details[0]
    }

}
