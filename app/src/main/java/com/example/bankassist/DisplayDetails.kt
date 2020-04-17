package com.example.bankassist
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_display_details.*

class DisplayDetails : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_details)

        val queueNumber = findViewById<TextView>(R.id.queueNumberDisplay)
        val ticketNumber = findViewById<TextView>(R.id.ticketNumberDisplay)

        // get data from select service activity
        val i:Intent = getIntent()
        val CUSTOMER_ID:String = i.getStringExtra("customerID")
        val TICKET_NUMBER:String = i.getStringExtra("ticketNumber")
        val QUEUE_NUMBER:String = i.getStringExtra("queueNumber")

        queueNumber.text = QUEUE_NUMBER
        ticketNumber.text = TICKET_NUMBER


        // Assign the values after API call
        DisplayDetails()

        OkButton.setOnClickListener {
            // Move to feedback page
            switchToFeedbackPage(CUSTOMER_ID)
        }
    }


    fun switchToFeedbackPage(CustID:String){
        val selectFeedbackPage = Intent(this , FeedbackPage::class.java)
        selectFeedbackPage.putExtra("customerID",CustID)
        startActivity(selectFeedbackPage)
    }

}
