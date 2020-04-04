package com.example.bankassist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_select_service.*

class SelectService : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_service)

        fun getTicketNumber(urlVal:String):String{
            // TODO: DO the API call here
            val queueNumber:String = "1F".toString()
            val ticketNumber:String = "135".toString()

            return queueNumber+ticketNumber
        }

        formButton.setOnClickListener {
            // TODO: Switch on bluetooth
            // TODO: Integrate Bluecats SDK
            // TODO: Send signals to beacon
            // TODO: Once Signal is recieved make an API call to get queue number and ticket number
            println(getTicketNumber("UrlValue"))
        }
    }
}
